package cn.itcast.oa.service;

import cn.itcast.oa.base.BaseDao;
import cn.itcast.oa.domain.Information;

public interface InformationService extends BaseDao<Information>{

	void deleteByIds(Long[] infoIds);

}
