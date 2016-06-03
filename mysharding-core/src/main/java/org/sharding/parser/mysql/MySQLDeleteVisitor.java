package org.sharding.parser.mysql;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
/**
 * 
 * @author wenlong.liu
 *
 */
public class MySQLDeleteVisitor extends AbstractVisitor  {

	@Override
	public boolean visit(final MySqlUpdateStatement x) {
		parseResultAddTable(x.getTableSource().toString(), x.getTableSource().getAlias());
        return super.visit(x);
    }
}
