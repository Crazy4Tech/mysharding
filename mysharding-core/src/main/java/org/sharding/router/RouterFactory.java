package org.sharding.router;

import org.sharding.executor.ExecuteContext;
import org.sharding.parser.ParseResult;
import org.sharding.parser.SQLParser;

/**
 * 
 * @author wenlong.liu
 *
 */
public class RouterFactory {

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static Router createRouter(ExecuteContext context){
		ParseResult parseResult = SQLParser.parse(context);
		context.setMergeContext(parseResult.getMergeContext());
		DataSourceRouter detegate = new DataSourceRouter(parseResult.getRouteContext(), context.getConfiguration());
		Router router = new LoadBalanceRouter(detegate, context.getConfiguration());
		return router;
	}
}
