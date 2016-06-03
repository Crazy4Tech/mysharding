package org.sharding.router;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.sharding.configuration.Configuration;
import org.sharding.parser.mysql.MySqlPrintVisitor;
import org.sharding.shard.DatabaseStrategy;
import org.sharding.shard.ShardTable;
import org.sharding.shard.Table;
import org.sharding.shard.TableStrategy;

/**
 * 
 * @author wenlong.liu
 *
 */
public class DataSourceRouter {
	
	private final RouteContext routeContext;
	
	private final Configuration configuration;
	
	private final Map<String, ActualTableAllOnDataSource> namenodeMap;
	
	
	public DataSourceRouter(RouteContext routeContext, Configuration configuration){
		this.routeContext = routeContext;
		this.configuration = configuration;
		this.namenodeMap= new HashMap<String, ActualTableAllOnDataSource>();
	}
	
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
		Collection<Table> tables = routeContext.getTables();
		for(Table table : tables)
		{
			ShardTable shardTable = configuration.getTable(table.getName());
			if(shardTable.isShardingDatabase())
			{
				DatabaseStrategy databaseStrategy = shardTable.getDatabaseStrategy();
				Collection<String> namenodes = databaseStrategy.doSharding(shardTable.getNamenodes(), routeContext.getDatabaseShardingConditions(shardTable));
				routeTable(namenodes, shardTable);
			}
			else
			{
				
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
				Collection<String> tablesOnNamenode = tableStrategy.doSharding(actualTables, routeContext.getTableShardingConditions(shardTable));
				this.put(namenode, shardTable.getName(), tablesOnNamenode);
			}
			else
			{
				//this.put(namenode, shardTable.getName(), tablesOnNamenode);
			}
		}
	}
	
	
	private void put(String namenode, String logicTable, Collection<String> tablesOnNamenode)
	{
		ActualTableAllOnDataSource namenodeFactor = namenodeMap.get(namenode);
		if(namenodeFactor==null){
			namenodeFactor = new ActualTableAllOnDataSource(namenode);
			namenodeMap.put(namenode, namenodeFactor);
		}
		ActualTableSingleOnDataSource tableFactor = new ActualTableSingleOnDataSource(logicTable, namenode, tablesOnNamenode);
		namenodeFactor.addLogicTables(tableFactor);
	}
	
	private String genSQL(DataSourceMapping sourceMapping){
		MySqlPrintVisitor visitor = new MySqlPrintVisitor(sourceMapping);
		routeContext.getSQLStatement().accept(visitor);
		return routeContext.getSQLStatement().toString();
	}
}
