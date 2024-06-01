package galeria.usuarios;

public class ManejoSesion {
    private static Empleado empleadoActual;
    private static CompradorPropietario compradorPropietarioActual;
    private static UsuariosRegistrados usuariosRegistrados;

    public static void loginEmpleado(String username, String password) {
        if (FileUtils.verifyUser(username, password)) {
            for (Empleado empleado : usuariosRegistrados.getUsuariosEnPrograma()) {
                if (empleado.getUsername().equals(username)) {
                    empleadoActual = empleado;
                    return;
                }
            }
        } else {
            // Handle login failure
        }
    }

    public static void loginCompradorPropietario(String username, String password) {
        if (FileUtils.verifyUser(username, password)) {
            for (CompradorPropietario comprador : usuariosRegistrados.getCompradoresEnPrograma()) {
                if (comprador.getUsername().equals(username)) {
                    compradorPropietarioActual = comprador;
                    return;
                }
            }
        } else {
            // Handle login failure
        }
    }

    public static Empleado getEmpleadoActual() {
        return empleadoActual;
    }

    public static CompradorPropietario getCompradorPropietarioActual() {
        return compradorPropietarioActual;
    }

    public static void logout() {
        empleadoActual = null;
        compradorPropietarioActual = null;
    }

    public static void setUsuariosRegistrados(UsuariosRegistrados usuarios) {
        usuariosRegistrados = usuarios;
    }

    public static UsuariosRegistrados getUsuariosRegistrados() {
        return usuariosRegistrados;
    }
}
