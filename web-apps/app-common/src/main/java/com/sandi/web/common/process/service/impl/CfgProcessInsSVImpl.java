package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.dao.ICfgProcessInsDao;
import com.sandi.web.common.process.entity.CfgProcessDefineEntity;
import com.sandi.web.common.process.entity.CfgProcessInsEntity;
import com.sandi.web.common.process.service.interfaces.ICfgProcessDefineSV;
import com.sandi.web.common.process.service.interfaces.ICfgProcessInsSV;
import com.sandi.web.common.process.service.interfaces.ICfgProcessParamInsSV;
import com.sandi.web.common.process.service.interfaces.ICfgTaskInsSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.common.utils.ExceptionUtil;
import com.sandi.web.utils.sec.SessionManager;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CfgProcessInsSVImpl implements ICfgProcessInsSV {
	private final Logger logger = Logger.getLogger(CfgProcessInsSVImpl.class);
	@Autowired
	private ICfgProcessInsDao dao;
	@Autowired
	private ICfgProcessDefineSV processDefineSV;
	@Autowired
	private ICfgProcessParamInsSV processParamInsSV;
	@Autowired
	private ICfgTaskInsSV taskInsSV;

	@Override
	public CfgProcessInsEntity startProcess(long processDefineId, Map params) throws Exception {
		logger.info("启动新流程..."+processDefineId);
		Map retMap = new HashMap();
		CfgProcessDefineEntity entity = processDefineSV.getValidEntityByProcessDefineId(processDefineId);

		if(entity!=null){
			CfgProcessInsEntity processInsEntity = new CfgProcessInsEntity();
			processInsEntity.setProcessInsId(processInsEntity.newId());
			processInsEntity.setProcessId(entity.getProcessId());
			processInsEntity.setProcessDefineId(entity.getProcessDefineId());
			processInsEntity.setProcessName(entity.getProcessName());
			processInsEntity.setProcessKey(entity.getProcessKey());
			processInsEntity.setProcessType(entity.getProcessType());
			processInsEntity.setProcessState(CommConstants.Process.PROCESS_INSTANCE_STATE_NORMAL);//流程标记为正常
			processInsEntity.setState(CommConstants.State.STATE_NORMAL);
			UserInfoInterface userInfo = SessionManager.getUser();
			if(userInfo!=null){
				processInsEntity.setCreator(userInfo.getUserId());
			}
			processInsEntity.setCreateDate(DateUtils.getCurrentDate());
			//处理流程参数
			processParamInsSV.startProcess(processInsEntity,params);

			taskInsSV.createNextTask(processInsEntity,null);
			dao.save(processInsEntity);//保存数据
			return processInsEntity;
		}else{
			ExceptionUtil.throwBusinessException("根据流程编号无法获取流程配置信息");
			return null;
		}
	}

	public CfgProcessInsEntity startChildProcess(long processDefineId,long parentProcessInsId,long parentTaskInsId,Map params) throws Exception {
		logger.info("启动新流程..."+processDefineId);
		CfgProcessDefineEntity entity = processDefineSV.getValidEntityByProcessDefineId(processDefineId);

		if(entity!=null){
			CfgProcessInsEntity processInsEntity = new CfgProcessInsEntity();
			processInsEntity.setProcessInsId(processInsEntity.newId());
			processInsEntity.setProcessId(entity.getProcessId());
			processInsEntity.setProcessDefineId(entity.getProcessDefineId());
			processInsEntity.setParentProcessInsId(parentProcessInsId);
			processInsEntity.setParentTaskInsId(parentTaskInsId);
			processInsEntity.setProcessName(entity.getProcessName());
			processInsEntity.setProcessType(entity.getProcessType());
			processInsEntity.setProcessKey(entity.getProcessKey());
			processInsEntity.setProcessState(CommConstants.Process.PROCESS_INSTANCE_STATE_WAIT);//流程标记为正常
			processInsEntity.setState(CommConstants.State.STATE_NORMAL);
			UserInfoInterface userInfo = SessionManager.getUser();
			if(userInfo!=null){
				processInsEntity.setCreator(userInfo.getUserId());
			}
			processInsEntity.setCreateDate(DateUtils.getCurrentDate());
			//处理流程参数
			processParamInsSV.startProcess(processInsEntity,params);

			taskInsSV.createNextTask(processInsEntity,null);
			dao.save(processInsEntity);//保存数据
			return processInsEntity;
		}else{
			ExceptionUtil.throwBusinessException("根据流程编号无法获取流程配置信息");
			return null;
		}
	}

	/**
	 * 修改流程数据
	 *
	 * @param cfgProcessInsEntity
	 */
	@Override
	public void updateProcess(CfgProcessInsEntity cfgProcessInsEntity) throws Exception {
		dao.updateById(cfgProcessInsEntity);
	}

	@Override
	public void stopProcess(long processInsId) throws Exception {
		CfgProcessInsEntity processInsEntity = dao.findById(processInsId);
		processInsEntity.setProcessState(CommConstants.Process.PROCESS_INSTANCE_STATE_STOP);
		if(SessionManager.getUser()!=null){
			processInsEntity.setOpId(SessionManager.getUser().getUserId());
		}
		processInsEntity.setDoneDate(DateUtils.getCurrentDate());
		dao.updateById(processInsEntity);
	}

	/**
	 * 完成流程
	 *
	 * @param processInsId
	 */
	@Override
	public void cancelProcess(long processInsId) throws Exception {
		CfgProcessInsEntity processInsEntity = dao.findById(processInsId);
		processInsEntity.setProcessState(CommConstants.Process.PROCESS_INSTANCE_STATE_DONE);
		if(SessionManager.getUser()!=null){
			processInsEntity.setOpId(SessionManager.getUser().getUserId());
		}
		processInsEntity.setDoneDate(DateUtils.getCurrentDate());
		dao.updateById(processInsEntity);

		//删除流程数据
		processParamInsSV.cancelProcess(processInsId);
		taskInsSV.cancelProcess(processInsId);
		dao.deleteById(processInsId);
	}

	@Override
	public List<CfgProcessInsEntity> getWaitProcess() throws Exception {
		CfgProcessInsEntity entity = new CfgProcessInsEntity();
		entity.setProcessState(CommConstants.Process.PROCESS_INSTANCE_STATE_WAIT);
		return dao.findByEntity(entity);
	}

	public CfgProcessInsEntity getProcessById(long processInsId) throws Exception{
		return dao.findById(processInsId);
	}
}