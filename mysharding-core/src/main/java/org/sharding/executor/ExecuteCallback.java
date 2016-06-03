package org.sharding.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author wenlongliu
 *
 */
public  class ExecuteCallback {
	
	
	/**
	 * 
	 * @author wenlongliu
	 *
	 * @param <T>
	 */
	public static abstract class StatmentCallback<T> {
		
		public abstract T execute(final Statement statement, final String sql) 
				throws SQLException;
	}

	/**
	 * 
	 * @author wenlongliu
	 *
	 * @param <T>
	 */
	public static abstract class PreparedStatementCallback<T> {
		
		public abstract T execute(final PreparedStatement statement) 
				throws SQLException;

	}
}

