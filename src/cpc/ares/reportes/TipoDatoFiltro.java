package cpc.ares.reportes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoDatoFiltro implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6441261330348656350L;
	private int 			id;
	private String 			descripcion;
	private Object			componente;
	private Object 			valor;
	private String			lateral;
	private List<Operador>	operadores;
	
	public TipoDatoFiltro(){
		operadores = new ArrayList<Operador>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Object getComponente() {
		return componente;
	}
	public void setComponente(Object componente) {
		this.componente = componente;
	}
	public Object getValor() {
		return valor;
	}
	public void setValor(Object valor) {
		this.valor = valor;
	}
	public String getLateral() {
		return lateral;
	}
	public void setLateral(String lateral) {
		this.lateral = lateral;
	}
	public List<Operador> getOperadores() {
		return operadores;
	}
	public void setOperadores(List<Operador> operadores) {
		this.operadores = operadores;
	}
}
