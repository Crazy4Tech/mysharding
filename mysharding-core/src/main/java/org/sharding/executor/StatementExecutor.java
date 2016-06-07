package org.sharding.executor;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.sharding.executor.ExecuteCallback.PreparedStatementCallback;
import org.sharding.executor.ExecuteCallback.StatmentCallback;
import org.sharding.jdbc.ShardResultSet;
import org.sharding.router.RouteUnit;
import org.sharding.router.Router;

/**
 * 
 * @author wenlong.liu
 *
 */
public class StatementExecutor implements Executor {
	
	private final static Logger logger = Logger.getLogger(StatementExecutor.class);

	@Override
	@SuppressWarnings("unchecked")
	public <T> T execute(final ExecuteContext context, final StatmentCallback<T> callback) throws SQLException {
		final ExecuteHandler handler = new ExecuteHandler(context);
		final Collection<RouteUnit> targets = Router.route(context);
		Collection<Future<Object>> futures = new ArrayList<Future<Object>>(targets.size());
		ExecutorService threadPool = context.getThreadPoolExecutor();
		for(final RouteUnit  target : targets)
		{
			logger.debug(target.toString());
			@SuppressWarnings("rawtypes")
			Future<Object> future = threadPool.submit(new Callable(){
				@Override
				public Object call() throws Exception {
					return handler.handle(target, callback);
			}});
			futures.add(future);
		}
		return (T) mergeResult(futures, context.getMergeContext());
	}

	/**
	 * 
	 */
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T execute(ExecuteContext context, final PreparedStatementCallback<T> callback) throws SQLException {
		final ExecuteHandler handler = new ExecuteHandler(context);
		final Collection<RouteUnit> targets = Router.route(context);
		Collection<Future<Object>> futures = new ArrayList<Future<Object>>(targets.size());
		ExecutorService threadPool = context.getThreadPoolExecutor();
		for(final RouteUnit  target : targets)
		{
			logger.debug(target.toString());
			@SuppressWarnings("rawtypes")
			Future<Object> future = threadPool.submit(new Callable(){
				@Override
				public Object call() throws Exception {
					return handler.handle(target, callback);
			}});
			futures.add(future);
		}
		return (T) mergeResult(futures, context.getMergeContext());
	}

	/**
	 * 合并结果集
	 * @param futures
	 * @return
	 */
	private Object mergeResult (Collection<Future<Object>> futures, MergeContext mergeContext) throws SQLException
	{
		try{
			List<Object> results = new ArrayList<Object>(futures.size());
			for(Future<Object> future : futures)
			{
				results.add(future.get());
			}
			
			if(results.isEmpty()){
				throw new SQLException("");
			}
			
			Object firstResult = results.get(0);
			if(firstResult instanceof Boolean)
				return mergeBooleanResult(results);
			else if(firstResult instanceof Integer)
				return mergeIntegerResult(results);
			else	
				return mergeResultSet(results, mergeContext);
		}catch(Exception ex){
			throw new SQLException(ex);
		}
	}
	
	private int mergeIntegerResult(List<Object> results)
	{
		int result = 0;
		for(Object each : results)
		{
			result = result + (int)each;
		}
		return result;
	}
	
	
	private boolean mergeBooleanResult(List<Object> results)
	{
		boolean result = true;
		for(Object each : results)
		{
			result = result & (boolean)each;
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	private ResultSet mergeResultSet(List<?> results, MergeContext mergeContext)
	{
		ShardResultSet result = new ShardResultSet((List<ResultSet>)results, mergeContext);
		return result;
	}
}
