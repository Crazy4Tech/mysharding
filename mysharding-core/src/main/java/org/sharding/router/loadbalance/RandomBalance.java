package org.sharding.router.loadbalance;

import java.util.List;
import java.util.Random;

import org.sharding.parser.StatementType;
import org.sharding.shard.ShardDatanode;
import org.sharding.shard.ShardNamenode;

/**
 * 随机
 * 
 * @author  wenlong.liu
 *
 */
public class RandomBalance implements LoadBalance {

	private static final Random random = new Random();
	
	@Override
	public ShardDatanode loadBalance(ShardNamenode namenode, StatementType statementType) {
		List<ShardDatanode> dataNodes = null;
		switch (statementType) {
			case SELECT:
				dataNodes = namenode.getReadDatanodes();
				break;
			case INSERT:
			case UPDATE:
			case DELETE:
				dataNodes = namenode.getWriteDatanodes();
				break;
		}
		int index = random.nextInt(dataNodes.size()) ;
		return dataNodes.get(index);
	}

}
