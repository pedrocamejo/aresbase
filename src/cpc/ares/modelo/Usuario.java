package cpc.ares.modelo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import cpc.ares.persistencia.PerUsuario;

import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.TipDatPar;





public class Usuario extends EnteSeguridad implements Serializable {
   
	private static final long serialVersionUID = 7126369224407133541L;
	
	private String 					contrasena;   
    private String					idSede;
    private Date 					ultimoAcceso;
    private Perfil 					perfil; 
    private Identidad   			identidad;
    private Sede					sede;
    private UnidadFuncional 		unidadActiva;
    private int         			cantidadGrupos;
    private boolean     			debeCambiarClave;
    private Fecha       			fechaVenceClave;
    private List<Grupo> 			grupos;
    private List<TipDatPar> 		sistemas;

	private List<UnidadFuncional> 	unidadesAdministrativas;
    private List<UnidadFuncional> 	unidadAdministrativaEliminar;
   
	public Usuario(int idUsuario, String nombreUsuario, String contrasena, String idSede, Date ultimoAcceso, boolean estado) {
		super(EnteSeguridad.USUARIO);
		super.setId(idUsuario);
		super.setNombre(nombreUsuario);
		super.setEstado(estado);
		this.contrasena = contrasena;
		this.idSede = idSede;
		this.ultimoAcceso = ultimoAcceso;
		
	}

	public Usuario(){
		super(EnteSeguridad.USUARIO);
		contrasena = "";   
	    idSede = "";
		identidad = new Identidad();
    }
	
	public Usuario(int id){
		super(EnteSeguridad.USUARIO);
		super.setId(id);
		
    }
	
	public Usuario(int idUsuario, String nombreUsuario, String contrasena, int cantidadGrupo){
		super(EnteSeguridad.USUARIO);
		super.setId(idUsuario);
		super.setNombre(nombreUsuario);
    	this.contrasena = contrasena;
    	this.cantidadGrupos = cantidadGrupo;
    	
    }
	
    public Usuario(String nombreUsuario, String contrasena){
    	super(EnteSeguridad.USUARIO);
		super.setNombre(nombreUsuario);
    	this.contrasena = contrasena;
    }
      
	
	public String getContrasena() {
		return contrasena;
	}
	
	public Identidad getIdentidad(){
		return identidad;
		
	}

	public Sede getSede(){
		return sede; 
		
		
	}

	public Date getUltimoAcceso() {
		return ultimoAcceso;
	}

	public String getIdSede() {
		return idSede;
	}
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public void setIdentidad(Identidad identidad) {
		this.identidad = identidad;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public int getCantidadGrupos() {
		return cantidadGrupos;
	}

	public void setCantidadGrupos(int cantidadGrupos) {
		this.cantidadGrupos = cantidadGrupos;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public void setContrasena(String nuevaContrasena) {
		this.contrasena = nuevaContrasena;
		
	}
	@Override
	public String toString() {	
		return this.getNombre();
	}

	public boolean isDebeCambiarClave() {
		return debeCambiarClave;
	}

	public void setDebeCambiarClave(boolean debeCambiarClave) {
		this.debeCambiarClave = debeCambiarClave;
	}

	public Fecha getFechaVenceClave() {
		return fechaVenceClave;
	}

	public void setFechaVenceClave(Fecha fechaVenceClave) {
		this.fechaVenceClave = fechaVenceClave;
	}

	public List<UnidadFuncional> getUnidadesAdministrativas() {
		return unidadesAdministrativas;
	}

	public String getListaUnidades() {
		StringBuilder cadena = new StringBuilder();
		for (UnidadFuncional unidad : unidadesAdministrativas) {
			//cadena.append("'");
			cadena.append(PerUsuario.getListaUnidades(unidad));
			//cadena.append("',");
		}
		//cadena.deleteCharAt(cadena.length()-1);
		if (cadena.length()>0){
			cadena.deleteCharAt(0);
		}
		cadena.insert(0, "(");
		cadena.append(")");
		return cadena.toString();
	}
	
	public void setUnidadesAdministrativas(List<UnidadFuncional> unidadAdministrativa) {
		this.unidadesAdministrativas = unidadAdministrativa;
	}

	public List<UnidadFuncional> getUnidadAdministrativaEliminar() {
		return unidadAdministrativaEliminar;
	}

	public void setUnidadAdministrativaEliminar(
			List<UnidadFuncional> unidadAdministrativaEliminar) {
		this.unidadAdministrativaEliminar = unidadAdministrativaEliminar;
	}
	
	public boolean contieneUnidadAdministrativa(String codigo){
		for (UnidadFuncional ua : unidadesAdministrativas) {
			System.out.println("Estudiado : "+ua.getId()+ "  Buscado "+ codigo);
			if (ua.getId().trim().equals(codigo.trim())){
				System.out.println(" --- > Encontrado");
				return true;
			}
		}
		return false;
	}

	public UnidadFuncional getUnidadActiva() {
		return unidadActiva;
	}

	public void setUnidadActiva(UnidadFuncional unidadActiva) {
		this.unidadActiva = unidadActiva;
	}
	
    public final List<TipDatPar> getSistemas() {
		return sistemas;
	}

	public final void setSistemas(List<TipDatPar> sistemas) {
		this.sistemas = sistemas;
	}

	public static Usuario getUsuario(List<Usuario> usuarios, int id){
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id)
				return usuario;
		}
		return null;
	}
	
	public final void setCedula(String cedula) {
		identidad.setCedula(cedula);
	}

	public final void setNombreIdentidad(String nombre) {
		identidad.setNombre(nombre);
	}

	public final void setApellido(String apellido) {
		identidad.setApellido(apellido);
	}

	public final void setCorreo(String correo) {
		identidad.setCorreo(correo);
	}

	public String getCedula() {
		return identidad.getCedula();
	}

	public String getNombreIdentidad() {
		return identidad.getNombre();
	}

	public String getApellido() {
		return identidad.getApellido();
	}

	public String getCorreo() {
		return identidad.getCorreo();
	}
	
}