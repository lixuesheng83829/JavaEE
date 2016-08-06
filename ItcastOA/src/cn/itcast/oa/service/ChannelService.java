package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.BaseDao;
import cn.itcast.oa.domain.Channel;

public interface ChannelService extends BaseDao<Channel>{

	List<Channel> findTopList();

	List<Channel> findChildren(Long parentId);

}
