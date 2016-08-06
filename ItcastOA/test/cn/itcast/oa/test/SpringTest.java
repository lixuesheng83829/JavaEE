package cn.itcast.oa.test;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringTest {

	private ApplicationContext ac =new ClassPathXmlApplicationContext("applicationContext.xml");
	
	private Log log = LogFactory.getLog(getClass());
	
	@Test
	public void testLog() throws Exception{
		log.debug("debug");
		log.info("info");
		log.warn("warn");
		log.error("error");
		log.fatal("fatal");

		
	}
		
	
	@Test
	public void testSessionFactory() throws Exception{
		SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println(sessionFactory);
	}
	
	@Test
	public void testTransaction() throws Exception{
		TestService testService = (TestService) ac.getBean("testService");
		testService.saveTwoUsers();
	}
	@Test
	public void test2() throws Exception{
		TestService testService = (TestService) ac.getBean("testService");
		testService.saveUsers_23();
	}
	
	
}
