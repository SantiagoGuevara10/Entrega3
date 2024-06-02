package galeria.inventarios;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import galeria.pieza.Pieza;
import subasta.Oferta;
import subasta.Subasta;
import galeria.usuarios.CompradorPropietario;
import galeria.usuarios.Empleado;
import galeria.usuarios.UsuariosRegistrados;

public class InventarioGeneral {
    private Map<String, Pieza> inventarioPasado;
    private Map<String, Pieza> inventarioBodega;
    private Map<String, Pieza> inventarioExhibido;
    private double inventarioDinero;
    private List<Subasta> subastasEnProceso;

    
    public InventarioGeneral() {
        inventarioBodega = new HashMap<>();
        inventarioPasado = new HashMap<>();
        inventarioExhibido = new HashMap<>();
        inventarioDinero = 0;
        subastasEnProceso = new LinkedList<>();
    }
	
    public void addInventarioDinero(int dinero) {
    	inventarioDinero += dinero;
    }
	
	public Map<String,Pieza> getInventarioPasado(){
		return inventarioPasado;
	}
	public Map<String,Pieza> getInventarioBodega(){
		return inventarioBodega;
	}
	
	public void setInventarioPasado(Map<String,Pieza>pasado) {
		this.inventarioPasado=pasado;
	}
	
	public void setInventarioExhibido(Map<String,Pieza>exhibido) {
		this.inventarioExhibido = exhibido;
	}
	
	public void setInventarioBodega(Map<String,Pieza>bodega) {
		this.inventarioBodega=bodega;
	}
	
	public Map<String,Pieza> getInventarioExhibido(){
		return inventarioExhibido;
	}
	
	public Pieza getPiezaInventarioPasado(String piezaId){
		return this.inventarioPasado.get(piezaId);
	}
	public Pieza getPiezaInventarioBodega(String piezaId){
		return this.inventarioBodega.get(piezaId);
	}
	
	public Pieza getPiezaInventarioExhibido(String piezaId){
		return this.inventarioExhibido.get(piezaId);
	}
	
	public boolean existeInventarioPasado(String piezaId) {
		return this.inventarioPasado.containsKey(piezaId);
	}
	
	public boolean existeInventarioBodega(String piezaId) {
		return this.inventarioBodega.containsKey(piezaId);
	}
	
	public boolean existeInventarioExhibido(String piezaId) {
		return this.inventarioExhibido.containsKey(piezaId);
	}
	
	public void addInventarioPasado(String piezaId, Pieza pieza) {
		this.inventarioPasado.put(piezaId, pieza);
	}
	public Pieza getPieza(String id) {
        if (inventarioBodega.containsKey(id)) {
            return inventarioBodega.get(id);
        } else if (inventarioExhibido.containsKey(id)) {
            return inventarioExhibido.get(id);
        }  else {
            return null;
        }
	}
		
	public void addInventarioBodega(String piezaId, Pieza pieza) {
		this.inventarioBodega.put(piezaId, pieza);
	}
	
	public void addInventarioExhibido(String piezaId, Pieza pieza) {
		this.inventarioExhibido.put(piezaId, pieza);
	}
	
	public void removeInventarioPasado(String piezaId) {
		this.inventarioPasado.remove(piezaId);
	}
	
	public void removeInventarioBodega(String piezaId) {
		this.inventarioBodega.remove(piezaId);
	}
	
	public void removeInventarioExhibido(String piezaId) {
		this.inventarioExhibido.remove(piezaId);
	}


	public double getInventarioDinero() {
		return inventarioDinero;
	}
	
	public void setSubasta(List<Subasta> subasta) {
		this.subastasEnProceso = subasta;
	}


	public void setInventarioDinero(double inventarioDinero) {
		this.inventarioDinero = inventarioDinero;
	}
	public List<Subasta> getSubastasEnProceso() {
        return subastasEnProceso;
    }

    public void addSubasta(Subasta subasta) {
        this.subastasEnProceso.add(subasta);
    }

    public void removeSubasta(Subasta subasta) {
        this.subastasEnProceso.remove(subasta);
    }
	public Map<String, Pieza> getPiezasDisponibles() {
	    Map<String, Pieza> piezasDisponibles = new HashMap<>();
	    piezasDisponibles.putAll(inventarioBodega);  
	    piezasDisponibles.putAll(inventarioExhibido);  
	    return piezasDisponibles;
	}
	
