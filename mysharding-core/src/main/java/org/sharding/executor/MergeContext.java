package org.sharding.executor;

import java.util.ArrayList;
import java.util.Collection;

import org.sharding.shard.Aggregate;
import org.sharding.shard.GroupBy;
import org.sharding.shard.Limit;
import org.sharding.shard.OrderBy;
/**
 * 
 * @author wenlong.liu
 *
 */
public class MergeContext {

	private final Collection<OrderBy> orderbys = new ArrayList<OrderBy>();
	
	private final Collection<Aggregate> aggregates = new ArrayList<Aggregate>();
	
	private final Collection<GroupBy> groupbys = new ArrayList<GroupBy>();
	
	private Limit limit = null;
	
	
	public Limit getLimit() {
		return limit;
	}
	
	public void setLimit(Limit limit) {
		this.limit=limit;
	}
	
	public void addGroupBy(GroupBy groupby) {
		this.groupbys.add(groupby);
	}
	
	public void addAggregate(Aggregate aggregate) {
		this.aggregates.add(aggregate);
	}
	
	public void addOrderBy(OrderBy orderby) {
		this.orderbys.add(orderby);
	}

	public Collection<OrderBy> getOrderbys() {
		return orderbys;
	}

	public Collection<Aggregate> getAggregates() {
		return aggregates;
	}

	public Collection<GroupBy> getGroupbys() {
		return groupbys;
	}
}
