package com.tamoino.core;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class ApplicationContextHelper {

	private static AbstractApplicationContext applicationContext = null;

	/**
	 * This method must never been called directly by production code. In
	 * production, the Facade application context singleton must be gotten
	 * always using Dependency Injection, and in this case the
	 * ApplicationContext would have been initiated from a different project.
	 * This method only exists for unit testing purposes in the Core project
	 * 
	 * @return
	 */
	static synchronized FacadeInterface getSingleton() {
		if (applicationContext == null) {
			applicationContext = new ClassPathXmlApplicationContext("beans-core-test.xml");
			applicationContext.registerShutdownHook();
		}
		return (FacadeInterface) applicationContext.getBean("facade");
	}

}
