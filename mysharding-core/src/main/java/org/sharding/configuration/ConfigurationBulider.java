package org.sharding.configuration;


import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.sharding.exception.DuplicatedNameException;
import org.sharding.shard.DatabaseStrategy;
import org.sharding.shard.ShardDatanode;
import org.sharding.shard.ShardNamenode;
import org.sharding.shard.ShardTable;
import org.sharding.shard.TableStrategy;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.alibaba.druid.pool.DruidDataSource;


/**
 * 
 * @author wenlongliu
 *
 */
public class ConfigurationBulider extends  AbstractBulider{

	private Configuration configuration;
	
	private ClassLoaderWrapper classLoaderWrapper;
	
	private String resource;
	
	
	public ConfigurationBulider(String resource)
	{
		this.resource = resource;
		this.configuration = new Configuration();
		this.classLoaderWrapper = new ClassLoaderWrapper();
	}
	
	public Configuration bulid() throws Exception
	{
		InputStream inputStream = Resources.getResourceAsStream(this.resource);
		Document document = createDocument(inputStream);
		parserSettings(xpath(document, "/configuration/settings/setting"));
		parserStrategies(xpath(document, "/configuration/strategies/strategy"));
		parserNamenodes(xpath(document, "/configuration/namenodes/namenode"));
		parserTables(xpath(document, "/configuration/tables/table"));
		configuration.setThreadPoolExecutor(createThreadPoolExecutor());
		return configuration;  
	}
	
	private void parserSettings(List<Node> nodeList) {
		for(Node node : nodeList)
		{
			Node nameNode =node.getAttributes().getNamedItem("name");
			Node valueNode =node.getAttributes().getNamedItem("value");
			configuration.addSetting(nameNode.getNodeValue(), valueNode.getNodeValue());
		}
	}
	
	
	private void parserStrategies(List<Node> nodeList) throws Exception{
		for(Node node : nodeList)
		{
			String name = node.getAttributes().getNamedItem("name").getNodeValue();
			String type = node.getAttributes().getNamedItem("type").getNodeValue();
			String classtype = node.getAttributes().getNamedItem("class").getNodeValue();
			if(configuration.getStrategy(name)!=null) 
				throw new DuplicatedNameException("parser Strategiey Duplicated Name");
			
			if("database".equalsIgnoreCase(type)){
				Class<?> dbStrategyClass = classLoaderWrapper.classForName(classtype);
				if(!DatabaseStrategy.class.isAssignableFrom(dbStrategyClass)){
					throw new Exception(String.format("%s do not is DatabaseStrategy", classtype));
				}
				DatabaseStrategy dbStrategyInstance = (DatabaseStrategy) dbStrategyClass.newInstance();
				configuration.addStrategy(name, dbStrategyInstance);
			}else if("table".equalsIgnoreCase(type)){
				Class<?> tableStrategyClass = classLoaderWrapper.classForName(classtype);
				if(!TableStrategy.class.isAssignableFrom(tableStrategyClass)){
					throw new Exception(String.format("%s do not is TableStrategy", classtype));
				}
				TableStrategy tableStrategyInstance = (TableStrategy) tableStrategyClass.newInstance();
				configuration.addStrategy(name, tableStrategyInstance);
			}else {
				throw new Exception(String.format("%s do not is TableStrategy", classtype));
			}
				
		}
	}
	
	private void parserNamenodes(List<Node> nodeList) {
		for(Node node : nodeList)
		{
			String name = node.getAttributes().getNamedItem("name").getNodeValue();
			String loadbalance = node.getAttributes().getNamedItem("loadbalance").getNodeValue();
			ShardNamenode namenode = new ShardNamenode(name, loadbalance);
			if(configuration.getNamenode(name)!=null)
				throw new DuplicatedNameException("parser namenode Duplicated Name");
			configuration.addNamenode(namenode);
			parserDatenodes(namenode, node);
		}
	}
	
	private void parserDatenodes(ShardNamenode namenodeConfig, Node namenode) {
		NodeList childList = namenode.getChildNodes();
		for(int i=0; i<childList.getLength(); i++)
		{
			Node datanode = childList.item(i);
			if(datanode.getNodeType()==Node.ELEMENT_NODE)
			{
				String name = datanode.getAttributes().getNamedItem("name").getNodeValue();
				String accessMode = datanode.getAttributes().getNamedItem("accessMode").getNodeValue();
				String weight = datanode.getAttributes().getNamedItem("weight").getNodeValue();
				ShardDatanode datenodeConfig = new ShardDatanode();
				configuration.addDatanode(datenodeConfig);
				datenodeConfig.setName(name);
				datenodeConfig.setAccessMode(accessMode);
				datenodeConfig.setWeight(Integer.valueOf(weight));
				namenodeConfig.addDatanode(datenodeConfig);
				parserDatanodeAttributes(datenodeConfig, datanode);
				
				DataSource dataSource = createDataSource(datenodeConfig);
				configuration.addDataSource(name, dataSource);
			}
		}
	}
	
