package org.sharding.shard;

public class Parameter<T> {

	private int   index;
	private T     value;
	
	public Parameter(int index, T value){
		this.index = index;
		this.value = value;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
}
