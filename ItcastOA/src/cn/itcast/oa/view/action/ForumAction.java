package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.HqlHelper;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum> {

	private static final long serialVersionUID = 1L;

	/**
	 * 0表示全部主题<br>
	 * 1表示只看精化帖
	 */
	private int viewType = 0;

	/**
	 * 0代表默认排序，所有置顶帖在前面，并按最后更新时间降序排序<br>
	 * 1代表只按最后更新时间排序<br>
	 * 2代表只按主题发表时间排序<br>
	 * 3代表按回复数量排序
	 */

	private int orderBy = 0;

	/**
	 * true表示升序<br>
	 * false表示降序
	 */
	private boolean asc = false;

	/** 版块列表 */
	public String list() throws Exception {
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}

	/** 显示单个版块（主题列表） */
	public String show() throws Exception {
		// 准备数据forum
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		// 准备数据topicList
		// List<Topic> topicList = topicService.findByForum(forum);
		// ActionContext.getContext().put("topicList", topicList);

		// 准备主题分页数据
		// PageBean pageBean = topicService.getPageBean(pageNum , forum);
		// ActionContext.getContext().getValueStack().push(pageBean);

		// 准备主题分页数据(使用公共方法)
		// String hql =
		// "FROM Topic t WHERE t.forum =? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC , t.lastUpdateTime DESC";
		// Object[] parameters = new Object[] { forum };
		// PageBean pageBean = topicService.getPageBean(pageNum, hql,
		// parameters);
		// ActionContext.getContext().getValueStack().push(pageBean);

		// ============================================================
		// // 准备主题分页数据(使用公共方法 + 过滤与排序)
		// List<Object> parameters = new ArrayList<Object>();
		// String hql = "FROM Topic t WHERE t.forum=? ";
		// parameters.add(forum);
		//
		// // >>过滤条件
		// if (viewType == 1) {// 1表示只看精化帖
		// hql += " AND t.type=? ";
		// parameters.add(Topic.TYPE_BEST);
		// }
		// // >>排序条件
		//
		// if (orderBy == 0) {// 0代表默认排序，所有置顶帖在前面，并按最后更新时间降序排序
		// hql +=
		// "ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC , t.lastUpdateTime DESC";
		// } else if (orderBy == 1) {// 1代表只按最后更新时间排序
		// hql += "ORDER BY t.lastUpdateTime" + (asc ? " ASC " : " DESC ");
		// } else if (orderBy == 2) {// 2代表只按主题发表时间排序
		// hql += "ORDER BY t.postTime" + (asc ? " ASC " : " DESC ");
		// } else if (orderBy == 3) {// 3代表按回复数量排序
		// hql += "ORDER BY t.replyCount" + (asc ? " ASC " : " DESC ");
		// }
		//
		// PageBean pageBean = topicService.getPageBean(pageNum, hql, parameters.toArray());
		// //
		// 之所以上方用list新购建parameters，因为getPageBean中parameters参数为集合类型，没有add方法供后期调整新增参数
		// // 集合固定？数组可变？
		// ActionContext.getContext().getValueStack().push(pageBean);
		// ======================================================================
		// 最终版：
		//一、构建查询条件
		new HqlHelper(Topic.class, "t")//		
				.addCondition("t.forum=?", forum)//
				.addCondition(viewType == 1, "t.type=?", Topic.TYPE_BEST)// 1表示只看精化帖
				.addOrder(orderBy == 0, "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)//
				.addOrder(orderBy == 0, "t.lastUpdateTime", false)//
				.addOrder(orderBy == 1, "t.lastUpdateTime", asc)//
				.addOrder(orderBy == 2, "t.postTime", asc)//
				.addOrder(orderBy == 3, "t.replyCount", asc)//
				.buildPageBeanForStruts2(pageNum, topicService);//pageNum:get/set方法在BaseAction中实现
		
		// ===============================================================
		return "show";
	}

	// -------------------------
	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

}
