package org.sharding.executor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection配置信息的回调
 * 
 * wenlong.liu
 *
 */
public abstract class ConnectionCallback {

	public abstract void execute(final Connection statement)  throws SQLException;
}
