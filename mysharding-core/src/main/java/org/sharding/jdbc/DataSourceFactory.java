package org.sharding.jdbc;

import org.sharding.configuration.Configuration;
import org.sharding.configuration.ConfigurationBulider;
/**
 * 
 * @author wenlongliu
 *
 */
public class DataSourceFactory {

	 private final Configuration configuration;
	 
	 public DataSourceFactory(String resource) throws Exception{
		 configuration = new  ConfigurationBulider(resource).bulid();
	 }
	 
	 /**
	  * 
	  * @return
	  */
	 public ShardDataSource openDataSource(){
		return new ShardDataSource(configuration);
	 }
}
