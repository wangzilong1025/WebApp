package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.persistence.entity.Rank;
import com.sandi.web.common.process.dao.ICfgTaskAssignInsDao;
import com.sandi.web.common.process.entity.*;
import com.sandi.web.common.process.service.interfaces.*;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.ExceptionUtil;
import com.sandi.web.utils.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CfgTaskAssignInsSVImpl implements ICfgTaskAssignInsSV {
	@Autowired
	private ICfgTaskAssignInsDao dao;
	@Autowired
	private ICfgTaskAssignSV taskAssignSV;
	@Autowired
	private ICfgTaskParamInsSV taskParamInsSV;
	@Autowired
	private ICfgProcessParamInsSV processParamInsSV;
	@Autowired
	private ICfgTaskInsSV taskInsSV;
	@Autowired
	private ICfgProcessPointSV processPointSV;

	/**
	 * 创建下一任务
	 */
	@Override
	public void createNextTask(CfgTaskInsEntity taskInsEntity, CfgTaskInsEntity preTaskInsEntity, CfgProcessInsEntity processInsEntity) throws Exception {
		List<CfgTaskAssignInsEntity> taskAssignInsEntityList = new ArrayList<CfgTaskAssignInsEntity>();
		List<CfgTaskAssignEntity> taskAssignEntityList =  taskAssignSV.getTaskAssignByTaskId(taskInsEntity.getTaskId());
		if(taskAssignEntityList!=null && taskAssignEntityList.size()>0){
			for(CfgTaskAssignEntity taskAssignEntity : taskAssignEntityList){
				CfgTaskAssignInsEntity taskAssignInsEntity = new CfgTaskAssignInsEntity();
				taskAssignInsEntity.setAssignInsId(taskAssignInsEntity.newId());
				taskAssignInsEntity.setTaskInsId(taskInsEntity.getTaskInsId());
				taskAssignInsEntity.setTaskId(taskInsEntity.getTaskId());
				taskAssignInsEntity.setProcessInsId(taskInsEntity.getProcessInsId());
				taskAssignInsEntity.setPointId(taskInsEntity.getPointId());
				taskAssignInsEntity.setTaskHandlerType(taskAssignEntity.getTaskHandlerType());
				//判断处理类型，获取处理值
				taskAssignInsEntity.setTaskHandler(getHandlerValue(taskAssignEntity,preTaskInsEntity,processInsEntity));
				taskAssignInsEntity.setReadOnlyFlag(taskAssignEntity.getReadOnlyFlag());
				taskAssignInsEntity.setState(CommConstants.State.STATE_NORMAL);
				taskAssignInsEntity.setCreateDate(taskInsEntity.getCreateDate());
				taskAssignInsEntity.setCreator(taskInsEntity.getCreator());
				taskAssignInsEntity.setDoneDate(taskInsEntity.getDoneDate());
				taskAssignInsEntity.setOpId(taskInsEntity.getOpId());

				taskAssignInsEntityList.add(taskAssignInsEntity);
			}
		}else {
			ExceptionUtil.throwBusinessException("无法找到"+taskInsEntity.getTaskName()+"的任务处理人");
		}

		dao.save(taskAssignInsEntityList);
	}

	/**
	 * 完成流程
	 * */
	public void cancelProcess(long taskInsId) throws Exception{
		CfgTaskAssignInsEntity taskAssignInsEntity = new CfgTaskAssignInsEntity();
		taskAssignInsEntity.setTaskInsId(taskInsId);
		dao.deleteByEntity(taskAssignInsEntity);
	}

	private String getHandlerValue(CfgTaskAssignEntity taskAssignEntity,CfgTaskInsEntity preTaskInsEntity,CfgProcessInsEntity processInsEntity) throws Exception{
		String handlerValue = "";
		if(StringUtils.equalsIgnoreCase(CommConstants.Process.TASK_ASSIGN_USER,taskAssignEntity.getTaskHandlerType()) //指定操作员
				|| StringUtils.equalsIgnoreCase(CommConstants.Process.TASK_ASSIGN_STATION,taskAssignEntity.getTaskHandlerType())){//指定岗位
			String handler = taskAssignEntity.getTaskHandler();
			if(StringUtils.contains(handler,"{") && StringUtils.contains(handler,"}")) {
				String key = handler.substring(1, handler.length() - 2);
				String value = "";
				//查找上一任务节点参数
				List<CfgTaskParamInsEntity> taskParamInsEntityList = taskParamInsSV.getParamEntityByTaskInsId(preTaskInsEntity.getTaskInsId());
				if (taskParamInsEntityList != null && taskParamInsEntityList.size() > 0) {
					for (CfgTaskParamInsEntity taskParamInsEntity : taskParamInsEntityList) {
						if (StringUtils.equalsIgnoreCase(taskParamInsEntity.getParamCode(), key)) {
							handlerValue = taskParamInsEntity.getParamValue();
							break;
						}
					}
				}
				if (StringUtils.isEmpty(value)) {
					//查看流程参数
					List<CfgProcessParamInsEntity> processParamInsEntityList = processParamInsSV.getParamEntityByProcessInsId(processInsEntity.getProcessInsId());
					if (processParamInsEntityList != null && processParamInsEntityList.size() > 0) {
						for (CfgProcessParamInsEntity processParamInsEntity : processParamInsEntityList) {
							if (StringUtils.equalsIgnoreCase(processParamInsEntity.getParamCode(), key)) {
								handlerValue = processParamInsEntity.getParamValue();
								break;
							}
						}
					}
				}
			}else{
				handlerValue = handler;
			}
		}else if(StringUtils.equalsIgnoreCase(CommConstants.Process.TASK_ASSIGN_PROCESS_CREATOR,taskAssignEntity.getTaskHandlerType())){//流程创建人
			handlerValue = processInsEntity.getCreator()+"";
		}else if(StringUtils.equalsIgnoreCase(CommConstants.Process.TASK_ASSIGN_TASK_DEALER,taskAssignEntity.getTaskHandlerType())){//某一节点处理人
			CfgProcessPointEntity entity = new CfgProcessPointEntity();
			entity.setStatesName(taskAssignEntity.getTaskHandler());
			entity.setProcessId(processInsEntity.getProcessId());
			List<CfgProcessPointEntity> lists = processPointSV.findByEntity(entity);
			if(lists!=null && lists.size()>0){
				CfgProcessPointEntity pointEntity = lists.get(0);
				long objId = pointEntity.getPointObjId();
				if(pointEntity.getPointType()==CommConstants.Process.POINT_TYPE_TASK){
					CfgTaskInsEntity tempEntity = new CfgTaskInsEntity();
					tempEntity.setProcessId(processInsEntity.getProcessId());
					tempEntity.setProcessInsId(processInsEntity.getProcessInsId());
					tempEntity.setTaskId(objId);
					tempEntity.setRank(new Rank("taskInsId",CommConstants.OrderType.DESC));
					List<CfgTaskInsEntity> taskInsEntityList = taskInsSV.findByEntity(tempEntity);
					if(taskInsEntityList!=null && taskInsEntityList.size()>0){
						handlerValue = taskInsEntityList.get(0).getDealOpId()+"";
					}
				}else if(pointEntity.getPointType()==CommConstants.Process.POINT_TYPE_PROCESS){//待定 TODO

				}
			}
		}
		return handlerValue;
	}
}