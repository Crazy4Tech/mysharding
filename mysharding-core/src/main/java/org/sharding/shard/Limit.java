package org.sharding.shard;

/**
 * 
 * @author wenlong.liu
 *
 */
public class Limit {

	private final int offset;
	
	private final int rowCount;
	
    public Limit(int offset, int rowCount){
    	this.rowCount = rowCount;
    	this.offset = offset;
    }
    
    public int getRowCount(){
    	return this.rowCount;
    }
    
    public int getOffset(){
    	return this.offset;
    }
}
