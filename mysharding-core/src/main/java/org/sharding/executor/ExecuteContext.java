package org.sharding.executor;

import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

import org.sharding.configuration.Configuration;
import org.sharding.jdbc.ShardConnection;
import org.sharding.jdbc.ShardStatement;
import org.sharding.shard.Parameter;

/**
 * 
 * @author wenlong.liu
 *
 */
public class ExecuteContext {

	private  ShardStatement  statement;
	
	private  Collection<Parameter<?>> parameters;
	
	private  Collection<ParameterCallback> parameterCallbacks;
	
	private  Collection<PrepareCallback> prepareCallbacks;
	
	private  MergeContext mergeContext;
	
	public ExecuteContext(ShardStatement statement){
		this.statement = statement;
	}
	
	
	public String getSql() {
		return statement.getSql();
	}
	
	public Collection<Parameter<?>> getParameters() {
		return parameters;
	}
	
	public void setParameters(Collection<Parameter<?>> params) {
		parameters = params;
	}
	
	public Collection<ParameterCallback> getParameterCallbacks() {
		return parameterCallbacks;
	}
	
	public void setParameterCallbacks(Collection<ParameterCallback> callbacks) {
		parameterCallbacks = callbacks;
	}
	
	public Collection<PrepareCallback> getPrepareCallbacks() {
		return prepareCallbacks;
	}
	
	public void setPrepareCallbacks(Collection<PrepareCallback> callbacks) {
		prepareCallbacks = callbacks;
	}
	
	public ShardStatement getStatement(){
		return this.statement;
	}
	
	public MergeContext getMergeContext() {
		return mergeContext;
	}

	public void setMergeContext(MergeContext mergeContext) {
		this.mergeContext = mergeContext;
	}
	
	public Configuration getConfiguration() {
		try {
			return ((ShardConnection)statement.getConnection()).getConfiguration();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ExecutorService getThreadPoolExecutor() {
		return getConfiguration().getThreadPoolExecutor();
	}
}
