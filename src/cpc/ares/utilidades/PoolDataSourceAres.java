package cpc.ares.utilidades;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Spring;

import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zkplus.spring.SpringUtil;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cva.pc.demeter.excepciones.ExcArgumentoInvalido;

public class PoolDataSourceAres {
	private static PoolDataSourceAres poolDataSource;
	private ComboPooledDataSource comboPooledDataSource;

	private PoolDataSourceAres() {
		new ComboPooledDataSource();
	}

	public static synchronized PoolDataSourceAres getInstance() {
		if (poolDataSource == null)
			poolDataSource = new PoolDataSourceAres();
		return poolDataSource;
	}

	public Connection getConection() throws SQLException, ExcArgumentoInvalido {
		if (comboPooledDataSource == null)
			comboPooledDataSource = obtenerConexion();
		if (comboPooledDataSource == null) 
			throw new ExcArgumentoInvalido("El DataSource con Nombre= Ares NO EXISTE");
		return comboPooledDataSource.getConnection();
	}
	
	private ComboPooledDataSource obtenerConexion(){
		return (ComboPooledDataSource) SpringUtil.getBean("datasourceAres");
	}
	
}