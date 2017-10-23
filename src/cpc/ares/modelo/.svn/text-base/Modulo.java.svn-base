package cpc.ares.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cva.pc.demeter.utilidades.TipDatPar;



public class Modulo implements Serializable {
   
   	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3422518142669090234L;
	private int 				idmodulo;   
    private String 				nombre;   
    private boolean 			estado;
    private TipDatPar 			sistemas;
    private List<Funcionalidad> funcionalidades;
    
    public Modulo(){
    	
    }
    
    public Modulo(int idModulo, String nombre){
    	this.idmodulo 	= idModulo;
    	this.nombre 	= nombre;
    	funcionalidades	= new ArrayList<Funcionalidad>();
    }

    public int getIdmodulo() {
	 return idmodulo;
    }


	public List<Funcionalidad> getFuncionalidades() {
		return funcionalidades;
	}

	public String getNombre() {
		return nombre;
	}

	
	public void agregarFuncionalidad(Funcionalidad funcionalidad){
		this.funcionalidades.add(funcionalidad);
	}

	public void setFuncionalidades(List<Funcionalidad> funcionalidad) {
		this.funcionalidades = funcionalidad;
	}
    public final TipDatPar getSistemas() {
		return sistemas;
	}

	public final void setSistemas(TipDatPar tipDatPar) {
		this.sistemas = tipDatPar;
	}
	public void setIdmodulo(int idmodulo) {
		this.idmodulo = idmodulo;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}