package cpc.ares.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import cpc.ares.modelo.Sede;
import cpc.ares.modelo.Sistema;
import cpc.ares.modelo.UnidadFuncional;
import cpc.ares.utilidades.PoolDataSourceAres;

import cva.pc.demeter.excepciones.ExcArgumentoInvalido;
import cva.pc.demeter.utilidades.TipDatPar;
import cva.pc.demeter.utilidades.TipDatParString;

public class PerOtros {
	
	public static List<Sede> obtenerSedes() throws ExcArgumentoInvalido, SQLException{
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
				sede = new  Sede(rs.getString("codubifis"), rs.getString("desubifis"), new TipDatParString(rs.getString("codest"),""), true);
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
	
	public static List<UnidadFuncional> obtenerUnidadesFuncionales() throws ExcArgumentoInvalido, SQLException{
		Connection connection = null;
		List<UnidadFuncional> unidades = new ArrayList<UnidadFuncional>();
		UnidadFuncional unidad;
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql.append("SELECT * ");
		cadenaSql.append("FROM viw_unidades ");		
		try {
			PoolDataSourceAres poolConeccion = PoolDataSourceAres.getInstance();
			connection = poolConeccion.getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			while (rs.next()) {
				//sede = new  Sede(rs.getString("coduac").substring(1), rs.getString("denuac"), new TipDatParString(rs.getString("codemp"),rs.getString("coduac")), (temp == 'D'));
				unidad = new  UnidadFuncional();
				unidad.setId(rs.getString("coduac"));
				unidad.setDescripcion(rs.getString("denuac"));
				unidad.setEmpresa(rs.getString("codemp"));
				unidad.setTipo(rs.getInt("int_tipo"));
				unidades.add(unidad);
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
		return unidades;
	}	
	
	public static List<TipDatPar> getSistemas() throws ExcArgumentoInvalido, SQLException{
		Connection connection = null;
		List<TipDatPar> sistemas = new ArrayList<TipDatPar>();
		TipDatPar sistema;
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql.append("SELECT * ");
		cadenaSql.append("FROM tbl_ares_sistema ");		
		try {
			PoolDataSourceAres poolConeccion = PoolDataSourceAres.getInstance();
			connection = poolConeccion.getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			while (rs.next()) {
				sistema = new  TipDatPar(rs.getInt("seq_idsistema"), rs.getString("str_nombresistema"));
				sistemas.add(sistema);
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
		return sistemas;
	}
	
	public static List<Sistema> obtenerSistemas() throws ExcArgumentoInvalido, SQLException{
		Connection connection = null;
		List<Sistema> sistemas = new ArrayList<Sistema>();
		Sistema sistema;
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql.append("SELECT * ");
		cadenaSql.append("FROM tbl_ares_sistema ");		
		try {
			PoolDataSourceAres poolConeccion = PoolDataSourceAres.getInstance();
			connection = poolConeccion.getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			while (rs.next()) {
				sistema = new  Sistema();
				sistema.setId(rs.getInt("seq_idsistema"));
				sistema.setDescripcion(rs.getString("str_descripcion"));
				sistema.setNombresistema(rs.getString("str_nombresistema"));
				sistema.setEstado(rs.getBoolean("str_estado"));
				sistemas.add(sistema);
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
		return sistemas;
	}
	
}