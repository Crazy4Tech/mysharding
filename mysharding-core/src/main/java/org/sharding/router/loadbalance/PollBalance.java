package org.sharding.router.loadbalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.sharding.parser.StatementType;
import org.sharding.shard.ShardDatanode;
import org.sharding.shard.ShardNamenode;

/**
 * 轮询
 * 
 * @author pc
 *
 */
public class PollBalance implements LoadBalance {

	private static AtomicInteger readtimes = new AtomicInteger(0);
	
	private static AtomicInteger writetimes = new AtomicInteger(0);
	
	@Override
	public ShardDatanode loadBalance(ShardNamenode namenode, StatementType statementType) {
		List<ShardDatanode> dataNodes = null;
		switch (statementType) {
			case SELECT:
				dataNodes = namenode.getReadDatanodes();
				if(readtimes.get()>=dataNodes.size()) readtimes.set(0);
				return dataNodes.get(readtimes.getAndIncrement());
			case INSERT:
			case UPDATE:
			case DELETE:
				dataNodes = namenode.getWriteDatanodes();
				if(writetimes.get()>=dataNodes.size()) writetimes.set(0);
				return dataNodes.get(writetimes.getAndIncrement());
			default:
				return null;
		}
	}

}
