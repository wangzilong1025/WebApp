package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.dao.ICfgTaskDefineDao;
import com.sandi.web.common.process.entity.CfgTaskDefineEntity;
import com.sandi.web.common.process.service.interfaces.ICfgTaskDefineSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfgTaskDefineSVImpl implements ICfgTaskDefineSV {
	@Autowired
	private ICfgTaskDefineDao dao;

	@Override
	public List<CfgTaskDefineEntity> findByEntity(CfgTaskDefineEntity taskDefineEntity) throws Exception{
		return dao.findByEntity(taskDefineEntity);
	}

	@Override
	public CfgTaskDefineEntity getTaskDefineById(long taskDefineId) throws Exception {
		return dao.findById(taskDefineId);
	}


	/**
	 * 保存数据
	 *
	 * @param taskDefineEntity
	 */
	@Override
	public void saveEntity(CfgTaskDefineEntity taskDefineEntity) throws Exception {
		dao.save(taskDefineEntity);
	}

	/**
	 * 根据流程编号删除数据
	 *
	 * @param processId
	 */
	@Override
	public void deleteByProcessId(long processId) throws Exception {
		CfgTaskDefineEntity entity = new CfgTaskDefineEntity();
		entity.setProcessId(processId);
		dao.deleteByEntity(entity);
	}
}