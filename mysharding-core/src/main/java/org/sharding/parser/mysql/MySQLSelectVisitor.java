package org.sharding.parser.mysql;

import org.sharding.shard.GroupBy;
import org.sharding.shard.Limit;
import org.sharding.shard.OrderBy;
import org.sharding.shard.OrderBy.OrderByType;
import org.sharding.shard.Parameter;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.expr.SQLAggregateExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.expr.SQLVariantRefExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlSelectGroupByExpr;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
/**
 * 
 * @author wenlong.liu
 *
 */
public class MySQLSelectVisitor extends AbstractVisitor  {


	
	@Override
    public final boolean visit(final SQLExprTableSource x) {
		super.visit(x);
		parseResultAddTable(x.toString(), x.getAlias());
        return false;
    }
	
    @Override
    public boolean visit(final SQLAggregateExpr x) {
        return super.visit(x);
    }
    
    public boolean visit(final SQLOrderBy x) {
    	for(SQLSelectOrderByItem item : x.getItems())
    	{
    		SQLExpr expr = item.getExpr();
    		OrderByType orderByType = null == item.getType() ? OrderByType.ASC : OrderByType.valueOf(item.getType().name());
    		if (expr instanceof SQLIdentifierExpr) {
    			this.getParseResult().addOrderBy(new OrderBy(((SQLIdentifierExpr) expr).getName(), orderByType));
            } else if (expr instanceof SQLPropertyExpr) {
            	getParseResult().addOrderBy(new OrderBy(((SQLPropertyExpr) expr).getName(), orderByType));
            }
    	}
        return super.visit(x);
    }
    
 
    @Override
    public boolean visit(final MySqlSelectGroupByExpr x) {
    	OrderByType orderByType = null == x.getType() ? OrderByType.ASC : OrderByType.valueOf(x.getType().name());
    	if (x.getExpr() instanceof SQLIdentifierExpr) {
			this.getParseResult().addGroupBy(new GroupBy(((SQLIdentifierExpr) x.getExpr()).getName(), orderByType));
        } else if (x.getExpr() instanceof SQLPropertyExpr) {
        	getParseResult().addGroupBy(new GroupBy(((SQLPropertyExpr) x.getExpr()).getName(), orderByType));
        }
        return super.visit(x);
    }
    
    
    @Override
    public boolean visit(final MySqlSelectQueryBlock.Limit x) {
    	int offset = 0, rowCount = 0;
    	if(x.getOffset() instanceof SQLIntegerExpr){
    		offset = ((SQLIntegerExpr)x.getOffset()).getNumber().intValue();
    		((SQLIntegerExpr)x.getOffset()).setNumber(0);
		}else if(x.getOffset() instanceof SQLVariantRefExpr){
			int index = ((SQLVariantRefExpr)x.getOffset()).getIndex();
			offset = (int) this.getParseContext().getParameters().get(index).getValue();
			this.getParseContext().getParameters().set(index, new Parameter<Integer>(index, 0));
		}
    	if(x.getRowCount() instanceof SQLIntegerExpr){
    		rowCount = ((SQLIntegerExpr)x.getRowCount()).getNumber().intValue();
    		((SQLIntegerExpr)x.getRowCount()).setNumber(offset + rowCount);
		}else if(x.getRowCount() instanceof SQLVariantRefExpr){
			int index = ((SQLVariantRefExpr)x.getRowCount()).getIndex();
			rowCount = (int) this.getParseContext().getParameters().get(index).getValue();
			this.getParseContext().getParameters().set(index, new Parameter<Integer>(index, offset + rowCount));
		}
    	this.getParseResult().setLimit(new Limit(rowCount, offset));
        return false;
    }
    
    
    @Override
    public void endVisit(final SQLSelectStatement x) {
        super.endVisit(x);
    }
}
