package org.sharding.parser.mysql;


import org.sharding.shard.Condition.BinaryOperator;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
/**
 * 
 * @author wenlong.liu
 *
 */
public class MySQLInsertVisitor extends AbstractVisitor  {

	@Override
	public boolean visit(final MySqlInsertStatement x) {
		parseResultAddTable(x.getTableSource().toString(), x.getTableSource().getAlias());
		for (int i = 0; i < x.getColumns().size(); i++) {
			parseCondition(x.getColumns().get(i).toString(), x.getTableName().toString() , BinaryOperator.EQUAL, x.getValues().getValues().get(i));
	    }
        return super.visit(x);
    }
}
