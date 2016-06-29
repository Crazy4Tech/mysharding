package org.sharding.router;

/**
 * 
 * @author wenlongliu
 *
 */
public class TableMapping {

	private final String logicName;
	
	private final String actualName;
	
	TableMapping(String logicName, String actualName){
		this.logicName = logicName;
		this.actualName = actualName;
	}

	public String getLogicName() {
		return logicName;
	}

	public String getActualName() {
		return actualName;
	}
	
	
}
