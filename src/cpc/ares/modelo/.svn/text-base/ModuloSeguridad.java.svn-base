package cpc.ares.modelo;

import java.io.Serializable;
import java.util.List;


public class ModuloSeguridad implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 316582315444379685L;
	private int 						id;
	private String						descripcion;
	private List<AccesoFuncionalidad>	accesos;
	
	
	
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
	public List<AccesoFuncionalidad> getAccesos() {
		return accesos;
	}
	public void setAccesos(List<AccesoFuncionalidad> accesos) {
		this.accesos = accesos;
	}		 
	
	public static AccesoFuncionalidad buscarAccesos(List<ModuloSeguridad> lista, int modulo, int acceso){
		for (ModuloSeguridad codigo : lista) {
			if (codigo.getId()== modulo)
				return AccesoFuncionalidad.buscarAccesos(codigo.getAccesos(), acceso);
		}
		return null;
		
	}
	

	
}
