package com.kvc.joy.core.sysres.param.service;

import com.kvc.joy.core.sysres.param.model.po.TSysParam;

import java.util.Map;

/**
 * 
 * @author Kevice
 * @time 2013-2-7 下午10:36:55
 */
public interface ISysParamService {

	/**
	 * 获取所有参数信息
	 * 
	 * @return
	 * @author Kevice
	 * @time 2013-2-7 下午10:43:48
	 */
	Map<String, TSysParam> get();

    /**
     * 获取所有property参数
     *
     * @return
     * @author Kevice
     * @time 2014-3-16 15:43:48
     */
    Map<String, TSysParam> getProperties();
	
	/**
	 * 根据参数名获取参数信息
	 * 
	 * @param paramName
	 * @return
	 * @author Kevice
	 * @time 2013-2-7 下午10:44:24
	 */
	TSysParam get(String paramName);
	
	/**
	 * 更新参数信息
	 * 
	 * @param sysParam
	 * @author Kevice
	 * @time 2013-2-8 上午10:39:16
	 */
	void put(TSysParam sysParam);

}
