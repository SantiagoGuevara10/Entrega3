package subasta;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import galeria.pieza.Pieza;
import galeria.usuarios.CompradorPropietario;
import galeria.usuarios.Administrador;

public class Subasta {
	private Map<String, Pieza> piezasDisponibles;
	private Map<String, List<Oferta>> ofertas;
	private List<CompradorPropietario> compradores;
	private boolean activa;
    private String id;
	
    public Subasta(String id, List<Pieza> piezas) {
        this.id = id;
        this.piezasDisponibles = new HashMap<>();
        this.ofertas = new HashMap<>();
        this.compradores = new LinkedList<>();
        for (Pieza pieza : piezas) {
            String piezaId = pieza.getIdPieza();
            this.piezasDisponibles.put(piezaId, pieza);
            this.ofertas.put(piezaId, new LinkedList<>());
        }
        this.activa = false;
    }
	
	public void crearOferta(CompradorPropietario comprador, String piezaId, int dinero) {
		Pieza pieza = this.piezasDisponibles.get(piezaId);
		Oferta oferta = new Oferta(comprador, pieza, dinero);
		this.ofertas.get(piezaId).add(oferta);
	}
	
	public Oferta getMaximaOferta(String piezaId) {
		List<Oferta> ofertas = this.getOfertas().get(piezaId);
		if (ofertas.size() <= 0) {
			return null;
		}
		Oferta maxima = ofertas.get(0);
		for (int i = 0; i < ofertas.size(); i++) {
			Oferta oferta = ofertas.get(i);
			if (oferta.getDinero() > maxima.getDinero()) {
				maxima = oferta;
			}
		}
		return maxima;
	}
	public Map<String, Pieza> getPiezasDisponibles() {
		return piezasDisponibles;
	}

	public void setPiezasDisponibles(Map<String, Pieza> piezasDisponibles) {
		this.piezasDisponibles = piezasDisponibles;
	}

	public Map<String, List<Oferta>> getOfertas() {
		return ofertas;
	}

	public void setOfertas(Map<String, List<Oferta>> ofertas) {
		this.ofertas = ofertas;
	}

	public List<CompradorPropietario> getCompradores() {
		return compradores;
	}

	public void setCompradores(List<CompradorPropietario> compradores) {
		this.compradores = compradores;
	}
	public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	
}