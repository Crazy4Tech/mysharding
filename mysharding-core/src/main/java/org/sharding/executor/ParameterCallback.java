package org.sharding.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * reparedStatement 给SQL语句设置参数的回调
 * setInteger(),setString(),set......
 * 
 * @author wenlong.liu
 *
 */
public abstract class ParameterCallback {

	public abstract void call(PreparedStatement preparedStatement) throws SQLException;
}
