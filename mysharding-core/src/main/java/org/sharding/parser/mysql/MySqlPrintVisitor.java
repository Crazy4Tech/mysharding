package org.sharding.parser.mysql;

import org.sharding.router.DataSourceMapping;
import org.sharding.shard.ShardUtil;

import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;


public class MySqlPrintVisitor extends MySqlOutputVisitor {
	
	DataSourceMapping mapping;
	
	public MySqlPrintVisitor(DataSourceMapping mapping){
		super(new StringBuffer());
		this.mapping = mapping;
	}

	
	@Override
    public final boolean visit(final SQLExprTableSource x) {
		String actualName = mapping.getActualName(ShardUtil.getExactlyValue(x.toString()));
		if(actualName!=null){
			((SQLIdentifierExpr)x.getExpr()).setName(actualName);
		}
		return false;
    }
}
