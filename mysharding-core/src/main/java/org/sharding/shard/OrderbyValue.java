package org.sharding.shard;

import java.util.HashMap;
import java.util.Map;

import org.sharding.shard.OrderBy.OrderByType;

/**
 * 
 * @author wenlong.liu
 *
 */
public class OrderbyValue implements Comparable<OrderbyValue>{

	private final Map<String,Value> values = new HashMap<String,Value>();
	
	public void addValue(String orderFiled, OrderByType orderType, Object value){
		values.put(orderFiled, new Value(orderFiled, orderType, value));
	}
	
	public Value getValue(String orderFiled){
		return values.get(orderFiled);
	}
	
	@Override
	public int compareTo(OrderbyValue other) {
		for(String key : values.keySet())
		{
			int result = this.getValue(key).compareTo(other.getValue(key));
			if(result!=0) return result;
		}
		return 0;
	}
	
	
	static class Value implements Comparable<Value>{
		private String orderFiled;
		private OrderByType orderType;
		private Object value;
		
		public Value(String orderFiled, OrderByType orderType, Object value){
			this.orderFiled = orderFiled;
			this.orderType = orderType;
			this.value = value;
		}
		
		public Object getValue(){
			return this.value;
		}
		
		public String getFiled(){
			return this.orderFiled;
		}

		@Override
		public int compareTo(Value other) {
			if(OrderByType.DESC==orderType)
			{
				if(this.value instanceof String)
					return ((String)this.value).compareTo((String)other.getValue());
				else if(this.value instanceof Number)
					return ((Number) this.value).doubleValue() > ((Number)other.getValue()).doubleValue() ? 1 : -1;
				else if(this.value instanceof java.util.Date)
					return ((java.util.Date)this.value).compareTo((java.util.Date)other.getValue());
			}else{
				if(this.value instanceof String)
					return ((String)other.getValue()).compareTo((String)this.value);
				else if(this.value instanceof Number)
					return ((Number)other.getValue()).doubleValue() > ((Number)this.value).doubleValue() ? 1 : -1;
				else if(this.value instanceof java.util.Date)
					return ((java.util.Date)other.getValue()).compareTo((java.util.Date)this.value);
			}
			return 0;
		}
	}
}
