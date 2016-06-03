package org.sharding.exception;

import java.sql.SQLException;

/**
 * 
 * @author pc
 *
 */
public class ExecuteException extends SQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 851906766690479864L;
	
	
	public ExecuteException(String reason){
		super(reason);
	}

}
