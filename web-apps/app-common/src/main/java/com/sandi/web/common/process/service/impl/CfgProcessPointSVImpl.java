package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.dao.ICfgProcessPointDao;
import com.sandi.web.common.process.entity.CfgProcessPointEntity;
import com.sandi.web.common.process.service.interfaces.ICfgProcessPointSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfgProcessPointSVImpl implements ICfgProcessPointSV {
	@Autowired
	private ICfgProcessPointDao dao;

	public List<CfgProcessPointEntity> findByEntity(CfgProcessPointEntity processPointEntity) throws Exception{
		return dao.findByEntity(processPointEntity);
	}

	public CfgProcessPointEntity getStartPoint(Long processId) throws Exception{
		CfgProcessPointEntity pointntity = new CfgProcessPointEntity();
		pointntity.setProcessId(processId);
		pointntity.setPointType(CommConstants.Process.POINT_TYPE_START);
		pointntity.setState(CommConstants.State.STATE_NORMAL);
		List<CfgProcessPointEntity> retLists = dao.findByEntity(pointntity);
		if(retLists!=null && retLists.size()>0){
			return retLists.get(0);
		}else{
			ExceptionUtil.throwBusinessException("无法找到"+processId+"开始节点");
			return null;
		}
	}

	@Override
	public CfgProcessPointEntity getPointRelEntityByPointId(Long pointId) throws Exception {
		return dao.findById(pointId);
	}

	/**
	 * 根据流程编号获取流程节点信息
	 *
	 * @param processId
	 */
	@Override
	public List<CfgProcessPointEntity> getPointRelEntityByProcessId(Long processId) throws Exception {
		CfgProcessPointEntity entity = new CfgProcessPointEntity();
		entity.setProcessId(processId);
		entity.setState(CommConstants.State.STATE_NORMAL);
		return dao.findByEntity(entity);
	}

	/**
	 * 保存数据
	 *
	 * @param processPointEntity
	 */
	@Override
	public void saveEntity(CfgProcessPointEntity processPointEntity) throws Exception {
		dao.save(processPointEntity);
	}

	/**
	 * 保存数据
	 *
	 * @param processPointEntityList
	 **/
	@Override
	public void saveEntity(List<CfgProcessPointEntity> processPointEntityList) throws Exception {
		dao.save(processPointEntityList);
	}


	/**
	 * 根据流程编号删除数据
	 *
	 * @param processId
	 */
	@Override
	public void deleteByProcessId(long processId) throws Exception {
		CfgProcessPointEntity pointRelEntity = new CfgProcessPointEntity();
		pointRelEntity.setProcessId(processId);
		dao.deleteByEntity(pointRelEntity);
	}
}