	 public void guardarUsuarios( File archivo ) throws IOException
	    {
	        PrintWriter writer = new PrintWriter( archivo );

	        writer.println("Información del inventario");
	        
    		for (Map.Entry<String, Pieza> entry : inventarioExhibido.entrySet()) {
    			String idPieza = entry.getKey();
    		    Pieza pieza = entry.getValue();
    		    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                String titulo = pieza.getTitulo();
                int anioCreacion = pieza.getAnioCreacion();
                String lugarCreacion = pieza.getLugarCreacion();
                String estadoPieza = pieza.getEstadoPieza();
                boolean estaExhibida = pieza.getEstaExhibida();
                boolean disponibleVenta = pieza.getDisponibleVenta(); 
                List<String> autores = pieza.getAutor();
                double valorFijo = pieza.getValorFijo();
                int valorMinimo = pieza.getValorMinimo();
                int valorInicial = pieza.getValorInicial();
                String fechaString = formato.format(pieza.getFechaDeIngreso());
                boolean esVigente = pieza.isEsVigente();
                String descripcion = pieza.getDescripcion();
    		    
        		if(descripcion.equals("Escultura")) {
                	PiezaEscultura escultura = (PiezaEscultura) pieza;
                	float peso = escultura.getPeso();
                    boolean usaElectricidad = escultura.getUsaElectricidad();
	                writer.println( "Exhibido:"+"Escultura"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+peso+":"+usaElectricidad);

                }
                else if(descripcion.equals("Fotografia")) {
                	PiezaFotografia fotografia = (PiezaFotografia) pieza;
                    boolean esDigital = fotografia.getEsDigital();
                    writer.println("Exhibido:"+"Fotografia"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+esDigital);

                	
                }
                else if(descripcion.equals("Pintura")) {
                	PiezaPintura pintura = (PiezaPintura) pieza;
                	int peso = pintura.getPeso();
                    String tecnica = pintura.getTecnica();

                    writer.println("Exhibido:"+"Pintura"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+peso+":"+tecnica);

                	
                }
                 else if(descripcion.equals("Video")) {
                 	PiezaVideo video = (PiezaVideo) pieza;
                    String calidad = video.getCalidad();
                	int duracion = video.getDuracion();

                    writer.println("Exhibido:"+"Video"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+calidad+":"+duracion);
 	                	
                	
                }
                	
                
                for(int k=0; k<autores.size();k++) {
                	String autor = autores.get(k);
                	writer.println("Exhibido:"+"Autor"+":"+idPieza+":"+autor);

                }}
    		
    		
    		
    		
    		for (Map.Entry<String, Pieza> entry : inventarioBodega.entrySet()) {
    			String idPieza = entry.getKey();
    		    Pieza pieza = entry.getValue();
    		    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                String titulo = pieza.getTitulo();
                int anioCreacion = pieza.getAnioCreacion();
                String lugarCreacion = pieza.getLugarCreacion();
                String estadoPieza = pieza.getEstadoPieza();
                boolean estaExhibida = pieza.getEstaExhibida();
                boolean disponibleVenta = pieza.getDisponibleVenta(); 
                List<String> autores = pieza.getAutor();
                double valorFijo = pieza.getValorFijo();
                int valorMinimo = pieza.getValorMinimo();
                int valorInicial = pieza.getValorInicial();
                String fechaString = formato.format(pieza.getFechaDeIngreso());
                boolean esVigente = pieza.isEsVigente();
                String descripcion = pieza.getDescripcion();
    		    
        		if(descripcion.equals("Escultura")) {
                	PiezaEscultura escultura = (PiezaEscultura) pieza;
                	float peso = escultura.getPeso();
                    boolean usaElectricidad = escultura.getUsaElectricidad();
	                writer.println( "Bodega:"+"Escultura"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+peso+":"+usaElectricidad);

                }
                else if(descripcion.equals("Fotografia")) {
                	PiezaFotografia fotografia = (PiezaFotografia) pieza;
                    boolean esDigital = fotografia.getEsDigital();
                    writer.println("Bodega:"+"Fotografia"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+esDigital);

                	
                }
                else if(descripcion.equals("Pintura")) {
                	PiezaPintura pintura = (PiezaPintura) pieza;
                	int peso = pintura.getPeso();
                    String tecnica = pintura.getTecnica();

                    writer.println("Bodega:"+"Pintura"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+peso+":"+tecnica);

                	
                }
                 else if(descripcion.equals("Video")) {
                 	PiezaVideo video = (PiezaVideo) pieza;
                    String calidad = video.getCalidad();
                	int duracion = video.getDuracion();

                    writer.println("Bodega:"+"Video"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+calidad+":"+duracion);
 	                	
                	
                }
                	
                
                for(int k=0; k<autores.size();k++) {
                	String autor = autores.get(k);
                	writer.println("Bodega:"+"Autor"+":"+idPieza+":"+autor);

                }}
    		
    		
    		
    		for (Map.Entry<String, Pieza> entry : inventarioPasado.entrySet()) {
    			String idPieza = entry.getKey();
    		    Pieza pieza = entry.getValue();
    		    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    		    String titulo = pieza.getTitulo();
                int anioCreacion = pieza.getAnioCreacion();
                String lugarCreacion = pieza.getLugarCreacion();
                String estadoPieza = pieza.getEstadoPieza();
                boolean estaExhibida = pieza.getEstaExhibida();
                boolean disponibleVenta = pieza.getDisponibleVenta(); 
                List<String> autores = pieza.getAutor();
                double valorFijo = pieza.getValorFijo();
                int valorMinimo = pieza.getValorMinimo();
                int valorInicial = pieza.getValorInicial();
                String fechaString = formato.format(pieza.getFechaDeIngreso());
                boolean esVigente = pieza.isEsVigente();
                String descripcion = pieza.getDescripcion();
    		    
        		if(descripcion.equals("Escultura")) {
                	PiezaEscultura escultura = (PiezaEscultura) pieza;
                	float peso = escultura.getPeso();
                    boolean usaElectricidad = escultura.getUsaElectricidad();
	                writer.println( "Pasado:"+"Escultura"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+peso+":"+usaElectricidad);

                }
                else if(descripcion.equals("Fotografia")) {
                	PiezaFotografia fotografia = (PiezaFotografia) pieza;
                    boolean esDigital = fotografia.getEsDigital();
                    writer.println("Pasado:"+"Fotografia"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+esDigital);

                	
                }
                else if(descripcion.equals("Pintura")) {
                	PiezaPintura pintura = (PiezaPintura) pieza;
                	int peso = pintura.getPeso();
                    String tecnica = pintura.getTecnica();

                    writer.println("Pasado:"+"Pintura"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+peso+":"+tecnica);

                	
                }
                 else if(descripcion.equals("Video")) {
                 	PiezaVideo video = (PiezaVideo) pieza;
                    String calidad = video.getCalidad();
                	int duracion = video.getDuracion();

                    writer.println("Pasado:"+"Video"+":"+idPieza+":"+titulo+":"+anioCreacion+":"+lugarCreacion+":"+estadoPieza+":"+estaExhibida+":"+disponibleVenta+":"+valorFijo+":"+valorMinimo+":"+valorInicial+":"+fechaString+":"+esVigente+":"+descripcion+":"+calidad+":"+duracion);
 	                	
                	
                }
                	
                
                for(int k=0; k<autores.size();k++) {
                	String autor = autores.get(k);
                	writer.println("Pasado:"+"Autor"+":"+idPieza+":"+autor);

                }}
    		
    		writer.println("\n");
    		double valor = inventarioDinero;
    		String dineroo = String.valueOf(valor);
    		writer.print("Dinero"+":"+dineroo);
    		writer.println("\n");
	        writer.println("Información de las subastas");
	        writer.println("\n");
	        for(int i=0;i< subastasEnProceso.size();i++) {
	        	List<String> idPiezasDisponibles = subastasEnProceso.get(i).getIdPiezasDisponibles();
	        	List<Oferta> ofertas = subastasEnProceso.get(i).getOfertas();
	            String id =  subastasEnProceso.get(i).getId();
	            
	            for(int j=0; j<ofertas.size();j++) {
	            	int dinero = ofertas.get(j).getDinero();
	            	String dinero1 = String.valueOf(dinero);
	            	String idComprador = ofertas.get(j).getidComprador();
	            	String idPieza = ofertas.get(j).getidPieza();
	            	writer.println("Subasta"+":"+id+":"+"Oferta"+":"+dinero1+":"+idComprador+":"+idPieza);
	            }
	            
	            for(int j=0; j<idPiezasDisponibles.size();j++) {
	            	String idPieza = idPiezasDisponibles.get(j);
	            	writer.println("Subasta"+":"+id+":"+"idPieza"+":"+idPieza);
	            }
	            
	            
	            
	        	
	        }

    		
    		writer.close( );}
	    
