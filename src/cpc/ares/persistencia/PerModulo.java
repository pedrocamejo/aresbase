package cpc.ares.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import cpc.ares.modelo.Modulo;
import cpc.ares.utilidades.PoolDataSourceAres;
import cva.pc.demeter.excepciones.ExcArgumentoInvalido;
import cva.pc.demeter.utilidades.TipDatPar;

public class PerModulo {	
	
	public static List<Modulo> obtenerModulo() throws ExcArgumentoInvalido, SQLException{
		List<Modulo> valores = new ArrayList<Modulo>();
		Connection connection = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			String tiraSQL = new String("SELECT * FROM tbl_ares_modulo ");			
			ResultSet rs = statement.executeQuery(tiraSQL);		
			valores.clear();		
			while (rs.next()) {		
				Modulo stm = new Modulo();
			
					stm.setIdmodulo(rs.getInt("seq_idmodulo"));
					stm.setNombre(rs.getString("str_nombre"));
					stm.setEstado(rs.getBoolean("bol_estado"));
					stm.setSistemas(obtenerSistema(connection,rs.getInt("int_idsistema")).get(0));
				valores.add(stm);	
			}
		} catch (SQLException e) {
			throw e;
		}
		return valores;
	}
	public static List<TipDatPar> obtenerSistema(Connection connection, int idSistema) throws SQLException{
		Statement statement = connection.createStatement();
		List<TipDatPar> sistemas = new ArrayList<TipDatPar>(); 
		StringBuilder sql = new StringBuilder("select * FROM tbl_ares_sistema ");
		sql.append("WHERE seq_idsistema = ");
		sql.append(idSistema);
		System.out.println(sql.toString());
		ResultSet rs = statement.executeQuery(sql.toString());			
		while (rs.next()) {
			sistemas.add(new TipDatPar(rs.getInt("seq_idsistema"),rs.getString("str_nombresistema")));
		}
		return sistemas;
	}
	
	
	
