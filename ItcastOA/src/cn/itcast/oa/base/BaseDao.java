package cn.itcast.oa.base;

import java.util.List;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.util.HqlHelper;

public interface BaseDao<T> {
	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 查询实体
	 * 
	 * @param id
	 * @return
	 */
	T getById(Long id);

	/**
	 * 查询实体
	 * 
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * 公共的查询分页信息
	 * 
	 * @param pageNum
	 *            传递的当前页信息
	 * @param queryListHQL
	 *            查询数据列表的HQL语句，如果在前面加上"SELECT COUNT(*)"可以拼接查询总数量的HQL语句
	 * @param parameters
	 *            参数列表,顺序与HQL中的"?"顺序一一对应
	 * @return
	 */
	@Deprecated
	PageBean getPageBean(int pageNum, String queryListHQL, Object[] parameters);

	/**
	 * 最终版:公共的查询分页信息
	 * 
	 * @param pageNum
	 * 			  当前显示页码
	 * @param hqlHelper
	 *            查询条件HQL语句与参数列表
	 * @return
	 */
	PageBean getPageBean(int pageNum, HqlHelper hqlHelper);

}
