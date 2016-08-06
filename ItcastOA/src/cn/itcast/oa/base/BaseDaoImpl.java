package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.cfg.Configuration;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.util.HqlHelper;

@Transactional(readOnly=true)
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	// abstract抽象类只能被继承，不能实例化
	@Resource
	private SessionFactory sessionFactory;

	protected Class<T> clazz;

	//通过在dao基类实现BaseDaoImpl中获取类型参数，方便子类继承调用。顺序：子类实例化-->父类默认构造函数-->子类默认构造函数
	public BaseDaoImpl() {
		//通过反射得到T的真实类型，由子类实例化生成
		//ParameterizedType与Class类似，区别在于表示带类型参数的Class（字节码？字面常量？表示类的元数据）
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class) pt.getActualTypeArguments()[0];
		// System.out.println("Class Name: " + clazz.getSimpleName());
	}
	
	/**保存对象 */
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void save(T entity) {
		getSession().save(entity);
	}

	/**更新对象 */
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void update(T entity) {
		getSession().update(entity);
	}
	
	/**根据传入id删除 */
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void delete(Long id) {
		Object obj = getSession().get(clazz, id);
		getSession().delete(obj);
	}

	/**根据传入id查询 */
	public T getById(Long id) {
		if (id == null) {
			return null;
		}
		return (T) getSession().get(clazz, id);
	}

	/**根据传入的ids数组查询 */
	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		}
		return getSession().createQuery(//
				"FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)")//使用变量名（ids）进行占位
				.setParameterList("ids", ids).list();
	}

	/**查询所有对象 */
	public List<T> findAll() {
		return getSession().createQuery(//
				"FROM " + clazz.getSimpleName())//
				.list();
	}

	// 公共的查询分页信息的方法
	/**  
	* @Name: getPageBean
	* @Description: 公共的查询分页信息的方法。
	* @Author: icelee
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-2-10（创建日期）
	* @Parameters: 
	* 	int pageNum:当前页码,初始进入列表页面时默认为1,点击分页跳转时为要跳转的页码;
	* 	String queryListHQL:查询列表数据的HQL语句;
	* 	Object[] parameters:查询语句所对应的参数数组;
	* @Return: PageBean：分页JavaBean对象
	*/
	public PageBean getPageBean(int pageNum, String queryListHQL, Object[] parameters) {
		int pageSize = Configuration.getPageSize();
		// >> 1.list--查询本页的数据列表
		Query listQuery = getSession().createQuery(queryListHQL);
		// >>>> 设置参数
		if (parameters != null && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				listQuery.setParameter(i, parameters[i]);
			}
		}
		// >>>> 设置当前页数据游标的起始位置
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list();// 执行查询，上述均属于查询语句拼接定义，需要以list方法来具体执行事务

		// >> 2.count--查询总记录数
		Query countQuery = getSession().createQuery("SELECT COUNT(*) " + queryListHQL);
		// >>>> 设置参数
		if (parameters != null && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				countQuery.setParameter(i, parameters[i]);
			}
		}
		Long count = (Long) countQuery.uniqueResult();// 执行查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	/**  
	* @Name: getPageBean(最终版)
	* @Description: 公共的查询分页信息的方法。
	* @Author: icelee
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-2-10（创建日期）
	* @Parameters: 
	* 	int pageNum:当前页码,初始进入列表页面时默认为1,点击分页跳转时为要跳转的页码;
	* 	HqlHelper hqlHelper:通过HqlHelper生成的查询列表数据的HQL语句,该对象包含查询参数
	* @Return: PageBean：分页JavaBean对象
	*/
	public PageBean getPageBean(int pageNum, HqlHelper hqlHelper) {
		
		//System.out.println("----------------->BaseDaoImpl.getPageBean(int pageNum, HqlHelper hqlHelper)");
		int pageSize = Configuration.getPageSize();
		
		List<Object> parameters = hqlHelper.getParameters();
		// >> 1.list--查询本页的数据列表
		Query listQuery = getSession().createQuery(hqlHelper.getQueryListHql());
		// >>>> 设置参数
		if (parameters != null && parameters.size() > 0) {
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		// >>>> 设置当前页数据游标的起始位置
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list();// 执行查询，上述均属于查询语句拼接定义，需要以list方法来具体执行事务

		// >> 2.count--查询总记录数
		Query countQuery = getSession().createQuery(hqlHelper.getQueryCountHql());
		// >>>> 设置参数
		if (parameters != null && parameters.size() > 0) {
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult();// 执行查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}
	
	
	/**
	 * 获取当前可用的session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
