package com.kvc.joy.core.persistence.jdbc.support.db.hsql;

import com.kvc.joy.commons.exception.SystemException;
import com.kvc.joy.core.persistence.jdbc.support.db.DbSupport;
import com.kvc.joy.core.persistence.jdbc.support.db.Schema;
import com.kvc.joy.core.persistence.jdbc.support.db.SqlStatementBuilder;
import com.kvc.joy.core.persistence.jdbc.support.utils.JdbcTool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * HsqlDb-specific support
 */
public class HsqlDbSupport extends DbSupport {
	/**
	 * Creates a new instance.
	 * 
	 * @param connection The connection to use.
	 */
	public HsqlDbSupport(Connection connection) {
		super(connection);
	}

	public String getScriptLocation() {
		return "com/googlecode/flyway/core/dbsupport/hsql/";
	}

	public String getCurrentUserFunction() {
		return "USER()";
	}

	@Override
	protected String doGetCurrentSchema() {
		ResultSet resultSet = null;
		String schema = null;

		try {
			resultSet = connection.getMetaData().getSchemas();
			while (resultSet.next()) {
				if (resultSet.getBoolean("IS_DEFAULT")) {
					schema = resultSet.getString("TABLE_SCHEM");
					break;
				}
			}
		} catch (Exception e) {
			throw new SystemException(e);
		} finally {
			JdbcTool.closeResultSet(resultSet);
		}

		return schema;
	}

	@Override
	protected void doSetCurrentSchema(Schema schema) {
		JdbcTool.execute(connection, "SET SCHEMA " + schema);
	}

	public boolean supportsDdlTransactions() {
		return false;
	}

	public String getBooleanTrue() {
		return "1";
	}

	public String getBooleanFalse() {
		return "0";
	}

	public SqlStatementBuilder createSqlStatementBuilder() {
		return new HsqlSqlStatementBuilder();
	}

	@Override
	public String doQuote(String identifier) {
		return "\"" + identifier + "\"";
	}

	@Override
	public Schema getSchema(String name) {
		return new HsqlSchema(this, name);
	}

	@Override
	public boolean catalogIsSchema() {
		return false;
	}

	@Override
	public int getNullType() {
		return Types.VARCHAR;
	}

}
