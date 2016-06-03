package org.sharding.executor;

import org.sharding.exception.DatabaseUnsupportedException;

public enum  DatabaseDialect {

	 MySQL, Oracle, SQLServer, DB2;
	    
    
    public static DatabaseDialect valueFrom(final String databaseDialect) {
        try {
            return DatabaseDialect.valueOf(databaseDialect);
        } catch (final IllegalArgumentException ex) {
            throw new DatabaseUnsupportedException(databaseDialect);
        }
    }
}
