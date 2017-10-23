package cpc.ares.modelo;


import java.io.Serializable;

public class UnidadFuncional implements Serializable, Comparable<UnidadFuncional>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4754525649802696291L;
	private String	id;
	private String  empresa;
	private String  descripcion;
	private int  tipo;
	
	public UnidadFuncional() {
		super();
	}
	
	
	public UnidadFuncional(String id, String empresa, String descripcion) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.descripcion = descripcion;
	}
	
	public UnidadFuncional(String id, String empresa, String descripcion, int tipo) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.descripcion = descripcion;
		this.tipo = tipo;
	}
	
	public UnidadFuncional(String id,  String descripcion) {
		super();
		this.id = id;
		this.empresa = "0001";
		this.descripcion = descripcion;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String toString(){
		return descripcion;
	}


	public int getTipo() {
		return tipo;
	}


	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int compareTo(UnidadFuncional arg0) {
		return this.getDescripcion().compareTo(arg0.getDescripcion());
	}

	public boolean 	equals(UnidadFuncional obj){
		return this.getId() == obj.getId();
	}
}

