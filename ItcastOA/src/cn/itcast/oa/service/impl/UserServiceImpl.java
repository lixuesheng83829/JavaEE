package cn.itcast.oa.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.UserService;
import cn.itcast.oa.util.HqlHelper;

@Service
public class UserServiceImpl extends BaseDaoImpl<User> implements UserService {

	@Override
	public User getByLoginNameAndPassword(String loginName, String password) {
		return (User) getSession().createQuery(//
				"FROM User u WHERE u.loginName = ? AND password = ?")//
				.setParameter(0, loginName)//
				.setParameter(1, DigestUtils.md5Hex(password))//使用MD5摘要
				.uniqueResult();
	}

	/**
	 * 判断登录名是否出现重复,返回一个message标识属性
	 * 	message=1:表示登录名为空,不可以保存
	 * 	message=2:表示登录名在数据库中已存在,不可以保存
	 * 	message=3:表示登录名在数据库中不存在，可以保存
	 */
	public String checkUserByName(String loginName) {
		String message="";
		if(StringUtils.isNotBlank(loginName)){
			HqlHelper hqlHelper = new HqlHelper(User.class, "u")//
							.addCondition("u.loginName=?", loginName);//
			String hql = hqlHelper.getQueryListHql();
			User u = (User) getSession().createQuery(hql)//
				.setParameter(0, hqlHelper.getParameters().get(0))//			
				.uniqueResult();
			if(u!=null){
				message = "2";
			}else{
				message = "3";
			}
		}
		//为空
		else{
			message = "1";
		}
		return message;
//		(User) getSession().createQuery(//
//				"From User u WHERE u.loginName = ?")
//				.setParameter(0, loginName)//
//				.uniqueResult();
	}
	
	

}
