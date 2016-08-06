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
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.ReplyService;

@Service
@SuppressWarnings("unchecked")
public class ReplyServiceImpl extends BaseDaoImpl<Reply> implements
		ReplyService {

	@Override
	public List<Reply> findByTopic(Topic topic) {
		return getSession().createQuery(//
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")//
				.setParameter(0, topic)//
				.list();
	}

	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(Reply reply) {
		//保存到数据库DB
		getSession().save(reply);
		//维护相关的信息
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();
		
		//版块的文章数（主题+回复）
		forum.setArticleCount(forum.getArticleCount() + 1);
		//主题的回复数
		topic.setReplyCount(topic.getReplyCount() + 1);
		//主题最后发表的回复
		topic.setLastReply(reply);
		//主题最后更新时间
		topic.setLastUpdateTime(reply.getPostTime());
		
		getSession().update(topic);
		getSession().update(forum);
	}

	@Override
	public PageBean getPageBean(int pageNum, Topic topic) {
		
		int pageSize = Configuration.getPageSize();
		//查询本页的数据列表
		List list = getSession().createQuery(//
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC ")//
				.setParameter(0, topic)//
				.setFirstResult((pageNum-1)*pageSize)//
				.setMaxResults(pageSize)//
				.list();
		//查询总记录数
		Long count =   (Long) getSession().createQuery(//
				"SELECT COUNT(*) FROM Reply r WHERE r.topic=?")//
				.setParameter(0, topic)//
				.uniqueResult();
		
		return new PageBean(pageNum, pageSize, list, count.intValue());
	}
	
	
}
