package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.Role;
import com.opensymphony.xwork2.ActionContext;
//@Controller不能被继承, 所以不能写在父类BaseAction上面
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long[] privilegeIds;
	
	/** 列表 */
	public String list() throws Exception {
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		roleService.delete(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		/*Role role = new Role();
		role.setName(name);
		role.setDescription(description);
		roleService.save(role);
		*/
	
		roleService.save(model);
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		//准备用于回显的数据,封装放在对象栈栈顶,页面的struts通过标签属性,以ognl表达式的形式自动获取数据进行回显
		Role role = roleService.getById(model.getId());
		/*this.name = role.getName();
		this.description = role.getDescription();*/
		ActionContext.getContext().getValueStack().push(role);
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		//从数据库中取到原ID对应的对象，该对象的id与表单中hidden提交过来的id是同一个，但其它属性值不同
		Role role = roleService.getById(model.getId());
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		roleService.update(role);
		return "toList";
	}
	
	/**设置权限页面	 */
	public String setPrivilegeUI() throws Exception {
		//1、准备列表数据
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().put("role", role);
		
		List <Privilege> topPrivilegeList = privilegeService.findTopList(); 
		ActionContext.getContext().put("topPrivilegeList", topPrivilegeList);
		 		
		//2、准备回显数据,构建与当前id指定的role对象关联的权限数组ids
			//Long[]数组实例化时即确定了数组长度,因此在本类中定义属性时不能初始化，与集合类(Set/List)有区别
		privilegeIds = new Long[role.getPrivileges().size()];
		int index = 0;
		for(Privilege privilege : role.getPrivileges()){
			privilegeIds[index++] = privilege.getId();
		}
		
		return "setPrivilegeUI";
	}
	
	/**设置权限*/
	public String setPrivilege() throws Exception {
		//1、从数据库中取出原对象
		Role role = roleService.getById(model.getId());
		
		//2、设置要修改的属性
		List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privilegeList));
		
		//3、更新到数据库中
		roleService.update(role);
		return "toList";
	}
	// ---------------------------------------

	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	
}
