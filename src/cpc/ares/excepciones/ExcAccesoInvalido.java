package cpc.ares.excepciones;

public class ExcAccesoInvalido extends Exception{

	private String mensaje;
	public ExcAccesoInvalido(String string) {
		// TODO Auto-generated constructor stub
		super(string);	
		this.mensaje = string;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2493687376842569315L;
	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
