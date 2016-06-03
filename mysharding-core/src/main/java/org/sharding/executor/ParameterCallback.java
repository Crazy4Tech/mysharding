package org.sharding.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author pc
 *
 */
public abstract class ParameterCallback {

	public abstract void call(PreparedStatement preparedStatement) throws SQLException;
}
