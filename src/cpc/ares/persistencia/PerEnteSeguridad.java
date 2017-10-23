package cpc.ares.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cpc.ares.modelo.AccesoFuncionalidad;
import cpc.ares.modelo.AccionFuncionalidad;
import cpc.ares.modelo.EnteSeguridad;
import cpc.ares.modelo.ModuloSeguridad;
import cpc.ares.modelo.Perfil;
import cpc.ares.modelo.Sistema;
import cpc.ares.utilidades.PoolDataSourceAres;

import cva.pc.demeter.excepciones.ExcArgumentoInvalido;


public class PerEnteSeguridad {
	
	
	public static List<EnteSeguridad> todosEntesSeguridad() throws SQLException, ExcArgumentoInvalido{
		List<EnteSeguridad> valores = new ArrayList<EnteSeguridad>();
		EnteSeguridad valor = null;
		Connection connection = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();			
			String tiraSQL = new String("SELECT DISTINCT viw_enteseguridad.tipo, viw_enteseguridad.str_tipo, viw_enteseguridad.int_id, viw_enteseguridad.str_nombre, viw_enteseguridad.bol_estado  FROM viw_enteseguridad WHERE viw_enteseguridad.bol_estado ");
			ResultSet rs = statement.executeQuery(tiraSQL);
			valores.clear();		
			while (rs.next()) {				
				valor = new EnteSeguridad(rs.getBoolean("tipo"));
				valor.setId(rs.getInt("int_id"));
				valor.setNombre(rs.getString("str_nombre"));
				valor.setEstado(true);
				valor.setConfigurado(false);				
				valor.setPerfilesAcceso(cargarPerfiles(valor, connection));
				valores.add(valor);				
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
		return valores;
	}
	
	
	
	public static List<EnteSeguridad> todosEntesSeguridadPorSistema() throws SQLException, ExcArgumentoInvalido{
		List<EnteSeguridad> valores = new ArrayList<EnteSeguridad>();
		EnteSeguridad valor = null;
		Connection connection = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			int idSistema = Sistema.getSistema();
			String tiraSQL = new String("SELECT * from viw_enteseguridad where bol_estado and int_idsistema = "+idSistema);
			ResultSet rs = statement.executeQuery(tiraSQL);
			valores.clear();		
			while (rs.next()) {				
				valor = new EnteSeguridad(rs.getBoolean("tipo"));
				valor.setId(rs.getInt("int_id"));
				valor.setNombre(rs.getString("str_nombre"));
				valor.setEstado(true);
				valor.setConfigurado(false);
				valor.setAccesos(cargarModulos(connection, valor,idSistema));
				valores.add(valor);				
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
		return valores;
	}
	
	
	private static List<Perfil> cargarPerfiles(EnteSeguridad ente, Connection connection) throws ExcArgumentoInvalido, SQLException{
		List<Perfil> perfiles = new ArrayList<Perfil>();
		Perfil perfil = null;
		try {
			
			Statement statement = connection.createStatement();			
			String tiraSQL = new String("SELECT viw_enteseguridad.*, tbl_ares_sistema.str_nombresistema, tbl_ares_sistema.str_nombrelogo  from viw_enteseguridad " +   
						" Left JOIN tbl_ares_sistema ON tbl_ares_sistema.seq_idsistema = viw_enteseguridad.int_idsistema " + 
						" Where (viw_enteseguridad.bol_estado or viw_enteseguridad.bol_estado is null) and (int_id= "+ente.getId()+" or int_id is null ");
			ResultSet rs = statement.executeQuery(tiraSQL);
			System.out.println("SQL - Ente Seguridad "+tiraSQL);
			perfiles.clear();		
			while (rs.next()) {				
				perfil = new Perfil();
				//perfil.setAccesos(cargarModulos(connection, ente));
				perfil.setModulosSeguridad(cargarModulos(connection,ente,rs.getInt("int_idsistema")));
				perfil.setNombreSistema(rs.getString("str_nombresistema"));
				perfiles.add(perfil);
				
			}
		} catch (SQLException e) {
			throw e;
		}
		return perfiles;
	}
	

	public static List<ModuloSeguridad> cargarModulosSistemaActual(Connection connection, EnteSeguridad ente) throws SQLException{
		List<ModuloSeguridad> valores = new ArrayList<ModuloSeguridad>();
		ModuloSeguridad valor = null;
		try {
			int idSistema = Sistema.getSistema();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT DISTINCT seq_idmodulo, nommod FROM viw_accesos_visibles Where int_idsistema = "+ idSistema +" Order by seq_idmodulo");
			valores.clear();		
			while (rs.next()) {				
				valor = new ModuloSeguridad();
				valor.setId(rs.getInt("seq_idmodulo"));
				valor.setDescripcion(rs.getString("nommod"));
				//valor.setAccesos(cargarFuncionalidades(connection, ente.getId(), valor.getId(), ente.isTipo(), ente.isConfigurado()));
				valor.setAccesos(cargarAccionesFuncionalidades(connection, ente.getId(), valor.getId(), ente.isTipo(), ente.isConfigurado()));
				valores.add(valor);				
			}
		} catch (SQLException e) {
			throw e;
		}
		return valores;
	}
	
	public static List<ModuloSeguridad> cargarModulos(Connection connection, EnteSeguridad ente, int idSistema) throws SQLException{
		List<ModuloSeguridad> valores = new ArrayList<ModuloSeguridad>();
		ModuloSeguridad valor = null;
		try {			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT DISTINCT seq_idmodulo, nommod FROM viw_accesos_visibles Where int_idsistema = "+ idSistema +" Order by seq_idmodulo");
			valores.clear();		
			while (rs.next()) {				
				valor = new ModuloSeguridad();
				valor.setId(rs.getInt("seq_idmodulo"));
				valor.setDescripcion(rs.getString("nommod"));
				//valor.setAccesos(cargarFuncionalidades(connection, ente.getId(), valor.getId(), ente.isTipo(), ente.isConfigurado()));
				valor.setAccesos(cargarAccionesFuncionalidades(connection, ente.getId(), valor.getId(), ente.isTipo(), ente.isConfigurado()));
				valores.add(valor);				
			}
		} catch (SQLException e) {
			throw e;
		}
		return valores;
	}
	
	
	
	public static List<AccesoFuncionalidad> cargarFuncionalidades(Connection connection, int idEnte, int idmodulo, boolean tipo, boolean configurado) throws SQLException{
		List<AccesoFuncionalidad> valores = new ArrayList<AccesoFuncionalidad>();
		StringBuilder tiraSql = new StringBuilder();
		AccesoFuncionalidad valor = null;
		try {
			Statement statement = connection.createStatement();
			tiraSql.append("SELECT distinct * FROM viw_accesos_visibles Where tipousuario = ");
			tiraSql.append(tipo);
			tiraSql.append(" and int_idgrupo = ");
			tiraSql.append(idEnte);
			tiraSql.append(" and seq_idmodulo = ");
			tiraSql.append(idmodulo);
			tiraSql.append(" Order by seq_idfuncionalidad");
			ResultSet rs = statement.executeQuery(tiraSql.toString());
			valores.clear();
			String salida = null;
			while (rs.next()) {				
				valor = new AccesoFuncionalidad();
				valor.setId(rs.getInt("seq_idfuncionalidad"));
				valor.setDescripcion(rs.getString("str_nombre"));
				valor.setAccionMaxima(new AccionFuncionalidad(rs.getString("str_masaccmax")));
				salida = rs.getString("str_mascara");
				if (salida == null){
					valor.setVisible(false);
					valor.setAccionEnte(new AccionFuncionalidad("0000000000000000"));
				}else{
					configurado = true;
					valor.setVisible(true);
					valor.setAccionEnte(new AccionFuncionalidad(salida));
				}
				valores.add(valor);				
			}
		} catch (SQLException e) {
			throw e;
		}
		return valores;
	}
	
	
	private static List<AccesoFuncionalidad> cargarAccionesFuncionalidades(Connection connection, int idEnte, int idmodulo, boolean tipo, boolean configurado) throws SQLException{
		List<AccesoFuncionalidad> valores = new ArrayList<AccesoFuncionalidad>();
		StringBuilder tiraSql = new StringBuilder();
		AccesoFuncionalidad valor = null;
		try {
			Statement statement = connection.createStatement();
			tiraSql.append("SELECT distinct * FROM viw_accesos_visibles Where tipousuario = ");
			tiraSql.append(tipo);
			tiraSql.append(" and int_idgrupo = ");
			tiraSql.append(idEnte);
			tiraSql.append(" and seq_idmodulo = ");
			tiraSql.append(idmodulo);
			tiraSql.append(" Order by seq_idfuncionalidad");
			ResultSet rs = statement.executeQuery(tiraSql.toString());
			valores.clear();
			String salida = null;
			while (rs.next()) {				
				valor = new AccesoFuncionalidad();
				valor.setId(rs.getInt("seq_idfuncionalidad"));
				valor.setNombre(rs.getString("str_nombre"));
				valor.setIcono(rs.getString("str_icono"));
				valor.setAccionMaxima(new AccionFuncionalidad(rs.getString("str_masaccmax")));
				salida = rs.getString("str_mascara");
				if (salida == null){
					valor.setVisible(false);
					valor.setAccionEnte(new AccionFuncionalidad("0000000000000000"));
				}else{
					configurado = true;
					valor.setVisible(true);
					valor.setAccionEnte(new AccionFuncionalidad(salida));
				}
				valores.add(valor);				
			}
		} catch (SQLException e) {
			throw e;
		}
		return valores;
	}
	
	
	
	public static void salvar(EnteSeguridad ente) throws SQLException, ExcArgumentoInvalido{		
		Connection connection = null;
		StringBuilder tiraSql;
		String cadenaInicial;
		String cadenaBorrado;
		if (ente.isTipo()){
			cadenaInicial ="INSERT INTO tbl_ares_permiso_usuario (int_idfuncionalidad, int_idusuario, str_mascara) VALUES(";
			cadenaBorrado = "DELETE FROM tbl_ares_permiso_usuario WHERE int_idusuario = "+ente.getId();
		}else{
			cadenaInicial = "INSERT INTO tbl_ares_permiso_grupo (int_idfuncionalidad, int_idgrupo, str_mascara) VALUES(";
			cadenaBorrado = "DELETE FROM tbl_ares_permiso_grupo WHERE int_idgrupo = " +ente.getId();
		}
		try {
			connection = PoolDataSourceAres.getInstance().getConection();				
			Statement statement = connection.createStatement();
			@SuppressWarnings("unused")
			int resultado = statement.executeUpdate(cadenaBorrado);
			for (ModuloSeguridad modulos: ente.getAccesos()) {
				for (AccesoFuncionalidad accesos : modulos.getAccesos()){
					if (accesos.isVisible()){
						tiraSql = new StringBuilder();
						tiraSql.append(cadenaInicial);
						tiraSql.append(accesos.getId());
						tiraSql.append(", ");
						tiraSql.append(ente.getId());
						tiraSql.append(", '");
						tiraSql.append(accesos.getAccionMaxima().getMascara());
						tiraSql.append("')");
						System.out.println(tiraSql.toString());
						resultado = statement.executeUpdate(tiraSql.toString());
					}
				}
			}
		} catch (SQLException e) {
			throw e;
		} catch (ExcArgumentoInvalido e) {
			throw e;
		}
		finally{
			try{ 
				connection.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
