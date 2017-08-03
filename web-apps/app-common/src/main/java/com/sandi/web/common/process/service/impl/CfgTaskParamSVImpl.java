package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.dao.ICfgTaskParamDao;
import com.sandi.web.common.process.entity.CfgTaskParamEntity;
import com.sandi.web.common.process.service.interfaces.ICfgTaskParamSV;
import com.sandi.web.common.utils.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfgTaskParamSVImpl implements ICfgTaskParamSV {
	@Autowired
	private ICfgTaskParamDao dao;

	@Override
	public List<CfgTaskParamEntity> getParamEntityByTaskId(long taskId) throws Exception {
		CfgTaskParamEntity paramEntity = new CfgTaskParamEntity();
		paramEntity.setState(CommConstants.State.STATE_NORMAL);
		paramEntity.setTaskId(taskId);
		return dao.findByEntity(paramEntity);
	}

	/**
	 * 保存任务参数数据
	 *
	 * @param taskParamEntityList
	 */
	@Override
	public void saveEntity(List<CfgTaskParamEntity> taskParamEntityList) throws Exception {
		dao.save(taskParamEntityList);
	}

	/**
	 * 根据流程编号删除数据
	 *
	 * @param processId
	 */
	@Override
	public void deleteByProcessId(long processId) throws Exception {
		CfgTaskParamEntity entity = new CfgTaskParamEntity();
		entity.setProcessId(processId);
		dao.deleteByEntity(entity);
	}
}