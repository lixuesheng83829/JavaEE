package cn.itcast.oa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.cfg.Configuration;
import cn.itcast.oa.dao.IElecSystemDDLDao;
import cn.itcast.oa.domain.ElecSystemDDL;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.util.HqlHelper;

@Repository(IElecSystemDDLDao.SERVICE_NAME)
public class ElecSystemDDLDaoImpl extends BaseDaoImpl<ElecSystemDDL> implements IElecSystemDDLDao{

	/**  
	* @Name: findAllByDistinct
	* @Description: 查询数据字典列表类型，并去掉重复值
	* @Author: 李学盛（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-6-9（创建日期）
	* @Parameters: 无
	* @Return: List<ElecSystemDDL>：存放数据字典的集合
	*/
	@Override
	public PageBean findAllByDistinct(int pageNum) {
		//1.拼接HQL语句 "Select distinct o.keyword From ElecSystemDDL o";
		String alias = "e";
		HqlHelper hqlHelper = new HqlHelper(clazz, alias);//"From ElecSystemDDL e";
		
		//因为采用distinct查询返回数据字典的类型去重之后的值，因此使用List<Object>进行接收数据集
		List<ElecSystemDDL> systemList = new ArrayList<ElecSystemDDL>();
		
		//TODO:完成语句拼接
		String keyword = alias+".keyword";//e.keyword
		PageBean pageBean = this.getDistinctPageBean(pageNum, hqlHelper, keyword);
		List<Object> list = pageBean.getRecordList();
		//组织页面返回的结果值
		if(list!=null&&list.size()>0){
			for(Object o : list){
				ElecSystemDDL elecSystemDDL = new ElecSystemDDL();
				//仅设置数据字典类型
				elecSystemDDL.setKeyword(o.toString());
				systemList.add(elecSystemDDL);
			}
		}
		pageBean.setRecordList(systemList);
		return pageBean;
	}
	//=======================================================
	/**  
	* @param string 
	 * @Name: getDistinctPageBean
	* @Description: 采用排重distinct查询数据字典列表,生成分页数据
	* @Author: 李学盛（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-6-16（创建日期）
	* @Parameters: int pageNum：分页的当前页码
	* 			   HqlHelper hqlHelper:拼接的hql语句,必须以From 开头,因为内部会对select count(*)进行拼接以查询总记录数
	* 			   String keyword:需要排重的字段名,例如:o.keyword,o为查询表的别名
	* @Return: PageBean：生成分页数据
	*/
	public PageBean getDistinctPageBean(int pageNum, HqlHelper hqlHelper, String keyword) {
		
		//System.out.println("----------------->BaseDaoImpl.getPageBean(int pageNum, HqlHelper hqlHelper)");
		//1.获取全局参数：每页显示条数
		int pageSize = Configuration.getPageSize();
		//2.获取传入hqlHelper语句封装的条件参数
		List<Object> parameters = hqlHelper.getParameters();
		//3.查询当前页的数据列表
		// >> 1.list--查询排重(Distinct)之后的所有数据
		Query listQuery = getSession().createQuery(hqlHelper.getQueryDistinctHql(keyword));
		// >>>> 设置参数
		if (parameters != null && parameters.size() > 0) {
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		// >> 2.截取当前页数据集
		// >>>> 设置当前页数据游标的起始位置
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list();// 执行查询，上述均属于查询语句拼接定义，需要以list方法来具体执行事务

		// >> 3.count--查询总记录数
		Query countQuery = getSession().createQuery("select count(Distinct o.keyword) From ElecSystemDDL o");
		// >>>> 设置参数
		if (parameters != null && parameters.size() > 0) {
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult();// 执行查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	
}
