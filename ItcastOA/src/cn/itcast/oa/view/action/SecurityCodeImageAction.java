package cn.itcast.oa.view.action;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.util.SecurityCode;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

//用于接收请求，并且回送验证码图片
@Controller
@Scope("prototype")
public class SecurityCodeImageAction extends ActionSupport implements
		SessionAware{
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;
	private ByteArrayInputStream imageStream;//Struts返回stream数据
	private String checkNumber;//页面通过ajax传递的验证码输入框的值
	private String timestamp;

	public String securityCode() throws Exception {
		imageStream = SecurityCode.getImageAsInputStream();
		String securityCode = SecurityCode.checkstring;
		session.put("checkstring", securityCode);
		return "securityCode";
	}
	
	public String checkNumber() throws Exception {
		String message="";
		//1.获取页面的验证码,通过属性接收
		/**
		 * message = "1":验证码输入有误,为空或者输入错误
		 * message = "2":session中没有已生成的验证码
		 * message = "3":验证码输入正确
		 */
		if(StringUtils.isBlank(checkNumber)){
			//this.addActionError("验证码输入有误！");
			message = "1";
		}else{
			//2.从Session中获取生成的验证码,session通过SessionAware注入
			String CHECK_NUMBER_KEY = (String) session.get("checkstring");
			if(StringUtils.isBlank(CHECK_NUMBER_KEY)){
				//this.addActionError("session中没有已生成的验证码！");
				message = "2";
			}else{
				if(!checkNumber.equalsIgnoreCase(CHECK_NUMBER_KEY)){
					message = "1";
				}else{
					message = "3";
				}
			}
		}
		ActionContext.getContext().getValueStack().push(message);
		return "checkNumber";
	}
	
	//====================================
	public ByteArrayInputStream getImageStream() {
		return imageStream;
	}

	public void setImageStream(ByteArrayInputStream imageStream) {
		this.imageStream = imageStream;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestamp() {
		return timestamp;
	}
}
