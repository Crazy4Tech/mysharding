package org.sharding.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.sharding.router.RouteUnit;
import org.sharding.executor.ExecuteCallback.StatmentCallback;
import org.sharding.executor.ExecuteCallback.PreparedStatementCallback;
import org.sharding.jdbc.ShardPreparedStatement;

/**
 * 
 * @author wenlong.liu
 *
 */
public class ExecuteHandler {
	
	private ExecuteContext executeContext;
	
	public ExecuteHandler(ExecuteContext executeContext){
		this.executeContext = executeContext;
	}

	/**
	 * 
	 * @param context
	 * @return
	 * @throws SQLException 
	 */
	<T> T handle(RouteUnit target, StatmentCallback<T> callback) throws SQLException
	{
		Statement statement = executeContext.getStatement().createStatement(target);
		for(PrepareCallback each : executeContext.getPrepareCallbacks())
		{
			each.call(statement);
		}
		T result = callback.execute(statement, target.getSql());
		return result;
	}
	
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws SQLException 
	 */
	<T> T handle(RouteUnit target, PreparedStatementCallback<T> callback) throws SQLException
	{
		PreparedStatement preparedStatement = ((ShardPreparedStatement)executeContext.getStatement()).createPreparedStatement(target);
		for(PrepareCallback each : executeContext.getPrepareCallbacks())
		{
			each.call(preparedStatement);
		}
		for(ParameterCallback each : executeContext.getParameterCallbacks())
		{
			each.call(preparedStatement);
		}
		T result = callback.execute(preparedStatement);
		return result;
	}
	
}
