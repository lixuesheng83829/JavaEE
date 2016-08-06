package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.domain.ElecSystemDDL;
import cn.itcast.oa.domain.PageBean;

public interface IElecSystemDDLService{
	public static final String SERVICE_NAME = "cn.itcast.oa.service.impl.ElecSystemDDLServiceImpl";

	PageBean findAllByDistinct(int pageNum);

	List<ElecSystemDDL> findSystemDDLListByKeyword(String keyword, int pageNum);

	void saveSystemDDL(ElecSystemDDL model);

}
