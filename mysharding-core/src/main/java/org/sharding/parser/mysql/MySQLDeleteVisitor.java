package org.sharding.parser.mysql;

import org.sharding.parser.StatementType;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
/**
 * 
 * @author wenlong.liu
 *
 */
public class MySQLDeleteVisitor extends AbstractVisitor  {

	public MySQLDeleteVisitor(){
		parseResult.getRouteContext().setStatementType(StatementType.DELETE);
	}
	
	@Override
	public boolean visit(final MySqlUpdateStatement x) {
		parseResultAddTable(x.getTableSource().toString(), x.getTableSource().getAlias());
        return super.visit(x);
    }
}
