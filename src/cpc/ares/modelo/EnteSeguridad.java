package cpc.ares.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EnteSeguridad implements Serializable{
	
	public static final boolean GRUPO 	= false;
	public static final boolean USUARIO = true;
	
	private static final long serialVersionUID = -568383192765033986L;
	
	private boolean					tipo;
	private int 					id;
	private String 					nombre;
	private boolean 				configurado;
	private boolean 				estado;	
	private List<Perfil>			perfilesAcceso;
	private List<ModuloSeguridad> 	accesos;
	
	public List<ModuloSeguridad> getAccesos() {
		return accesos;
	}
	public void setAccesos(List<ModuloSeguridad> accesos) {
		this.accesos = accesos;
	}
	public EnteSeguridad(boolean tipo) {
		super();
		this.tipo = tipo;
		this.perfilesAcceso = new ArrayList<Perfil>(); 
	}
	public EnteSeguridad() {
		super();
		this.perfilesAcceso = new ArrayList<Perfil>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isConfigurado() {
		return configurado;
	}
	public void setConfigurado(boolean configurado) {
		this.configurado = configurado;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	public String getEstado() {
		if (estado)
			return "ACTIVO";
		else
			return "INACTIVO";
	}
	
	public boolean isTipo() {
		return tipo;
	}
	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	

	public static EnteSeguridad buscar(List<EnteSeguridad> lista, String valor){
		int id;
		boolean tipo;
		String subcadena = valor.substring(0, 1);
		if (subcadena.equals("G"))
			tipo = false;
		else
			tipo = true;
		subcadena = valor.substring(1);
		id = Integer.parseInt(subcadena);	
		for (EnteSeguridad codigo : lista) {			
			if (codigo.getId() == id && codigo.isTipo() == tipo)
				return codigo;
		}
		return null;
	}
	
	public List<Perfil> getPerfilesAcceso() {
		return perfilesAcceso;
	}
	public void setPerfilesAcceso(List<Perfil> perfilesAcceso) {
		this.perfilesAcceso = perfilesAcceso;
	}

}
