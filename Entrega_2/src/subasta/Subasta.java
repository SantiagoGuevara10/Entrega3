package subasta;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import galeria.pieza.Pieza;
import galeria.usuarios.CompradorPropietario;
import galeria.usuarios.Administrador;

public class Subasta {
	private List<String> idPiezasDisponibles;
	private List<Oferta> ofertas;
    private String id;
	
    public Subasta(String id) {
        this.id = id;
        this.idPiezasDisponibles = new LinkedList<>();
        this.ofertas = new LinkedList<>();
    }

	public List<String> getIdPiezasDisponibles() {
		return idPiezasDisponibles;
	}

	public void setIdPiezasDisponibles(List<String> idPiezasDisponibles) {
		this.idPiezasDisponibles = idPiezasDisponibles;
	}

	public List<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	
}