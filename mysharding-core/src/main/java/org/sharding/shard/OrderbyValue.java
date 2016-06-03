package org.sharding.shard;

import java.util.ArrayList;
import java.util.List;

import org.sharding.shard.OrderBy.OrderByType;

/**
 * 
 * @author wenlong.liu
 *
 */
public class OrderbyValue implements Comparable<OrderbyValue>{

	private final List<Value> values = new ArrayList<Value>();
	
	public void addValue(String orderFiled, OrderByType orderType, Object value){
		values.add(new Value(orderFiled, orderType, value));
	}
	
	@Override
	public int compareTo(OrderbyValue o) {
		return 0;
	}
	
	private static class Value implements Comparable<OrderbyValue>{
		private String orderFiled;
		private OrderByType orderType;
		private Object value;
		
		public Value(String orderFiled, OrderByType orderType, Object value){
			this.orderFiled = orderFiled;
			this.orderType = orderType;
			this.value = value;
		}

		@Override
		public int compareTo(OrderbyValue other) {
			return 0;
		}
	}
}
