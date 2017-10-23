package cpc.ares.persistencia;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.ares.modelo.Grupo;
import cpc.ares.modelo.Identidad;
import cpc.ares.modelo.Usuario;
import cpc.ares.utilidades.PoolDataSourceAres;

import cva.pc.demeter.excepciones.ExcArgumentoInvalido;

import cva.pc.demeter.utilidades.Fecha;



public class PerGrupo {	
	
	public static List<Grupo> obtenerGrupos() throws SQLException, ExcArgumentoInvalido, PropertyVetoException{
		List<Grupo> valores = new ArrayList<Grupo>();
		Connection connection = null;
		try {
			int idSistema = (Integer) SpringUtil.getBean("sistema");
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM tbl_ares_grupo INNER  JOIN tbl_ares_grupo_sistema ON tbl_ares_grupo.seq_idgrupo = tbl_ares_grupo_sistema.int_idgrupo where int_idsistema = "+idSistema);
			valores.clear();		
			while (rs.next()) {		
				Grupo grp = new Grupo(rs.getInt("seq_idgrupo"),rs.getString("str_nombre"),rs.getString("str_descripcion"), rs.getBoolean("bol_estado"),new Fecha(rs.getDate("dtm_fechacreacion")));
				grp.setUsuarios(obtenerMiembros(connection, rs.getInt("seq_idgrupo")));
				valores.add(grp);				
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
	}
		
	public static void salvar(Grupo grupo) throws SQLException, ExcArgumentoInvalido{		
		Connection connection = null;					
		String cadenaSQL =  null;
		
		try {
			connection = PoolDataSourceAres.getInstance().getConection();				
			Statement statement = connection.createStatement();			
	
			if (grupo.getId()>0){
				cadenaSQL = new String("UPDATE tbl_ares_grupo "+
						 				" SET str_descripcion = '"  +grupo.getDescripcionGrupo()+"'" 	+								                 
						 				" WHERE seq_idgrupo = "+grupo.getId());
			    statement.executeUpdate(cadenaSQL);
			    
			}else{
				ResultSet rs = statement.executeQuery("SELECT nextval('tbl_ares_grupo_seq_idgrupo_seq') AS next_id");
				if (rs.next()) {
					grupo.setId(rs.getInt("next_id"));
				}
				cadenaSQL = new String("INSERT INTO tbl_ares_grupo"+
						  				" VALUES ("+grupo.getId()+",1,'"+grupo.getNombre()+"','"+grupo.getDescripcionGrupo()+"',true, '"+grupo.getFechaCreacion().obtenerFormatoSQL()+"')");
				statement.executeUpdate(cadenaSQL.toString());
			}
			salvarMiembrosDeGrupo(connection,grupo);
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
	
	private static void salvarMiembrosDeGrupo(Connection connection,Grupo grupo) throws SQLException, ExcArgumentoInvalido{
				
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
	}
	
	
}
