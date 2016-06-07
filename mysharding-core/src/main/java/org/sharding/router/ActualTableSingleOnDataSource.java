package org.sharding.router;

import java.util.Collection;
import java.util.List;

/**
 * 
 * @author wenlongliu
 *
 */
public class ActualTableSingleOnDataSource {
	
	private final String namenode;

	private final String logicName;
	  
	private final List<String> actualTables;
	
	public ActualTableSingleOnDataSource(String logicTable, String namenode, List<String> actualTables){
		this.logicName = logicTable;
		this.namenode = namenode;
		this.actualTables = actualTables;
	}
	
	public String getLogicName() {
		return logicName;
	}

	public String getNamenode() {
		return namenode;
	}

	public List<String> getActualTables() {
		return actualTables;
	}
	

	public void addActualTable(Collection<String> actualTables) {
		this.actualTables.addAll(actualTables);
	}

	
	
}
