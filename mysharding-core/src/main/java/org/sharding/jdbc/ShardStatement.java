package org.sharding.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sharding.executor.ExecuteCallback.StatmentCallback;
import org.sharding.executor.ExecuteContext;
import org.sharding.executor.StatementExecutor;
import org.sharding.router.RouteUnit;


/**
 * 
 * @author wenlongliu
 *
 */
public class ShardStatement extends AbstractStatement{

	
	
	public ShardStatement(ShardConnection connection){
		super(connection);
	}
	
	public ShardStatement(ShardConnection connection, int resultSetType, int resultSetConcurrency){
		super(connection, resultSetType, resultSetConcurrency);
	}
	
	public ShardStatement(ShardConnection connection, int resultSetType, int resultSetConcurrency, int resultSetHoldability){
		super(connection,resultSetType, resultSetConcurrency, resultSetHoldability);
	}
	
	@Override
	public boolean execute(final String sql) throws SQLException {
		this.setSql(sql);
		StatementExecutor statementExecutor = new StatementExecutor();
		return statementExecutor.execute(bulidExecuteContext(), new StatmentCallback<Boolean>(){
			@Override
			public Boolean execute(Statement statement, String sql) throws SQLException {
				return statement.execute(sql);
		}});
	}

	@Override
	public boolean execute(final String sql,  final int autoGeneratedKeys) throws SQLException {
		this.setSql(sql);
		StatementExecutor statementExecutor = new StatementExecutor();
		return statementExecutor.execute(bulidExecuteContext(), new StatmentCallback<Boolean>(){
			@Override
			public Boolean execute(Statement statement, String sql) throws SQLException {
				return statement.execute(sql, autoGeneratedKeys);
		}});
	}

	@Override
	public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
		this.setSql(sql);
		StatementExecutor statementExecutor = new StatementExecutor();
		return statementExecutor.execute(bulidExecuteContext(), new StatmentCallback<Boolean>(){
			@Override
			public Boolean execute(Statement statement, String sql) throws SQLException {
				return statement.execute(sql, columnIndexes);
		}});
	}

	@Override
	public boolean execute(final String sql, final String[] columnNames) throws SQLException {
		this.setSql(sql);
		StatementExecutor statementExecutor = new StatementExecutor();
		return statementExecutor.execute(bulidExecuteContext(), new StatmentCallback<Boolean>(){
			@Override
			public Boolean execute(Statement statement, String sql) throws SQLException {
				return statement.execute(sql, columnNames);
		}});
	}

	@Override
	public int[] executeBatch() throws SQLException {
		return null;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		this.setSql(sql);
		StatementExecutor statementExecutor = new StatementExecutor();
		return statementExecutor.execute(bulidExecuteContext(), new StatmentCallback<ResultSet>(){
			@Override
			public ResultSet execute(Statement statement, String sql) throws SQLException {
				return statement.executeQuery(sql);
		}});
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		this.setSql(sql);
		StatementExecutor statementExecutor = new StatementExecutor();
		return statementExecutor.execute(bulidExecuteContext(), new StatmentCallback<Integer>(){
			@Override
			public Integer execute(Statement statement, String sql) throws SQLException {
				return statement.executeUpdate(sql);
		}});
	}

	@Override
	public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
		this.setSql(sql);
		StatementExecutor statementExecutor = new StatementExecutor();
		return statementExecutor.execute(bulidExecuteContext(), new StatmentCallback<Integer>(){
			@Override
			public Integer execute(Statement statement, String sql) throws SQLException {
				return statement.executeUpdate(sql, autoGeneratedKeys);
		}});
	}

	@Override
	public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
		this.setSql(sql);
		StatementExecutor statementExecutor = new StatementExecutor();
		return statementExecutor.execute(bulidExecuteContext(), new StatmentCallback<Integer>(){
			@Override
			public Integer execute(Statement statement, String sql) throws SQLException {
				return statement.executeUpdate(sql, columnIndexes);
		}});
	}

	@Override
	public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
		this.setSql(sql);
		StatementExecutor statementExecutor = new StatementExecutor();
		return statementExecutor.execute(bulidExecuteContext(), new StatmentCallback<Integer>(){
			@Override
			public Integer execute(Statement statement, String sql) throws SQLException {
				return statement.executeUpdate(sql, columnNames);
		}});
	}

	@Override
	public ExecuteContext bulidExecuteContext() {
		ExecuteContext context = new ExecuteContext(this);
		context.setPrepareCallbacks(this.prepareCallbacks);
		return context;
	}

	public Statement createStatement(RouteUnit unit) throws SQLException{
		Statement statement = null;
		if(resultSetHoldability==0)
			statement = getConnection(unit.getDataSource()).createStatement(resultSetType, resultSetConcurrency);
		else
			statement = getConnection(unit.getDataSource()).createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		statements.add(statement);
		return statement;
	}
}
