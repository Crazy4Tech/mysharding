package org.sharding.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sharding.executor.MergeContext;
import org.sharding.shard.OrderbyValue;
import org.sharding.shard.ShardUtil;

/**
 * 
 * @author wenlongLiu
 *
 */
public class ShardResultSet extends ResultsetWrapper {

	private final List<ResultSet> delegates;
	private final List<ResultSet> copyDelegates;
	private final MergeContext context;
	private boolean initial = false;
	
	public ShardResultSet(List<ResultSet> delegates, MergeContext context){
		this.delegates = delegates;
		this.context = context;
		this.copyDelegates= new ArrayList<ResultSet>(delegates.size());
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
		return skipOffset();
	}
	
	private boolean skipOffset() throws SQLException{
		if(context.getLimit()!=null)
		{
			for(int i=0; i<context.getLimit().getOffset(); i++)
			{
				if(nextForSharding()==false) return false;
			}
		}
		return true;
	}
	
	private boolean nextForSharding() throws SQLException{
		if(!context.getOrderbys().isEmpty())
		{
			OrderbyValue currentValue = null;
			for(ResultSet resultset : copyDelegates)
			{
				
					OrderbyValue otherValue = ShardUtil.getOrderValue(context.getOrderbys(), resultset);
					if(currentValue==null){
						currentValue = otherValue;
						currentResultSet = resultset;
					}else{
						if(currentValue.compareTo(otherValue)>0) currentResultSet = resultset;
					}
				
			}
		}
		else
		{
			//if(!currentResultSet.)
		}
		return !copyDelegates.isEmpty();
	}

	@Override
	public void close() throws SQLException {
		for(ResultSet resultset : delegates)
		{
			resultset.close();
		}
	}

	@Override
	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCursorName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void beforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterLast() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean first() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean last() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean absolute(int row) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean previous() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowInserted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
