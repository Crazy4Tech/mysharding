package org.sharding.router.loadbalance;

import org.sharding.parser.StatementType;
import org.sharding.shard.ShardDatanode;
import org.sharding.shard.ShardNamenode;

/**
 * 
 * @author wenlong.liu
 *
 */
public interface LoadBalance {

	/**
	 * 
	 * @param namenode
	 * @param statementType
	 * @return
	 */
	ShardDatanode loadBalance(ShardNamenode namenode, StatementType statementType);
}
