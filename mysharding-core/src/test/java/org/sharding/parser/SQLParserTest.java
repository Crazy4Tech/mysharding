package org.sharding.parser;

import java.util.ArrayList;
import java.util.List;

import org.sharding.shard.Parameter;

import junit.framework.TestCase;

public class SQLParserTest  extends TestCase{

	public void test(){
		String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=? AND o.order_id=?";

		List<Parameter<? extends Object>> params = new ArrayList<Parameter<? extends Object>>();
		Parameter<Integer> param1 = new Parameter<Integer>(1, 20);
		params.add(param1);
		Parameter<Integer> param2 = new Parameter<Integer>(1, 100);
		params.add(param2);
		//ParseResult result = SQLParser.parse(sql, params);
		//result.getConditions();
	}
}
