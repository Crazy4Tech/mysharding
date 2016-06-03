package org.sharding.shard;
/**
 * 
 * @author liuwenlong
 *
 */
public class Table {
	
	private String name;
	
	private String alias;
	
	public Table(){
	}
	
	public Table(String name, String alias){
		this.name = name;
		this.alias = alias;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}

	
	@Override
	public int hashCode() {
		int hash = 1;
	    hash = hash * 31 + name.hashCode();
	    hash = hash * 31 + (alias == null ? 0 : alias.hashCode());
	    return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==null) 
			return false;
		if(!(obj instanceof Table))
			return false;
		
		return name.equals(((Table)obj).getName());
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
}
