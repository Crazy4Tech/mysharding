package org.sharding.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * 
 * @author wenlong.liu
 *
 */
public abstract class AbstractResultsetWrapper extends AbstractUnsupportedOperationResultSet{
	
	protected ResultSet currentResultSet;
	
	@Override
	public Statement getStatement() throws SQLException {
		return this.currentResultSet.getStatement();
	}
	
	@Override
	public boolean wasNull() throws SQLException {
		return this.currentResultSet.wasNull();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return this.currentResultSet.getWarnings();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return this.currentResultSet.getMetaData();
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		this.currentResultSet.setFetchDirection(direction);
	}

	@Override
	public int getFetchDirection() throws SQLException {
		return this.currentResultSet.getFetchDirection();
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		this.currentResultSet.setFetchSize(rows);
	}

	@Override
	public int getFetchSize() throws SQLException {
		return this.currentResultSet.getFetchSize();
	}

	@Override
	public int getType() throws SQLException {
		return this.currentResultSet.getType();
	}

	@Override
	public int getConcurrency() throws SQLException {
		return this.currentResultSet.getConcurrency();
	}
	
	protected void setCurrentResultSet(ResultSet resultSet){
		this.currentResultSet = resultSet;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		return currentResultSet.getString(columnIndex);
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		return currentResultSet.getBoolean(columnIndex);
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		return currentResultSet.getByte(columnIndex);
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		return currentResultSet.getShort(columnIndex);
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		return currentResultSet.getInt(columnIndex);
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		return currentResultSet.getLong(columnIndex);
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		return currentResultSet.getFloat(columnIndex);
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		return currentResultSet.getDouble(columnIndex);
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		return currentResultSet.getBigDecimal(columnIndex);
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		return currentResultSet.getBytes(columnIndex);
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		return currentResultSet.getDate(columnIndex);
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		return currentResultSet.getTime(columnIndex);
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return currentResultSet.getTimestamp(columnIndex);
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		return currentResultSet.getAsciiStream(columnIndex);
	}

	@Override
	@Deprecated
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return currentResultSet.getUnicodeStream(columnIndex);
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		return currentResultSet.getBinaryStream(columnIndex);
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		return currentResultSet.getString(columnLabel);
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		return currentResultSet.getBoolean(columnLabel);
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		return currentResultSet.getByte(columnLabel);
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		return currentResultSet.getShort(columnLabel);
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		return currentResultSet.getInt(columnLabel);
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		return currentResultSet.getLong(columnLabel);
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		return currentResultSet.getFloat(columnLabel);
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		return currentResultSet.getDouble(columnLabel);
	}

	@Override
	@Deprecated
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		return currentResultSet.getBigDecimal(columnLabel, scale);
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		return currentResultSet.getBytes(columnLabel);
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		return currentResultSet.getDate(columnLabel);
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		return currentResultSet.getTime(columnLabel);
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		return currentResultSet.getTimestamp(columnLabel);
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		return currentResultSet.getAsciiStream(columnLabel);
	}

	@Override
	@Deprecated
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		return currentResultSet.getUnicodeStream(columnLabel);
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		return currentResultSet.getBinaryStream(columnLabel);
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		return currentResultSet.getObject(columnIndex);
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		return currentResultSet.getObject(columnLabel);
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		return currentResultSet.findColumn(columnLabel);
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		return currentResultSet.getCharacterStream(columnIndex);
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		return currentResultSet.getCharacterStream(columnLabel);
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return currentResultSet.getBigDecimal(columnIndex);
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		return currentResultSet.getBigDecimal(columnLabel);
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		return currentResultSet.getObject(columnIndex, map);
	}


	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		return currentResultSet.getBlob(columnIndex);
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		return currentResultSet.getClob(columnIndex);
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		return currentResultSet.getArray(columnIndex);
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		return currentResultSet.getObject(columnLabel, map);
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		return currentResultSet.getRef(columnLabel);
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		return currentResultSet.getBlob(columnLabel);
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		return currentResultSet.getClob(columnLabel);
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		return currentResultSet.getArray(columnLabel);
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return currentResultSet.getDate(columnIndex, cal);
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		return currentResultSet.getDate(columnLabel, cal);
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return currentResultSet.getTime(columnIndex, cal);
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return currentResultSet.getTime(columnLabel, cal);
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		return currentResultSet.getTimestamp(columnIndex, cal);
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		return currentResultSet.getTimestamp(columnLabel, cal);
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		return currentResultSet.getURL(columnIndex);
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		return currentResultSet.getURL(columnLabel);
	}
	
	@Override
	public String getNString(int columnIndex) throws SQLException {
		return currentResultSet.getNString(columnIndex);
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		return currentResultSet.getNString(columnLabel);
	}
	
	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		return currentResultSet.getObject(columnIndex, type);
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		return currentResultSet.getObject(columnLabel, type);
	}
	
	@Override
	public NClob getNClob(final int columnIndex) throws SQLException {
		return currentResultSet.getNClob(columnIndex);
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		return currentResultSet.getNClob(columnLabel);
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return currentResultSet.getSQLXML(columnIndex);
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return currentResultSet.getSQLXML(columnLabel);
	}
}
