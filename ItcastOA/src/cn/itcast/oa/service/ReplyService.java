package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.BaseDao;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;

public interface ReplyService extends BaseDao<Reply>{
	/**
	 * 查询指定主题中所有的回复列表，排序：最前面的是最早的回复
	 * 
	 * @param topic
	 * @return
	 */
	@Deprecated
	List<Reply> findByTopic(Topic topic);

	/**
	 * 按分页获取回复列表信息
	 * 
	 * @param pageNum
	 * @param topic
	 * @return new PageBean(pageNum, pageSize, list, count.intValue())
	 */
	@Deprecated
	PageBean getPageBean(int pageNum, Topic topic);

}
