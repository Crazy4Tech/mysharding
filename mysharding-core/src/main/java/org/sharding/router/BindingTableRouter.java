package org.sharding.router;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author wenlongliu
 *
 */
public class BindingTableRouter {
	
	private ActualTableAllOnDataSource dataSource;
	
	
	
	public BindingTableRouter(ActualTableAllOnDataSource dataSource){
		this.dataSource = dataSource;
	}

	public Collection<DataSourceMapping> route(){
		List<List<TableMapping>> results = decartes(dataSource.getLogicTables());
		List<DataSourceMapping> dataSourceMappings  = new ArrayList<DataSourceMapping>(results.size());
		for(List<TableMapping> tableMappings : results)
		{
			dataSourceMappings.add(new DataSourceMapping(dataSource.getNamenode(), tableMappings));
		}
		return dataSourceMappings;
	}
	

	
    public static List<List<TableMapping>> decartes(List<ActualTableSingleOnDataSource> dataSources) {
        int rows = dataSources.size() > 0 ? 1 : 0;
        for (ActualTableSingleOnDataSource data : dataSources) {
            rows *= data.getActualTables().size();
        }
        // 笛卡尔积索引记录
        int[] record = new int[dataSources.size()];
        List<List<TableMapping>> results = new ArrayList<List<TableMapping>>();
        for (int i = 0; i < rows; i++)
        {
            List<TableMapping> row = new ArrayList<TableMapping>();
            for (int index = 0; index < record.length; index++) 
            {
                String logicTable = dataSources.get(index).getLogicName();
    			String actualName = dataSources.get(index).getActualTables().get(record[index]);
    			TableMapping tableMapping = new TableMapping(logicTable, actualName);
    			row.add(tableMapping);
            }
            results.add(row);
            crossRecord(dataSources, record, dataSources.size() - 1);
        }
        return results;
    }

    /**
     * @param sourceArgs
     * @param record
     * @param level
     */
    private static void crossRecord(List<ActualTableSingleOnDataSource> dataSources, int[] record, int level) {
        record[level] = record[level] + 1;
        if (record[level] >= dataSources.get(level).getActualTables().size() && level > 0) 
        {
            record[level] = 0;
            crossRecord(dataSources, record, level - 1);
        }
    }
	
}
