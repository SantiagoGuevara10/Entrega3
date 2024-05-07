package consolas;

import galeria.usuarios.*;
import galeria.pieza.Pieza;
import galeria.inventarios.InventarioGeneral;
import subasta.Oferta;
import java.util.LinkedList;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

public class ConsolaAdministrador extends ConsolaBasica {
    private Administrador administrador;
    private InventarioGeneral inventario;

    public ConsolaAdministrador(InventarioGeneral inventario) {
        this.inventario = inventario;
    }
    
    protected void mostrarMenuPrincipal() throws IOException {
        System.out.println("Bienvenido a la consola de Administración, ");
        mostrarOpcionesAdministrativas();
    }

    private void mostrarOpcionesAdministrativas() {
        System.out.println("Opciones Administrativas:");
        System.out.println("1. Agregar Pieza");
        System.out.println("2. Devolver Pieza");
        System.out.println("3. Verificar Usuario");
        System.out.println("4. Registrar Oferta");
        System.out.println("5. Salir");
        int opcion = pedirEnteroAlUsuario("Seleccione una opción:");
        switch (opcion) {
            case 1:
                agregarPieza();
                break;
            case 2:
                devolverPieza();
                break;
            case 3:
                verificarUsuario();
                break;
            case 4:
                registrarOferta();
                break;
            case 5:
                System.out.println("Saliendo...");
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida, intente de nuevo.");
                mostrarOpcionesAdministrativas();
                break;
        }
    }

    private void agregarPieza() {
        String idPieza = pedirCadenaAlUsuario("Ingrese el ID de la pieza:");
        Pieza nuevaPieza = new Pieza(idPieza, "Default Title", 2020, "Unknown", "bodega", false, false, new LinkedList<>(), 0.0, 0, 0, new Date(), true, "No description", null);
        inventario.addInventarioBodega(idPieza, nuevaPieza);
        System.out.println("Pieza con ID " + idPieza + " agregada exitosamente al inventario.");
    }

    private void devolverPieza() {
        String idPieza = pedirCadenaAlUsuario("Ingrese el ID de la pieza:");
        Pieza pieza = inventario.getPiezaInventarioBodega(idPieza);
        if (pieza == null) pieza = inventario.getPiezaInventarioExhibido(idPieza);
        if (pieza == null) pieza = inventario.getPiezaInventarioPasado(idPieza);
        System.out.println("Pieza devuelta y actualizada en el inventario.");
    }

    private void verificarUsuario() {
        String idUsuario = pedirCadenaAlUsuario("Ingrese el ID del usuario a verificar:");
        
        CompradorPropietario usuario = buscarCompradorPorId(idUsuario); 
        administrador.verificarUsuario(usuario);
        System.out.println("Usuario verificado correctamente.");
    }

    private void registrarOferta() {
        String idPieza = pedirCadenaAlUsuario("Ingrese el ID de la pieza:");
        Pieza pieza = inventario.getPiezaInventarioBodega(idPieza); 
        if (pieza == null) pieza = inventario.getPiezaInventarioExhibido(idPieza);
        CompradorPropietario comprador = buscarCompradorPorId(pedirCadenaAlUsuario("Ingrese el ID del comprador:"));
        int dinero = pedirEnteroAlUsuario("Ingrese el monto de la oferta:");
        Oferta oferta = new Oferta(comprador, pieza, dinero);
        System.out.println("Oferta registrada exitosamente.");
    }

    
    private CompradorPropietario buscarCompradorPorId(String id) {
        
        return new CompradorPropietario(id, "Nombre", "username", "passwordHash", "contactInfo", 0.0, true, new LinkedList<>(), new LinkedList<>());
    }
}