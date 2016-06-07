package org.sharding.shard;

import java.util.Collection;
import java.util.List;
/**
 * 
 * @author wenlongLiu
 *
 */
public interface TableStrategy{

	List<String> doSharding(Collection<String> tables,Collection<Condition> parameters);
}
