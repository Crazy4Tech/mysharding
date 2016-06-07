package org.sharding.router;


import java.util.Collection;
import org.sharding.executor.ExecuteContext;
import org.sharding.parser.ParseResult;
import org.sharding.parser.SQLParser;

/**
 * 
 * @author wenlong.liu
 *
 */
public class Router {

	
	public static Collection<RouteUnit> route(ExecuteContext context){
		ParseResult parseResult = SQLParser.parse(context);
		context.setMergeContext(parseResult.getMergeContext());
		DataSourceRouter router = new DataSourceRouter(parseResult.getRouteContext(), context.getConfiguration());
		return router.route();
	}
	
	
}
