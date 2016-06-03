package org.sharding.router;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author pc
 *
 */
public class BindingTableRouter {
	
	private ActualTableAllOnDataSource dataSource;
	
	private final Collection<DataSourceMapping> dataSourceMappings;
	
	public BindingTableRouter(ActualTableAllOnDataSource dataSource){
		this.dataSource = dataSource;
		this.dataSourceMappings = new ArrayList<DataSourceMapping>();
	}

	public Collection<DataSourceMapping> route(){
		int[] counter = new int[dataSource.getLogicTables().size()];
		Integer countIndex = counter.length - 1;
		int rows = dataSource.getLogicTables().isEmpty() ? 0 : 1;
		for(ActualTableSingleOnDataSource logicTable : dataSource.getLogicTables())
		{
			rows = rows * logicTable.getActualTables().size();
		}
		
		for(int i=0; i<rows; i++)
		{
			bingEveryTable(counter, countIndex);
		}
		return dataSourceMappings;
	}
	
	private void bingEveryTable(int[] counter, Integer counterIndex)
	{
		DataSourceMapping dataSourceMapping = new DataSourceMapping(dataSource.getNamenode());
		dataSourceMappings.add(dataSourceMapping);
		for(int index=0; index<dataSource.getLogicTables().size(); index++)
		{
			String logicTable = dataSource.getLogicTables().get(index).getLogicName();
			String actualName = dataSource.getLogicTables().get(index).getListActualTables().get(counter[index]);
			TableMapping tableMapping = new TableMapping(logicTable, actualName);
			dataSourceMapping.addTableMapping(tableMapping);
		}
		moveIndx(counter, counterIndex);
	}
	
	private void moveIndx(int[] counter, Integer counterIndex)
	{
		 counter[counterIndex]++;  
         if (counter[counterIndex] >= dataSource.getLogicTables().get(counterIndex).getListActualTables().size()) {  
             counter[counterIndex] = 0;  
             counterIndex--;  
             if (counterIndex >= 0) {  
            	 moveIndx(counter, counterIndex);  
             }  
             counterIndex = dataSource.getLogicTables().size() - 1;  
         }  
	}
	
}
