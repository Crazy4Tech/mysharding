package org.sharding.shard;


/**
 * 
 * @author wenlong.liu
 *
 */
public class OrderBy {
	
	private final String name;
    
    private final OrderByType orderType;
    
    public OrderBy(String name, OrderByType orderByType){
    	this.name = name;
    	this.orderType = orderByType;
    }
    
	public String getName() {
		return name;
	}

	public OrderByType getOrderType() {
		return orderType;
	}
	
	public static enum OrderByType {
	    ASC, DESC
	}

}
