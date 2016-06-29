package org.sharding.router;

import org.sharding.parser.StatementType;

/**
 * 
 * @author wenlong.liu
 *
 */
public class RouteUnit {
	
	private String dataSource;
	
	private String sql;
	
	private StatementType statementType;
	
	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public StatementType getStatementType() {
		return statementType;
	}

	public void setStatementType(StatementType statementType) {
		this.statementType = statementType;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("dataSource=").append(dataSource);
		buffer.append(";sql=").append(sql);
		return buffer.toString();
	}

}
