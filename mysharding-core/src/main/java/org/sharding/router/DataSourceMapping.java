package org.sharding.router;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author wenlongliu
 *
 */
public class DataSourceMapping {

	private final String namenode;

	private final Collection<TableMapping> tableMappings;
	
	public DataSourceMapping(String namenode){
		this.namenode = namenode;
		this.tableMappings = new ArrayList<TableMapping>();
	}
	
	public DataSourceMapping(String namenode, Collection<TableMapping> tableMappings){
		this.namenode = namenode;
		this.tableMappings = tableMappings;
	}

	public String getNamenode() {
		return namenode;
	}

	public Collection<TableMapping> getTableMappings() {
		return tableMappings;
	}
	
	public void addTableMapping(TableMapping mapping) {
		 tableMappings.add(mapping);
	}

	
	public String getActualName(String logic){
		for(TableMapping mapping : tableMappings){
			if(logic.equals(mapping.getLogicName()))
				return mapping.getActualName();
		}
		return null;
	}
}
