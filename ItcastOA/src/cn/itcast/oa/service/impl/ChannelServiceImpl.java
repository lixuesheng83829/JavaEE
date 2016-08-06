package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.domain.Channel;
import cn.itcast.oa.service.ChannelService;
@Service
@SuppressWarnings("unchecked")
public class ChannelServiceImpl extends BaseDaoImpl<Channel> implements ChannelService{

	public List<Channel> findTopList() {
		return getSession().createQuery(//
				"FROM Channel c WHERE c.parent IS NULL")//
				.list();
	}

	public List<Channel> findChildren(Long parentId) {
		return getSession().createQuery(//
				"FROM Channel c WHERE c.parent.id = ?")//
				.setParameter(0, parentId)
				.list();
	}
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(Channel channel) {
		//save操作hibernate将保存对象放在session缓存中,待session关闭时提交事务??
		super.save(channel);
		//必须先执行save操作,以便自动生成id进行调用,由于仍在一个session中
		channel.setPosition(channel.getId().intValue());
	}
	
	
}