/*	public static void salvar(Sistema sistema) throws SQLException, ExcArgumentoInvalido{		
		Connection connection = null;					
		String cadenaSQL =  null;
		
		try {
			connection = PoolDataSourceAres.getInstance().getConection();				
			Statement statement = connection.createStatement();			
	
			if (sistema.getId()>0){
				cadenaSQL = new String(
						"UPDATE tbl_ares_sistema "+
						" SET str_nombresistema = '"+sistema.getNombresistema()+"', str_descripcion = '"  +sistema.getDescripcion()+"', str_nombrelogo = '"  +sistema.getNombrelogo()+"'"
						+" WHERE seq_idsistema = "+sistema.getId());
			    statement.executeUpdate(cadenaSQL);
			}else{
				ResultSet rs = statement.executeQuery("SELECT nextval('tbl_ares_grupo_seq_idgrupo_seq') AS next_id");
				if (rs.next()) {
					sistema.setId(rs.getInt("next_id"));
				}
				cadenaSQL = new String(
						"INSERT INTO tbl_ares_sistema(seq_idsistema, str_nombresistema, str_descripcion, str_licencia, str_nombrelogo, str_estado, str_version)"+
						" VALUES ("+sistema.getId()+
						",'"+sistema.getNombresistema()+
						"','"+sistema.getDescripcion()+
						"','"+sistema.getLicencia()+
						"','"+sistema.getNombrelogo()+
						"','"+sistema.isEstado()+
						"','"+sistema.getVersion()+
						"')");
				statement.executeUpdate(cadenaSQL.toString());
			}
			//salvarMiembrosDeGrupo(connection,sistema);
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
	
	public static Sistema eliminar(Sistema sistema) throws SQLException, ExcArgumentoInvalido {
		Connection connection = null;		
		String cadenaSQLEliminar = "DELETE FROM tbl_ares_sistema WHERE seq_idsistema = "+sistema.getId()+"";
		try {			
			connection = PoolDataSourceAres.getInstance().getConection();				
			Statement statement = connection.createStatement();			
			statement.executeUpdate(cadenaSQLEliminar.toString());
			return sistema;
		} catch (SQLException e) {
			    if (e.getMessage().indexOf("tbl_ares_usuario_sistema")>0)
			    	e = new SQLException("No se puede Borrar un Sistema con usuarios asociados al mismo. Excluya los usuarios del Sistema e intente luego");
			    if (e.getMessage().indexOf("fk_tbl_ares_grupo_sistema_sistema")>0)
			    	e = new SQLException("No se puede Borrar un Sistema asociado a un Grupo. Quite todos los grupos del sistema");
				throw e;
		} catch (ExcArgumentoInvalido e) {
			throw e;
		}
		finally{
			try{
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}	
	}
/*	public static List<TipDatPar> obtenerSistemas(Connection connection, int idGrupo) throws SQLException{
		Statement statement = connection.createStatement();
		List<TipDatPar> sistemas = new ArrayList<TipDatPar>(); 
		StringBuilder sql = new StringBuilder("select * FROM tbl_ares_grupo_sistema JOIN tbl_ares_sistema on seq_idsistema = int_idsistema ");
		sql.append("WHERE int_idgrupo = ");
		sql.append(idGrupo);
		System.out.println(sql.toString());
		ResultSet rs = statement.executeQuery(sql.toString());			
		while (rs.next()) {
			sistemas.add(new TipDatPar(rs.getInt("seq_idsistema"),rs.getString("str_nombresistema")));
		}
		return sistemas;
	}
	
	public static List<Usuario> getUsuariosGrupo() throws SQLException, ExcArgumentoInvalido{
		List<Usuario> valores = new ArrayList<Usuario>();
		Connection connection = null;
		//IPerSede sede = Sistema.getSedes();
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			String sql = new String ("SELECT * from tbl_ares_usuario Order by seq_idusuario");
			ResultSet rs = statement.executeQuery(sql);			
			valores.clear();		
			while (rs.next()) {	
				Usuario usr = new Usuario(rs.getInt("seq_idusuario"),rs.getString("str_nombreusuario"),rs.getString("str_contrasena"),rs.getInt("int_cant_grupos"));
				usr.setSede(PerSede.cargarSede(rs.getString("str_idsede")));
				usr.setEstado(rs.getBoolean("bol_estado"));
				usr.setIdentidad(PerUsuario.cargarIdentidad(usr.getId()));

				
				valores.add(usr);
			}
		} catch (SQLException e) {
			throw e;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcArgumentoInvalido(e.getMessage());
			
		}finally{
			try{ 
				connection.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return valores;
	}
	
	public static List<Grupo> obtenerGruposPorUsuario(Connection connection, int idUsuario) throws SQLException, ExcArgumentoInvalido, PropertyVetoException{
		List<Grupo> valores = new ArrayList<Grupo>();		
		try {			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM viw_usuarios_en_grupo 	WHERE seq_idusuario = "+idUsuario);
			valores.clear();		
			while (rs.next()) {		
				Grupo grp = new Grupo(rs.getInt("int_idgrupo"),rs.getString("nombre_grupo"),rs.getString("str_descripcion"), rs.getBoolean("bol_estado"),new Fecha(rs.getDate("dtm_fechacreacion")));				
				valores.add(grp);				
			}
		} catch (SQLException e) {
			throw e;
		}
		return valores;
	}*/
		