	private void parserTables(List<Node> nodeList) throws Exception {
		for(Node tablenode : nodeList)
		{
			String name = tablenode.getAttributes().getNamedItem("name").getNodeValue();
			String databaseShardingStrategy = tablenode.getAttributes().getNamedItem("databaseShardingStrategy").getNodeValue();
			String databaseShardingColumns = tablenode.getAttributes().getNamedItem("databaseShardingColumns").getNodeValue();
			String tableShardingStrategy = tablenode.getAttributes().getNamedItem("tableShardingStrategy").getNodeValue();
			String tableShardingColumns = tablenode.getAttributes().getNamedItem("tableShardingColumns").getNodeValue();
			ShardTable tableConfig = new ShardTable();
			tableConfig.setName(name);
			configuration.addTable(tableConfig);
			
			if(databaseShardingStrategy!=null)
			{
				DatabaseStrategy strategyInstance = (DatabaseStrategy) configuration.getStrategy(databaseShardingStrategy);
				tableConfig.setDatabaseStrategy(strategyInstance);
				if(strategyInstance==null)
					throw new Exception(String.format("%s table's databaseShardingStrategy instance of attribute %s do not find ", name, databaseShardingStrategy));
				
				String[] dbColumns =  databaseShardingColumns.split(",");
				tableConfig.setDatabaseStrategyColumns(dbColumns);
				if(dbColumns==null || dbColumns.length==0)
					throw new Exception(String.format("%s table's database Sharding columns requrid", name));
			}
			
			if(tableShardingStrategy!=null)
			{
				TableStrategy strategyInstance = (TableStrategy) configuration.getStrategy(tableShardingStrategy);
				tableConfig.setTableStrategy(strategyInstance);
				if(strategyInstance==null)
					throw new Exception(String.format("%s table's tableShardingStrategy instance of attribute %s do not find ", name, databaseShardingStrategy));
				
				String[] tableColumns =  tableShardingColumns.split(",");
				tableConfig.setTableStrategyColumns(tableColumns);
				if(tableColumns==null || tableColumns.length==0)
					throw new Exception(String.format("%s table's table Sharding columns requrid", name));
			}
			
			parserTableNamenodes(tableConfig, tablenode);
		}
	}
	
	private void parserTableNamenodes(ShardTable tableConfig, Node tablenode) throws Exception {
		NodeList childList = tablenode.getChildNodes();
		for(int i=0; i<childList.getLength(); i++)
		{
			Node childnode = childList.item(i);
			if(childnode.getNodeType()==Node.ELEMENT_NODE && "namenodes".equals(childnode.getNodeName()))
			{
				NodeList namenodeList = childnode.getChildNodes();
				for(int j=0; j<namenodeList.getLength(); j++)
				{
					Node namenode = namenodeList.item(j);
					if(namenode.getNodeType()==Node.ELEMENT_NODE)
					{
						String refnname = namenode.getAttributes().getNamedItem("ref").getNodeValue();
						String tablenames = namenode.getAttributes().getNamedItem("tablenames").getNodeValue();
						if(configuration.getNamenode(refnname)==null)
							throw new Exception(String.format("the namenode '%s' do not find", refnname));
						String[] actualTables =  tablenames.split(",");
						tableConfig.addNamenode(refnname, Arrays.asList(actualTables));
					}
				}
			}
		}
	}
	
	private void parserDatanodeAttributes(ShardDatanode config, Node datanode){
		NodeList childList = datanode.getChildNodes();
		String[] attributes = {"driverClassName","username","password","initialSize","maxActive","maxWait","maxIdle","minIdle"};
		for(String key : attributes)
		{
			try {
				config.setAttribute(key, configuration.getStringSetting(key));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//override attribute of setting
		for(int i=0; i<childList.getLength(); i++)
		{
			Node childnode = childList.item(i);
			if(childnode.getNodeType()==Node.ELEMENT_NODE)
			{
				String key = childnode.getNodeName();
				String value = childnode.getTextContent();
				try {
					if(value!=null && "".equals(value.trim())==false)
						config.setAttribute(key, value.trim());
				} catch (Exception e) {
				}
			}
		}
		
	}
	
	private DataSource createDataSource(ShardDatanode datenode){
		DruidDataSource dataSource =  new DruidDataSource();
		dataSource.setUsername(datenode.getUsername());
		dataSource.setUrl(datenode.getUrl());
		dataSource.setPassword(datenode.getPassword());
		dataSource.setDriverClassName(datenode.getDriverClassName());
		dataSource.setMaxActive(datenode.getMaxActive());
		dataSource.setMaxWait(datenode.getMaxWait());
		//dataSource.setMaxIdle(maxIdle);
		dataSource.setMinIdle(datenode.getMinIdle());
		dataSource.setInitialSize(datenode.getInitialSize());
		return dataSource;
	}
	
	
	private ExecutorService createThreadPoolExecutor()
	{
		int nThreads = configuration.getIntSetting("threadPoolSize");
		nThreads = nThreads==0 ? 10 : nThreads;
		return Executors.newFixedThreadPool(nThreads); 
	}
	
}
