package cpc.ares.modelo;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import org.zkoss.zkplus.spring.SpringUtil;
import cva.pc.demeter.excepciones.ExcArgumentoInvalido;

public class Perfil  implements Serializable {
  
   
	private static final long serialVersionUID = -5130765768015411379L;	
	@SuppressWarnings("unused")
	private int sistema;	
	private String nombreSistema;
	private List<ModuloSeguridad> modulosSeguridad;
	private List<Modulo> modulos;    
	

	public Perfil(int sistema, List<Modulo> list) {
		super();
		sistema = (Integer) SpringUtil.getBean("sistema");
		modulos = list;
	}

    public Perfil() throws SQLException, ExcArgumentoInvalido {
		super();
		//modulos = PerUsuario.cargarModulos();
	}
    
    
	public void agregarModulo(Modulo modulo){
    	modulos.add(modulo);
    }
    
    public void agregarSistema(Modulo modulo){
    	modulos.add(modulo);
    }  
    
    public void verificarAccesoGlobal(){
    	
    }

	public List<Modulo> getModulos() {
		return modulos;
	}
	
	 public String getNombreSistema() {
		return nombreSistema;
	}

	public void setNombreSistema(String nombreSistema) {
		this.nombreSistema = nombreSistema;
	}
   
	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}	
	
	public List<ModuloSeguridad> getModulosSeguridad() {
		return modulosSeguridad;
	}

	public void setModulosSeguridad(List<ModuloSeguridad> modulosSeguridad) {
		this.modulosSeguridad = modulosSeguridad;
	}
	

}