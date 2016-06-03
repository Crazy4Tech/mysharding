package org.sharding.shard;

import java.util.Collection;
/**
 * 
 * @author wenlongLiu
 *
 */
public interface TableStrategy{

	Collection<String> doSharding(Collection<String> tables,Collection<Condition> parameters);
}
