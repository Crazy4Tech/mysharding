package org.sharding.shard;
/**
 * 
 * @author wenlongLiu
 *
 */
public class ShardUtil {

	public static String getExactlyValue(String value) {
		if(value==null) return null;
		value = value.replace("[", "");
		value = value.replace("]", "");
		value = value.replace("`", "");
		value = value.replace("\"", "");
		return value;
    }
}
