package cpc.ares.reportes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cva.pc.demeter.utilidades.TipDatPar;

public class DataFiltro implements Serializable{
	/**
	 * 
	 */
	
	private static final long 	serialVersionUID = -6193853147238481031L;
	private TipoDatoFiltro 		tipo;
	private	List<TipDatPar>		valores;
	private	String				campo;
	
	public DataFiltro(){
		valores = new ArrayList<TipDatPar>();
	}
	
	public TipoDatoFiltro getTipo() {
		return tipo;
	}
	public void setTipo(TipoDatoFiltro tipo) {
		this.tipo = tipo;
	}
	public List<TipDatPar> getValores() {
		return valores;
	}
	public void setValores(List<TipDatPar> valores) {
		this.valores = valores;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
}
