package cpc.ares.modelo;

import java.io.Serializable;
import java.util.List;

public class AccesoFuncionalidad extends Funcionalidad implements Serializable{	
	
	private static final long serialVersionUID = 8147268552261187791L;
	private	int 					id;
	private String 					descripcion;
	private boolean 				visible;
	private AccionFuncionalidad 	accionEnte;
	
	
	
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
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public AccionFuncionalidad getAccionEnte() {
		return accionEnte;
	}
	public void setAccionEnte(AccionFuncionalidad accionMaxima) {
		this.accionEnte = accionMaxima;
	}
	
	public static AccesoFuncionalidad buscarAccesos(List<AccesoFuncionalidad> lista, int acceso){
		for (AccesoFuncionalidad codigo : lista) {
			if (codigo.getId()== acceso)
				return codigo;
		}
		return null;
		
	}
	
	
}
