package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.BaseDao;
import cn.itcast.oa.domain.Department;

public interface DepartmentService extends BaseDao<Department>{

	/**
	 * 查询顶级部门列表
	 * @return
	 */
	List<Department> findTopList();

	List<Department> findChildren(Long parentId);

		
}
