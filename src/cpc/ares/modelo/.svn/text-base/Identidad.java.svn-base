package cpc.ares.modelo;

import java.io.Serializable;
import java.sql.SQLException;

import cpc.ares.persistencia.PerUsuario;
import cva.pc.demeter.excepciones.ExcArgumentoInvalido;

import cpc.ares.modelo.Sede;
import cva.pc.demeter.utilidades.Fecha;




public class Identidad  implements Serializable  {

	private static final long serialVersionUID = 847703828128907801L;
	private String cedula;   
	private String nombre;   
	private String apellido;   
	private String correo;   
	private String nombreFoto;   
	private Fecha fechaCreacion;   
	private Fecha ultimoAcceso;
	private Sede sede;
		 	  
	  
	public Identidad(String cedula, String nombre, String apellido,	String correo,  String nombreFoto,	Fecha fechaCreacion) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.nombreFoto = nombreFoto;
		this.fechaCreacion = fechaCreacion;
		
	}
	
	public Identidad() {
		super();
		this.cedula = "";
		this.nombre = "";
		this.apellido = "";
		this.correo = "";
		this.nombreFoto = "";
	}
	
	public final void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public final void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public final void setCorreo(String correo) {
		this.correo = correo;
	}

	public final void setNombreFoto(String nombreFoto) {
		this.nombreFoto = nombreFoto;
	}

	public final void setFechaCreacion(Fecha fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public final void setUltimoAcceso(Fecha ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}
	
	
	public  String getNombreCompleto(){
		StringBuilder cadena = new StringBuilder();
		cadena.append(nombre);
		cadena.append(" ");
		cadena.append(apellido);
		return cadena.toString();
	}
	
	public String getCedula() {
		return cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public String getNombreFoto() {
		return nombreFoto;
	}

	public Fecha getFechaCreacion() {
		return fechaCreacion;
	}

	public Fecha getUltimoAcceso() {
		return ultimoAcceso;
	}
	
	public Usuario getUsuario(String cedula){
		Usuario usr = null;		
		try {
			usr = PerUsuario.obtenerPorCedula(cedula);
		} catch (ExcArgumentoInvalido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usr;
		
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}
	  
	  
}
