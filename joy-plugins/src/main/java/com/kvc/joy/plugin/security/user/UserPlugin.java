package com.kvc.joy.plugin.security.user;

import com.kvc.joy.core.init.service.IJoyPlugin;
import com.kvc.joy.core.init.support.properties.JoyProperties;
import com.kvc.joy.plugin.security.user.model.po.TUserLoginLog;
import org.springframework.stereotype.Component;

/**
 * 
 * @since 1.0.0
 * @author Kevice
 * @time 2013年9月29日 上午1:28:49
 */
@Component
public class UserPlugin implements IJoyPlugin {

	@Override
	public String getName() {
		return "用户管理";
	}

	@Override
	public int getInitPriority() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void startup() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public boolean isEnabled() {
		return JoyProperties.PLUGIN_USER_ENABLED;
	}

	@Override
	public String getSqlMigrationPrefix() {
		return "USER";
	}

	@Override
	public String getPoPackage() {
		return TUserLoginLog.class.getPackage().getName();
	}
	
	@Override
	public String getCtxConfLocation() {
		return "classpath*:/conf/plugin-appCtx-user.xml";
	}

}
