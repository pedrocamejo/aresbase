package cpc.ares.interfaz;

import java.sql.SQLException;
import java.util.List;


import cva.pc.demeter.excepciones.ExcArgumentoInvalido;


public interface IPersistencias<T> {
	//public static Solicitud obtener(long id, Sede oSede) throws SQLException, ExcArgumentoInvalido;
	public List<T> salvar(List<T> valores) throws SQLException, ExcArgumentoInvalido;
	public  void agregar(List<T> valores) throws SQLException, ExcArgumentoInvalido;
	public  void modificar(T valor) throws SQLException, ExcArgumentoInvalido;
	public  void eliminar(T valor) throws SQLException, ExcArgumentoInvalido;
	public  void anular(T valor) throws SQLException, ExcArgumentoInvalido;
	
}
