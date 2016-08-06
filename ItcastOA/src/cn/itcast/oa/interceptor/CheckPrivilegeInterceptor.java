package cn.itcast.oa.interceptor;

import cn.itcast.oa.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//获取当前用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		
		//获取当前访问的URL，并去掉当前应用程序的前缀(namespace+"/"+actionName)
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String privilegeUrl = null;
		
		if(namespace.endsWith("/")){
			privilegeUrl = namespace + actionName;
		}else{
			privilegeUrl = namespace +"/"+ actionName;
		}
		
		//要去掉开头的"/"
		if(privilegeUrl.startsWith("/")){
			privilegeUrl = privilegeUrl.substring(1);
		}
		
		//如果未登录页面
		if(user == null){
			if(privilegeUrl.startsWith("userAction_login")||privilegeUrl.startsWith("securityCodeImageAction")){//login,loginUI
				//如果是登录页面,或者是验证码图片,则放行
				return invocation.invoke();
			}else{
				//如果不是做登录操作，就转到登录页面
				return "loginUI";
			}
		}
		//如果已登录，则判断权限
		else{
			//如果有权限就放行
			if(user.hasPrivilegeByUrl(privilegeUrl)){
				return invocation.invoke();
			}
			//如果没权限就转到提示页面
			else{
				return "noPrivilegeError";
			}
		}
//		System.out.println("--------------->之前");
//		String result = invocation.invoke();
//		System.out.println("--------------->之后");
//		return result;
	}

}
