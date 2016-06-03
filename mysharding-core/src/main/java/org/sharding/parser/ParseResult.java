/**
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package org.sharding.parser;

import org.sharding.executor.MergeContext;
import org.sharding.router.RouteContext;
import org.sharding.shard.Aggregate;
import org.sharding.shard.Condition;
import org.sharding.shard.GroupBy;
import org.sharding.shard.Limit;
import org.sharding.shard.OrderBy;
import org.sharding.shard.Table;

import com.alibaba.druid.sql.ast.SQLStatement;

/**
 * 
 * @author wenlong.liu
 *
 */
public final class ParseResult {
	
	private final RouteContext routeContext;
	
	private final MergeContext mergeContext;
	
	public ParseResult(){
		this.routeContext = new RouteContext();
		this.mergeContext = new MergeContext();
	}

	public void setSQLStatement(SQLStatement statement) {
		this.routeContext.setSQLStatement(statement);
	}
	
	public void addShardCondition(Condition tableCondition) {
		this.routeContext.addShardCondition(tableCondition);
	}

	public void addTable(Table table) {
		this.routeContext.addTable(table);
	}

	public void setLimit(Limit limit) {
		this.mergeContext.setLimit(limit);
	}
	
	public void addGroupBy(GroupBy groupby) {
		this.mergeContext.addGroupBy(groupby);
	}
	
	public void addAggregate(Aggregate aggregate) {
		this.mergeContext.addAggregate(aggregate);
	}
	
	public void addOrderBy(OrderBy orderby) {
		this.mergeContext.addOrderBy(orderby);
	}

	public RouteContext getRouteContext() {
		return routeContext;
	}

	public MergeContext getMergeContext() {
		return mergeContext;
	}
}
