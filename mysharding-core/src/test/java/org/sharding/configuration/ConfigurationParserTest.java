package org.sharding.configuration;


import org.sharding.configuration.Configuration;
import org.sharding.configuration.ConfigurationBulider;
import junit.framework.TestCase;

public class ConfigurationParserTest extends TestCase {


	public void test() throws Exception{
		String resource = "org/sharding/configuration/configuration.xml";
		try {
			Configuration config = new  ConfigurationBulider(resource).bulid();
			assertEquals( config.getIntSetting("maxActive"),50);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	

	
}
