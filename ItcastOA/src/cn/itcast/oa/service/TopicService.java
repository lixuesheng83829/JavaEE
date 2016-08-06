package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.BaseDao;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;

public interface TopicService extends BaseDao<Topic>{
	/**
	 * 
	 * 查询指定版块中的主题列表、排序；置顶贴-->拥有最新回复的主题
	 * @param forum
	 * @return
	 */
	@Deprecated
	List<Topic> findByForum(Forum forum);
	
	/**
	 * 查询主题列表的分页列表信息
	 * @param pageNum 当前页码
	 * @param forum 当前主题所属版块
	 * @return
	 */
	@Deprecated
	PageBean getPageBean(int pageNum, Forum forum);

}
