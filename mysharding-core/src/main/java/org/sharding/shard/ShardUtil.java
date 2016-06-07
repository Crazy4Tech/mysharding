package org.sharding.shard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 
 * @author wenlongLiu
 *
 */
public class ShardUtil {

	public static String getExactlyValue(String value) {
		if(value==null) return null;
		value = value.replace("[", "");
		value = value.replace("]", "");
		value = value.replace("`", "");
		value = value.replace("\"", "");
		return value;
    }
	
	
	public static OrderbyValue getOrderValue(Collection<OrderBy> orderbys, ResultSet resultset) throws SQLException {
		OrderbyValue orderbyValue = new OrderbyValue();
		for(OrderBy orderby : orderbys)
		{
			Object value = resultset.getObject(orderby.getName());
			orderbyValue.addValue(orderby.getName(), orderby.getOrderType(), value);
		}
		return orderbyValue;
    }
	
	
  
}
