package org.sharding.configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.sql.DataSource;

import org.sharding.shard.ShardDatanode;
import org.sharding.shard.ShardNamenode;
import org.sharding.shard.ShardTable;
/**
 * 
 * @author wenlong.liu
 *
 */
public class Configuration 
{
	private final static  String GLOBAL_NAMENODE = "default";
	
	private final Map<String, Object> strategyMap = new HashMap<String, Object>();
	
	private final Map<String, ShardNamenode> namenodeMap  = new HashMap<String, ShardNamenode>();
	
	private final Map<String, ShardDatanode> datanodeMap  = new HashMap<String, ShardDatanode>();
	
	private final Map<String, ShardTable> tableMap  = new HashMap<String, ShardTable>();
	
	private final Map<String, String> settingMap = new HashMap<String, String>();
	
	private final Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
	
	private ExecutorService ThreadPool;
	
	public Configuration(){
	}
	

	
	public void addSetting(String key, String value){
		settingMap.put(key, value);
	}
	
	public String getStringSetting(String key){
		return settingMap.get(key);
	}
	
	public int getIntSetting(String key){
		String value = settingMap.get(key);
		try{
			return Integer.parseInt(value);
		}catch(Exception ex){
			return 0;
		}
	}
	
	public void addStrategy(String key, Object config){
		strategyMap.put(key, config);
	}
	
	public Object getStrategy(String key){
		return strategyMap.get(key);
	}
	
	public void addNamenode(ShardNamenode config){
		namenodeMap.put(config.getName(), config);
	}
	
	public ShardNamenode getNamenode(String key){
		return namenodeMap.get(key);
	}
	
	public Collection<String> getNamenodes(){
		return namenodeMap.keySet();
	}
	
	public void addTable(ShardTable table){
		tableMap.put(table.getName(), table);
	}
	
	public ShardTable getTable(String key){
		return tableMap.get(key);
	}
	
	public void addDatanode(ShardDatanode node){
		datanodeMap.put(node.getName(), node);
	}
	
	public ShardDatanode getDatanode(String key){
		return datanodeMap.get(key);
	}
	
	public DataSource getDataSource(String key){
		return this.dataSourceMap.get(key);
	}
	
	public void addDataSource(String key,DataSource dataSource){
		this.dataSourceMap.put(key, dataSource);
	}
	
	public ExecutorService getThreadPoolExecutor(){
		return this.ThreadPool;
	}
	
	public void setThreadPoolExecutor(ExecutorService service){
		 this.ThreadPool = service;
	}
	
	public String getGlobalNamenode(){
		return GLOBAL_NAMENODE;
	}
}
