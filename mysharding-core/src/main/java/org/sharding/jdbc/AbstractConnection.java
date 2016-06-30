package org.sharding.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Collection;
import org.sharding.executor.ConnectionCallback;

/**
 * 
 * @author wenlong.liu
 *
 */
public abstract class AbstractConnection extends AbstractUnsupportedOperationConnection {
	
	private boolean autoCommit = true;
    
    private boolean readOnly = true;
    
    private boolean closed;
    
    private int transactionIsolation = TRANSACTION_READ_UNCOMMITTED;
    
	private final Collection<Connection> connections = new ArrayList<Connection>();
	
	private final Collection<ConnectionCallback> callbacks = new ArrayList<ConnectionCallback>(3);

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.getClass().isAssignableFrom(iface);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		try {
			return (T) this;
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	@Override
	public void clearWarnings() throws SQLException {
		for(Connection conn : getRealConnections()){
			conn.clearWarnings();
		}
	}

	@Override
	public void close() throws SQLException {
		this.closed = true;
		for(Connection conn : getRealConnections()){
			conn.close();
		}
		getRealConnections().clear();
	}

	
	@Override
	public void commit() throws SQLException {
		for(Connection conn : getRealConnections()){
			conn.commit();
		}
	}
	
	@Override
	public void rollback() throws SQLException {
		for(Connection conn : getRealConnections()){
			conn.rollback();
		}
	}

	@Override
	public void setAutoCommit(final boolean autoCommit) throws SQLException {
		this.autoCommit = autoCommit;
		callbacks.add(new ConnectionCallback(){
			@Override
			public void execute(Connection connection) throws SQLException {
				connection.setAutoCommit(autoCommit);
			}});
	}
	
	@Override
	public boolean getAutoCommit() throws SQLException {
		return this.autoCommit;
	}

	
	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return null;
	}

	@Override
	public void setTransactionIsolation(final int level) throws SQLException {
		this.transactionIsolation = level;
		callbacks.add(new ConnectionCallback(){
			@Override
			public void execute(Connection connection) throws SQLException {
				connection.setTransactionIsolation(level);
			}});
	}
	
	@Override
	public int getTransactionIsolation() throws SQLException {
		return this.transactionIsolation;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return this.closed;
	}
	
	@Override
	public void setReadOnly(final boolean readOnly) throws SQLException {
		this.readOnly = readOnly;
		callbacks.add(new ConnectionCallback(){
			@Override
			public void execute(Connection connection) throws SQLException {
				connection.setReadOnly(readOnly);
			}});
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return this.readOnly;
	}

	@Override
	public int getHoldability() throws SQLException {
		 return ResultSet.CLOSE_CURSORS_AT_COMMIT;
	}
	
	@Override
	public void setHoldability(int arg0) throws SQLException {	
	}

	
	protected synchronized void  addRealConnection(Connection connection){
		connections.add(connection);
	}

	protected Collection<Connection> getRealConnections(){
		return connections;
	}

	public Collection<ConnectionCallback> getCallbacks() {
		return callbacks;
	}
	
}
