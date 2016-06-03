package org.sharding.router;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wenlongliu
 *
 */
public class ActualTableAllOnDataSource {

	private final String namenode;
	
	private final List<ActualTableSingleOnDataSource> logicTables;

	public ActualTableAllOnDataSource(String namenode){
		this.namenode = namenode;
		this.logicTables = new ArrayList<ActualTableSingleOnDataSource>();;
	}
	
	
	public String getNamenode() {
		return namenode;
	}

	public List<ActualTableSingleOnDataSource> getLogicTables() {
		return logicTables;
	}
	
	

	public void addLogicTables(ActualTableSingleOnDataSource factor) {
		this.logicTables.add(factor);
	}
}
