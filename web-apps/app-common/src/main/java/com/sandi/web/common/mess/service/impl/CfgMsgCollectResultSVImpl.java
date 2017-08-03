package com.sandi.web.common.mess.service.impl;

import com.sandi.web.common.mess.dao.ICfgMsgCollectResultDao;
import com.sandi.web.common.mess.entity.CfgMsgCollectResultEntity;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgCollectResultSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfgMsgCollectResultSVImpl implements ICfgMsgCollectResultSV {
	@Autowired
	private ICfgMsgCollectResultDao dao;

	@Override
	public List<CfgMsgCollectResultEntity> getCollectResultByBusiMsgId(Long busiMsgId) throws Exception {
		CfgMsgCollectResultEntity entity = new CfgMsgCollectResultEntity();
		entity.setBusiMsgId(busiMsgId);
		return dao.findByEntity(entity);
	}

	@Override
	public void saveEntity(CfgMsgCollectResultEntity entity) throws Exception {
		if(entity.getResultId()==null || entity.getResultId()<=0){
			entity.setResultId(entity.newId());
		}
		dao.save(entity);
	}

	@Override
	public void deleteEntity(Long resultId) throws Exception {
		dao.deleteById(resultId);
	}

	@Override
	public void deleteEntity(CfgMsgCollectResultEntity entity) throws Exception {
		deleteEntity(entity.getResultId());
	}

	@Override
	public void saveEntity(List<CfgMsgCollectResultEntity> entityList) throws Exception {
		dao.save(entityList);
	}

	@Override
	public void deleteEntity(List<CfgMsgCollectResultEntity> entityList) throws Exception {
		if(entityList!=null && entityList.size()>0){
			for(CfgMsgCollectResultEntity entity : entityList){
				deleteEntity(entity.getResultId());
			}
		}
	}
}