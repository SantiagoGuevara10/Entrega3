package subasta;

import java.util.List;

import galeria.pieza.Pieza;
import galeria.usuarios.CompradorPropietario;

public class Oferta {
	private int dinero;
	private String idComprador;
	private String idPieza;
	
	public Oferta(String idComprador, String idPieza, int dinero) {
		this.idComprador = idComprador;
		this.idPieza = idPieza;
		this.dinero = dinero;
	}
	
	public int getDinero() {
		return dinero;
	}
	public void setDinero(int dinero) {
		this.dinero = dinero;
	}
	public String getidComprador() {
		return idComprador;
	}
	public void setidComprador(String idComprador) {
		this.idComprador = idComprador;
	}
	public String getidPieza() {
		return idPieza;
	}
	public void setidPieza(String idPieza) {
		this.idPieza = idPieza;
	}
	
}