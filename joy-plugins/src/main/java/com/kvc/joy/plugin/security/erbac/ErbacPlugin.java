package com.kvc.joy.plugin.security.erbac;

import com.kvc.joy.core.init.service.IJoyPlugin;
import com.kvc.joy.core.init.support.properties.JoyProperties;
import com.kvc.joy.plugin.security.user.model.po.TUserBasic;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Kevice
 * @time 2013-2-14 上午10:35:38
 */
@Component
public class ErbacPlugin implements IJoyPlugin {

	public String getName() {
		return "扩展的基于角色访问控制";
	}

	public void startup() {
		// EhCacheManager shiroCacheManager =
		// PluginBeanFactory.getShiroCacheManager();
		// if (JoyProperties.ehchcheEnabled) {
		// EhCacheManagerFactoryBean ehCacheManagerFactory =
		// PluginBeanFactory.getEhCacheManagerFactory();
		// shiroCacheManager.setCacheManager(ehCacheManagerFactory.getObject());
		// }
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public boolean isEnabled() {
		return JoyProperties.PLUGIN_ERBAC_ENABLED;
	}

	public int getInitPriority() {
		return Integer.MAX_VALUE;
	}

	@Override
	public String getSqlMigrationPrefix() {
		return "ERBAC";
	}

	@Override
	public String getPoPackage() {
		return TUserBasic.class.getPackage().getName();
	}

	@Override
	public String getCtxConfLocation() {
		return "classpath*:/conf/plugin-appCtx-erbac.xml";
	}

}
