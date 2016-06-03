package org.sharding.parser;


import java.util.List;

import org.sharding.exception.DatabaseUnsupportedException;
import org.sharding.executor.DatabaseDialect;
import org.sharding.executor.ExecuteContext;
import org.sharding.parser.mysql.MySQLDeleteVisitor;
import org.sharding.parser.mysql.MySQLInsertVisitor;
import org.sharding.parser.mysql.MySQLSelectVisitor;
import org.sharding.parser.mysql.MySQLUpdateVisitor;
import org.sharding.shard.Parameter;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.dialect.db2.parser.DB2StatementParser;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.sqlserver.parser.SQLServerStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;


public class SQLParser {

	   
	public static ParseResult parse(ExecuteContext context)
    {
		SQLStatement statement = createSQLStatementParser(DatabaseDialect.MySQL, context.getSql()).parseStatement();
		SQLVisitor visitor = createSQLVisitor(DatabaseDialect.MySQL, statement);
		ParseContext parseContext = new ParseContext();
		parseContext.setParameters((List<Parameter<?>>)context.getParameters());
		parseContext.setConfiguration(context.getConfiguration());
		parseContext.setSQLStatement(statement);
		visitor.setParseContext(parseContext);
		statement.accept((SQLASTVisitor)visitor);
		return visitor.getParseResult();
    }
	
	
	 private static SQLStatementParser createSQLStatementParser(final DatabaseDialect databaseType, final String sql) {
	        switch (databaseType) {
	            case MySQL: 
	                return new MySqlStatementParser(sql);
	            case Oracle: 
	                return new OracleStatementParser(sql);
	            case SQLServer: 
	                return new SQLServerStatementParser(sql);
	            case DB2: 
	                return new DB2StatementParser(sql);
	            default: 
	                throw new DatabaseUnsupportedException(String.format("Cannot support database type [%s]", databaseType));
	        }
	  }
	 
	 private static SQLVisitor createSQLVisitor(final DatabaseDialect databaseType, final SQLStatement sqlStatement) {
		 switch (databaseType) {
         case MySQL: 
        	 if (sqlStatement instanceof SQLSelectStatement) {
                 return new MySQLSelectVisitor();
             }
             if (sqlStatement instanceof SQLInsertStatement) {
            	 return new MySQLInsertVisitor();
             }
             if (sqlStatement instanceof SQLUpdateStatement) {
            	 return new MySQLUpdateVisitor();
             }
             if (sqlStatement instanceof SQLDeleteStatement) {
            	 return new MySQLDeleteVisitor();
             }
         default: 
             throw new DatabaseUnsupportedException(String.format("Cannot support database type [%s]", databaseType));
     }
    }
}
