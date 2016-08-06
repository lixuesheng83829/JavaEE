package cn.itcast.oa.info.action;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Channel;
import cn.itcast.oa.domain.Information;
import cn.itcast.oa.util.ChannelUtils;
import cn.itcast.oa.util.HqlHelper;
@Controller
@Scope("prototype")
public class InformationAction extends BaseAction<Information>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long[] channelIds;
	
	private Long[] infoIds;
	
	public String list() throws Exception {
		/*List<Information> informationList = informationService.findAll();
		ActionContext.getContext().put("informationList", informationList);*/
		//带分页查询,方法内已将pageBean放入栈中
		new HqlHelper(Information.class, "i")//	
			//.addCondition("i.isShow=?", new Byte("1"))//isShow:1显示，0隐藏
			.addOrder("i.postDate", false)//
			.buildPageBeanForStruts2(pageNum, informationService);

		return "list";
	}
	public String delete() throws Exception {
		informationService.delete(model.getId());
		return "toList";
	}
	
	public String delByIds() throws Exception {
		if (infoIds == null || infoIds.length == 0) {
			//由于跳转重定向,action值栈中的对象丢失前台页面无法获取错误信息
			//1.addFieldError("delInfoByIds", "您尚未选择所要删除的文章！");
			
			//session作用域过大,错误信息会在一个session中一直保留
			//2.ActionContext.getContext().getSession().put("delInfoByIds", "您尚未选择所要删除的文章！");
			//TODO 重定向跳转错误信息传递
			ServletActionContext.getRequest().setAttribute("delInfoByIds", "您尚未选择所要删除的文章！");
			
		}else{
			informationService.deleteByIds(infoIds);
		}
		return "toList";
	}
	
	public String addUI() throws Exception {
		//栏目频道列表,有待修改成ajax交互分级显示
		List<Channel> topList = channelService.findTopList();
		List<Channel> channelList = ChannelUtils.getAll(topList);
		ActionContext.getContext().put("channelList", channelList);
		return "saveUI";
	}
	public String add() throws Exception {
		Date time = new Date();
		model.setPostDate(time);
		model.setCtime(time);
		model.setPostIp(ServletActionContext.getRequest().getRemoteAddr());
		
		List<Channel> channelList = channelService.getByIds(channelIds);
		model.setChannels(new HashSet<Channel>(channelList));
		
		informationService.save(model);
		return "toList";
	}
	public String editUI() throws Exception {
		List<Channel> topList = channelService.findTopList();
		List<Channel> channelList = ChannelUtils.getAll(topList);
		ActionContext.getContext().put("channelList", channelList);
		
		Information info = informationService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(info);
		
		channelIds = new Long[info.getChannels().size()];
		if(info.getChannels().size()>0){
			int index = 0;
			for(Channel channel:info.getChannels()){
				channelIds[index++] = channel.getId();
			}
		}
		return "saveUI";
	}
	public String edit() throws Exception {
		Information info = informationService.getById(model.getId());
		info.setTitle(model.getTitle());
		info.setContent(model.getContent());
		info.setPic(model.getPic());
		info.setAuthor(model.getAuthor());
		info.setPostDate(new Date());
		info.setPostIp(ServletActionContext.getRequest().getRemoteAddr());
		info.setIsShow(model.getIsShow());
		info.setChannels(new HashSet<Channel>(channelService.getByIds(channelIds)));
		informationService.update(info);
		return "toList";
	}
	
	public String show() throws Exception {
		Information info = informationService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(info);
		return "showInfo";
	}
	
	
	//===========================================
	public void setChannelIds(Long[] channelIds) {
		this.channelIds = channelIds;
	}
	public Long[] getChannelIds() {
		return channelIds;
	}
	public void setInfoIds(Long[] infoIds) {
		this.infoIds = infoIds;
	}
	public Long[] getInfoIds() {
		return infoIds;
	}
}
