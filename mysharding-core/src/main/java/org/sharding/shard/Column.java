package org.sharding.shard;



/**
 * 
 * @author wenlong.liu
 *
 */
public class Column {
	
	private String table;
	
	private String name;

	private String alias;

	
	
	public Column(String name,  String table){
		this.name = name;
		this.table = table;
	}
	
	public Column(String name, String alias, String table){
		this.name = name;
		this.alias = alias;
		this.table = table;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
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
	public String toString() {
         if (table != null) {
             return table + "." + name;
         }

         return name;
     }
}
