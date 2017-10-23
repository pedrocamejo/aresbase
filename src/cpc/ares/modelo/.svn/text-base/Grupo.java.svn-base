package cpc.ares.modelo;
import java.util.ArrayList;
import java.util.List;
import cva.pc.demeter.utilidades.Fecha;

public class Grupo extends EnteSeguridad {
	
	private static final long serialVersionUID = -7396487841852415037L;
	private String 		descripcionGrupo;
	private Fecha 		fechaCreacion;
	private String 		fechaString;
	private Sistema 	sistema;
	private  List<Usuario> usuarios; 
	private  List<Usuario> usuariosEliminar;
	public Grupo(){
		super(EnteSeguridad.GRUPO);
		usuarios = new ArrayList<Usuario>();
		usuariosEliminar = new ArrayList<Usuario>();
	}
	
	
	public Grupo(int idGrupo, String nombreGrupo, String descripcionGrupo, boolean estado, Fecha fechaCreacion ){
		super(EnteSeguridad.GRUPO);
		super.setId(idGrupo);
		super.setNombre(nombreGrupo);
		super.setEstado(estado);
		this.descripcionGrupo = descripcionGrupo;
		this.fechaCreacion = fechaCreacion;
		usuarios = new ArrayList<Usuario>();
		usuariosEliminar = new ArrayList<Usuario>();
		
	}
	
	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}
	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}

	public Fecha getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Fecha fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public String getFechaString() {
		return fechaString;
	}

	public void setFechaString(String fechaString) {
		this.fechaString = fechaString;
	}


	public List<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void agregarUsuario(Usuario usuario){
		this.usuarios.add(usuario);
	}
	
	public void agregarUsuarioEliminar(Usuario usuario){
		this.usuariosEliminar.add(usuario);
	}
	
	public void quitarUsuario(Usuario usuario){
		this.usuarios.remove(usuario);
	}
	
	public void quitarUsuarioEliminar(Usuario usuario){
		this.usuariosEliminar.remove(usuario);
	}


	public List<Usuario> getUsuariosEliminar() {
		return usuariosEliminar;
	}


	public void setUsuariosEliminar(List<Usuario> usuariosEliminar) {
		this.usuariosEliminar = usuariosEliminar;
	}
	
	public boolean contieneUsuario(int idUsuario){
		for (Usuario usuario: usuarios){
			if (usuario.getId()== idUsuario) return true;
		}
			
		return false;
	}
	@Override
	public String toString() {
		
		return this.getNombre();
	}


}
