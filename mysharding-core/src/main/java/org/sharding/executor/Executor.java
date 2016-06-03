package org.sharding.executor;

import java.sql.SQLException;

import org.sharding.executor.ExecuteCallback.StatmentCallback;
import org.sharding.executor.ExecuteCallback.PreparedStatementCallback;;
/**
 * 
 * @author wenlongliu
 *
 */
public interface Executor {

	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws SQLException 
	 */
	<T> T execute(ExecuteContext context, StatmentCallback<T> callback) throws SQLException;
	

	
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws SQLException 
	 */
	<T> T execute(ExecuteContext context, PreparedStatementCallback<T> callback) throws SQLException;
	
	
	
	//<T> T executeBatch(ExecuteContext context) throws SQLException;
}
