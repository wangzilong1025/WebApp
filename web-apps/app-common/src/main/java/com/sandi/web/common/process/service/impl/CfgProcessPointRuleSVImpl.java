package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.dao.ICfgProcessPointRuleDao;
import com.sandi.web.common.process.entity.CfgProcessPointRuleEntity;
import com.sandi.web.common.process.service.interfaces.ICfgProcessPointRuleSV;
import com.sandi.web.common.utils.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfgProcessPointRuleSVImpl implements ICfgProcessPointRuleSV {
	@Autowired
	private ICfgProcessPointRuleDao dao;

	@Override
	public List<CfgProcessPointRuleEntity> getPointRuleEntity(long pointId) throws Exception {
		CfgProcessPointRuleEntity entity = new CfgProcessPointRuleEntity();
		entity.setPointId(pointId);
		entity.setState(CommConstants.State.STATE_NORMAL);
		return dao.findByEntity(entity);
	}

	/**
	 * 保存数据
	 *
	 * @param pointRuleEntity
	 */
	@Override
	public void saveEntity(CfgProcessPointRuleEntity pointRuleEntity) throws Exception {
		dao.save(pointRuleEntity);
	}

	/**
	 * 保存数据
	 *
	 * @param pointRuleEntityList
	 */
	@Override
	public void saveEntity(List<CfgProcessPointRuleEntity> pointRuleEntityList) throws Exception {
		dao.save(pointRuleEntityList);
	}

	/**
	 * 根据流程编号删除数据
	 *
	 * @param processId
	 */
	@Override
	public void deleteByProcessId(long processId) throws Exception {
		CfgProcessPointRuleEntity entity = new CfgProcessPointRuleEntity();
		entity.setProcessId(processId);
		dao.deleteByEntity(entity);
	}
}