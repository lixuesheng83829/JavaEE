package cn.itcast.oa.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.dao.IElecSystemDDLDao;
import cn.itcast.oa.domain.ElecSystemDDL;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.service.IElecSystemDDLService;
import cn.itcast.oa.util.HqlHelper;

@Service(IElecSystemDDLService.SERVICE_NAME)
@Transactional(readOnly=true)
public class ElecSystemDDLServiceImpl extends BaseDaoImpl<ElecSystemDDL> implements IElecSystemDDLService{
	/** 数据字典表Dao	 */
	@Resource(name=IElecSystemDDLDao.SERVICE_NAME)
	IElecSystemDDLDao elecSystemDDLDao;
	
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
		PageBean listPageBean = elecSystemDDLDao.findAllByDistinct(pageNum);
		return listPageBean;
	}

	/**  
	* @Name: findSystemDDLListByKeyword
	* @Description: 以数据类型为条件，查询数据字典
	* @Author: 李学盛（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-7-31（创建日期）
	* @Parameters: String ：数据类型
	* @Return: List<ElecSystemDDL>：存放数据字典的集合
	*/
	@Override
	public List<ElecSystemDDL> findSystemDDLListByKeyword(String keyword,int pageNum) {
		HqlHelper hqlHelper = new HqlHelper(clazz)//
				.addCondition("o.keyword=?", keyword)//
				.addOrder("o.ddlCode", true);//
		PageBean pageBean = elecSystemDDLDao.getPageBean(pageNum, hqlHelper);
		@SuppressWarnings("unchecked")
		List<ElecSystemDDL> list = pageBean.getRecordList();
		return list;
	}
	
	/**  
	* @Name: saveSystemDDL
	* @Description: 保存数据字典
	* @Author: 李学盛（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-8-1（创建日期）
	* @Parameters: ElecSystemDDL ：VO对象
	* @Return: 无
	*/
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveSystemDDL(ElecSystemDDL model) {
		//1.获取页面传递的参数
		//数据类型
		String keyword = model.getKeywordname();
		//业务标识
		String typeFlag = model.getTypeflag();
		//数据项的值（数组）
		String itemnames [] = model.getItemname();
		//如果typeFlag==new:新增一种新的数据类型
		
	}

}
