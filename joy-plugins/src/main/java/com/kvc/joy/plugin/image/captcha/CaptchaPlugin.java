package com.kvc.joy.plugin.image.captcha;

import com.kvc.joy.core.init.service.IJoyPlugin;
import com.kvc.joy.core.init.support.properties.JoyProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @since 1.0.0
 * @author 唐玮琳
 * @time 2013年9月21日 下午5:28:21
 */
@Component
public class CaptchaPlugin implements IJoyPlugin {

	@Override
	public String getName() {
		return "验证码生成";
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
		return JoyProperties.PLUGIN_CAPTCHA_ENABLED;
	}

	@Override
	public String getSqlMigrationPrefix() {
		return "CAPTCHA";
	}

	@Override
	public String getPoPackage() {
		return null;
	}

	@Override
	public String getCtxConfLocation() {
		return "classpath*:/conf/plugin-appCtx-captcha.xml";
	}

	
}