/*	private static void salvarMiembrosDeGrupo(Connection connection,Grupo grupo) throws SQLException, ExcArgumentoInvalido{
				
		try {							
			Statement statement = connection.createStatement();
			String cadenaSQL = new String("DELETE FROM tbl_ares_miembros_grupo WHERE int_idgrupo = "+grupo.getId());							
			statement.executeUpdate(cadenaSQL);
			for (Usuario usuario : grupo.getUsuarios()) {
				if (!existeMiembroGrupo(connection, grupo.getId(), usuario.getId())){
					cadenaSQL = new String("INSERT INTO tbl_ares_miembros_grupo VALUES ("+grupo.getId()+","+usuario.getId()+")");							
					statement.executeUpdate(cadenaSQL);
				}
			}
			
		} catch (SQLException e) {							
			throw e;
		} 
		
	}
	
	public static boolean existeGrupo(String nombreGrupo) throws SQLException, ExcArgumentoInvalido{
		boolean existe = false;
		Connection connection = null;
		String cadenaSQL = new String("SELECT * FROM tbl_ares_grupo WHERE str_nombre = '"+nombreGrupo+"'");
		try {
			connection = PoolDataSourceAres.getInstance().getConection();				
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSQL.toString());
			if (rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}catch (ExcArgumentoInvalido e) {
			e.printStackTrace();
			throw e;
		}finally{
			try{ 
				connection.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return existe;
	}
		
	public static boolean existeMiembroGrupo(Connection connection, int idGrupo, int idUsuario) throws SQLException, ExcArgumentoInvalido{
		boolean existe = false;
		
		String cadenaSQL = new String("SELECT * FROM tbl_ares_miembros_grupo WHERE int_idgrupo = "+idGrupo +" AND int_idusuario = "+idUsuario);
		try {							
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSQL.toString());
			if (rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}		
		return existe;
	}

	public static Grupo obtener(int idGrupo) throws SQLException, ExcArgumentoInvalido {
		Connection connection = null;
		Grupo grp = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from tbl_ares_grupo where seq_idgrupo ="+idGrupo);
			
			while (rs.next()) {					
				grp = new Grupo(rs.getInt("seq_idgrupo"),rs.getString("str_nombre"),rs.getString("str_descripcion"), rs.getBoolean("bol_estado"),new Fecha(rs.getDate("dtm_fechacreacion")));
				grp.setUsuarios(obtenerMiembros(connection, idGrupo));
			}
			return grp;
		} catch (SQLException e) {
			throw e;
		}finally{
			try{ 
				connection.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static List<Usuario> obtenerMiembros(Connection connection, int idGrupo) throws SQLException{
		List<Usuario> miembros = new ArrayList<Usuario>();
		try {			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from viw_usuarios_en_grupo where int_idgrupo ="+idGrupo);
			
			while (rs.next()) {	
				Usuario usr = new Usuario(rs.getInt("seq_idusuario"),rs.getString("str_nombreusuario"),rs.getString("str_contrasena"),rs.getInt("int_cant_grupos"));
				usr.setEstado(rs.getBoolean("bol_estado"));
				usr.setIdentidad(new Identidad(rs.getString("str_cedula"),rs.getString("str_nombre"),rs.getString("str_apellido"),rs.getString("str_correo"),rs.getString("str_nombrefoto"),new Fecha(rs.getDate("dtm_fechacreacion"))));
				miembros.add(usr);				
			}
			return miembros;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public static Grupo eliminar(Grupo grupo) throws SQLException, ExcArgumentoInvalido {
		Connection connection = null;		
		String cadenaSQLEliminar = "DELETE FROM tbl_ares_grupo WHERE seq_idgrupo = "+grupo.getId()+"";
		try {			
			connection = PoolDataSourceAres.getInstance().getConection();				
			Statement statement = connection.createStatement();			
			statement.executeUpdate(cadenaSQLEliminar.toString());
			return grupo;
		} catch (SQLException e) {
			    if (e.getMessage().indexOf("tbl_ares_miembros_grupo")>0)
			    	e = new SQLException("No se puede Borrar un Grupo con Miembros asociados al mismo. Excluya los usuarios del Grupo e intente luego");
			    if (e.getMessage().indexOf("fk_tbl_ares_permiso_grupo_grupo")>0)
			    	e = new SQLException("No se puede Borrar un Grupo asociado a un Perfil. Quite todos los permisos del grupo");
				throw e;
		} catch (ExcArgumentoInvalido e) {
			throw e;
		}
		finally{
			try{
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			
		}	
	}*/	
}