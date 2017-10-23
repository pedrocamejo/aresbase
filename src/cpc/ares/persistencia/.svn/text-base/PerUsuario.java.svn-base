package cpc.ares.persistencia;

import java.beans.PropertyVetoException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zkplus.spring.SpringUtil;

import cpc.ares.interfaz.IAplicacion;
import cpc.ares.interfaz.IComando;
import cpc.ares.interfaz.IPerSede;
import cpc.ares.modelo.AccionFuncionalidad;
import cpc.ares.modelo.Funcionalidad;
import cpc.ares.modelo.Grupo;
import cpc.ares.modelo.Identidad;
import cpc.ares.modelo.Modulo;
import cpc.ares.modelo.Perfil;
import cpc.ares.modelo.Sede;
import cpc.ares.modelo.Sistema;
import cpc.ares.modelo.UnidadFuncional;
import cpc.ares.modelo.Usuario;
import cpc.ares.utilidades.FactoriaFuncionalidad;
import cpc.ares.utilidades.PoolDataSourceAres;
import cva.pc.demeter.excepciones.ExcAccesoInvalido;
import cva.pc.demeter.excepciones.ExcArgumentoInvalido;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcEntradaInvalida;
import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.TipDatPar;

public class PerUsuario {

	public static final int ID = 0;
	public static final int NOMBRE_USUARIO = 1;
	public static final int CEDULA = 2;
	public static final int NOMBRE = 3;
	public static final int APELLIDO = 4;

