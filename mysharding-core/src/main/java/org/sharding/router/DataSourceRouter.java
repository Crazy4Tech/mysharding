package org.sharding.router;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sharding.configuration.Configuration;
import org.sharding.parser.mysql.MySqlPrintVisitor;
import org.sharding.shard.Condition;
import org.sharding.shard.DatabaseStrategy;
import org.sharding.shard.ShardTable;
import org.sharding.shard.Table;
import org.sharding.shard.TableStrategy;

/**
 * 
 * @author wenlong.liu
 *
 */
public class DataSourceRouter implements Router{
	
	private final RouteContext routeContext;
	
	private final Configuration configuration;
	
	private final Map<String, ActualTableAllOnDataSource> namenodeMap;
	
	
	public DataSourceRouter(RouteContext routeContext, Configuration configuration){
		this.routeContext = routeContext;
		this.configuration = configuration;
		this.namenodeMap= new HashMap<String, ActualTableAllOnDataSource>();
	}
	
	
	@Override
	public Collection<RouteUnit> route()
	{
		Collection<ActualTableAllOnDataSource> dataSource = routeDatasource();
		Collection<DataSourceMapping> dataSourceMappings = routeBindDataSource(dataSource);
		Collection<RouteUnit> routeUnits = new ArrayList<RouteUnit>(dataSourceMappings.size());
		for(DataSourceMapping sourceMapping : dataSourceMappings)
		{
			RouteUnit routeUnit = new RouteUnit();
			routeUnit.setDataSource(sourceMapping.getNamenode());
			routeUnit.setSql(genSQL(sourceMapping));
			routeUnit.setStatementType(routeContext.getStatementType());
			routeUnits.add(routeUnit);
		}
		return routeUnits;
	}
	
	
	private Collection<DataSourceMapping> routeBindDataSource(Collection<ActualTableAllOnDataSource> datasoures)
	{
		Collection<DataSourceMapping> dataSourceMappings = new ArrayList<DataSourceMapping>();
		for(ActualTableAllOnDataSource datasoure : datasoures)
		{
			Collection<DataSourceMapping>  mappings = new BindingTableRouter(datasoure).route();
			dataSourceMappings.addAll(mappings);
		}
		return dataSourceMappings;
	}
	
	
	private Collection<ActualTableAllOnDataSource>  routeDatasource(){
		for(Table table : routeContext.getTables())
		{
			ShardTable shardTable = configuration.getTable(table.getName());
			if(shardTable==null || shardTable.isGlobalTable())
			{
				//不配置的表默认是全局数据库中的表
				shardTable = new ShardTable();
				shardTable.setAccessmode(ShardTable.GLOBAL_MODE);
				shardTable.setName(table.getName());
				Collection<String> namenodes = Arrays.asList(new String[]{configuration.getGlobalNamenode()});
				routeTable(namenodes, shardTable);
			}
			else if(shardTable.isShardingDatabase())
			{
				DatabaseStrategy databaseStrategy = shardTable.getDatabaseStrategy();
				Collection<Condition> shardValues = routeContext.getDatabaseShardingConditions(shardTable);
				Collection<String> namenodes = databaseStrategy.doSharding(shardTable.getNamenodes(), shardValues);
				if(namenodes==null || namenodes.isEmpty()) namenodes = shardTable.getNamenodes();
				routeTable(namenodes, shardTable);
			}
			else if(shardTable.isBroadcastTable())
			{
				//广播表是每个数据库中都有这个表
				shardTable = new ShardTable();
				shardTable.setAccessmode(ShardTable.BROADCAST_MODE);
				shardTable.setName(table.getName());
				Collection<String> namenodes = Arrays.asList(new String[]{});
				routeTable(namenodes, shardTable);
			}
		}
		return namenodeMap.values();
	}
	
	
	private  void routeTable(Collection<String> namenodes, ShardTable shardTable){
		for(String namenode : namenodes)
		{
			if(shardTable.isShardingTable())
			{
				TableStrategy tableStrategy = shardTable.getTableStrategy();
				Collection<String> actualTables = shardTable.getActualTablesOnNamenode(namenode);
				Collection<Condition> shardValues = routeContext.getTableShardingConditions(shardTable);
				List<String> tablesOnNamenode = tableStrategy.doSharding(actualTables, shardValues);
				if(tablesOnNamenode==null || tablesOnNamenode.isEmpty()) tablesOnNamenode = (List<String>)actualTables;
				this.put(namenode, shardTable.getName(), tablesOnNamenode);
			}
			else if(shardTable.isGlobalTable())
			{
				this.put(namenode, shardTable.getName(), Arrays.asList(new String[]{shardTable.getName()}));
			}
			else if(shardTable.isBroadcastTable())
			{
				//this.put("BROADCAST", shardTable.getName(), Arrays.asList(new String[]{shardTable.getName()}));
			}
			else
			{
				//只分裤不分表
				this.put(namenode, shardTable.getName(), Arrays.asList(new String[]{shardTable.getName()}));
			}
		}
	}
	
	
	private void put(String namenode, String logicTable, List<String> tablesOnNamenode)
	{
		ActualTableAllOnDataSource namenodeTableMapping = namenodeMap.get(namenode);
		if(namenodeTableMapping==null){
			namenodeTableMapping = new ActualTableAllOnDataSource(namenode);
			namenodeMap.put(namenode, namenodeTableMapping);
		}
		ActualTableSingleOnDataSource tableMapping = new ActualTableSingleOnDataSource(logicTable, namenode, tablesOnNamenode);
		namenodeTableMapping.addLogicTables(tableMapping);
	}
	
	
	private String genSQL(DataSourceMapping sourceMapping){
		StringBuilder sqlBuilder = new StringBuilder();
		routeContext.getSQLStatement().accept(new MySqlPrintVisitor(sqlBuilder, sourceMapping));
		return sqlBuilder.toString();
	}
}
