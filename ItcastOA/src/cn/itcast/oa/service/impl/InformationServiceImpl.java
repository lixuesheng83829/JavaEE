package cn.itcast.oa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.domain.Information;
import cn.itcast.oa.service.InformationService;
@Service
public class InformationServiceImpl extends BaseDaoImpl<Information> implements InformationService{
	
	/**
	 * 批量删除
	 */
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteByIds(Long[] infoIds) {
		/*String sqlString = getHql(infoIds,"informationId");
		sqlString = "delete from itcast_channel_information where "+sqlString;
		getSession().createSQLQuery(sqlString)//
				.executeUpdate();*/
		
		String sqlString = "delete from itcast_channel_information where informationId in (:ids)";
		getSession().createSQLQuery(sqlString)//
			.setParameterList("ids", infoIds)//
			.executeUpdate();
		
		
		String hql = "delete from Information where id in (:ids)";
		getSession().createQuery(hql)//
			.setParameterList("ids", infoIds)//
			.executeUpdate();
		
		/*String hql = getHql(infoIds,"id");
        getSession().createQuery(//
        		"delete from Information where "+hql)//
        		.executeUpdate();*/
	}
	/**
	 * in和or的效率,所在的列是否有索引或者是否是主键。如果有索引或者主键性能没啥差别
	 * 如果没有索引，随着in或者or后面的数据量越多，in的效率不会有太大的下降，
	 * 但是or会随着记录越多的话性能下降非常厉害，基本上是指数级增长。
	 */
	private String getHql(Long[] infoIds, String idName) {
		String hql = "";
		for(int i=0;i<infoIds.length;i++) {
            if(i==0) {
                hql = idName+"="+infoIds[i];
            } else {
                hql =hql + " or "+idName+"="+infoIds[i];
            }
        }
		return hql;
	}
	
}
