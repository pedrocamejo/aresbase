package cpc.ares.interfaz;

import java.io.Serializable;
import java.util.Map;

import cpc.ares.modelo.AccionFuncionalidad;




public interface IComando extends Serializable {
	public void ejecutar();
	public void setId(int id);
	public int getId();
	public Object seleccionar(int id);
	public Map<String, Object> getParametros();
	public void setApp(IAplicacion app);
	public IAplicacion getApp();
	public void setParametros(Map<String, Object> parametros);
	public void setAccionFuncionalidad(AccionFuncionalidad funcionalidades);
	public AccionFuncionalidad getAccionFuncionalidad();
	public void agregarParametro(String codigo, Object valor);
}
