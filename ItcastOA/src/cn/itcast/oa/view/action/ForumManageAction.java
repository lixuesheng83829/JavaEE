package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
@Controller
@Scope("prototype")
public class ForumManageAction extends BaseAction<Forum>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**列表*/
	public String list() throws Exception {
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	/**删除*/
	public String delete() throws Exception {
		forumService.delete(model.getId());
		return "toList";
	}
	/**添加页面*/
	public String addUI() throws Exception {
		return "saveUI";
	}
	/**添加*/
	public String add() throws Exception {
		forumService.save(model);
		return "toList";
	}
	/**修改页面*/
	public String editUI() throws Exception {
		//准备回显数据
		Forum forum =forumService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(forum);
		return "saveUI";
	}
	/**修改*/
	public String edit() throws Exception {
		//从数据库中取出原数据
		Forum forum =forumService.getById(model.getId());
		//设置要修改的属性，注意不能直接保存forum对象，因为此处的修改属性只是针对部分，一些隐藏属性一般不作修改
		forum.setName(model.getName());
		forum.setDescription(model.getDescription());
		//更新
		forumService.update(forum);
		return "toList";
	}
	/**上移*/
	public String moveUp() throws Exception {
		forumService.moveUp(model.getId());
		return "toList";
	}
	/**下移*/
	public String moveDown() throws Exception {
		forumService.moveDown(model.getId());
		return "toList";
	}

}
