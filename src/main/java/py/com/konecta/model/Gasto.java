package py.com.konecta.model;

public class Gasto {
	
	String texto;
	double importe;

	public Gasto(String t, double i) {
		this.texto = t;
		this.importe = i;
	}
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

}
