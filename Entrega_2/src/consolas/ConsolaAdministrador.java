package consolas;

import galeria.usuarios.*;
import galeria.pieza.Pieza;
import galeria.inventarios.InventarioGeneral;
import galeria.inventarios.PiezaEscultura;
import galeria.inventarios.PiezaFotografia;
import galeria.inventarios.PiezaPintura;
import galeria.inventarios.PiezaVideo;
import subasta.Oferta;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

public class ConsolaAdministrador extends ConsolaBasica {
    private Administrador administrador;
    private InventarioGeneral inventario;
    private UsuariosRegistrados users;
    private File archivo;
    private File archivo2;

    public ConsolaAdministrador(InventarioGeneral inventario,UsuariosRegistrados users, File archivo, File archivo2) {
        this.inventario = inventario;
        this.users=users;
        this.archivo = archivo;
        this.archivo2 = archivo2;
    }
    
    protected void mostrarMenuPrincipal() throws IOException {
        System.out.println("Bienvenido a la consola de Administración, ");
        mostrarOpcionesAdministrativas();
    }

    private void mostrarOpcionesAdministrativas() throws IOException {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nOpciones Administrativas:");
            System.out.println("1. Agregar Pieza");
            System.out.println("2. Devolver Pieza");
            System.out.println("3. Verificar Usuario");
            System.out.println("4. Registrar Oferta");
            System.out.println("5. Ver Historia de Compras");
            System.out.println("6. Calcular Valor de Colección");
            System.out.println("7. Salir");

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
                    verHistoriaCompras();
                    break;
                case 6:
                    calcularValorColeccion();
                    break;
                case 7:
                    System.out.println("Saliendo al menú principal...");
                    continuar = false; 
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
                    break;
            }
        }
    }

    private void agregarPieza() throws IOException {
    	String piezaID = "";
    	Pieza pieza = null;
        String idComprador = pedirCadenaAlUsuario("Ingrese el ID del comprador");
        for(int i=0; i<users.getCompradoresEnPrograma().size();i++) {
        	CompradorPropietario comprador = users.getCompradoresEnPrograma().get(i);
        	if(comprador.getIdUsuario().equals(idComprador)){
        		piezaID = pedirCadenaAlUsuario("Ingresar el ID de la pieza que ingresará");
        		String descripcion = comprador.getPieza(i).getDescripcion();
        		List<Pieza> piezas = comprador.getPiezas();
        		for(int j=0; j<piezas.size();j++) {
        			if(piezas.get(j).getIdPieza().equals(piezaID)) {
        				if(descripcion.equals("Escultura")) {
                        	pieza = (PiezaEscultura) piezas.get(j);
                		}
                		else if (descripcion.equals("Fotografia")) {
                        	pieza = (PiezaFotografia) piezas.get(j);
                		}
                		else if (descripcion.equals("Pintura")) {
                        	pieza = (PiezaPintura)piezas.get(j);
                		}
                		else if (descripcion.equals("Video")) {
                         	pieza = (PiezaVideo)piezas.get(j);
                		}
        				
        			}
        		}
        		
                	
                	
		        		int valor = pedirEnteroAlUsuario("Indique en donde va a colocar: 1.Bodega 2. Exhibicion");
		        		if(valor==1) {
		        			inventario.addInventarioBodega(piezaID, pieza);
		        			
		        		}
		        		else {
		        			inventario.addInventarioExhibido(piezaID, pieza);
		        		}
		        		comprador.getPiezas().remove(i);
		        		
		        		break;
        	}
        	
        }
        
		System.out.println("Pieza con ID " + piezaID + " agregada exitosamente al inventario.");
		users.guardarUsuarios(archivo);
		inventario.guardarUsuarios(archivo2);
    
    }

    private void devolverPieza() throws IOException {
        String idPieza = pedirCadenaAlUsuario("Ingrese el ID de la pieza");
        String idUsuario = pedirCadenaAlUsuario("Ingrese el ID de la persona a la cual se le devuelve la pieza");
        Pieza pieza = null;
        for (Entry<String, Pieza> entry : inventario.getInventarioBodega().entrySet()) {
            String key = entry.getKey();
            Pieza value = entry.getValue();
            if(key.equals(idPieza)) {
            	pieza = value;
            	inventario.getInventarioBodega().remove(key);
            }}
        for (Entry<String, Pieza> entry : inventario.getInventarioExhibido().entrySet()) {
            String key = entry.getKey();
            Pieza value = entry.getValue();
            if(key.equals(idPieza)) {
            	pieza = value;
            	inventario.getInventarioExhibido().remove(key);
            	
            }}
        for(int i=0; i< users.getCompradoresEnPrograma().size();i++) {
        	CompradorPropietario comprador = users.getCompradoresEnPrograma().get(i);
        	if(comprador.getIdUsuario().equals(idUsuario)) {
        		comprador.agregarPieza(pieza);
        	}
        }
        
        
        System.out.println("Pieza devuelta y actualizada en el inventario.");
        users.guardarUsuarios(archivo);
		inventario.guardarUsuarios(archivo2);
    }

    private void verificarUsuario() throws IOException {
        String idUsuario = pedirCadenaAlUsuario("Ingrese el ID del usuario a verificar:");
        
        CompradorPropietario usuario = buscarCompradorPorId(idUsuario); 
        administrador.verificarUsuario(usuario);
        System.out.println("Usuario verificado correctamente.");
        users.guardarUsuarios(archivo);
		inventario.guardarUsuarios(archivo2);
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
    private void verHistoriaCompras() {
        String idUsuario = pedirCadenaAlUsuario("Ingrese el ID del comprador:");
        CompradorPropietario comprador = buscarCompradorPorId(idUsuario);
        String historial = administrador.verHistoriaCompras(comprador);
        System.out.println("Historia de Compras:\n" + historial);
    }

    private void calcularValorColeccion() {
        String idUsuario = pedirCadenaAlUsuario("Ingrese el ID del comprador:");
        CompradorPropietario comprador = buscarCompradorPorId(idUsuario);
        double valorTotal = administrador.calcularValorColeccion(comprador);
        System.out.println("El valor total de la colección del comprador es: $" + valorTotal);
    }
    
    public void crearUsuario(UsuariosRegistrados users) {
        Random random = new Random();
        List<Integer> numeros = new LinkedList<>();
        for(int i =0; i<users.getUsuariosEnPrograma().size();i++) {
        	String num = users.getUsuariosEnPrograma().get(i).getIdEmpleado();
        	int numero = Integer.parseInt(num);
        	numeros.add(numero);
        	
        }
        
        for(int i =0; i<users.getCompradoresEnPrograma().size();i++) {
        	String num = users.getCompradoresEnPrograma().get(i).getIdUsuario();
        	int numero = Integer.parseInt(num);
        	numeros.add(numero);
        	
        }
        
        int numeroAleatorio;
        do {
            numeroAleatorio = random.nextInt(1000); // Generar un número aleatorio entre 0 y 9 (por ejemplo)
        } while (numeros.contains(numeroAleatorio));
        
    	String idEmpleado = String.valueOf(numeroAleatorio);
    	String nombre = pedirCadenaAlUsuario("Ingrese su nombre completo: ");
    	String username = pedirCadenaAlUsuario("Ingrese un usuario de su preferencia");
    	String passwordHash = pedirCadenaAlUsuario("Ingrese una clave de su preferencia");
    	String role = "Administrador";
    	Administrador admin = new Administrador(idEmpleado, nombre, username, passwordHash, role);
    	users.addUsuario(admin);
    	
            
    	
    }

    
    private CompradorPropietario buscarCompradorPorId(String id) {
        for (CompradorPropietario comprador : users.getCompradoresEnPrograma()) {
            if (comprador.getIdUsuario().equals(id)) {
                return comprador;
            }
        }
        System.out.println("Comprador no encontrado.");
        return null;
    }
    
    public void autenticarUsuario(String tipoUsuario, BufferedReader reader) throws IOException {
        System.out.print("Ingrese su nombre de usuario: ");
        String username = reader.readLine();
        System.out.print("Ingrese su contraseña: ");
        String password = reader.readLine();

        boolean autenticado = false;

        
        for (Empleado empleado : users.getUsuariosEnPrograma()) {
            if (empleado.getUsername().equals(username) && empleado.getPasswordHash().equals(password) && empleado.getRole().equals(tipoUsuario)) {
                autenticado = true;
                administrador = (Administrador) empleado;
                break;
            }
        }

        
        if (!autenticado && tipoUsuario.equals("CompradorPropietario")) {
            for (CompradorPropietario comprador : users.getCompradoresEnPrograma()) {
                if (comprador.getUsername().equals(username) && comprador.getPasswordHash().equals(password)) {
                    autenticado = true;
                    
                    break;
                }
            }
        }

        if (autenticado) {
            System.out.println("Autenticación exitosa. Bienvenido " + tipoUsuario + ".");
            
        } else {
            System.out.println("Credenciales incorrectas o rol incorrecto.");
        }
    


}
    
    
    
}
