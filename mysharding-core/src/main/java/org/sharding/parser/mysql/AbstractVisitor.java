package org.sharding.parser.mysql;

import java.util.Arrays;
import java.util.List;

import org.sharding.executor.DatabaseDialect;
import org.sharding.parser.ParseContext;
import org.sharding.parser.ParseResult;
import org.sharding.parser.SQLVisitor;
import org.sharding.shard.Condition.BinaryOperator;
import org.sharding.shard.ShardTable;
import org.sharding.shard.ShardUtil;
import org.sharding.shard.Table;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBetweenExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumericLiteralExpr;
import com.alibaba.druid.sql.ast.expr.SQLTextLiteralExpr;
import com.alibaba.druid.sql.ast.expr.SQLVariantRefExpr;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat.Column;

/**
 * 
 * @author wenlong.liu
 *
 */
public abstract class AbstractVisitor extends MySqlSchemaStatVisitor implements SQLVisitor {

	protected  ParseResult parseResult = new ParseResult();
	
	protected  ParseContext context;
	
	@Override
	public DatabaseDialect getDatabaseDialect() {
		return DatabaseDialect.MySQL;
	}
	
	@Override
	public ParseResult getParseResult(){
		return this.parseResult;
	}
	
	@Override
	public void setParseContext(ParseContext context) {
		this.context = context;
		this.parseResult.setSQLStatement(context.getSQLStatement());
	}

	@Override
	public ParseContext getParseContext() {
		return context;
	}
	
	@Override
    public boolean visit(final SQLBinaryOpExpr x) {
        switch (x.getOperator()) {
            case BooleanOr: 
                //parseContext.setHasOrCondition(true);
                break;
            case Equality: 
            	parseCondition(x.getLeft(), BinaryOperator.EQUAL, Arrays.asList(x.getRight()));
                break;
            default:
                break;
        }
        return super.visit(x);
    }
	
	@Override
    public boolean visit(final SQLInListExpr x) {
        if (!x.isNot()) {
        	parseCondition(x.getExpr()	, BinaryOperator.IN, x.getTargetList());
        }
        return super.visit(x);
    }
    
    @Override
    public boolean visit(final SQLBetweenExpr x) {
    	parseCondition(x.getTestExpr(), BinaryOperator.BETWEEN,  Arrays.asList(x.getBeginExpr(), x.getEndExpr()));
        return super.visit(x);
    }
	
    protected void parseCondition(String columnName, String tableName, BinaryOperator operator, SQLExpr valueExpr){
    	org.sharding.shard.Column shardingColumn = new org.sharding.shard.Column(ShardUtil.getExactlyValue(columnName), ShardUtil.getExactlyValue(tableName));
    	addShardingCondition(shardingColumn, operator, Arrays.asList(valueExpr));
    }
	/**
	 * 
	 * @param expr
	 * @param operator
	 * @param valueExprs
	 */
	protected void parseCondition(SQLExpr expr, BinaryOperator operator, List<SQLExpr> valueExprs){
    	Column druidColumn = getColumn(expr);
    	if(druidColumn==null) return;
    	org.sharding.shard.Column shardingColumn = new org.sharding.shard.Column(ShardUtil.getExactlyValue(druidColumn.getName()), ShardUtil.getExactlyValue(druidColumn.getTable()));
    	addShardingCondition(shardingColumn, operator, valueExprs);
    }
	
	/**
	 * 
	 * @param expr
	 * @param operator
	 * @param valueExprs
	 */
	protected void parseResultAddTable(String name, String alias){
		Table table = new Table(ShardUtil.getExactlyValue(name), ShardUtil.getExactlyValue(alias));
		this.getParseResult().addTable(table);
    }
	
	private boolean isShardingColumn(org.sharding.shard.Column shardingColumn){
		ShardTable table = getParseContext().getConfiguration().getTable(shardingColumn.getTable());
		if(table == null) return false;
		return  table.getShardingColumns().contains(shardingColumn.getName());
	}
	
	private void addShardingCondition(org.sharding.shard.Column shardingColumn, BinaryOperator operator, List<SQLExpr> valueExprs){
		if(isShardingColumn(shardingColumn)) 
        {
	        org.sharding.shard.Condition shardCondition = new org.sharding.shard.Condition();
	    	shardCondition.setColumn(shardingColumn);
	    	shardCondition.setOperator(operator);
	    	for(SQLExpr value : valueExprs)
	    	{
	    		if(SQLTextLiteralExpr.class.isAssignableFrom(value.getClass())){
	    			shardCondition.addValue(((SQLTextLiteralExpr)value).getText());
	    		}else if(SQLNumericLiteralExpr.class.isAssignableFrom(value.getClass())){
	    			shardCondition.addValue(((SQLNumericLiteralExpr)value).getNumber());
	    		}else if(value instanceof SQLVariantRefExpr){
	    			int index = ((SQLVariantRefExpr)value).getIndex();
	    			shardCondition.addValue(this.getParseContext().getParameters().get(index).getValue());
	    		}
	    	}
	    	if(!shardCondition.getValues().isEmpty())
	    		this.getParseResult().addShardCondition(shardCondition);
        }
	}
}
