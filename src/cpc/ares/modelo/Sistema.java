package cpc.ares.modelo;

import java.io.Serializable;
import java.util.*;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.ares.interfaz.IAplicacion;
import cpc.ares.interfaz.IPerSede;


public class Sistema  implements Serializable, Comparable<Sistema> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1798012477107180053L;
	private int			id;		
	private String 		nombresistema;
    private String 		descripcion;
    private String 		licencia;
    private String 		parametros;
    private String 		nombrelogo;
    private boolean		estado;
    private String 		version;   
    private Collection<Usuario> usuario;
      
    public Sistema(){
    	
    }
	public Sistema(int idSistema, String nombresistema, String descripcion, String licencia, String parametros, String nombrelogo, String version, boolean estado ){
			this.nombresistema = nombresistema;
			this.descripcion = descripcion;
			this.licencia = licencia;
			this.parametros = parametros;
			this.nombrelogo = nombrelogo;
			this.version	= version;
	}
   
    public String getNombresistema() {
	   return nombresistema;
    }

	public void setNombresistema(String nombresistema) {
		this.nombresistema = nombresistema;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getLicencia() {
		return licencia;
	}
	
	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}
	
	public String getParametros() {
		return parametros;
	}
	
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	
	public String getNombrelogo() {
		return nombrelogo;
	}
	
	public void setNombrelogo(String nombrelogo) {
		this.nombrelogo = nombrelogo;
	}
	
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Collection<Usuario> getUsuario() {
	      if (usuario == null)
	         usuario = new java.util.HashSet<Usuario>();
	      return usuario;
	}   

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}
	
	
	public static IAplicacion getAplicacion() {
		return (IAplicacion) SpringUtil.getBean("aplicacion");
	}

	public static Integer getSistema() {
		return (Integer) SpringUtil.getBean("sistema");
	}
	
	public static IPerSede getSedes() {
		return (IPerSede) SpringUtil.getBean("sedes");
	}
	
	public String toString(){
		return nombresistema;
	}

	public int compareTo(Sistema arg0) {
		return this.getDescripcion().compareTo(arg0.getDescripcion());
	}
	
	public boolean 	equals(Sistema obj){
		return this.getId() == obj.getId();
	}
}