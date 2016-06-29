package org.sharding.parser.mysql;


import org.sharding.parser.StatementType;
import org.sharding.shard.Table;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
/**
 * 
 * @author wenlong.liu
 *
 */
public class MySQLUpdateVisitor extends AbstractVisitor  {

	public MySQLUpdateVisitor(){
		parseResult.getRouteContext().setStatementType(StatementType.UPDATE);
	}
	
	@Override
	public boolean visit(final MySqlDeleteStatement x) {
		Table table = new Table(x.getTableSource().toString(), x.getTableSource().getAlias());
		this.getParseResult().addTable(table);
        return super.visit(x);
    }
}
