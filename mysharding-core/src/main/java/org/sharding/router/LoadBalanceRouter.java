package org.sharding.router;

import java.util.Collection;

import org.sharding.configuration.Configuration;
import org.sharding.router.loadbalance.LoadBalance;
import org.sharding.router.loadbalance.PollBalance;
import org.sharding.router.loadbalance.RandomBalance;
import org.sharding.shard.ShardDatanode;
import org.sharding.shard.ShardNamenode;

/**
 * 
 * @author wenlong.liu
 *
 */
public class LoadBalanceRouter implements Router{

	private final Configuration configuration;
	
	private final DataSourceRouter dataSourceRouter;
	
	public LoadBalanceRouter(DataSourceRouter delegate, Configuration configuration)
	{
		this.configuration = configuration;
		this.dataSourceRouter = delegate;
	}
	
	@Override
	public Collection<RouteUnit> route() {
		Collection<RouteUnit>  routeUnits = dataSourceRouter.route();
		for(RouteUnit unit : routeUnits)
		{
			ShardDatanode datanode = loadBalance(unit);
			unit.setDataSource(datanode.getName());
		}
		return routeUnits;
	}

	
	
	private ShardDatanode loadBalance(RouteUnit unit) {
		ShardNamenode namenode = configuration.getNamenode(unit.getDataSource());
		LoadBalance loadBalance = createLoadBalance(namenode);
		return loadBalance.loadBalance(namenode, unit.getStatementType());
	}
	
	
	/**
	 * 
	 * @param namenode
	 * @return
	 */
	private LoadBalance createLoadBalance(ShardNamenode namenode){
		switch(namenode.getLoadbalance()){
			case RANDOM:
				return new RandomBalance();
			case POLL:
				return new PollBalance();
			default:
				return new RandomBalance();
		}
	}

}
