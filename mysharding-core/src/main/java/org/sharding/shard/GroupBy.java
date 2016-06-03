package org.sharding.shard;

import org.sharding.shard.OrderBy.OrderByType;

/**
 * 
 * @author wenlong.liu
 *
 */
public class GroupBy {

	private final String name;
    
    private final OrderByType orderType;
    
    public GroupBy(String name, OrderByType orderByType){
    	this.name = name;
    	this.orderType = orderByType;
    }
    
	public String getName() {
		return name;
	}

	public OrderByType getOrderType() {
		return orderType;
	}
}