 @SuppressWarnings("deprecation")
public static InventarioGeneral cargarEstado( File archivo ) throws FileNotFoundException, IOException, NumberFormatException, ParseException
	    {
	 Map<String, Pieza> inventarioBodega = new HashMap<>();
	 Map<String, Pieza> inventarioPasado = new HashMap<>();
	 Map<String, Pieza> inventarioExhibido = new HashMap<>();
	 double inventarioDinero = 0; 
	 List<Subasta> subastasEnProceso = new LinkedList<>();

	        BufferedReader br = new BufferedReader( new FileReader( archivo ) );
	        String line = br.readLine( );
	        while( line != null )
	        {
	            String[] partes = line.split( ":" );
	            if( partes[ 0 ].equals( "Subasta" ) && partes[ 2 ].equals("Oferta")) {
	            	String dinero1 = partes[3];
	            	int dinero = Integer.parseInt(dinero1);
	            	String id = partes[1];
	            	String idComprador = partes[4];
	            	String idPieza = partes[5];
	            	Oferta oferta = new Oferta(idComprador, idPieza, dinero);
	            	Subasta subasta = new Subasta(id);
	            	subasta.getOfertas().add(oferta);
	            	subastasEnProceso.add(subasta);
	            	
	            	
	            }
	            if( partes[ 0 ].equals( "Subasta" ) && partes[ 2 ].equals("idPieza")) {
	            	String idPieza = partes[3];
	            	String id = partes[1];
	            	for(int j=0; j<subastasEnProceso.size();j++) {
	            		Subasta subasta = subastasEnProceso.get(j);
	            		String idsub = subasta.getId();
	            		if(idsub.equals(id)) {
	            			subasta.getIdPiezasDisponibles().add(idPieza);
	            		}
	            		
	            	}
	            	
	            	
	            	
	            }
	            	
	            	
	            	
	            	
	            	
	            if( partes[ 0 ].equals( "Exhibido" ) && !partes[ 1 ].equals("Autor"))
		            {String dateInString = partes[12];
 	                Date fecha = new Date();
 	                String[] fechita = dateInString.split( "-" );
 	                int year = Integer.parseInt(fechita[0]);
 	                int month = Integer.parseInt(fechita[1]);
 	                int day = Integer.parseInt(fechita[2]);
 	                fecha.setYear(year);
 	                fecha.setMonth(month-1);
 	                fecha.setDate(day);
 	                String idPieza = partes[2];
	                String titulo =  partes[3];
	                int anioCreacion = Integer.parseInt(partes[4]);
	                String lugarCreacion = partes[5];
	                String estadoPieza = partes[6];
	                boolean estaExhibida = Boolean.parseBoolean(partes[7]);
	                boolean disponibleVenta = Boolean.parseBoolean(partes[8]);
	                List<String> autores = new LinkedList<>();
	                double valorFijo = Double.parseDouble(partes[9]);
	                int valorMinimo = Integer.parseInt(partes[10]);
	                int valorInicial = Integer.parseInt(partes[11]);
	                boolean esVigente = Boolean.parseBoolean(partes[13]);
	                String descripcion = partes[14];
	            	
	            	if( partes[ 1 ].equals( "Escultura" ) )
		            {int peso;
					try {
		                double tempValue = Double.parseDouble(partes[15]);
		                peso = (int) tempValue;
		            } catch (NumberFormatException e) {
		                e.printStackTrace();
		                peso = 0; 
		            }
		                boolean usaElectricidad = Boolean.parseBoolean(partes[16]);
		                PiezaEscultura pieza = new PiezaEscultura(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, peso, usaElectricidad);

		                inventarioExhibido.put(idPieza, pieza);
		                
	            		
		            }
	            	
	            	if( partes[ 1 ].equals( "Fotografia" ) )
		            {boolean esDigital = Boolean.parseBoolean(partes[15]);
	                PiezaFotografia pieza = new PiezaFotografia(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, esDigital);
	                inventarioExhibido.put(idPieza, pieza);

	            		
		            }
	            	
	            	else if( partes[ 1 ].equals( "Pintura" ) )
		            {
	            		int peso;
						try {
			                double tempValue = Double.parseDouble(partes[15]);
			                peso = (int) tempValue;
			            } catch (NumberFormatException e) {
			                e.printStackTrace();
			                peso = 0; 
			            }
		                String tecnica = partes[16];
		                PiezaPintura pieza = new PiezaPintura(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, peso, tecnica);
		                inventarioExhibido.put(idPieza, pieza);
		                
	            		
		            }
	            	else if( partes[ 1 ].equals( "Video" ) )
		            {
	            		String calidad = partes[15];
	 	                int duracion = Integer.parseInt(partes[16]);
	 	                PiezaVideo pieza = new PiezaVideo(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, calidad, duracion);
	 	                inventarioExhibido.put(idPieza, pieza);
	            	
	            	
		            }
	            	
	            }
	            else if( partes[ 0 ].equals( "Bodega" )&& !partes[ 1 ].equals("Autor") )
	            {String dateInString = partes[12];
	                Date fecha = new Date();
	                String[] fechita = dateInString.split( "-" );
	                int year = Integer.parseInt(fechita[0]);
	                int month = Integer.parseInt(fechita[1]);
	                int day = Integer.parseInt(fechita[2]);
	                fecha.setYear(year);
	                fecha.setMonth(month-1);
	                fecha.setDate(day);String idPieza = partes[2];
                String titulo =  partes[3];
                int anioCreacion = Integer.parseInt(partes[4]);
                String lugarCreacion = partes[5];
                String estadoPieza = partes[6];
                boolean estaExhibida = Boolean.parseBoolean(partes[7]);
                boolean disponibleVenta = Boolean.parseBoolean(partes[8]);
                List<String> autores = new LinkedList<>();
                double valorFijo = Double.parseDouble(partes[9]);
                int valorMinimo = Integer.parseInt(partes[10]);
                int valorInicial = Integer.parseInt(partes[11]);
                boolean esVigente = Boolean.parseBoolean(partes[13]);
                String descripcion = partes[14];
            	
            	if( partes[ 1 ].equals( "Escultura" ) )
	            {int peso;
				try {
	                double tempValue = Double.parseDouble(partes[15]);
	                peso = (int) tempValue;
	            } catch (NumberFormatException e) {
	                e.printStackTrace();
	                peso = 0; 
	            }
	                boolean usaElectricidad = Boolean.parseBoolean(partes[16]);
	                PiezaEscultura pieza = new PiezaEscultura(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, peso, usaElectricidad);

	                inventarioBodega.put(idPieza, pieza);
	                
            		
	            }
            	
            	if( partes[ 1 ].equals( "Fotografia" ) )
	            {boolean esDigital = Boolean.parseBoolean(partes[15]);
                PiezaFotografia pieza = new PiezaFotografia(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, esDigital);
                inventarioBodega.put(idPieza, pieza);

            		
	            }
            	
            	else if( partes[ 1 ].equals( "Pintura" ) )
	            {
            		int peso;
					try {
		                double tempValue = Double.parseDouble(partes[15]);
		                peso = (int) tempValue;
		            } catch (NumberFormatException e) {
		                e.printStackTrace();
		                peso = 0; 
		            }
	                String tecnica = partes[16];
	                PiezaPintura pieza = new PiezaPintura(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, peso, tecnica);
	                inventarioBodega.put(idPieza, pieza);
	                
            		
	            }
            	else if( partes[ 1 ].equals( "Video" ) )
	            {
            		String calidad = partes[15];
 	                int duracion = Integer.parseInt(partes[16]);
 	                PiezaVideo pieza = new PiezaVideo(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, calidad, duracion);
 	                inventarioBodega.put(idPieza, pieza);
            	
            	
	            
	            	
	            }}
	            else if( partes[ 0 ].equals( "Pasado" )&& !partes[ 1 ].equals("Autor") )
	            {
	            	String dateInString = partes[12];
 	                Date fecha = new Date();
 	                String[] fechita = dateInString.split( "-" );
 	                int year = Integer.parseInt(fechita[0]);
 	                int month = Integer.parseInt(fechita[1]);
 	                int day = Integer.parseInt(fechita[2]);
 	                fecha.setYear(year);
 	                fecha.setMonth(month-1);
 	                fecha.setDate(day);
	            	String idPieza = partes[2];
	                String titulo =  partes[3];
	                int anioCreacion = Integer.parseInt(partes[4]);
	                String lugarCreacion = partes[5];
	                String estadoPieza = partes[6];
	                boolean estaExhibida = Boolean.parseBoolean(partes[7]);
	                boolean disponibleVenta = Boolean.parseBoolean(partes[8]);
	                List<String> autores = new LinkedList<>();
	                double valorFijo = Double.parseDouble(partes[9]);
	                int valorMinimo = Integer.parseInt(partes[10]);
	                int valorInicial = Integer.parseInt(partes[11]);
	                boolean esVigente = Boolean.parseBoolean(partes[13]);
	                String descripcion = partes[14];
	            	
	            	if( partes[ 1 ].equals( "Escultura" ) )
		            {
		            int peso;
					try {
		                double tempValue = Double.parseDouble(partes[15]);
		                peso = (int) tempValue;
		            } catch (NumberFormatException e) {
		                e.printStackTrace();
		                peso = 0; 
		            }
		                boolean usaElectricidad = Boolean.parseBoolean(partes[16]);
		                PiezaEscultura pieza = new PiezaEscultura(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, peso, usaElectricidad);

		                inventarioPasado.put(idPieza, pieza);
		                
	            		
		            }
	            	
	            	if( partes[ 1 ].equals( "Fotografia" ) )
		            {boolean esDigital = Boolean.parseBoolean(partes[15]);
	                PiezaFotografia pieza = new PiezaFotografia(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, esDigital);
	                inventarioPasado.put(idPieza, pieza);

	            		
		            }
	            	
	            	else if( partes[ 1 ].equals( "Pintura" ) )
		            {
	            		int peso;
						try {
			                double tempValue = Double.parseDouble(partes[15]);
			                peso = (int) tempValue;
			            } catch (NumberFormatException e) {
			                e.printStackTrace();
			                peso = 0; 
			            }
		                String tecnica = partes[16];
		                PiezaPintura pieza = new PiezaPintura(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, peso, tecnica);
		                inventarioPasado.put(idPieza, pieza);
		                
	            		
		            }
	            	else if( partes[ 1 ].equals( "Video" ) )
		            {
	            		String calidad = partes[15];
	 	                int duracion = Integer.parseInt(partes[16]);
	 	                PiezaVideo pieza = new PiezaVideo(idPieza, titulo, anioCreacion, lugarCreacion , estadoPieza, estaExhibida, disponibleVenta, autores, valorFijo, valorMinimo, valorInicial, fecha,esVigente, descripcion, calidad, duracion);
	 	                inventarioPasado.put(idPieza, pieza);
	            	
	            	
		            }
	            	
	            }
	            
	            else if( partes[ 0 ].equals( "Dinero" ) )
	            {
	            	inventarioDinero = Double.parseDouble(partes[1]);
	            	
	            }
	            
	            else if (partes[ 0 ].equals( "Pasado" )&& partes[ 1 ].equals("Autor") ) {
	            	String idPieza = partes[2];
	            	String autor = partes[3];
	            	for(int m=0; m<inventarioPasado.size();m++) {
	                	Pieza pieza = inventarioPasado.get(idPieza);
	                	pieza.setAutores(autor);
	                	}
	            	
	            	
	            }
	            
	            else if (partes[ 0 ].equals( "Bodega" )&& partes[ 1 ].equals("Autor") ) {
	            	String idPieza = partes[2];
	            	String autor = partes[3];
	            	for(int m=0; m<inventarioBodega.size();m++) {
	                	Pieza pieza = inventarioBodega.get(idPieza);
	                	pieza.setAutores(autor);
	                	}
	            	
	            	
	            }
	            
	            else if (partes[ 0 ].equals( "Exhibido" )&& partes[ 1 ].equals("Autor") ) {
	            	String idPieza = partes[2];
	            	String autor = partes[3];
	            	for(int m=0; m<inventarioExhibido.size();m++) {
	                	Pieza pieza = inventarioExhibido.get(idPieza);
	                	pieza.setAutores(autor);
	                	}
	            	
	            	
	            }
	            
	            
	        
	            line = br.readLine( );	
            }        br.close( );  
            
            InventarioGeneral total = new InventarioGeneral();
            total.setInventarioDinero(inventarioDinero);
            total.setInventarioBodega(inventarioBodega);
            total.setInventarioExhibido(inventarioExhibido);
            total.setInventarioPasado(inventarioPasado);
            total.setSubasta(subastasEnProceso);
            
            return total;
            
	        
	    
	    
	    
	    
	    }
 





}