package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseDao;
import cn.itcast.oa.domain.PageBean;

/**
 * 用于辅助拼接生成HQL的工具类<br>
 * 同时提供return HQL和return parameters方法
 * @author icelee
 * 
 */
public class HqlHelper {

	private String fromClause; // From子句，必须
	private String whereClause = ""; // Where子句，可选
	private String orderByClause = ""; // OrderBy子句，可选

	private List<Object> parameters = new ArrayList<Object>();// 参数列表

	/**
	 * 生成FROM子句，设置默认别名为o
	 * 
	 * @param clazz
	 */
	public HqlHelper(Class clazz) {
		this.fromClause = "FROM " + clazz.getSimpleName() + " o";
	}

	/**
	 * 生成FROM子句，由调用者设置默认别名
	 * 
	 * @param clazz
	 * @param alias
	 *            设定的数据库表别名
	 */
	public HqlHelper(Class clazz, String alias) {
		this.fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
	}

	/**
	 * 拼接Where子句
	 * 
	 * @param condition
	 * @param params
	 */
	public HqlHelper addCondition(String condition, Object... params) {
		//拼接
		if(whereClause.length()==0){
			whereClause = " WHERE " + condition;
		}else{
			whereClause += " AND " + condition;
		}
		
		//保存参数，该参数是由getPageBean使用，在DAO层实现HQL语句执行
		if(params != null && params.length > 0){
			for(Object obj: params ){
				parameters.add(obj);
			}
		}
		return this;
	}

	/**
	 * 如果append为true，则拼接Where子句
	 * 
	 * @param append
	 *            是否要拼接当前语句
	 * @param condition
	 * @param params
	 */
	public HqlHelper addCondition(boolean append, String condition, Object... params) {
		if (append) {
			addCondition(condition, params);
		}
		return this;
	}

	/**
	 * 拼接OrderBy子句
	 * 
	 * @param propertyName
	 *            属性名,例如"o.id"
	 * @param isAsc
	 *            true表示升序，false表示降序
	 */
	public HqlHelper addOrder(String propertyName, boolean isAsc) {
		if(orderByClause.length()==0){
			orderByClause = " ORDER BY " + propertyName + (isAsc ? " ASC" : " DESC");
		}else{
			orderByClause += ", " + propertyName + (isAsc ? " ASC" : " DESC");
		}
		return this;
	}

	/**
	 * 如果append为true，拼接OrderBy子句
	 * @param append
	 *            是否要拼接当前语句
	 * @param propertyName
	 *            属性名
	 * @param isAsc
	 *            true表示升序，false表示降序
	 */
	public HqlHelper addOrder(boolean append, String propertyName, boolean isAsc) {
		if (append) {
			addOrder(propertyName, isAsc);
		}
		return this;
	}

	/**
	 * 获取生成的查询数据列表的HQL语句
	 * 
	 * @return
	 */
	public String getQueryListHql() {
		return fromClause + whereClause + orderByClause;
	}
	
	/**
	 * 获取生成的查询总记录数HQL语句<br>
	 * 没有orderBy子句，无须排序 
	 * @return
	 */
	public String getQueryCountHql() {
		return "SELECT COUNT(*) " + fromClause + whereClause;
	}
	
	/**  
	* 例句：Select distinct o.keyword From ElecSystemDDL o
	* @Name: getQueryDistinctHql
	* @Description: 查询数据字典列表类型，并去掉重复值
	* @Author: 李学盛（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2016-6-16（创建日期）
	* @Parameters: String keyword：需要排重的字段名,例如:o.keyword,o为查询表的别名
	* @Return: String：生成排重的查询语句
	*/
	public String getQueryDistinctHql(String keyword){
		return "SELECT DISTINCT "+ keyword + " "+ fromClause + whereClause;
	}

	// ======================================
	/**
	 * 获取参数列表,与HQL过滤条件中的"?"一一对应
	 * 
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	/**
	 * 查询并准备分页信息（放到栈顶）
	 * @param pageNum
	 * @param service
	 * @return
	 */
	public HqlHelper buildPageBeanForStruts2(int pageNum, BaseDao<?> service) {
//		System.out.println("============>HqlHelper.buildPageBeanForStruts2()");
		PageBean pageBean = service.getPageBean(pageNum, this);
		ActionContext.getContext().getValueStack().push(pageBean);
		return this;
	}
	
	

}
