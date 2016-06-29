package org.sharding.router;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.sharding.parser.StatementType;
import org.sharding.shard.Condition;
import org.sharding.shard.ShardTable;
import org.sharding.shard.Table;

import com.alibaba.druid.sql.ast.SQLStatement;

/**
 * 
 * @author pc
 *
 */
public class RouteContext {

	private final Collection<Condition> shardConditions = new ArrayList<Condition>();
	
	private final Collection<Table> tables = new HashSet<Table>();
	
	private SQLStatement statement;
	
	private StatementType statementType;
	
	
	public Collection<Condition> getShardConditions() {
		return shardConditions;
	}
  
	public void addShardCondition(Condition tableCondition) {
		this.shardConditions.add(tableCondition);
	}

	public Collection<Table> getTables() {
		return tables;
	}

	public void addTable(Table table) {
		this.tables.add(table);
	}

	public void setSQLStatement(SQLStatement statement) {
		this.statement = statement;
	}
	
	public SQLStatement getSQLStatement() {
		return statement;
	}
	
	public StatementType getStatementType() {
		return statementType;
	}

	public void setStatementType(StatementType statementType) {
		this.statementType = statementType;
	}
	
	public Collection<Condition> getDatabaseShardingConditions(ShardTable logicTable) {
		Collection<Condition> conditions = new ArrayList<Condition>();
		for(String column : logicTable.getDatabaseStrategyColumns())
		{
			for(Condition each : shardConditions)
			{
				if(column.equalsIgnoreCase(each.getColumn().getName()))
				{
					conditions.add(each);
				}
			}
		}
		return conditions;
	}
	
	public Collection<Condition> getTableShardingConditions(ShardTable logicTable) {
		Collection<Condition> conditions = new ArrayList<Condition>();
		for(String column : logicTable.getTableStrategyColumns())
		{
			for(Condition each : shardConditions)
			{
				if(column.equalsIgnoreCase(each.getColumn().getName()))
				{
					conditions.add(each);
				}
			}
		}
		return conditions;
	}

}
