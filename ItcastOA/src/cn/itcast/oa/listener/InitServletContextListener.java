package cn.itcast.oa.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.service.PrivilegeService;
//此处不用Spring的@Component注释生成bean,因为监听器需要在web.xml配置，会由Tomcat反射生成监听器实例
//所以此处不能使用@Resource注入（注入前提是当前类需要由spring管理，即@Component）PrivilegeService实例，须由Spring类加载器（ContextLoaderListener）获取
public class InitServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		
		//得到Service的实例对象,ApplicationContext是spring初始化的容器
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
		PrivilegeService privilegeService = (PrivilegeService) ac.getBean("privilegeServiceImpl");
		
		//准备所有顶级权限的集合
		List<Privilege> topPrivilegeList = privilegeService.findTopList();
		application.setAttribute("topPrivilegeList", topPrivilegeList);
		System.out.println("-----已准备好顶级权限的数据--------");
		
		//准备所有权限的URL集合
		List<String> allPrivilegeUrls = privilegeService.getAllPrivilegeUrls();
		application.setAttribute("allPrivilegeUrls", allPrivilegeUrls);
		System.out.println("-----已准备好所有权限的URL数据--------");
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
