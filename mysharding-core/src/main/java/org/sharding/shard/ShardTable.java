package org.sharding.shard;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 
 * @author wenlongliu
 *
 */
public class ShardTable{

	/**
	 * 
	 */
	private String name;
	private DatabaseStrategy databaseStrategy;
	private String[] databaseStrategyColumns;
	private TableStrategy tableStrategy;
	private String[] tableStrategyColumns;
	private Map<String, Collection<String>> namenodes = new HashMap<String, Collection<String>>();
	private Collection<String> shardingColumns = new HashSet<String>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DatabaseStrategy getDatabaseStrategy() {
		return databaseStrategy;
	}

	public void setDatabaseStrategy(DatabaseStrategy datbaseStrategy) {
		this.databaseStrategy = datbaseStrategy;
	}

	public TableStrategy getTableStrategy() {
		return tableStrategy;
	}

	public void setTableStrategy(TableStrategy tableStrategy) {
		this.tableStrategy = tableStrategy;
	}

	public String[] getDatabaseStrategyColumns() {
		return databaseStrategyColumns;
	}

	public void setDatabaseStrategyColumns(String[] datbaseStrategyColumns) {
		this.databaseStrategyColumns = datbaseStrategyColumns;
		for(String column : datbaseStrategyColumns)
		{
			this.shardingColumns.add(column);
		}
	}

	public String[] getTableStrategyColumns() {
		return tableStrategyColumns;
	}

	public void setTableStrategyColumns(String[] tableStrategyColumns) {
		this.tableStrategyColumns = tableStrategyColumns;
		for(String column : tableStrategyColumns)
		{
			this.shardingColumns.add(column);
		}
	}
	
	public void addNamenode(String namenode, Collection<String> tables){
		namenodes.put(namenode, tables);
	}
	
	public Collection<String> getNamenodes(){
		return namenodes.keySet();
	}
	
	public Collection<String> getActualTablesOnNamenode(String namenode){
		return namenodes.get(namenode);
	}
	
	public Collection<String> getShardingColumns(){
		return this.shardingColumns;
	}
	
	public boolean isShardingDatabase(){
		if(this.databaseStrategy!=null && 
				this.databaseStrategyColumns!=null && 
				this.databaseStrategyColumns.length>0)
			return true;
		else
			return false;
	}
	
	public boolean isShardingTable(){
		if(this.tableStrategy!=null && 
				this.tableStrategyColumns!=null && 
				this.tableStrategyColumns.length>0)
			return true;
		else
			return false;
	}
}
