package cpc.ares.utilidades;

import cpc.ares.interfaz.IAplicacion;
import cpc.ares.interfaz.IComando;
import cpc.ares.modelo.AccionFuncionalidad;






public class FactoriaFuncionalidad {
	
	public IComando construir(String valor, AccionFuncionalidad funcionalidad, IAplicacion AppDemeter){
		IComando comando = null;
		try {
			comando= (IComando) Class.forName(valor).newInstance();
			comando.setAccionFuncionalidad(funcionalidad);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			AppDemeter.mostrarError(e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			AppDemeter.mostrarError(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			AppDemeter.mostrarError(e.getMessage());
		} 
		return comando;
	}
	
}