	public static List<Usuario> obtenerUsuarios() throws SQLException,
			ExcArgumentoInvalido {
		List<Usuario> valores = new ArrayList<Usuario>();
		Connection connection = null;
		// IPerSede sede = Sistema.getSedes();
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			String sql = new String(
					"SELECT * from tbl_ares_usuario Order by seq_idusuario");
			ResultSet rs = statement.executeQuery(sql);
			valores.clear();
			while (rs.next()) {
				Usuario usr = new Usuario(rs.getInt("seq_idusuario"), rs
						.getString("str_nombreusuario"), rs
						.getString("str_contrasena"), rs
						.getInt("int_cant_grupos"));
				usr.setSede(PerSede.cargarSede(rs.getString("str_idsede")));
				usr.setEstado(rs.getBoolean("bol_estado"));
				usr.setIdentidad(cargarIdentidad(usr.getId()));
				usr.setDebeCambiarClave(rs.getBoolean("bol_cambiar_clave"));
				usr.setSistemas(obtenerSistemas(connection, usr.getId()));
				usr.setGrupos(obtenerGrupos(connection, usr.getId()));
				usr.setUnidadesAdministrativas(obtenerUnidadesAdministrativas(
						connection, usr.getId()));

				valores.add(usr);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ExcAccesoInvalido e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (cpc.ares.excepciones.ExcAccesoInvalido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return valores;
	}

	public static List<TipDatPar> obtenerSistemas(Connection connection,
			int idUsuario) throws SQLException {
		Statement statement = connection.createStatement();
		List<TipDatPar> sistemas = new ArrayList<TipDatPar>();
		StringBuilder sql = new StringBuilder(
				"select * FROM tbl_ares_usuario_sistema JOIN tbl_ares_sistema on seq_idsistema = int_idsistema ");
		sql.append("WHERE int_idusuario = ");
		sql.append(idUsuario);
		System.out.println(sql.toString());
		ResultSet rs = statement.executeQuery(sql.toString());
		while (rs.next()) {
			sistemas.add(new TipDatPar(rs.getInt("seq_idsistema"), rs
					.getString("str_nombresistema")));
		}
		return sistemas;
	}

	public static Usuario verificarUsuario(String login, String clave,
			int idSistema, IAplicacion AppAres) throws ExcAccesoInvalido,
			NoSuchAlgorithmException, SQLException, ExcArgumentoInvalido {

		Usuario usuario;
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql
				.append("SELECT seq_idusuario, str_nombreusuario, bol_estado, str_contrasena, str_idsede, int_cant_grupos, bol_cambiar_clave, dtm_fechacreacion, MD5('");
		cadenaSql.append(clave);
		cadenaSql
				.append("') as claveCifrada from viw_usuarios_en_sistema WHERE str_nombreusuario = '");
		cadenaSql.append(login);
		cadenaSql.append("' AND int_idsistema =");
		cadenaSql.append(idSistema);
		Connection connection = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			if (rs.next()) {
				if (!rs.getBoolean("bol_estado"))
					throw new ExcAccesoInvalido(
							"No tiene acceso a este sistema");
				if (!rs.getString("str_contrasena").equals(
						rs.getString("claveCifrada")))
					throw new ExcAccesoInvalido("Clave invalida");
				usuario = new Usuario(rs.getInt("seq_idusuario"), rs
						.getString("str_nombreusuario"), rs
						.getString("str_contrasena"), rs
						.getString("str_idsede"), rs
						.getDate("dtm_fechacreacion"), rs
						.getBoolean("str_idsede"));
				usuario.setCantidadGrupos(rs.getInt("int_cant_grupos"));
				usuario.setPerfil(cargarPerfil(usuario.getId(), AppAres));
				usuario
						.setIdentidad(cargarIdentidad(rs
								.getInt("seq_idusuario")));
				usuario.setSede(PerSede.cargarSede(rs.getString("str_idsede")));
				usuario.setDebeCambiarClave(rs.getBoolean("bol_cambiar_clave"));
				usuario
						.setUnidadesAdministrativas(obtenerUnidadesAdministrativas(
								connection, usuario.getId()));
			} else
				throw new ExcAccesoInvalido("Usuario no valido");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcArgumentoInvalido(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return usuario;
	}

	// Modificacion AdS. Wilker Yépez 19/10/2011
	public static Usuario verificarUsuarioXSede(String login, String clave,
			Sede sede, int idSistema, IAplicacion AppAres)
			throws ExcAccesoInvalido, NoSuchAlgorithmException, SQLException,
			ExcArgumentoInvalido {

		Usuario usuario;
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql
				.append("SELECT seq_idusuario, str_nombreusuario, bol_estado, str_contrasena, str_idsede, int_cant_grupos, bol_cambiar_clave, dtm_fechacreacion, MD5('");
		cadenaSql.append(clave);
		cadenaSql
				.append("') as claveCifrada from viw_usuarios_en_sistema WHERE str_nombreusuario = '");
		cadenaSql.append(login);
		cadenaSql.append("' AND int_idsistema =");
		cadenaSql.append(idSistema);
		Connection connection = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			if (rs.next()) {
				if (!rs.getBoolean("bol_estado"))
					throw new ExcAccesoInvalido("No tiene acceso a este sistema");
				if (!rs.getString("str_contrasena").equals(rs.getString("claveCifrada")))
					throw new ExcAccesoInvalido("Clave invalida");
				if (sede == null)
					throw new ExcAccesoInvalido("Seleccione una sede por favor");
				if (rs.getString("str_idsede").equals(sede.getIdSede())
						|| (!rs.getString("str_idsede")
								.equals(sede.getIdSede()) && AppAres.conAccesoMultisede(rs.getInt("seq_idusuario")))) {
					usuario = new Usuario(rs.getInt("seq_idusuario"), rs
							.getString("str_nombreusuario"), rs
							.getString("str_contrasena"), sede.getIdSede(), rs
							.getDate("dtm_fechacreacion"), rs
							.getBoolean("str_idsede"));
					usuario.setCantidadGrupos(rs.getInt("int_cant_grupos"));
					usuario.setPerfil(cargarPerfil(usuario.getId(), AppAres));
					usuario.setIdentidad(cargarIdentidad(rs
							.getInt("seq_idusuario")));
					usuario.setSede(sede);
					usuario.setDebeCambiarClave(rs
							.getBoolean("bol_cambiar_clave"));
					usuario
							.setUnidadesAdministrativas(obtenerUnidadesAdministrativas(
									connection, usuario.getId()));
				} else
					throw new ExcAccesoInvalido(
							"No tiene acceso a la sede seleccionada");
			} else
				throw new ExcAccesoInvalido("Usuario no valido");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcArgumentoInvalido(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return usuario;
	}

	// // Modificacion AdS. Wilker Yépez 19/10/2011
	// public static Usuario verificarUsuarioXSede(String login, String clave,
	// Sede sede, int idSistema, IAplicacion AppAres) throws ExcAccesoInvalido,
	// NoSuchAlgorithmException, SQLException, ExcArgumentoInvalido{
	//
	// Usuario usuario;
	// StringBuilder cadenaSql = new StringBuilder();
	// cadenaSql.append("SELECT seq_idusuario, str_nombreusuario, bol_estado, str_contrasena, str_idsede, int_cant_grupos, bol_cambiar_clave, dtm_fechacreacion, MD5('");
	// cadenaSql.append(clave);
	// cadenaSql.append("') as claveCifrada from viw_usuarios_en_sistema WHERE str_nombreusuario = '");
	// cadenaSql.append(login);
	// cadenaSql.append("' AND int_idsistema =") ;
	// cadenaSql.append(idSistema) ;
	// cadenaSql.append(" AND str_idsede = '") ;
	// cadenaSql.append(sede.getIdSede()) ;
	// cadenaSql.append("'") ;
	// Connection connection = null;
	// //IPerSede sede = Sistema.getSedes();
	// System.out.println(cadenaSql.toString());
	// try {
	// connection = PoolDataSourceAres.getInstance().getConection();
	// Statement statement = connection.createStatement();
	// ResultSet rs = statement.executeQuery(cadenaSql.toString());
	// if (rs.next()) {
	// if (!rs.getBoolean("bol_estado"))
	// throw new ExcAccesoInvalido("No tiene acceso a este sistema");
	// if (!rs.getString("str_contrasena").equals(rs.getString("claveCifrada")))
	// throw new ExcAccesoInvalido("Clave invalida");
	// usuario = new Usuario(rs.getInt("seq_idusuario"),
	// rs.getString("str_nombreusuario"),
	// rs.getString("str_contrasena"),rs.getString("str_idsede"),
	// rs.getDate("dtm_fechacreacion"),rs.getBoolean("str_idsede"));
	// usuario.setCantidadGrupos(rs.getInt("int_cant_grupos"));
	// usuario.setPerfil(cargarPerfil(usuario.getId(),AppAres)); // estaba
	// comentado -- virificar esto para la aplicacion Ares
	// usuario.setIdentidad(cargarIdentidad(rs.getInt("seq_idusuario")));
	// usuario.setSede(PerSede.cargarSede(rs.getString("str_idsede")));
	//				
	// System.out.println("Sede en Persistencia "+
	// usuario.getSede().getIdSede());
	//				
	// usuario.setDebeCambiarClave(rs.getBoolean("bol_cambiar_clave"));
	// usuario.setUnidadesAdministrativas(obtenerUnidadesAdministrativas(connection,usuario.getId()));
	// }else
	// throw new ExcAccesoInvalido("Usuario no valido");
	// } catch (SQLException e) {
	// e.printStackTrace();
	// throw e;
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new ExcArgumentoInvalido(e.getMessage());
	// }finally{
	// try{
	// connection.close();
	// }catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return usuario;
	// }

	public static Identidad cargarIdentidad(int idUsuario)
			throws ExcAccesoInvalido, NoSuchAlgorithmException, SQLException,
			ExcArgumentoInvalido {

		Identidad usuario = null;
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql
				.append("SELECT * from tbl_ares_usuario WHERE seq_idusuario = ");
		cadenaSql.append(idUsuario);
		Connection connection = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			if (rs.next()) {
				usuario = new Identidad(rs.getString("str_cedula"), rs
						.getString("str_nombre"), rs.getString("str_apellido"),
						rs.getString("str_correo"), rs
								.getString("str_nombrefoto"), new Fecha(rs
								.getDate("dtm_fechacreacion")));
			} else
				throw new ExcAccesoInvalido("Problemas con Usuario");
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return usuario;
	}

	public static Perfil cargarPerfil(int idUsuario, int idSistema,
			List<Modulo> modulos, IAplicacion AppAres) throws SQLException,
			ExcArgumentoInvalido {
		Perfil miPerfil = new Perfil(idSistema, modulos);
		actualizarModulos(miPerfil.getModulos(), idUsuario, AppAres);
		return miPerfil;
	}

	public static Perfil cargarPerfil(int idUsuario, IAplicacion AppDemeter)
			throws SQLException, ExcArgumentoInvalido {
		Perfil miPerfil = new Perfil();
		miPerfil.setModulos(PerUsuario.cargarModulos());
		actualizarModulos(miPerfil.getModulos(), idUsuario, AppDemeter);
		return miPerfil;
	}

	public static List<Modulo> obtenerPermisos(int idUsuario,
			IAplicacion AppAres) throws SQLException, ExcArgumentoInvalido {
		List<Modulo> valores = cargarModulos();
		for (Modulo modulo : valores) {
			modulo.setFuncionalidades(PerUsuario.cargarFuncionalidades(
					idUsuario, modulo.getIdmodulo(), AppAres));
		}
		return valores;
	}

	public static List<Modulo> obtenerArbolFuncional(int idSistema)
			throws SQLException, ExcArgumentoInvalido {

		List<Modulo> valores = new ArrayList<Modulo>();
		StringBuilder cadenaSql = new StringBuilder();
		Modulo modulo;
		cadenaSql
				.append("Select Distinct seq_idmodulo, tbl_ares_modulo.str_nombre, tbl_ares_modulo.bol_estado ");
		cadenaSql.append("from tbl_ares_modulo where int_idsistema = ");
		cadenaSql.append(idSistema);
		cadenaSql.append(" and tbl_ares_modulo.bol_estado = true");
		valores.clear();
		Connection connection = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			while (rs.next()) {
				int i = rs.getInt("seq_idmodulo");
				String nombre = rs.getString("str_nombre");
				modulo = new Modulo(i, nombre);
				modulo.setFuncionalidades(cargarFuncionalidades(connection, i));
				valores.add(modulo);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return valores;
	}

	public static List<Modulo> cargarModulos() throws SQLException,
			ExcArgumentoInvalido {

		List<Modulo> valores = new ArrayList<Modulo>();
		int idSistema = (Integer) SpringUtil.getBean("sistema");
		StringBuilder cadenaSql = new StringBuilder();
		Modulo modulo;
		cadenaSql
				.append("Select Distinct seq_idmodulo, tbl_ares_modulo.str_nombre, tbl_ares_modulo.bol_estado ");
		cadenaSql.append("from tbl_ares_modulo where int_idsistema = ");
		cadenaSql.append(idSistema);
		cadenaSql.append(" and tbl_ares_modulo.bol_estado = true");
		valores.clear();
		System.out.println(cadenaSql.toString());
		Connection connection = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			while (rs.next()) {
				int i = rs.getInt("seq_idmodulo");
				String nombre = rs.getString("str_nombre");
				modulo = new Modulo(i, nombre);
				valores.add(modulo);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return valores;
	}

	public static List<Modulo> cargarModulos(Connection connection,
			int idSistema) throws SQLException, ExcArgumentoInvalido {

		List<Modulo> valores = new ArrayList<Modulo>();
		StringBuilder cadenaSql = new StringBuilder();
		Modulo modulo;
		cadenaSql
				.append("Select Distinct seq_idmodulo, tbl_ares_modulo.str_nombre, tbl_ares_modulo.bol_estado ");
		cadenaSql.append("from tbl_ares_modulo where int_idsistema = ");
		cadenaSql.append(idSistema);
		cadenaSql.append(" and tbl_ares_modulo.bol_estado = true");

		valores.clear();
		try {

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			while (rs.next()) {
				int i = rs.getInt("seq_idmodulo");
				String nombre = rs.getString("str_nombre");
				modulo = new Modulo(i, nombre);
				modulo.setFuncionalidades(cargarFuncionalidades(connection, i));
				valores.add(modulo);
			}
		} catch (SQLException e) {
			throw e;
		}
		return valores;
	}

	public static List<Modulo> actualizarModulos(List<Modulo> modulos,
			int idUsuario, IAplicacion AppAres) throws SQLException,
			ExcArgumentoInvalido {
		List<Modulo> valores = modulos;
		for (Modulo modulo : valores) {
			modulo.setFuncionalidades(PerUsuario.cargarFuncionalidades(
					idUsuario, modulo.getIdmodulo(), AppAres));
		}
		return valores;
	}

	private static List<Funcionalidad> cargarFuncionalidades(int idUsuario,
			int idModulo, IAplicacion AppAres) throws SQLException,
			ExcArgumentoInvalido {
		List<Funcionalidad> valores = new ArrayList<Funcionalidad>();
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql
				.append("SELECT * FROM viw_accesos_depurados where int_idusuario = ");
		cadenaSql.append(idUsuario);
		cadenaSql.append(" and int_idmodulo = ");
		cadenaSql.append(idModulo);
		cadenaSql.append(" order by int_orden asc");
		System.out.println(cadenaSql.toString());

		Connection connection = null;
		valores.clear();
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			while (rs.next()) {
				IComando funcionalidad = new FactoriaFuncionalidad().construir(
						rs.getString("str_comando"), new AccionFuncionalidad(rs
								.getString("mascara")), AppAres);
				funcionalidad.setId(rs.getInt("seq_idfuncionalidad"));
				cargarDatosComando(connection, funcionalidad, rs
						.getInt("seq_idfuncionalidad"));
				valores.add(new Funcionalidad(rs.getInt("seq_idfuncionalidad"),
						rs.getString("str_nombre"), funcionalidad, rs
								.getString("mascara"), rs
								.getString("str_icono")));
			}
		} catch (SQLException e) {

			throw e;
		} catch (NullPointerException e) {
			// TODO: handle exception
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return valores;
	}

	private static List<Funcionalidad> cargarFuncionalidades(
			Connection connection, int idModulo) throws SQLException,
			ExcArgumentoInvalido {
		List<Funcionalidad> valores = new ArrayList<Funcionalidad>();
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql.append("SELECT * FROM tbl_ares_funcionalidad WHERE");
		cadenaSql.append(" int_idmodulo = ");
		cadenaSql.append(idModulo);
		valores.clear();
		try {

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSql.toString());
			while (rs.next()) {
				valores.add(new Funcionalidad(rs.getInt("seq_idfuncionalidad"),
						rs.getString("str_nombre"), rs
								.getString("str_descripcion"), rs
								.getString("str_masaccmax"), rs
								.getString("str_icono")));
			}
		} catch (SQLException e) {
			throw e;
		}
		return valores;
	}

	private static void cargarDatosComando(Connection connection,
			IComando comando, int idFuncionalidad) throws SQLException {
		Statement statement = connection.createStatement();
		StringBuilder cadenaSql = new StringBuilder();
		cadenaSql
				.append("SELECT * FROM tbl_ares_parametros_comandos where int_idfuncionalidad = ");
		cadenaSql.append(idFuncionalidad);
		ResultSet rs = statement.executeQuery(cadenaSql.toString());
		while (rs.next()) {
			comando.agregarParametro("" + rs.getInt("int_idparameto"), rs
					.getString("str_valor"));
		}

	}

	public static Usuario obtenerPorCedula(String cedula)
			throws ExcArgumentoInvalido, SQLException {
		Connection connection = null;
		String cadenaSQL = new String(
				"Select * from tbl_ares_usuario where str_cedula = '" + cedula
						+ "'");
		Usuario usr = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSQL.toString());
			while (rs.next()) {
				usr = new Usuario(rs.getInt("seq_idusuario"), rs
						.getString("str_nombreusuario"), rs
						.getString("str_contrasena"), rs
						.getString("str_idsede"), new Date(0), rs
						.getBoolean("bol_estado"));
				usr.setDebeCambiarClave(rs.getBoolean("bol_cambiar_clave"));
			}
			return usr;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static List<Identidad> obtenerIdentidades()
			throws ExcArgumentoInvalido, SQLException {
		List<Identidad> valores = new ArrayList<Identidad>();
		Connection connection = null;
		String cadenaSQL = new String("Select * from tbl_ares_usuario");
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSQL.toString());
			while (rs.next()) {
				Identidad identidad = new Identidad(rs.getString("str_cedula"),
						rs.getString("str_nombre"), rs
								.getString("str_apellido"), rs
								.getString("str_correo"), "", new Fecha(rs
								.getDate("dtm_fechacreacion")));
				valores.add(identidad);
			}
			return valores;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static boolean verificarUsuario(Connection connection,
			Usuario usuario) throws SQLException, ExcEntradaInvalida,
			ExcEntradaInconsistente {
		StringBuilder tiraSql = new StringBuilder();
		tiraSql
				.append("Select distinct str_nombreusuario, str_cedula, seq_idusuario, bol_estado from tbl_ares_usuario ");
		tiraSql.append("where str_cedula = '");
		tiraSql.append(usuario.getIdentidad().getCedula().toUpperCase());
		tiraSql.append("'");
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(tiraSql.toString());
		System.out.println(tiraSql.toString());
		if (rs.next()) {
			usuario.setId(rs.getInt("seq_idusuario"));
			if (!rs.getBoolean("bol_estado")) {
				System.out.println("Usuario inactivo : "
						+ rs.getString("str_nombreusuario"));
				throw new ExcEntradaInconsistente("Usuario existente "
						+ usuario.getNombre()
						+ " inactivo : desea reactivarlo?");
			}
			if (rs.getString("str_nombreusuario").equals(usuario.getNombre())) {
				return true;
			} else {
				System.out.println("Usuario existente con otra identidad : "
						+ rs.getString("str_nombreusuario"));
				throw new ExcEntradaInvalida("Usuario existente "
						+ usuario.getNombre() + " con otra identidad : "
						+ rs.getString("str_nombreusuario"));
			}

		} else {
			return false;
		}
	}

	public static Usuario salvar(Usuario usuario) throws SQLException,
			ExcArgumentoInvalido {
		Connection connection = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			connection.setAutoCommit(false);
			if (usuario.getId() != 0)
				modificar(connection, usuario);
			else
				agregar(connection, usuario);
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			try {
				connection.setAutoCommit(true);
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return usuario;
	}

	public static void agregar(Connection connection, Usuario usuario)
			throws SQLException, ExcArgumentoInvalido {
		StringBuilder tiraSql = new StringBuilder();
		try {
			int id = -1;
			boolean valido = verificarUsuario(connection, usuario);
			System.out.println("Usuario valido :" + valido
					+ ", usuario activo :" + usuario.isEstado());
			Statement statement = connection.createStatement();
			if (!valido) {
				ResultSet rs = statement
						.executeQuery("SELECT nextval('tbl_ares_usuario_seq_idusuario_seq') AS next_id");
				if (rs.next()) {
					id = rs.getInt("next_id");
				}
				usuario.setId(id);
				tiraSql
						.append("INSERT INTO tbl_ares_usuario(str_nombreusuario, str_cedula, str_contrasena, str_nombre, str_apellido, str_correo, dtm_fechacreacion, bol_estado, str_idsede, bol_cambiar_clave, seq_idusuario )");
				tiraSql.append("values ('");
				tiraSql.append(usuario.getNombre().toLowerCase());
				tiraSql.append("','");
				tiraSql
						.append(usuario.getIdentidad().getCedula()
								.toUpperCase());
				tiraSql.append("',md5('" + usuario.getContrasena());
				tiraSql.append("'), '");
				tiraSql
						.append(usuario.getIdentidad().getNombre()
								.toUpperCase());
				tiraSql.append("', '");
				tiraSql.append(usuario.getIdentidad().getApellido()
						.toUpperCase());
				tiraSql.append("','");
				tiraSql
						.append(usuario.getIdentidad().getCorreo()
								.toLowerCase());
				tiraSql.append("', '");
				if (usuario.getIdentidad().getFechaCreacion() == null)
					usuario.getIdentidad().setFechaCreacion(new Fecha());
				tiraSql.append(usuario.getIdentidad().getFechaCreacion()
						.obtenerFormatoSQL());
				tiraSql.append("', true, '");
				tiraSql.append(usuario.getSede().getIdSede());
				tiraSql.append("',");
				tiraSql.append(usuario.isDebeCambiarClave());
				tiraSql.append(",");
				tiraSql.append(id);
				tiraSql.append(")");
				System.out.println(tiraSql.toString());
				statement.executeUpdate(tiraSql.toString());
				if (usuario.isEstado()) {
					salvarUsuarioSistemas(connection, usuario);
					salvarUnidadesAdministrativas(connection, usuario);
					salvarGruposUsuario(connection, usuario);
					eliminarUnidadesAdministrativas(connection, usuario);

				}
			}
		} catch (SQLException e) {
			usuario.setId(0);
			if (e.getMessage().indexOf("unq_nombre_usuario") > 0)
				throw new SQLException("El Usuario esta duplicado");
			else if (e.getMessage().indexOf("unq_unicedula") > 0)
				throw new SQLException(
						"Existe un Usuario con la cedula indicada");
			else
				throw e;
		} catch (Exception e) {
			usuario.setId(0);
			e.printStackTrace();
			throw new ExcArgumentoInvalido(e.getMessage());
		}
	}

	public static void modificar(Connection connection, Usuario usuario)
			throws SQLException, ExcArgumentoInvalido {
		StringBuilder tiraSql = new StringBuilder();
		Statement statement = connection.createStatement();
		try {
			tiraSql.append("UPDATE tbl_ares_usuario set str_cedula = '");
			tiraSql.append(usuario.getIdentidad().getCedula().toUpperCase()
					.toUpperCase());
			tiraSql.append("', str_nombre = '");
			tiraSql.append(usuario.getIdentidad().getNombre().toUpperCase());
			tiraSql.append("', str_apellido = '");
			tiraSql.append(usuario.getIdentidad().getApellido().toUpperCase());
			tiraSql.append("', str_correo = '");
			tiraSql.append(usuario.getIdentidad().getCorreo().toLowerCase());
			tiraSql.append("', str_idsede = '");
			tiraSql.append(usuario.getSede().getIdSede());
			tiraSql.append("', bol_cambiar_clave = ");
			tiraSql.append(usuario.isDebeCambiarClave());
			tiraSql.append(" where seq_idusuario = ");
			tiraSql.append(usuario.getId());
			System.out.println(tiraSql.toString());
			statement.executeUpdate(tiraSql.toString());
			if (usuario.isEstado()) {
				// statement.executeUpdate(tiraSql.toString());
				salvarUsuarioSistemas(connection, usuario);
				eliminarUnidadesAdministrativas(connection, usuario);
				salvarUnidadesAdministrativas(connection, usuario);
				salvarGruposUsuario(connection, usuario);
			}
		} catch (SQLException e) {
			if (e.getMessage().indexOf("unq_nombre_usuario") > 0)
				throw new SQLException("El Usuario esta duplicado");
			else if (e.getMessage().indexOf("unq_unicedula") > 0)
				throw new SQLException(
						"Existe un Usuario con la cedula indicada");
			else
				throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcArgumentoInvalido(e.getMessage());
		}
	}

	public static List<UnidadFuncional> getUnidades(Usuario usuario) {
		Connection connection = null;
		List<UnidadFuncional> unidades = new ArrayList<UnidadFuncional>();
		StringBuilder tiraSQL = new StringBuilder();
		String unidadesStr = usuario.getListaUnidades();
		tiraSQL
				.append("SELECT Distinct coduac, codemp, denuac, tipouni FROM viw_usuario_unidades_funcionales	WHERE codemp = '");
		if (usuario.getUnidadesAdministrativas().size() > 0)
			tiraSQL.append(usuario.getUnidadesAdministrativas().get(0)
					.getEmpresa());
		else
			tiraSQL.append("0001");
		if (unidadesStr.length() > 2) {
			tiraSQL.append("' and coduac in ");
			tiraSQL.append(unidadesStr);
		} else
			tiraSQL.append("'");
		System.out.println(tiraSQL.toString());
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(tiraSQL.toString());
			while (rs.next()) {
				unidades.add(new UnidadFuncional(rs.getString("coduac"), rs
						.getString("codemp"), rs.getString("denuac"), rs
						.getInt("tipouni")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ExcArgumentoInvalido e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return unidades;
	}

	public static String getListaUnidades(Connection connection,
			UnidadFuncional unidad) {
		String salida = new String();
		StringBuilder tiraSQL = new StringBuilder();
		tiraSQL.append("select cp_getunidades('");
		tiraSQL.append(unidad.getEmpresa());
		tiraSQL.append("', '");
		tiraSQL.append(unidad.getId());
		tiraSQL.append("', ");
		tiraSQL.append(unidad.getTipo());
		tiraSQL.append(")");
		System.out.println(tiraSQL.toString());
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(tiraSQL.toString());
			if (rs.next())
				salida = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return salida;
	}

	public static String getListaUnidades(UnidadFuncional unidad) {
		Connection connection = null;
		String salida = new String();
		StringBuilder tiraSQL = new StringBuilder();
		tiraSQL.append("select cp_getunidades('");
		tiraSQL.append(unidad.getEmpresa());
		tiraSQL.append("', '");
		tiraSQL.append(unidad.getId());
		tiraSQL.append("', ");
		tiraSQL.append(unidad.getTipo());
		tiraSQL.append(")");
		System.out.println(tiraSQL.toString());
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(tiraSQL.toString());
			if (rs.next())
				salida = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ExcArgumentoInvalido e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return salida;
	}

	public static boolean existeUsuario(String cedula) {
		boolean existe = false;
		Connection connection = null;
		String cadenaSQL = new String(
				"SELECT * FROM viw_usuarios_en_sistema where str_cedula = '"
						+ cedula.trim() + "'");
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSQL.toString());
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ExcArgumentoInvalido e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return existe;

	}

	public static Usuario obtener(int idUsuario) throws ExcArgumentoInvalido,
			SQLException, cpc.ares.excepciones.ExcAccesoInvalido {
		Connection connection = null;
		String cadenaSQL = new String(
				"Select * from tbl_ares_usuario where seq_idusuario = "
						+ idUsuario);
		Usuario usr = null;
		// IPerSede sede = Sistema.getSedes();
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSQL.toString());
			while (rs.next()) {
				usr = new Usuario(rs.getInt("seq_idusuario"), rs
						.getString("str_nombreusuario"), rs
						.getString("str_contrasena"), rs
						.getString("str_idsede"), new Date(0), rs
						.getBoolean("bol_estado"));
				usr.setCantidadGrupos(rs.getInt("int_cant_grupos"));
				usr.setSede(PerSede.cargarSede(usr.getIdSede()));
				usr.setIdentidad(cargarIdentidad(usr.getId()));
				usr.setDebeCambiarClave(rs.getBoolean("bol_cambiar_clave"));
				usr.setGrupos(PerGrupo.obtenerGruposPorUsuario(connection,
						idUsuario));
				usr.setUnidadesAdministrativas(obtenerUnidadesAdministrativas(
						connection, idUsuario));
			}
			return usr;
		} catch (SQLException e) {
			throw e;
		} catch (ExcAccesoInvalido e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return usr;
	}

	public static Usuario obtenerPorNombreUsuario(String nombreUsuario)
			throws ExcArgumentoInvalido, SQLException,
			cpc.ares.excepciones.ExcAccesoInvalido {
		Connection connection = null;
		String cadenaSQL = new String(
				"Select * from tbl_ares_usuario where str_nombreusuario = '"
						+ nombreUsuario) + "'";
		Usuario usr = null;
		// IPerSede sede = Sistema.getSedes();
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSQL.toString());
			while (rs.next()) {
				int idUsuario = rs.getInt("seq_idusuario");
				usr = new Usuario(idUsuario, rs
						.getString("str_nombreusuario"), rs
						.getString("str_contrasena"), rs
						.getString("str_idsede"), new Date(0), rs
						.getBoolean("bol_estado"));
				usr.setCantidadGrupos(rs.getInt("int_cant_grupos"));
				usr.setSede(PerSede.cargarSede(usr.getIdSede()));
				usr.setIdentidad(cargarIdentidad(usr.getId()));
				usr.setDebeCambiarClave(rs.getBoolean("bol_cambiar_clave"));
				usr.setGrupos(PerGrupo.obtenerGruposPorUsuario(connection,
						idUsuario));
				usr.setUnidadesAdministrativas(obtenerUnidadesAdministrativas(
						connection, idUsuario));
			}
			return usr;
		} catch (SQLException e) {
			throw e;
		} catch (ExcAccesoInvalido e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return usr;

	}

	private static List<UnidadFuncional> obtenerUnidadesAdministrativas(
			Connection connection, int idUsuario) throws SQLException,
			ExcArgumentoInvalido, ExcAccesoInvalido {
		List<UnidadFuncional> valores = new ArrayList<UnidadFuncional>();
		StringBuilder tiraSql = new StringBuilder();
		try {
			Statement statement = connection.createStatement();
			tiraSql
					.append("SELECT * FROM viw_usuario_unidades_funcionales	WHERE int_idusuario = ");
			tiraSql.append(idUsuario);
			ResultSet rs = statement.executeQuery(tiraSql.toString());
			valores.clear();
			while (rs.next()) {
				valores.add(new UnidadFuncional(rs.getString("coduac"), rs
						.getString("codemp"), rs.getString("denuac"), rs
						.getInt("tipouni")));
			}
		} catch (SQLException e) {
			throw e;
		}
		return valores;
	}

	private static List<Grupo> obtenerGrupos(Connection connection,
			int idUsuario) throws SQLException, ExcArgumentoInvalido,
			ExcAccesoInvalido {
		List<Grupo> valores = new ArrayList<Grupo>();
		StringBuilder tiraSql = new StringBuilder();
		try {
			Statement statement = connection.createStatement();
			tiraSql
					.append("SELECT int_idgrupo, nombre_grupo, str_descripcion FROM viw_usuarios_en_grupo WHERE seq_idusuario = ");
			tiraSql.append(idUsuario);
			ResultSet rs = statement.executeQuery(tiraSql.toString());
			valores.clear();
			Grupo grupo;
			while (rs.next()) {
				grupo = new Grupo();
				grupo.setId(rs.getInt("int_idgrupo"));
				grupo.setNombre(rs.getString("nombre_grupo"));
				grupo.setDescripcionGrupo(rs.getString("str_descripcion"));
				valores.add(grupo);
			}
		} catch (SQLException e) {
			throw e;
		}
		return valores;
	}

	public static Usuario eliminar(Usuario usuario) throws SQLException,
			ExcArgumentoInvalido {
		Connection connection = null;
		String cadenaSQLEliminar = "DELETE FROM tbl_ares_usuario WHERE seq_idusuario = "
				+ usuario.getId() + " ";
		System.out.println(cadenaSQLEliminar);
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			@SuppressWarnings("unused")
			int resultado = statement.executeUpdate(cadenaSQLEliminar
					.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (ExcArgumentoInvalido e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();

			}

		}

		return usuario;
	}

	public static void cambiarClave(Usuario usuario) throws SQLException,
			ExcArgumentoInvalido {
		Connection connection = null;
		String cadenaSQL = "UPDATE tbl_ares_usuario SET str_contrasena = MD5('"
				+ usuario.getContrasena()
				+ "'), bol_cambiar_clave = false  WHERE str_nombreusuario = '"
				+ usuario.getNombre() + "'";
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(cadenaSQL.toString());
		} catch (SQLException e) {
			throw e;
		} catch (ExcArgumentoInvalido e) {
			throw e;
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public static List<Usuario> obtenerTodos() throws ExcArgumentoInvalido,
			SQLException, cpc.ares.excepciones.ExcAccesoInvalido {
		Connection connection = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		int idSistema = (Integer) SpringUtil.getBean("sistema");
		String cadenaSQL = new String(
				"SELECT * FROM tbl_ares_usuario where bol_estado = true and int_idsistema = "
						+ idSistema);
		Usuario usr = null;
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(cadenaSQL.toString());
			while (rs.next()) {
				usr = new Usuario(rs.getInt("seq_idusuario"), rs
						.getString("str_nombreusuario"), rs
						.getString("str_contrasena"), rs
						.getString("str_idsede"), new Date(0), rs
						.getBoolean("bol_estado"));
				usr.setCantidadGrupos(rs.getInt("int_cant_grupos"));
				usr.setDebeCambiarClave(rs.getBoolean("bol_cambiar_clave"));
				usr.setSede(PerSede.cargarSede(usr.getIdSede()));
				usr.setIdentidad(cargarIdentidad(usr.getId()));
				usuarios.add(usr);
			}
			return usuarios;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ExcAccesoInvalido e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return usuarios;
	}

	public static void agregarUsuarioASistema(Usuario usuario, int idSistema)
			throws SQLException, ExcArgumentoInvalido {
		Connection connection = null;
		String cadenaSQL = "INSERT INTO tbl_ares_usuario_sistema VALUES ((SELECT seq_idusuario FROM tbl_ares_usuario WHERE  str_nombreusuario = '"
				+ usuario.getNombre()
				+ "'),"
				+ idSistema
				+ ",'"
				+ new Fecha().obtenerFormatoSQL() + "')";
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(cadenaSQL.toString());
			System.out.println(cadenaSQL.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ExcArgumentoInvalido e) {
			e.printStackTrace();
			System.out.println(e.toString());
			throw e;
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public static void salvarUsuarioSistemas(Connection connection,
			Usuario usuario) throws SQLException {
		StringBuilder tiraSql = new StringBuilder();
		Statement statement = connection.createStatement();

		tiraSql
				.append("Delete FROM tbl_ares_usuario_sistema Where int_idusuario = ");
		tiraSql.append(usuario.getId());
		tiraSql.append(" and  int_idsistema not in ");
		tiraSql.append(obtenerIndices(usuario.getSistemas()));
		System.out.println(tiraSql);
		statement.executeUpdate(tiraSql.toString());
		List<TipDatPar> cargados = obtenerSistemas(connection, usuario.getId());
		if (usuario.getGrupos() != null) {
			for (TipDatPar sistema : usuario.getSistemas()) {
				if (!sistema.existe(cargados)) {
					tiraSql = new StringBuilder();
					tiraSql
							.append("INSERT INTO tbl_ares_usuario_sistema(int_idusuario, int_idsistema, dtm_fecha_agregacion) ");
					tiraSql.append("VALUES (");
					tiraSql.append(usuario.getId());
					tiraSql.append(",");
					tiraSql.append(sistema.getCodigo());
					tiraSql.append(", now() )");
					System.out.println(tiraSql.toString());
					statement.executeUpdate(tiraSql.toString());
				}
			}
		}

	}

	public static void salvarGruposUsuario(Connection connection,
			Usuario usuario) throws SQLException, ExcArgumentoInvalido {
		StringBuilder cadenaSQL = new StringBuilder();
		try {
			Statement statement = connection.createStatement();
			cadenaSQL
					.append("DELETE FROM tbl_ares_miembros_grupo where int_idusuario = ");
			cadenaSQL.append(usuario.getId());
			statement.executeUpdate(cadenaSQL.toString());
			if (usuario.getGrupos() != null) {
				for (Grupo grupo : usuario.getGrupos()) {
					cadenaSQL = new StringBuilder(
							"INSERT INTO tbl_ares_miembros_grupo (int_idgrupo, int_idusuario)VALUES (");
					cadenaSQL.append(grupo.getId());
					cadenaSQL.append(", ");
					cadenaSQL.append(usuario.getId());
					cadenaSQL.append(")");
					System.out.println(cadenaSQL.toString());
					statement.executeUpdate(cadenaSQL.toString());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcArgumentoInvalido(e.getMessage());
		}
	}

	public static void salvarUnidadesAdministrativas(Connection connection,
			Usuario usuario) throws SQLException, ExcArgumentoInvalido {
		try {
			Statement statement = connection.createStatement();
			if (usuario.getUnidadesAdministrativas() != null) {
				for (UnidadFuncional ua : usuario.getUnidadesAdministrativas()) {
					if (!estaAsociadoAUnidadAdministrativa(connection, usuario
							.getId(), ua.getId(), ua.getEmpresa())) {
						String cadenaSQL = "INSERT INTO tbl_ares_usuario_unidad_administrativa VALUES ("
								+ usuario.getId()
								+ ",'"
								+ ua.getId()
								+ "','"
								+ ua.getEmpresa() + "')";
						System.out.println(cadenaSQL.toString());
						statement.executeUpdate(cadenaSQL.toString());
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ExcArgumentoInvalido e) {
			e.printStackTrace();
			throw e;
		}

	}

	public static void eliminarUnidadesAdministrativas(Connection connection,
			Usuario usuario) throws SQLException, ExcArgumentoInvalido {
		try {
			Statement statement = connection.createStatement();
			if (usuario.getUnidadAdministrativaEliminar() != null)
				for (UnidadFuncional ua : usuario
						.getUnidadAdministrativaEliminar()) {
					String cadenaSQL = "DELETE FROM tbl_ares_usuario_unidad_administrativa WHERE int_idusuario = "
							+ usuario.getId()
							+ " AND str_codunidad ='"
							+ ua.getId()
							+ "' and codemp = '"
							+ ua.getEmpresa()
							+ "'";
					System.out.println(cadenaSQL.toString());
					statement.executeUpdate(cadenaSQL.toString());
				}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}

	}

	public static boolean estaAsociadoAUnidadAdministrativa(
			Connection connection, int idUsuario, String codUnidad,
			String empresa) throws SQLException, ExcArgumentoInvalido {
		try {
			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			String cadenaSQL = "SELECT * FROM tbl_ares_usuario_unidad_administrativa  WHERE int_idusuario = "
					+ idUsuario
					+ "  AND str_codunidad = '"
					+ codUnidad
					+ "' AND codemp = '" + empresa + "'";
			System.out.println(cadenaSQL);
			ResultSet rs = statement.executeQuery(cadenaSQL.toString());
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ExcArgumentoInvalido e) {
			e.printStackTrace();
			throw e;
		}
		return false;

	}

	public static List<Usuario> obtenerTodosPor(int tipoBusqueda, String valor)
			throws SQLException, ExcArgumentoInvalido, ExcAccesoInvalido,
			NoSuchAlgorithmException, cpc.ares.excepciones.ExcAccesoInvalido {
		List<Usuario> lista = new ArrayList<Usuario>();
		Connection connection = null;
		StringBuilder tiraSql = new StringBuilder();
		ResultSet rs = null;
		int idSistema = Sistema.getSistema();

		try {

			connection = PoolDataSourceAres.getInstance().getConection();
			Statement statement = connection.createStatement();
			tiraSql
					.append("SELECT * from viw_usuarios_en_sistema WHERE seq_idsistema = "
							+ idSistema + " order by seq_idusuario");
			switch (tipoBusqueda) {

			case ID:
				tiraSql = new StringBuilder();
				tiraSql
						.append("SELECT * from viw_usuarios_en_sistema WHERE seq_idsistema = "
								+ idSistema
								+ "  AND seq_idusuario = "
								+ valor
								+ " order by seq_idusuario");
				break;

			case NOMBRE_USUARIO:
				tiraSql = new StringBuilder();
				tiraSql
						.append("SELECT * from viw_usuarios_en_sistema WHERE seq_idsistema = "
								+ idSistema
								+ "  AND str_nombreusuario = '"
								+ valor.toLowerCase()
								+ "' order by seq_idusuario");
				break;

			case CEDULA:
				tiraSql = new StringBuilder();
				tiraSql
						.append("SELECT * from viw_usuarios_en_sistema WHERE seq_idsistema = "
								+ idSistema
								+ " AND str_cedula like '%"
								+ valor
								+ "%' order by seq_idusuario");
				break;

			case NOMBRE:
				tiraSql = new StringBuilder();
				tiraSql
						.append("SELECT * from viw_usuarios_en_sistema WHERE seq_idsistema = "
								+ idSistema
								+ " AND str_nombre like '%"
								+ valor
								+ "%' order by seq_idusuario");
				break;

			case APELLIDO:
				tiraSql = new StringBuilder();
				tiraSql
						.append("SELECT * from viw_usuarios_en_sistema WHERE seq_idsistema = "
								+ idSistema
								+ " AND str_apellido like '%"
								+ valor + "%'order by seq_idusuario");
				break;
			}

			rs = statement.executeQuery(tiraSql.toString());
			while (rs.next()) {
				Usuario usr = new Usuario(rs.getInt("seq_idusuario"), rs
						.getString("str_nombreusuario"), rs
						.getString("str_contrasena"), rs
						.getString("str_idsede"), new Date(0), rs
						.getBoolean("bol_estado"));
				usr.setCantidadGrupos(rs.getInt("int_cant_grupos"));
				usr.setDebeCambiarClave(rs.getBoolean("bol_cambiar_clave"));
				usr.setSede(PerSede.cargarSede(usr.getIdSede()));
				usr.setIdentidad(cargarIdentidad(usr.getId()));
				lista.add(usr);

			}

			return lista;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static Sede obtenerSede(String codigoBuscar) throws SQLException,
			ExcArgumentoInvalido, ExcAccesoInvalido {
		IPerSede pSede = Sistema.getSedes();
		List<Sede> sedes = (List<Sede>) pSede.obtenerSedes();
		for (Sede sede : sedes) {
			if (sede.getIdSede().equals(codigoBuscar))
				return sede;
		}
		return null;
	}

	public static String obtenerIndices(List<TipDatPar> lista) {
		StringBuilder cadena = new StringBuilder("(");
		for (TipDatPar salida : lista) {
			cadena.append(salida.getCodigo());
			cadena.append(",");
		}
		cadena.deleteCharAt(cadena.length() - 1);
		cadena.append(")");
		return cadena.toString();
	}

}
