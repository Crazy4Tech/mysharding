package org.sharding.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.sharding.executor.MergeContext;
import org.sharding.shard.OrderbyValue;
import org.sharding.shard.ShardUtil;

/**
 * 
 * @author wenlongLiu
 *
 */
public class ShardGenericResultSet extends AbstractCompositeResultSet {

	private final MergeContext context;
	
	private boolean initial = false;
	
	public ShardGenericResultSet(List<ResultSet> delegates, MergeContext context){
		super(delegates);
		this.context = context;
	}
	
	
	@Override
	public boolean next() throws SQLException {
		if(!initial)
		{
			return initialNext();
		}
		else
		{
			return nextForSharding();
		}
	}
	
	private boolean initialNext() throws SQLException {
		for(ResultSet each : delegates)
		{
			if(each.next()) 
			{
				copyDelegates.add(each);
				setCurrentResultSet(each);
			}
		}
		initial = true;
		orderByResultSet();
		return skipOffset();
	}
	
	private boolean skipOffset() throws SQLException{
		if(context.getLimit()!=null)
		{
			for(int i=1; i<context.getLimit().getOffset(); i++)
			{
				if(nextForSharding()==false) return false;
			}
		}
		return !copyDelegates.isEmpty();
	}
	
	private boolean nextForSharding() throws SQLException{
		if(!currentResultSet.next())
		{
			copyDelegates.remove(currentResultSet);
			if(!copyDelegates.isEmpty())currentResultSet = copyDelegates.get(0);
		}
		orderByResultSet();
		return !copyDelegates.isEmpty();
	}
	
	/**
	 * 排序
	 * @return
	 * @throws SQLException
	 */
	private void orderByResultSet() throws SQLException{
		if(!context.getOrderbys().isEmpty())
		{
			OrderbyValue currentValue = null;
			for(ResultSet resultset : copyDelegates)
			{
				OrderbyValue otherValue = ShardUtil.getOrderValue(context.getOrderbys(), resultset);
				if(currentValue==null)
				{
					currentValue = otherValue;
					currentResultSet = resultset;
				}
				else
				{
					if(currentValue.compareTo(otherValue)>0) currentResultSet = resultset;
				}
			}
		}
	}
}
