package cpc.ares.modelo;

import java.io.Serializable;

import cva.pc.demeter.utilidades.TipDatParString;

public class Sede  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2424656128738749910L;
	private String 			idSede;
	private String 			nombre;
	private TipDatParString estado;
	private boolean 		esSede;
	
	private TipDatParString	empresa;
	
	public Sede() {
		super();
	}
	
	public Sede(String idSede, String nombre, TipDatParString estado, boolean esSede) {
		super();
		this.idSede = idSede;
		this.nombre = nombre;
		this.estado = estado;
		this.esSede = esSede;
	}

	public boolean isEsSede() {
		return esSede;
	}


	public void setEsSede(boolean esSede) {
		this.esSede = esSede;
	}

	public String getIdSede() {
		return idSede;
	}


	public String getNombre() {
		return nombre;
	}


	public TipDatParString getEstado() {
		return estado;
	}

	public String toString(){
		return nombre;
	}

	public TipDatParString getEmpresa() {
		return empresa;
	}

	public void setEmpresa(TipDatParString empresa) {
		this.empresa = empresa;
	}

}
