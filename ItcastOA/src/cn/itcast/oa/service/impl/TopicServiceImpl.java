package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.cfg.Configuration;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.TopicService;

@Service
@SuppressWarnings("unchecked")
public class TopicServiceImpl extends BaseDaoImpl<Topic> implements
		TopicService {

	@Override
	public List<Topic> findByForum(Forum forum) {
		return getSession().createQuery(//
						// TODO 排序:置顶贴-->拥有最新回复的主题,除了置顶帖（2）外，精化（1）普通（0）同等处理（均按0）
						//case语句为mysql标准语法，oracle不同
						"FROM Topic t WHERE t.forum =? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC , t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.list();
	}

	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(Topic topic) {
		// 1.设置属性并保存
		// topic.setType(Topic.TYPE_NORMAL);//默认为“普通”
		// topic.setReplyCount(0);//默认为0
		// topic.setLastReply(null);//默认为null
		topic.setLastUpdateTime(topic.getPostTime());
		getSession().save(topic);

		// 2.维护相关信息,外键相关
		Forum forum = topic.getForum();
		//主题数
		forum.setTopicCount(forum.getTopicCount()+1);
		//文章数（主题+回复）
		forum.setArticleCount(forum.getArticleCount()+1);
		//最后发表的主题
		forum.setLastTopic(topic);
		getSession().update(forum);
	}

	@Override
	public PageBean getPageBean(int pageNum, Forum forum) {
		
		int pageSize = Configuration.getPageSize();
		//查询本页的数据列表
		List list = getSession().createQuery(//
				"FROM Topic t WHERE t.forum =? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC , t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.setFirstResult((pageNum-1)*pageSize)//
				.setMaxResults(pageSize)//
				.list();
		//查询总记录数
		Long count =   (Long) getSession().createQuery(//
				"SELECT COUNT(*) FROM Topic t WHERE t.forum =?")//
				.setParameter(0, forum)//
				.uniqueResult();
		
		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

}
