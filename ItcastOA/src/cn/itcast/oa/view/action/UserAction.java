package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.util.DepartmentUtils;
import cn.itcast.oa.util.HqlHelper;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	/**
	 * VO属性,用于接收表单中非持久化的传值对象属性值
	 */
	private static final long serialVersionUID = 1L;
	private Long departmentId;
	private Long[] roleIds;
	private String checkNumber;//图片验证码

	/** 列表 */
	public String list() throws Exception {
//		List<User> userList = userService.findAll();
//		ActionContext.getContext().put("userList", userList);
		
		// 最终版：
		//一、构建查询条件
		new HqlHelper(User.class, "u")//	
				.addOrder("u.id", false)//
				.buildPageBeanForStruts2(pageNum, userService);
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		userService.delete(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		// 准备数据departmentList,显示树状结构
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		//准备数据:roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		//1、新建对象并设置属性（也可以使用model）
		Department department = departmentService.getById(departmentId);
		model.setDepartment(department);
		
//		Set<Role> roles = new HashSet<Role>();
//		roles.addAll(roleList);
		List<Role> roleList = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<Role>(roleList));
		
		String passwdMD5 = DigestUtils.md5Hex("1234");
		model.setPassword(passwdMD5);
		
		userService.save(model);
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 准备数据departmentList,显示树状结构
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		
		//准备回显数据roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		
		//准备回显数据，注意与上述准备数据的区别，上述为部门和岗位列表清单，下述为某个用户对象的具体属性值
		//包括该用户所对应的部门id，岗位ids
		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		if (user.getDepartment() != null){
			departmentId = user.getDepartment().getId();
		}
		if (user.getRoles().size() > 0){
			roleIds = new Long[user.getRoles().size()];
			int index = 0;
			for (Role role :user.getRoles() ){
				roleIds[index++]= role.getId();
			}
		}
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		//1.从数据库中取出原对象,由hidden属性传递
		User user = userService.getById(model.getId());
		
		//2.设置要修改的属性，由saveUI传递过来
		//普通属性
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		user.setGender(model.getGender());
		user.setPassword(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());
		
		//对外关联属性设置
		user.setDepartment(departmentService.getById(departmentId));
		//关联岗位时，由于user domain中设置的外键为set集合，BaseDaoImpl中设置的是list集合
		//需要对两者进行类型转换匹配
		List<Role> roleList = roleService.getByIds(roleIds);
		user.setRoles(new HashSet<Role>(roleList));
		//3.更新到数据库
		userService.update(user);
		return "toList";
	}

	/** 重置密码 */
	public String initPassword() throws Exception {
		//1、根据url的id参数从数据库中取出原对象
		User user = userService.getById(model.getId());
		//2、设置要修改的属性（要使用md5摘要）
		String passwdMD5 = DigestUtils.md5Hex("1234");
		user.setPassword(passwdMD5);
		//3、更新到数据库
		userService.update(user);
		return "toList";
	}
	
	/** 登录页面 */
	public String loginUI() throws Exception {
		return "loginUI";
	}
	/** 登录 */
	public String login() throws Exception {
		//服务器端验证
		User user = userService.getByLoginNameAndPassword(
		model.getLoginName(),model.getPassword());
		
		//获取表单提交的验证码
		//String checkNumber = ServletActionContext.getRequest().getParameter("checkNumber");
		//验证码是否正确
		String CHECK_NUMBER_KEY = (String) ActionContext.getContext().getSession().get("checkstring");
		if(checkNumber.equalsIgnoreCase(CHECK_NUMBER_KEY)){
			if(user == null){
				//用户名或者密码不正确
				addFieldError("login", "用户名或者密码错误!");//
				return "loginUI";
			}else{
				//正确就登录用户
				ActionContext.getContext().getSession().put("user", user);
				return "toIndex";
			}
		}else{
			addFieldError("login", "验证码输入有误!");//
			return "loginUI";
		}
	}
	/** 注销 */
	public String logout() throws Exception {
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}
	/**
	 * @Description:使用ajax完成登录名输入服务器端异步校验,判断数据库中是否有该用户名,保证注册的登录名唯一
	 */
	public String checkUser(){
		//1.获取登录名
		String loginName = model.getLoginName();
		//2.新增checkUserByName方法,根据用户名判断数据库中是否已存在该登录名
		String message = userService.checkUserByName(loginName);
		
		//3.将判断的返回结果message放在栈顶,以便struts的json插件自动封装返回前台ajax
		model.setMessage(message);//栈顶对象是User对象
		//ActionContext.getContext().getValueStack().push(message);//栈顶对象是String类型的属性
		//字符串loginName.length()判断效率高于"".equals(loginName)
		return "checkUser";
	}
	
	
	//--------------------------

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}
	
	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	
}
