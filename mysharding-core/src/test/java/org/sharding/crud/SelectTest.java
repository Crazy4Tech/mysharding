package org.sharding.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.sharding.jdbc.DataSourceFactory;
import org.sharding.jdbc.ShardDataSource;
import junit.framework.TestCase;

public class SelectTest  extends TestCase{

	ShardDataSource datasource;
	
	@Override
	protected void setUp() throws Exception {
		String resource = "org/sharding/configuration/configuration.xml";
		datasource = new DataSourceFactory(resource).openDataSource();
	}
	
	public void test() throws Exception{
		String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=? AND o.order_id=?";
		
		try( Connection connection = datasource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 1);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getInt(1));
                    System.out.println(rs.getInt(2));
                    System.out.println(rs.getInt(3));
                }
            }
		} catch ( Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
}
