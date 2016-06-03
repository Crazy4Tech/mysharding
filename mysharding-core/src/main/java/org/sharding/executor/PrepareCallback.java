package org.sharding.executor;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author pc
 *
 */
public abstract class PrepareCallback {

	public abstract void call(Statement statement) throws SQLException;
}
