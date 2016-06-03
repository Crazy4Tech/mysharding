package org.sharding.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.sharding.jdbc.DataSourceFactory;
import org.sharding.jdbc.ShardDataSource;
import junit.framework.TestCase;

public class InsertTest  extends TestCase{

	ShardDataSource datasource;
	
	@Override
	protected void setUp() throws Exception {
		String resource = "org/sharding/configuration/configuration.xml";
		datasource = new DataSourceFactory(resource).openDataSource();
	}

	public void test() throws Exception{
		String orderSql = "INSERT INTO `t_order` (`order_id`, `user_id`, `status`) VALUES (?, ?, ?)";
        String orderItemSql = "INSERT INTO `t_order_item` (`order_item_id`, `order_id`, `user_id`, `status`) VALUES (?, ?, ?, ?)";
        for (int orderId = 1; orderId <= 4; orderId++) {
            for (int userId = 1; userId <= 2; userId++) {
                try (Connection connection = datasource.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(orderSql);
                    preparedStatement.setInt(1, orderId);
                    preparedStatement.setInt(2, userId);
                    preparedStatement.setString(3, "insert");
                    preparedStatement.execute();
                    preparedStatement.close();
                
                    preparedStatement = connection.prepareStatement(orderItemSql);
                    int orderItemId = orderId + 4;
                    preparedStatement.setInt(1, orderItemId);
                    preparedStatement.setInt(2, orderId);
                    preparedStatement.setInt(3, userId);
                    preparedStatement.setString(4, "insert");
                    preparedStatement.execute();
                    preparedStatement.close();
                // CHECKSTYLE:OFF
                } catch (final Exception ex) {
                // CHECKSTYLE:ON
                    ex.printStackTrace();
                }
            }
        }
	}
}
