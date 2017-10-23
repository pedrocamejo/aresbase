package cpc.ares.interfaz;

import java.sql.SQLException;
import java.util.List;

import cpc.ares.modelo.Sede;

import cva.pc.demeter.excepciones.ExcAccesoInvalido;
import cva.pc.demeter.excepciones.ExcArgumentoInvalido;


public interface IPerSede {
	public Sede cargarSede(String idsede) throws SQLException, ExcArgumentoInvalido, ExcAccesoInvalido;
	public List<Sede> obtenerSedes() throws SQLException, ExcArgumentoInvalido, ExcAccesoInvalido;

}
