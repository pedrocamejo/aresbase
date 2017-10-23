/***********************************************************************
 * Author:  RMantilla

 ***********************************************************************/
package cpc.ares.modelo;

import java.io.Serializable;

import cpc.ares.interfaz.IComando;

public class Funcionalidad implements Serializable {
   
	private static final long serialVersionUID = 5094171312071252882L;
	private int idservicio;   
    private String nombre;   
    private String descripcion;
	private String icono;   
    private IComando comando;
    private AccionFuncionalidad accionMaxima;
    
    public Funcionalidad(){
    	
    }
	public Funcionalidad(int idservicio, String nombre,
			 IComando comando, String accion, String icono) {
		super();
		this.idservicio = idservicio;
		this.nombre = nombre;
		this.icono = icono;
		this.comando = comando;
		this.accionMaxima = new AccionFuncionalidad(accion);
	}    
    
	public Funcionalidad(int idservicio, String nombre, 
			 IComando comando, String accion) {
		super();
		this.idservicio = idservicio;
		this.nombre = nombre;
		this.icono = "";
		this.comando = comando;
		this.accionMaxima = new AccionFuncionalidad(accion);
	}  
	
	public Funcionalidad(int idservicio, String nombre, String descripcion, String accion, String icono) {
		super();
		this.idservicio = idservicio;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.icono = icono;		
		this.accionMaxima = new AccionFuncionalidad(accion);
	} 
	
    public int getIdservicio() {
    	return idservicio;
    }

	public AccionFuncionalidad getAccionMaxima() {
		return accionMaxima;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getIcono() {
		return icono;
	}
	
	public IComando getComando() {
		return comando;
	}
	 public String getDescripcion() {
			return descripcion;
	}
	 
	public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
	}
	
	public void setIdservicio(int idservicio) {
		this.idservicio = idservicio;
	}
	
	public void setIcono(String icono) {
		this.icono = icono;
	}
	
	public void setComando(IComando comando) {
		this.comando = comando;
	}
	public void setAccionMaxima(AccionFuncionalidad accion) {
		this.accionMaxima = accion;
	}
	
	
}