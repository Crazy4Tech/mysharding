package org.sharding.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import org.sharding.exception.MethodUnsupportedException;
import org.sharding.executor.ExecuteContext;
import org.sharding.executor.PrepareCallback;
/**
 * 
 * @author wenlongLiu
 *
 */
public abstract class AbstractStatement implements Statement {

	
	private boolean isClosed;
	private boolean isPoolable;
	private boolean isCloseOnCompletion;
	private int fetchDirection;
	private int fetchSize;
	private int maxFieldSize;
	private int maxRows;
	private int queryTimeout;
	private ShardConnection connection;
	protected String sql;
	protected int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
	protected int resultSetConcurrency =  ResultSet.CONCUR_READ_ONLY;
	protected int resultSetHoldability = 0;
	protected Collection<PrepareCallback> prepareCallbacks = new LinkedList<PrepareCallback>();
	protected Collection<Statement> statements = new LinkedList<Statement>();

	
	protected AbstractStatement(ShardConnection connection){
		this.connection = connection;
	}
	
	protected AbstractStatement(ShardConnection connection, int resultSetType, int resultSetConcurrency){
		this.connection = connection;
		this.resultSetType = resultSetType;
		this.resultSetConcurrency = resultSetConcurrency;
	}
	
	protected AbstractStatement(ShardConnection connection, int resultSetType, int resultSetConcurrency, int resultSetHoldability){
		this.connection = connection;
		this.resultSetType = resultSetType;
		this.resultSetConcurrency = resultSetConcurrency;
		this.resultSetHoldability = resultSetHoldability;
	}
	
	public abstract ExecuteContext bulidExecuteContext();
	 
	
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
	public void addBatch(String sql) throws SQLException {
		throw new MethodUnsupportedException("addBatch");
	}

	@Override
	public void clearBatch() throws SQLException {
		throw new MethodUnsupportedException("addBatch");
	}
	
	@Override
	public void cancel() throws SQLException {
		for(Statement each : getRouteStatements()){
			each.cancel();
		}
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		for(Statement each : getRouteStatements()){
			if(each.getWarnings()!=null)
				return each.getWarnings();
		}
		return null;
	}

	@Override
	public void clearWarnings() throws SQLException {
		for(Statement each : getRouteStatements()){
			each.clearWarnings();
		}
	}

	@Override
	public boolean isClosed() throws SQLException {
		return this.isClosed;
	}
	
	@Override
	public void close() throws SQLException {
		this.isClosed = true;
		for(Statement each : getRouteStatements()){
			each.close();
		}
	}

	@Override
	public int getFetchDirection() throws SQLException {
		return this.fetchDirection;
	}
	
	@Override
	public void setFetchDirection(final int direction) throws SQLException {
		this.fetchDirection = direction;
		prepareCallbacks.add(new PrepareCallback(){
			@Override
			public void call(Statement statement) throws SQLException {
				statement.setFetchDirection(direction);
			}
		});
	}

	@Override
	public int getFetchSize() throws SQLException {
		return this.fetchSize;
	}
	
	@Override
	public void setFetchSize(final int rows) throws SQLException {
		this.fetchSize = rows;
		prepareCallbacks.add(new PrepareCallback(){
			@Override
			public void call(Statement statement) throws SQLException {
				statement.setFetchSize(rows);
			}
		});
	}
	
	

	@Override
	public int getMaxFieldSize() throws SQLException {
		return this.maxFieldSize;
	}
	
	@Override
	public void setMaxFieldSize(final int max) throws SQLException {
		this.maxFieldSize = max;
		prepareCallbacks.add(new PrepareCallback(){
			@Override
			public void call(Statement statement) throws SQLException {
				statement.setMaxFieldSize(max);
			}
		});
	}

	@Override
	public int getMaxRows() throws SQLException {
		return this.maxRows;
	}
	
	@Override
	public void setMaxRows(final int max) throws SQLException {
		this.maxRows = max;
		prepareCallbacks.add(new PrepareCallback(){
			@Override
			public void call(Statement statement) throws SQLException {
				statement.setMaxRows(max);
			}
		});
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		return false;
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		return false;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return this.queryTimeout;
	}
	
	@Override
	public void setQueryTimeout(final int seconds) throws SQLException {
		this.queryTimeout = seconds;
		prepareCallbacks.add(new PrepareCallback(){
			@Override
			public void call(Statement statement) throws SQLException {
				statement.setQueryTimeout(seconds);
			}
		});
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		return this.resultSetConcurrency;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return this.resultSetHoldability;
	}

	@Override
	public int getResultSetType() throws SQLException {
		return this.resultSetType;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		int updates = 0;
		for(Statement each : getRouteStatements()){
			updates = updates + each.getUpdateCount();
		}
		return updates;
	}

	
	@Override
	public boolean isPoolable() throws SQLException {
		return this.isPoolable;
	}

	@Override
	public void setPoolable(final boolean poolable) throws SQLException {
		this.isPoolable = poolable;
		prepareCallbacks.add(new PrepareCallback(){
			@Override
			public void call(Statement statement) throws SQLException {
				statement.setPoolable(poolable);
			}
		});
	}
	
	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		return isCloseOnCompletion;
	}
	
	@Override
	public void closeOnCompletion() throws SQLException {
		this.isCloseOnCompletion = true;
		for(Statement each : getRouteStatements()){
			each.closeOnCompletion();
		}
		if(getRouteStatements().isEmpty()){
			prepareCallbacks.add(new PrepareCallback(){
				@Override
				public void call(Statement statement) throws SQLException {
					statement.closeOnCompletion();
				}
			});
		}
	}
	
	@Override
	public void setCursorName(final String name) throws SQLException {
		prepareCallbacks.add(new PrepareCallback(){
			@Override
			public void call(Statement statement) throws SQLException {
				statement.setCursorName(name);
			}
		});
	}

	@Override
	public void setEscapeProcessing(final boolean enable) throws SQLException {
		prepareCallbacks.add(new PrepareCallback(){
			@Override
			public void call(Statement statement) throws SQLException {
				statement.setEscapeProcessing(enable);
			}
		});
	}
	
	@Override
	public ResultSet getResultSet() throws SQLException {
		return null;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return null;
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return this.connection;
	}
	
	public String getSql() {
		return this.sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public Connection getConnection(String key) throws SQLException {
		return this.connection.getConnection(key);
	}
	
	
	public Collection<Statement> getRouteStatements() {
		return this.statements;
	}
}
