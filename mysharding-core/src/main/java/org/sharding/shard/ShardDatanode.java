package org.sharding.shard;

import java.lang.reflect.Method;

/**
 * 
 * @author wenlong.liu
 *
 */
public class ShardDatanode{

	private String  name;
	private String  url;
	private String  username;
	private String  password;
	private String  driverClassName;
	private String  accessMode;
	private Integer weight;
	private Integer initialSize;
	private Integer maxActive;
	private Integer maxWait;
	private Integer maxIdle;
	private Integer minIdle;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public Integer getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}
	public Integer getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}
	public Integer getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}
	public Integer getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	public Integer getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public void setAttribute(String key, String value)
	{
		char[] cs=key.toCharArray();
        cs[0]-=32;
        String methodName = "set"+String.valueOf(cs);
        Method method = null;
        try{
        		method = getClass().getMethod(methodName, Integer.class);
        		method.invoke(this, Integer.valueOf(value));
        }catch(Exception ex){
        }
        
    	if(method==null) 
    	{
    		try{
	    		method = getClass().getMethod(methodName, String.class);
	    		method.invoke(this, value);
    		}catch(Exception ex){
    	    }
    	}
	    

	}
}
