package org.sharding.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.sharding.configuration.Configuration;

/**
 * 
 * @author wenlong.liu
 *
 */
public class ShardDataSource implements DataSource {

	private final Configuration configuration;
	private PrintWriter logWriter;
	private int loginTimeout;
	
	public ShardDataSource(Configuration configuration){
		this.configuration = configuration;
		this.logWriter = new PrintWriter(System.out);
	}
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return this.logWriter;
	}
	
	@Override
	public void setLogWriter(PrintWriter writer) throws SQLException {
		this.logWriter = writer;
	}
	
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}
	
	@Override
	public int getLoginTimeout() throws SQLException {
		return this.loginTimeout;
	}


	@Override
	public void setLoginTimeout(int timeout) throws SQLException {
		this.loginTimeout = timeout;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.getClass().isAssignableFrom(iface);
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		try {
			return (T) this;
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		return new ShardConnection(this);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}

	public Configuration getConfiguration(){
		return this.configuration;
	}
	
	public DataSource getDataSource(String key){
		return this.configuration.getDataSource(key);
	}
}
