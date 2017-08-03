package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.dao.ICfgTaskAssignDao;
import com.sandi.web.common.process.entity.CfgTaskAssignEntity;
import com.sandi.web.common.process.service.interfaces.ICfgTaskAssignSV;
import com.sandi.web.common.utils.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfgTaskAssignSVImpl implements ICfgTaskAssignSV {
	@Autowired
	private ICfgTaskAssignDao dao;

	/**
	 * 保存数据
	 *
	 * @param taskAssignEntityList
	 */
	@Override
	public void saveEntity(List<CfgTaskAssignEntity> taskAssignEntityList) throws Exception {
		dao.save(taskAssignEntityList);
	}

	/**
	 * 根据流程编号删除数据
	 *
	 * @param processId
	 */
	@Override
	public void deleteByProcessId(long processId) throws Exception {
		CfgTaskAssignEntity entity = new CfgTaskAssignEntity();
		entity.setProcessId(processId);
		dao.deleteByEntity(entity);
	}

	/**
	 * 根据任务编号获取任务处理人
	 *
	 * @param taskId
	 */
	@Override
	public List<CfgTaskAssignEntity> getTaskAssignByTaskId(long taskId) throws Exception {
		CfgTaskAssignEntity entity = new CfgTaskAssignEntity();
		entity.setTaskId(taskId);
		entity.setState(CommConstants.State.STATE_NORMAL);
		return dao.findByEntity(entity);
	}
}