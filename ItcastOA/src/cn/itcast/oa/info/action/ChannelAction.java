package cn.itcast.oa.info.action;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Channel;
import cn.itcast.oa.util.ChannelUtils;

@Controller
@Scope("prototype")
public class ChannelAction extends BaseAction<Channel> {

	/**
	 * 栏目频道
	 */
	private static final long serialVersionUID = 1L;

	private Long parentId;//补充JSP页面交互的属性域,表示当前栏目的父栏目的id
	
	public String list() throws Exception {
		List<Channel> channelList = null;
		//通过Jsp页面是否传递parentId判断显示顶层还是指定层的栏目列表
		if(parentId==null){
			channelList = channelService.findTopList();
		}else{
			channelList = channelService.findChildren(parentId);
			//此处准备的parent数据用于JSP中"返回"上级功能按钮的传递参数
			Channel parent = channelService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		//准备好用于JSP页面显示的栏目列表数据
		ActionContext.getContext().put("channelList", channelList);
		return "list";
	}

	public String delete() throws Exception {
		channelService.delete(model.getId());
		return "toList";
	}

	public String addUI() throws Exception {
		//准备数据显示栏目的树状结构,根据util中的递归遍历树的方法,需首先提供顶层栏目
		//此处无需准备当前栏目回显数据,因为jsp新建按钮带该参数并在本action中的parentId属性自动封装并回显
		List<Channel> topList = channelService.findTopList();
		List<Channel> channelList = ChannelUtils.getAll(topList);
		ActionContext.getContext().put("channelList", channelList);
		return "saveUI";
	}

	public String add() throws Exception {
		model.setParent(channelService.getById(parentId));
		//新增栏目时,创建时间和修改时间为同值
		Date time = new Date();
		model.setPostDate(time);
		model.setCtime(time);
		channelService.save(model);
		return "toList";
	}
	public String editUI() throws Exception {
		//准备树状栏目列表
		List<Channel> topList = channelService.findTopList();
		List<Channel> channelList = ChannelUtils.getAll(topList);
		ActionContext.getContext().put("channelList", channelList);
		//准备回显数据
		Channel channel = channelService.getById(model.getId());
		//将回显数据置于栈顶
		ActionContext.getContext().getValueStack().push(channel);
		//与新建功能的回显不同,修改按钮仅带本栏目的id属性此处应手动准备parentId进行回显
		if(channel.getParent()!=null){
			parentId = channel.getParent().getId();
		}
		return "saveUI";
	}
	
	public String edit() throws Exception {
		//此处model.getId()获取的是页面<s:hidden>隐藏域传递的id
		Channel channel = channelService.getById(model.getId());
		//修改属性值
		channel.setName(model.getName());
		channel.setPostDate(new Date());//修改提交更新时间
//		TODO 修改栏目position
//		channel.setPosition(model.getPosition());
		channel.setDescription(model.getDescription());
		channel.setParent(channelService.getById(parentId));
		
		//保存到数据库中
		channelService.update(channel);
		return "toList";
	}

	
	//---------------------------------------
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

}
