package org.sharding.router;

/**
 * 
 * @author wenlong.liu
 *
 */
public class RouteUnit {
	
	private String dataSource;
	
	private String sql;
	
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

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("dataSource=").append(dataSource);
		buffer.append(";sql=").append(sql);
		return buffer.toString();
	}
	
	
}
