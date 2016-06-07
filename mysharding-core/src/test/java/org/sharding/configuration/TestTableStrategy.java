package org.sharding.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sharding.shard.Condition;
import org.sharding.shard.TableStrategy;

public class TestTableStrategy implements TableStrategy {

	@Override
	public List<String> doSharding(Collection<String> tables, Collection<Condition> conditions) {
		List<String> targetTables = new ArrayList<String>(tables.size());
		for (String each : tables) 
		{
			for(Condition condition : conditions)
			{
				for(Object value : condition.getShardingValues())
				{
					if (each.endsWith((Integer)value % 2 + "")) {
						targetTables.add(each);
						break;
		            }
				}
			}
        }
		return targetTables;
	}

}
