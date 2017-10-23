package cpc.ares.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cpc.ares.excepciones.ExcAccesoInvalido;
import cpc.ares.modelo.Sede;
import cpc.ares.utilidades.PoolDataSourceAres;
import cva.pc.demeter.excepciones.ExcArgumentoInvalido;
import cva.pc.demeter.utilidades.TipDatParString;

public class PerSede {
	
	public static Sede cargarSede(String idsede) throws SQLException, ExcArgumentoInvalido, ExcAccesoInvalido{
		Connection connection = null;
		Sede sede;
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql.append("SELECT * ");		   
		cadenaSql.append("FROM viw_ares_sigesp_sedes ");
		cadenaSql.append("WHERE codubifis = '");
		cadenaSql.append(idsede);
		cadenaSql.append("'");
		System.out.println(cadenaSql.toString());
		try {
			PoolDataSourceAres poolConeccion = PoolDataSourceAres.getInstance();
			connection = poolConeccion.getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			if (rs.next()) {
				sede = new  Sede(rs.getString("codubifis"), rs.getString("desubifis"), new TipDatParString(rs.getString("codest"),""),true);
				sede.setEmpresa(new TipDatParString(rs.getString("codemp"),""));
			}else
				throw new ExcAccesoInvalido("Problemas con la Sede");
		} catch (SQLException e) {
			throw e;
		}finally{
			try{ 
				connection.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sede;
	}

	public static List<Sede> obtenerSedes() throws ExcArgumentoInvalido, SQLException{
		Connection connection = null;
		List<Sede> sedes = new ArrayList<Sede>();
		Sede sede;
		try {
			PoolDataSourceAres poolConeccion = PoolDataSourceAres.getInstance();
			connection = poolConeccion.getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from viw_ares_sigesp_sedes");
			while (rs.next()) {
				sede = new  Sede(rs.getString("codubifis"), rs.getString("desubifis"), new TipDatParString(rs.getString("codest"),""),true);
				sede.setEmpresa(new TipDatParString(rs.getString("codemp"),""));
				sedes.add(sede);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			try{ 
				connection.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sedes;
	}
	
	
	public static List<Sede> obtenerSedesFisicas() throws ExcArgumentoInvalido, SQLException{
		Connection connection = null;
		List<Sede> sedes = new ArrayList<Sede>();
		Sede sede;
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql.append("SELECT * ");
		cadenaSql.append("FROM viw_ares_sigesp_sedes ");		
		try {
			PoolDataSourceAres poolConeccion = PoolDataSourceAres.getInstance();
			connection = poolConeccion.getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			while (rs.next()) {
				sede = new  Sede(rs.getString("codubifis"), rs.getString("desubifis"), new TipDatParString(rs.getString("codest"),""),true);
				sede.setEmpresa(new TipDatParString(rs.getString("codemp"),""));
				sedes.add(sede);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			try{ 
				connection.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sedes;
	}
	
	
}
