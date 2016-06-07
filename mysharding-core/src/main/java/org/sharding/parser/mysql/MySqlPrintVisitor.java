package org.sharding.parser.mysql;

import org.sharding.router.DataSourceMapping;
import org.sharding.shard.ShardUtil;

import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;


public class MySqlPrintVisitor extends MySqlOutputVisitor {
	
	DataSourceMapping mapping;
	
	public MySqlPrintVisitor(StringBuilder output, DataSourceMapping mapping){
		super(output);
		this.mapping = mapping;
	}

	
	@Override
    public final boolean visit(final SQLExprTableSource x) {
		String actualName = mapping.getActualName(ShardUtil.getExactlyValue(x.toString()));
		if(actualName!=null){
			 print(actualName);
		}
		if (x.getAlias() != null) {
            print(' ');
            print(x.getAlias());
        }

        for (int i = 0; i < x.getHintsSize(); ++i) {
            print(' ');
            x.getHints().get(i).accept(this);
        }
		return false;
    }
}
