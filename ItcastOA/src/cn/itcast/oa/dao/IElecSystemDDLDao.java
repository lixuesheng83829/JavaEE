package cn.itcast.oa.dao;

import cn.itcast.oa.base.BaseDao;
import cn.itcast.oa.domain.ElecSystemDDL;
import cn.itcast.oa.domain.PageBean;

public interface IElecSystemDDLDao extends BaseDao<ElecSystemDDL>{
	public static final String SERVICE_NAME = "cn.itcast.oa.dao.impl.ElecSystemDDLDaoImpl";

	PageBean findAllByDistinct(int pageNum);
}
