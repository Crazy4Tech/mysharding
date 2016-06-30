package org.sharding.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author pc
 *
 */
public abstract class AbstractCompositeResultSet extends AbstractResultsetWrapper {

	private boolean closed;
	 
	protected final List<ResultSet> delegates;
	
	protected final List<ResultSet> copyDelegates;
	
	/**
	 * 
	 * @param delegates
	 */
	protected AbstractCompositeResultSet(List<ResultSet> delegates){
		this.delegates = delegates;
		this.copyDelegates= new ArrayList<ResultSet>(delegates.size());
	}
	
	
	@Override
	public void close() throws SQLException {
		for(ResultSet resultset : delegates){
			resultset.close();
		}
		this.closed = true;
		this.delegates.clear();
		this.copyDelegates.clear();
	}

	


	@Override
	public boolean isClosed() throws SQLException {
		return this.closed;
	}
	
	@Override
	public void clearWarnings() throws SQLException {
		for(ResultSet resultset : delegates){
			resultset.clearWarnings();
		}
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
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.getClass().isAssignableFrom(iface);
	}
}
