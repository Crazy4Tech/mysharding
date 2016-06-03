package org.sharding.configuration;

import java.util.ArrayList;
import java.util.Collection;

import org.sharding.shard.Condition;
import org.sharding.shard.TableStrategy;

public class TestTableStrategy implements TableStrategy {

	@Override
	public Collection<String> doSharding(Collection<String> tables, Collection<Condition> conditions) {
		Collection<String> targetTables = new ArrayList<String>(tables.size());
		for (String each : tables) 
		{
			for(Condition condition : conditions)
			{
				for(Object value : condition.getValues())
				{
					if (each.endsWith((Integer)value % 2 + "")) {
						targetTables.add(each);
		            }
				}
			}
        }
		return targetTables;
	}

}
