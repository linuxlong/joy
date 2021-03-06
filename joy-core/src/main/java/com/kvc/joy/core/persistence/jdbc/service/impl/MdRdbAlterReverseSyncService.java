package com.kvc.joy.core.persistence.jdbc.service.impl;

import com.kvc.joy.commons.lang.string.StringTool;
import com.kvc.joy.commons.log.Log;
import com.kvc.joy.commons.log.LogFactory;
import com.kvc.joy.core.init.support.properties.JoyProperties;
import com.kvc.joy.core.persistence.jdbc.model.vo.MdRdbColumn;
import com.kvc.joy.core.persistence.jdbc.model.vo.MdRdbColumnComment;
import com.kvc.joy.core.persistence.jdbc.model.vo.MdRdbTable;
import com.kvc.joy.core.persistence.jdbc.model.vo.RdbConnection;
import com.kvc.joy.core.persistence.jdbc.service.IMdRdbAlterReverseSyncService;
import com.kvc.joy.core.persistence.jdbc.support.EntityCommentAndDefaultValueScanner;
import com.kvc.joy.core.persistence.jdbc.support.MdRdbColumnDefaultValueResovler;
import com.kvc.joy.core.persistence.jdbc.support.db.DbSupport;
import com.kvc.joy.core.persistence.jdbc.support.db.DbSupportFactory;
import com.kvc.joy.core.persistence.jdbc.support.utils.JdbcTool;
import com.kvc.joy.core.persistence.jdbc.support.utils.MdRdbTool;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @since 1.0.0
 * @author Kevice
 * @time 2013-4-10 下午8:15:48
 */
public class MdRdbAlterReverseSyncService implements IMdRdbAlterReverseSyncService {

	protected static final Log logger = LogFactory.getLog(MdRdbAlterReverseSyncService.class);

	@Transactional
	public void generate(String... pkgs) {
		List<MdRdbTable> tables = EntityCommentAndDefaultValueScanner.scan(pkgs);
		List<String> sqlList = genTableCommentSql(tables);
		sqlList.addAll(genColumnCommentSql(tables));
		executeSql(sqlList);
	}

	protected List<String> genTableCommentSql(List<MdRdbTable> tables) {
		List<String> sqlList = new ArrayList<String>();
		Connection conn = JdbcTool.getConnectionByDsId(JoyProperties.DB_DATASOURCEID);
		try {
			RdbConnection rdbConnection = new RdbConnection(JoyProperties.DB_DATASOURCEID, conn);
			Map<String, MdRdbTable> tableMap = MdRdbTool.getTables(rdbConnection);
			for (MdRdbTable table : tables) {
				String tableComment = table.getComment();
				if (StringTool.isNotBlank(tableComment)) {
					String tableName = table.getName();
					MdRdbTable tableInDb = tableMap.get(tableName);
					String tableCommentInDb = tableInDb.getComment();
					if (StringTool.equals(tableComment, tableCommentInDb) == false) {
						DbSupport dbSupport = DbSupportFactory.createDbSupport(conn);
						sqlList.add(dbSupport.getAlterTableCommentSql(table));
						tableInDb.setComment(tableComment);
					}
				}
			}
		} finally {
			JdbcTool.closeConnection(conn);
		}
		return sqlList;
	}
	
	protected List<String> genColumnCommentSql(List<MdRdbTable> tables) {
		List<String> sqlList = new ArrayList<String>();
		Connection conn = JdbcTool.getConnectionByDsId(JoyProperties.DB_DATASOURCEID);
		try {
			RdbConnection rdbConnection = new RdbConnection(JoyProperties.DB_DATASOURCEID, conn);
			DbSupport dbSupport = DbSupportFactory.createDbSupport(conn);
			for (MdRdbTable table : tables) {
				Map<String, MdRdbColumn> columnMap = MdRdbTool.getColumns(rdbConnection, table.getName());
				Collection<MdRdbColumn> columns = table.getColumns();
				for (MdRdbColumn column : columns) {
					String columnName = column.getName();
					MdRdbColumn columnInDb = columnMap.get(columnName);
					if (columnInDb == null) {
						logger.error("列不存在: " + table.getName() + "." + columnName);
						continue;
					}
					
					// comment
					MdRdbColumnComment comment = column.getComment();
					String columnComment = comment.toString();
					MdRdbColumnComment commentInDb = columnInDb.getComment();
					String columnCommentInDb = commentInDb == null ? "" : commentInDb.toString();
					if (StringTool.equals(columnComment, columnCommentInDb) == false) {
						sqlList.add(dbSupport.getAlterColumnCommentSql(table, column, columnInDb));
						columnInDb.setComment(comment);
					}
					
					// default value, 必须在comment后面，否则会被刷掉
					String defaultValue = MdRdbColumnDefaultValueResovler.getDefaultValue(dbSupport, column);
					column.setDefaultValue(defaultValue);
					String defaultValueInDb = columnInDb.getDefaultValue();
					if (StringTool.equals(defaultValue, defaultValueInDb) == false) {
						sqlList.add(dbSupport.getAlterColumnDefaultValueSql(table, column, columnInDb));
						columnInDb.setDefaultValue(defaultValue);
					}
				}
			}
		} finally {
			JdbcTool.closeConnection(conn);
		}
		return sqlList;
	}
	
	protected void executeSql(List<String> sqlList) {
		if(sqlList.isEmpty() == false && logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder("\n实体表结构更新SQL语句如下: \n");
			for (String sql : sqlList) {
				sb.append(sql).append("\n");
			}
			logger.debug(sb.toString());
		}
		if (sqlList.isEmpty()) {
			logger.info("实体表结构未改变，不用同步数据库。");
		} else {
			logger.info("开始更新实体表结构...");
			long start = System.currentTimeMillis();
			String[] sqls = sqlList.toArray(new String[sqlList.size()]);
			JdbcTool.jdbcTemplate().batchUpdate(sqls);
			long end = System.currentTimeMillis();
			logger.info("实体表结构更新完成，共执行" + sqlList.size() + "条SQL语句，耗时" + ((end - start) / 1000) + "秒。");
		}
	}

}
