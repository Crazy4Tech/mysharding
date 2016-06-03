package org.sharding.shard;

import java.util.Collection;
/**
 * 
 * @author wenlongLiu
 *
 */
public interface DatabaseStrategy{

	Collection<String> doSharding(final Collection<String> dataSourceNames, final Collection<Condition> shardValues);
}
