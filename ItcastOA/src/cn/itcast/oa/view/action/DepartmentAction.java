package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.util.DepartmentUtils;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department> {

	private static final long serialVersionUID = 1L;
	private Long parentId;

	/**
	 * 列表功能
	 */
	public String list() throws Exception {
		List<Department> departmentList = null;
		if( parentId == null ){
			departmentList = departmentService.findTopList();
		}else{
			departmentList = departmentService.findChildren(parentId);
			Department parent = departmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
			//也可以放入当前model中
			//model.setParent(departmentService.getById(parentId));
		}
		ActionContext.getContext().put("departmentList", departmentList);
		return "list";
	}

	/**
	 * 删除功能
	 */
	public String delete() throws Exception {
		departmentService.delete(model.getId());
		return "toList";
	}

	/**
	 * 添加页面
	 */
	public String addUI() throws Exception {
		// 准备数据departmentList,显示树状结构
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		return "saveUI";
	}

	/**
	 * 添加功能
	 */
	public String add() throws Exception {
		// 1、新建对象并封装属性，也可以使用model（在BaseAction中已经通过反射实体类型newInstance新建model）
		model.setParent(departmentService.getById(parentId));

		departmentService.save(model);
		return "toList";
	}

	/**
	 * 修改页面
	 */
	public String editUI() throws Exception {
		// 准备数据departmentList,显示树状结构
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);

		// 准备回显的数据
		Department department = departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department);
		if (department.getParent() != null) {
			parentId = department.getParent().getId();
		}
		return "saveUI";
	}

	/**
	 * 修改页面
	 */
	public String edit() throws Exception {
		// 1.从数据库中获取原对象
		Department department = departmentService.getById(model.getId());

		// 2.设置要修改的属性
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		department.setParent(departmentService.getById(parentId));// 设置上级部门

		// 3.更新到数据库中
		departmentService.update(department);
		return "toList";
	}

	// -------------------------------------
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
