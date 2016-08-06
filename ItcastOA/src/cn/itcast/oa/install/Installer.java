package cn.itcast.oa.install;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.User;

/**
 * 安装程序
 * @author icelee
 *
 */
@Component
public class Installer {
	@Resource
	private SessionFactory sessionFactory; 
	
	@Transactional
	public void install(){
		Session session = sessionFactory.getCurrentSession();
		//==============================
		//一、超级管理员
		/*User user = new User();
		user.setName("超级管理员");
		user.setLoginName("admin");
		user.setPassword(DigestUtils.md5Hex("admin"));
		session.save(user);//保存*/		
		
		//二、超级管理员
		Privilege menu, menu1, menu2, menu3, menu4, menu5;
		menu = (Privilege) session.get(Privilege.class, 1L);
		menu4 = new Privilege("字典管理", "elecSystemDDLAction_list", null, menu );
		session.save(menu4);
		session.save(new Privilege("字典列表", "elecSystemDDLAction_list", null, menu4));
		session.save(new Privilege("字典删除", "elecSystemDDLAction_delete", null, menu4));
		session.save(new Privilege("字典添加", "elecSystemDDLAction_add", null, menu4));
		session.save(new Privilege("字典修改", "elecSystemDDLAction_edit", null, menu4));
		/*menu = new Privilege("系统管理", null, "FUNC20082.gif", null );
		menu1 = new Privilege("岗位管理", "roleAction_list", null, menu );
		menu2 = new Privilege("部门管理", "departmentAction_list", null, menu );
		menu3 = new Privilege("用户管理", "userAction_list", null, menu );
		
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);

		session.save(new Privilege("岗位列表", "roleAction_list", null, menu1));
		session.save(new Privilege("岗位删除", "roleAction_delete", null, menu1));
		session.save(new Privilege("岗位添加", "roleAction_add", null, menu1));
		session.save(new Privilege("岗位修改", "roleAction_edit", null, menu1));
		
		session.save(new Privilege("部门列表", "departmentAction_list", null, menu2));
		session.save(new Privilege("部门删除", "departmentAction_delete", null, menu2));
		session.save(new Privilege("部门添加", "departmentAction_add", null, menu2));
		session.save(new Privilege("部门修改", "departmentAction_edit", null, menu2));
		
		session.save(new Privilege("用户列表", "userAction_list", null, menu3));
		session.save(new Privilege("用户删除", "userAction_delete", null, menu3));
		session.save(new Privilege("用户添加", "userAction_add", null, menu3));
		session.save(new Privilege("用户修改", "userAction_edit", null, menu3));
		session.save(new Privilege("用户初始化密码", "userAction_iniPassword", null, menu3));*/
		
		//=================网上交流========================
		
		/*menu = new Privilege("网上交流", null, "FUNC20064.gif", null );
		menu1 = new Privilege("论坛管理", "forumManageAction_list", null, menu );
		menu2 = new Privilege("论坛", "forumAction_list", null, menu );
		
		session.save(menu);
		session.save(menu1);
		session.save(menu2);*/
		//===============审批流转==========================
		
		/*menu = new Privilege("审批流转", null, "FUNC20057.gif", null );
		menu1 = new Privilege("审批流程管理", "processDefinitionAction_list", null, menu );
		menu2 = new Privilege("申请模板管理", "applicationTemplateAction_list", null, menu );
		menu3 = new Privilege("起草申请", "flowAction_applicationTemplateList", null, menu );
		menu4 = new Privilege("待我审批", "flowAction_myTaskList", null, menu );
		menu5 = new Privilege("我的申请查询", "forumManageAction_myApplicationList", null, menu );
		
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);
		session.save(menu4);
		session.save(menu5);*/
		
		//=================文章发布===============================
//		menu = new Privilege("内容发布", null, "FUNC20082.gif", null );
//		menu1 = new Privilege("栏目管理", "channelAction_list", null, menu );
//		menu2 = new Privilege("文章管理", "informationAction_list", null, menu );
//		menu3 = new Privilege("简介管理", "introAction_list", null, menu );
		
//		session.save(menu);
//		session.save(menu1);
//		session.save(menu2);
//		session.save(menu3);

//		session.save(new Privilege("栏目列表", "channelAction_list", null, menu1));
//		session.save(new Privilege("栏目删除", "channelAction_delete", null, menu1));
//		session.save(new Privilege("栏目添加", "channelAction_add", null, menu1));
//		session.save(new Privilege("栏目修改", "channelAction_edit", null, menu1));
//		
//		session.save(new Privilege("文章列表", "informationAction_list", null, menu2));
//		session.save(new Privilege("文章删除", "informationAction_delete", null, menu2));
//		session.save(new Privilege("文章添加", "informationAction_add", null, menu2));
//		session.save(new Privilege("文章修改", "informationAction_edit", null, menu2));
//		
//		session.save(new Privilege("简介列表", "introAction_list", null, menu3));
//		session.save(new Privilege("简介删除", "introAction_delete", null, menu3));
//		session.save(new Privilege("简介添加", "introAction_add", null, menu3));
//		session.save(new Privilege("简介修改", "introAction_edit", null, menu3));
		
		//=================附件管理===============================
		/*menu = new Privilege("附件管理", null, "FUNC20071.gif", null );
		menu1 = new Privilege("上传下载", "attachmentAction_list", null, menu );
		menu2 = new Privilege("目录管理", "directoryAction_list", null, menu );
		
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		
		session.save(new Privilege("附件列表", "attachmentAction_list", null, menu1));
		session.save(new Privilege("附件删除", "attachmentAction_delete", null, menu1));
		session.save(new Privilege("附件添加", "attachmentAction_add", null, menu1));
		session.save(new Privilege("附件修改", "attachmentAction_edit", null, menu1));
		
		session.save(new Privilege("目录列表", "directoryAction_list", null, menu2));
		session.save(new Privilege("目录删除", "directoryAction_delete", null, menu2));
		session.save(new Privilege("目录添加", "directoryAction_add", null, menu2));
		session.save(new Privilege("目录修改", "directoryAction_edit", null, menu2));*/
	}
	
	public static void main(String[] args) {
		System.out.println("正在执行初始化安装...");
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		Installer installer = (Installer)ac.getBean("installer");
		installer.install();
		System.out.println("==安装完毕==");
	}
}
