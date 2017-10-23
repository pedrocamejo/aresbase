package cpc.ares.interfaz;

import java.util.List;

import cpc.ares.modelo.Sede;
import cpc.ares.modelo.UnidadFuncional;
import cpc.ares.modelo.Usuario;






public interface IAplicacion {

	
	
	public void mostrarError(String error);
	public void mostrarInformacion(String informacion);
	public void mostrarConfirmacion(String confirmacion);
	public void mostrarImpresion(String pregunta);
	public Object getContextProperty(String caracteristica);
	public void limpiarEscritorio();
	public void setMenuIni(IMenu menuIni) ;
	public void cargarInfUsuario(String usuario, Sede sede);
	public void cargarInfUsuario(String usuario, Sede sede, List<UnidadFuncional> unidades);
	public void cargarInfUsuario(String usuario, Sede sede,String foto);
	public void reiniciarInfUsaurio();
	public Sede getSede() ;
	public String getdatosUsuario() ;
	public void cambiarFuete(int tamano);
	public void configurarEscritorio() throws Exception;
	public void restablecerEscritorio() ;
	public void setUsuario(Usuario usuario);
	public Usuario getUsuario();
	public void setSede(Sede sede);
	public boolean conAccesoMultisede(int idUsuario);
}
