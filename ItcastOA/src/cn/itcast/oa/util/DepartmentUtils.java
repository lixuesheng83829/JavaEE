package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.itcast.oa.domain.Department;

public class DepartmentUtils {
	/**
	 * 遍历部门树,得到所有的部门列表,并修改名称并表示层次
	 * @param topList
	 * @return
	 */
	public static List<Department> getAllDepartments(List<Department> topList) {
		List<Department> list = new ArrayList<Department>();
		walkDepartmentTrees(topList,"┣",list);
		return list;
	}
	/**
	 * 遍历部门树,把遍历出来的部门都放到指定的集合中,采用递归算法
	 * @param topList
	 * @param list
	 */
	private static void walkDepartmentTrees(Collection<Department> topList,String prefix, List<Department> list){
		for(Department top : topList){
			Department copy = new Department();
			copy.setId(top.getId());
			copy.setName(prefix + top.getName());
			list.add(copy);
			walkDepartmentTrees(top.getChildren(),"----" + prefix,list);
		}
	}
}
