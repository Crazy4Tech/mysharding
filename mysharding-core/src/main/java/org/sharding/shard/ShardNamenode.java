package org.sharding.shard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sharding.router.loadbalance.AccessMode;
import org.sharding.router.loadbalance.LoanBalanceMode;

/**
 * 
 * @author wenlong.liu
 *
 */
public class ShardNamenode
{
	private String name;
	
	private LoanBalanceMode loadbalance;
	
	private Collection<ShardDatanode> datanodes = new ArrayList<ShardDatanode>();
	
	public ShardNamenode(String name, LoanBalanceMode loadbalance){
		this.name = name;
		this.loadbalance = loadbalance;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public LoanBalanceMode getLoadbalance() {
		return loadbalance;
	}
	
	public void setLoadbalance(LoanBalanceMode loadbalance) {
		this.loadbalance = loadbalance;
	}

	public Collection<ShardDatanode> getDatanodes() {
		return datanodes;
	}
	
	public void addDatanode(ShardDatanode datanode){
		if(this.datanodes==null){
			this.datanodes = new ArrayList<ShardDatanode>();
		}
		this.datanodes.add(datanode);
	}

	public List<ShardDatanode> getReadDatanodes() {
		List<ShardDatanode> readnodes = new ArrayList<ShardDatanode>(datanodes.size());
		for(ShardDatanode datenode : datanodes)
		{
			if(datenode.getAccessMode()==AccessMode.READ  
				|| datenode.getAccessMode()==AccessMode.READWRITE)
			{
				readnodes.add(datenode);
			}
		}
		return readnodes;
	}
	
	public List<ShardDatanode> getWriteDatanodes() {
		List<ShardDatanode> writenodes = new ArrayList<ShardDatanode>(datanodes.size());
		for(ShardDatanode datenode : datanodes)
		{
			if(datenode.getAccessMode()==AccessMode.WRITE  
					|| datenode.getAccessMode()==AccessMode.READWRITE)
				{
					writenodes.add(datenode);
				}
		}
		return writenodes;
	}
	
	
}
