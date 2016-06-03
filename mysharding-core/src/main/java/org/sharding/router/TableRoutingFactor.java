package org.sharding.router;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author wenlongliu
 *
 */
public class TableRoutingFactor {

	private String logicTable;
	  
	private Collection<String> actualTables = new ArrayList<String>();
	
	public TableRoutingFactor(String logicTable, Collection<String> actualTables){
		this.logicTable = logicTable;
		this.actualTables = actualTables;
	}
	
	public String getLogicTable() {
		return logicTable;
	}

	public void setLogicTable(String logicTable) {
		this.logicTable = logicTable;
	}

	public Collection<String> getActualTables() {
		return actualTables;
	}

	public void addActualTable(Collection<String> actualTables) {
		this.actualTables.addAll(actualTables);
	}

	
	
}
