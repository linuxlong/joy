package com.kvc.joy.core.persistence.jdbc.service;

import com.kvc.joy.core.persistence.jdbc.model.vo.MdRdbPrimaryKey;
import com.kvc.joy.core.persistence.jdbc.model.vo.RdbConnection;

import java.io.Serializable;

/**
 * 
 * @author Kevice
 * @time 2013-2-3 下午9:23:22
 */
public interface IMdRdbPrimaryKeyService extends Serializable {

	/**
	 * 
	 * 
	 * @param connection
	 * @param tableName
	 * @return
	 * @author Kevice
	 * @time 2013-2-3 下午9:24:38
	 */
	MdRdbPrimaryKey getPrimaryKey(RdbConnection connection, String tableName);
	
}
