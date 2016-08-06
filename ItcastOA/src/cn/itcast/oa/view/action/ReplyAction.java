package cn.itcast.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;

@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long topicId;
	
	/** 发表新回复页面 */
	public String addUI() throws Exception {
		//准备数据:topic
		Topic topic = topicService.getById(topicId);
		ActionContext.getContext().put("topic", topic);
		return "addUI";
	}
	
	/** 发表新回复 */
	public String add() throws Exception {
		//1、封装(已经封装了title,content,faceIcon)
//		model.setTitle(title);
//		model.setFaceIcon(faceIcon);
//		model.setContent(content);
		//此处topicId由s:hidden传递
		model.setTopic(topicService.getById(topicId));
		
		model.setAuthor(getCurrentUser());
		model.setPostTime(new Date());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		
		//2、保存
		replyService.save(model);
		return "toTopicShow";//转到新回复所属主题的显示页面
	}

	
	//--------------------------------------------
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Long getTopicId() {
		return topicId;
	}
}
