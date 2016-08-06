package cn.itcast.oa.view.action;

import java.util.Date;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.HqlHelper;
@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic>{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long forumId;
	//private Long topicId;此处不用topicId接收id，可用model自动接收封装
	//因为此处是topic实体的action，modeldriven默认接收封装的是topic属性，因此forumId需要另外定义
	
	/** 显示单个主题（主贴+回帖） */
	public String show() throws Exception {
		//准备数据:topic
		Topic topic = topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);
		
		//准备数据:replyList，分页列表
		//List<Reply> replyList =  replyService.findByTopic(topic);
		//ActionContext.getContext().put("replyList", replyList);
		
		//准备数据:replyList回复列表的分页信息
		// PageBean pageBean = replyService.getPageBean(pageNum, topic);
		// ActionContext.getContext().getValueStack().push(pageBean);

		// //准备数据:replyList回复列表的分页信息（使用公共的方法）
		// String hql = "FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC";
		// Object[] parameters = new Object[] { topic };
		//		
		// PageBean pageBean = replyService.getPageBean(pageNum, hql,
		// parameters);
		// ActionContext.getContext().getValueStack().push(pageBean);
		
		// 最终版：
		//一、构建查询条件
		new HqlHelper(Reply.class, "r")//		
				.addCondition("r.topic=?", topic)//
				.addOrder("r.postTime", true)//
				.buildPageBeanForStruts2(pageNum, replyService);
		
		return "show";
	}
	
	/** 发表新主题页面 */
	public String addUI() throws Exception {
		//准备数据:forum
		Forum forum = forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);
		return "addUI";
	}
	
	/** 发表新主题 */
	public String add() throws Exception {
		//1.封装。数据信息的封装大体可以分为三类，一是表单中的字段信息，二是跟随请求的附属信息，三是需要业务层加工产生的信息
		//>>表单字段。因为基类实现了modeldriven接口,model已实现Topic实体的部分属性名与表单字段名相同的属性值封装
		model.setForum(forumService.getById(forumId));
		
		//>>可以直接获取的附属信息
			//getCurrentUser()方法在基类工具类中定义，因为使用范围较广，设置protected范围。
		model.setAuthor(getCurrentUser());
			//此处之所以用ServletActionContext而不是ActionContext因为后者包含对象范围较小，get方法获取的多为map对象（如getSession）
			//ServletActionContext可以获取原始的http对象。
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		
		//>>应在业务层中通过业务方法加工得到的信息
//		model.setType(type);
//		model.setReplyCount(replyCount);
//		model.setLastReply(lastReply);
//		model.setLastUpdateTime(lastUpdateTime);
		
		//2.保存
		topicService.save(model);
		return "toShow";//转到新主题的显示页面
	}
	
	
	
	//--------------------------------------------
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	public Long getForumId() {
		return forumId;
	}
	
	
}
