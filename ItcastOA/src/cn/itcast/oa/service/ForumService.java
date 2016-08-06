package cn.itcast.oa.service;

import cn.itcast.oa.base.BaseDao;
import cn.itcast.oa.domain.Forum;

public interface ForumService extends BaseDao<Forum>{
	/**
	 * 上移，最上方的不能上移
	 * @param id
	 */
	void moveUp(Long id);
	/**
	 * 下移，最下方的不能下移
	 * @param id
	 */
	void moveDown(Long id);
	
}
