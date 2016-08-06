package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.AttachmentService;
import cn.itcast.oa.service.ChannelService;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.service.ForumService;
import cn.itcast.oa.service.IElecSystemDDLService;
import cn.itcast.oa.service.InformationService;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.service.ReplyService;
import cn.itcast.oa.service.RoleService;
import cn.itcast.oa.service.TopicService;
import cn.itcast.oa.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public abstract class BaseAction<T> extends ActionSupport implements
		ModelDriven<T> {

	private static final long serialVersionUID = 1L;
	@Resource
	protected RoleService roleService;
	@Resource
	protected DepartmentService departmentService;
	@Resource
	protected UserService userService;
	@Resource
	protected PrivilegeService privilegeService;
	@Resource
	protected ForumService forumService;
	@Resource
	protected TopicService topicService;
	@Resource
	protected ReplyService replyService;
	@Resource
	protected ChannelService channelService;
	@Resource
	protected InformationService informationService;
	@Resource
	protected AttachmentService attachmentService;
	/**注入数据字典表service*/
	@Resource(name=IElecSystemDDLService.SERVICE_NAME)
	protected IElecSystemDDLService elecSystemDDLService;
	
	protected T model;

	@SuppressWarnings("unchecked")
	public BaseAction() {
		try {
			//得到model的类型信息
			ParameterizedType pt = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			Class clazz = (Class) pt.getActualTypeArguments()[0];
			//通过反射生成model的实例
			model = (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

// 干吗用?getmodel的使用者是modeldriveninterceptor检测到action实现了modeldriven接口，
	//通过getmodel获得action的model实体，并压入valuestack栈顶，用于接收jsp页面数据
	@Override
	public T getModel() {
		return model;
	}
	
	/**
	 * 获取当前登录用户
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		return (User) ActionContext.getContext().getSession().get("user");
	}
	
	//分页用，页码默认为第1页,若未传递页码参数，则不调用set方法赋值
	protected int pageNum = 1;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	} 
}
