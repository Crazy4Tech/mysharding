package org.sharding.configuration;

import java.util.ArrayList;
import java.util.Collection;

import org.sharding.shard.Condition;
import org.sharding.shard.DatabaseStrategy;

public class TestDatabaseStrategy implements DatabaseStrategy {

	@Override
	public Collection<String> doSharding(Collection<String> dataSourceNames, Collection<Condition> shardValues) {
		Collection<String> targetDataSources = new ArrayList<String>(dataSourceNames.size());
		for (String each : dataSourceNames) 
		{
			for(Condition condition : shardValues)
			{
				for(Object value : condition.getValues())
				{
					if (each.endsWith((Integer)value % 2 + "")) {
						targetDataSources.add(each);
		            }
				}
			}
        }
		return targetDataSources;
	}

}
