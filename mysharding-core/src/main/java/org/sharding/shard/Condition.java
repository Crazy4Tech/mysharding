package org.sharding.shard;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wenlong.liu
 *
 */
public class Condition {
	
	private  Column column;
    
    private  BinaryOperator operator;
    
    private  List<Object> values = new ArrayList<Object>();
    
    
    public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public BinaryOperator getOperator() {
		return operator;
	}

	public void setOperator(BinaryOperator operator) {
		this.operator = operator;
	}

	public List<Object> getValues() {
		return values;
	}

	public void addValue(Object value) {
		this.values.add(value);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(column.toString()).append(" ");
		buffer.append(operator).append(" ");
		buffer.append(values.toString());
		return buffer.toString();
	}
	
	public enum BinaryOperator {
		EQUAL, BETWEEN, IN, NOTIN;
		
		public static BinaryOperator of(String value)
		{
			 if("=".equalsIgnoreCase(value))
				 return EQUAL;
			 for(BinaryOperator blah : values()) 
			 {
		          if(blah.toString().equalsIgnoreCase(value))
		        	  return blah;
		     }
			 return null;
		}
    }


	

	
}
