package org.sharding.parser;

import java.util.List;

import org.sharding.configuration.Configuration;
import org.sharding.shard.Parameter;

import com.alibaba.druid.sql.ast.SQLStatement;

/**
 * 
 * @author pc
 *
 */
public class ParseContext {

	private List<Parameter<?>> parameters;
	
	private Configuration configuration;
	
	private SQLStatement statement;
	
	
	public void setParameters(List<Parameter<?>> parameters) {
		this.parameters = parameters;
	}

	public List<Parameter<?>> getParameters() {
		return parameters;
	}
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Configuration getConfiguration() {
		return configuration;
	}
	
	public void setSQLStatement(SQLStatement statement) {
		this.statement = statement;
	}

	public SQLStatement getSQLStatement() {
		return statement;
	}
}
