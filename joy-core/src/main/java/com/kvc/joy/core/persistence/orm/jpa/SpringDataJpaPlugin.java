package com.kvc.joy.core.persistence.orm.jpa;

import com.kvc.joy.core.init.service.IJoyPlugin;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Kevice
 * @time 2013-2-16 下午8:48:42
 */
@Component
public class SpringDataJpaPlugin implements IJoyPlugin {

	@Override
	public String getName() {
		return "spring-data-jpa";
	}

	@Override
	public int getInitPriority() {
		return 0;
	}

	@Override
	public void startup() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getSqlMigrationPrefix() {
		return "SPRING_DATA_JPA";
	}

	@Override
	public String getPoPackage() {
		return null;
	}
	
	@Override
	public String getCtxConfLocation() {
		return "classpath*:/conf/comp-appCtx-spring-data-jpa.xml";
	}

}
