package org.sharding.shard;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author wenlong.liu
 *
 */
public class ShardNamenode
{
	private String name;
	private String loadbalance;
	private Collection<ShardDatanode> datanodes = new ArrayList<ShardDatanode>();
	
	public ShardNamenode(String name, String loadbalance){
		this.name = name;
		this.loadbalance = loadbalance;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getLoadbalance() {
		return loadbalance;
	}
	
	public void setLoadbalance(String loadbalance) {
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

	
	
}
