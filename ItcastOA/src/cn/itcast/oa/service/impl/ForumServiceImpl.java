package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.service.ForumService;
@Service
@SuppressWarnings("unchecked")
public class ForumServiceImpl extends BaseDaoImpl<Forum> implements ForumService{

	
	@Override
	public List<Forum> findAll() {
		return getSession().createQuery(//
				"FROM Forum f ORDER BY f.position ASC")//
				.list();
	}
	
	
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(Forum forum) {
		//SELECT MAX(f.position) + 1 FROM Forum f
		//保存到db中，生成Id值
		getSession().save(forum);
		// 指定position为最大
		forum.setPosition(forum.getId().intValue());
		//因为是持久化状态，所以不需要调用update()方法
	}


	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void moveUp(Long id) {
		//获取要交换的两个Forum
		Forum forum = getById(id);//当前操作的Forum
		Forum other = (Forum) getSession().createQuery(//我上面的那个forum
				"FROM Forum f WHERE f.position <? ORDER BY f.position DESC")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//从0开始取,到1结束
				.setMaxResults(1)//
				.uniqueResult();
		
		//最上面的forum条目不能上移
		if(other == null){
			return;//直接返回什么都不做
		}
		
		//交换position的值
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		//更新到数据库中，因为与save同样理由，不需要显式写更新代码update
	}
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void moveDown(Long id) {
		//获取要交换的两个Forum
		Forum forum = getById(id);//当前操作的Forum
		Forum other = (Forum) getSession().createQuery(//我下面的那个forum
				"FROM Forum f WHERE f.position >? ORDER BY f.position ASC")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//
				.setMaxResults(1)//
				.uniqueResult();
		
		//最下面的forum条目不能下移
		if(other == null){
			return;
		}
		
		//交换position的值
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		//更新到数据库中，因为与save同样理由，不需要显示写更新代码update
	}

}
