package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.dao.ICfgTaskInsDao;
import com.sandi.web.common.process.entity.*;
import com.sandi.web.common.process.service.interfaces.*;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.common.utils.ExceptionUtil;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.sec.SecManage;
import com.sandi.web.utils.sec.SessionManager;
import com.sandi.web.utils.sec.entity.Station;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CfgTaskInsSVImpl implements ICfgTaskInsSV {
	private static final Logger logger = Logger.getLogger(CfgTaskInsSVImpl.class);
	@Autowired
	private ICfgTaskInsDao dao;
	@Autowired
	private ICfgProcessDefineSV processDefineSV;
	@Autowired
	private ICfgProcessInsSV processInsSV;
	@Autowired
	private ICfgProcessPointSV processPointSV;
	@Autowired
	private ICfgProcessRuleSV processRuleSV;
	@Autowired
	private ICfgProcessPointRuleSV pointRuleSV;
	@Autowired
	private ICfgTaskDefineSV taskDefineSV;
	@Autowired
	private ICfgTaskParamInsSV taskParamInsSV;
	@Autowired
	private ICfgTaskAssignInsSV taskAssignInsSV;

	@Override
	public List<CfgTaskInsEntity> findByEntity(CfgTaskInsEntity taskInsEntity) throws Exception{
		return dao.findByEntity(taskInsEntity);
	}


	@Override
	public void completeTask(long taskInsId, Map taskParams) throws Exception {
		CfgTaskInsEntity taskInsEntity = dao.findById(taskInsId);
		if(taskInsEntity!=null){
			completeTask(taskInsEntity,taskParams);
		}
	}

	@Override
	public void completeTask(long processInsId, long taskId, Map taskParams) throws Exception {
		CfgTaskInsEntity entity = new CfgTaskInsEntity();
		entity.setProcessInsId(processInsId);
		entity.setTaskId(taskId);
		entity.setTaskState(CommConstants.Process.TASK_STATE_WAIT);
		List<CfgTaskInsEntity> taskInsEntityList = dao.findByEntity(entity);
		if(taskInsEntityList!=null){
			completeTask(taskInsEntityList.get(0),taskParams);
		}
	}

	private void completeTask(CfgTaskInsEntity taskInsEntity,Map params) throws Exception{
		taskInsEntity.setTaskState(CommConstants.Process.TASK_STATE_DONE);
		if(SessionManager.getUser()!=null){
			UserInfoInterface userInfoInterface = SessionManager.getUser();
			taskInsEntity.setDealOpId(userInfoInterface.getUserId());
			taskInsEntity.setDealOpName(userInfoInterface.getUserName());
		}
		taskParamInsSV.completeTask(taskInsEntity,params);

		dao.updateById(taskInsEntity);
	}

	/**
	* 创建下一任务
	 * @param processInsEntity 流程实例
	 * @param taskInsEntity 任务实例
	* **/
	public void createNextTask(CfgProcessInsEntity processInsEntity, CfgTaskInsEntity taskInsEntity) throws Exception{
		//判断是否有并行的节点,且没有完成
		CfgTaskInsEntity entity = new CfgTaskInsEntity();
		entity.setProcessInsId(processInsEntity.getProcessInsId());
		entity.setTaskState(CommConstants.Process.TASK_STATE_WAIT);
		List<CfgTaskInsEntity> retLists = dao.findByEntity(entity);
		if(retLists!=null && retLists.size()>0){
			return;
		}

		List<CfgProcessPointEntity> nextPointRelEntityList = getNextPoint(processInsEntity,taskInsEntity);
		List<CfgTaskInsEntity> taskInsEntityList = new ArrayList<CfgTaskInsEntity>();
		if(nextPointRelEntityList!=null && nextPointRelEntityList.size()>0){
			for(CfgProcessPointEntity pointRelEntity : nextPointRelEntityList) {
				if(pointRelEntity.getPointType()==CommConstants.Process.POINT_TYPE_TASK) {//普通人工任务
					CfgTaskInsEntity nextTaskInsEntity = createTask(processInsEntity, taskInsEntity, pointRelEntity);
					CfgTaskDefineEntity taskDefineEntity = taskDefineSV.getTaskDefineById(nextTaskInsEntity.getTaskId());
					String dealClass = taskDefineEntity.getTaskDealClass();
					String dealParam = taskDefineEntity.getTaskDealParam();
					boolean b = true;
					if(StringUtils.isNotEmpty(dealClass)){
						IAutoTaskSV autoTaskSV = (IAutoTaskSV) Class.forName(dealClass).newInstance();
						b = autoTaskSV.beforeDeal(nextTaskInsEntity,dealParam);
					}

					if(b) {
						//设置处理人
						taskAssignInsSV.createNextTask(nextTaskInsEntity,taskInsEntity,processInsEntity);
					}
					taskInsEntityList.add(nextTaskInsEntity);
				}else if(pointRelEntity.getPointType()==CommConstants.Process.POINT_TYPE_PROCESS){//启动子流程
					CfgTaskInsEntity nextTaskInsEntity = createProcessTask(processInsEntity,taskInsEntity,pointRelEntity);
					//将任务参数转化为Map
					List<CfgTaskParamInsEntity> taskParamInsEntityList = taskParamInsSV.getParamEntityByTaskInsId(taskInsEntity.getTaskInsId());
					Map params = new HashMap();
					if(taskParamInsEntityList!=null && taskParamInsEntityList.size()>0){
						for(CfgTaskParamInsEntity paramInsEntity:taskParamInsEntityList){
							params.put(paramInsEntity.getParamCode(),paramInsEntity.getParamValue());
						}
					}
					//启动子流程
					processInsSV.startChildProcess(pointRelEntity.getPointObjId(),nextTaskInsEntity.getTaskInsId(),processInsEntity.getProcessInsId(),params);
					taskInsEntityList.add(nextTaskInsEntity);
				}else if(pointRelEntity.getPointType()==CommConstants.Process.POINT_TYPE_END){//流程结束
					//是否为子流程结束
					if(processInsEntity.getParentProcessInsId()!=null && processInsEntity.getParentProcessInsId()>0){
						//将任务标记为已完成
						long parentTaskInsId = processInsEntity.getParentTaskInsId();
						CfgTaskInsEntity parentTaskInsEntity = dao.findById(parentTaskInsId);
						parentTaskInsEntity.setTaskState(CommConstants.Process.TASK_STATE_DONE);
						dao.updateById(parentTaskInsEntity);
					}
					//流程结束
					processInsSV.cancelProcess(processInsEntity.getProcessInsId());
				}
			}
		}
		if(taskInsEntityList.size()>0){
			dao.save(taskInsEntityList);
		}
	}

	/**
	 * 流程完成
	 *
	 * @param processInsId
	 */
	@Override
	public void cancelProcess(long processInsId) throws Exception {
		CfgTaskInsEntity entity = new CfgTaskInsEntity();
		entity.setProcessInsId(processInsId);
		List<CfgTaskInsEntity> taskInsEntityList = dao.findByEntity(entity);
		if(taskInsEntityList!=null && taskInsEntityList.size()>0){
			for(CfgTaskInsEntity taskInsEntity : taskInsEntityList){
				taskParamInsSV.cancelProcess(taskInsEntity.getTaskInsId());
				taskAssignInsSV.cancelProcess(taskInsEntity.getTaskInsId());
				dao.deleteById(taskInsEntity.getTaskInsId());
			}
		}
	}

	/**
	 * 处理已完成的任务
	 * */
	public void dealCompleteTask() throws Exception{
		CfgTaskInsEntity entity = new CfgTaskInsEntity();
		entity.setTaskState(CommConstants.Process.TASK_STATE_DONE);
		List<CfgTaskInsEntity> taskInsEntityList = dao.findByEntity(entity);
		if(taskInsEntityList!=null && taskInsEntityList.size()>0){
			for(CfgTaskInsEntity taskInsEntity : taskInsEntityList){
				try {
					CfgTaskDefineEntity taskDefineEntity = taskDefineSV.getTaskDefineById(taskInsEntity.getTaskId());
					String dealClass = taskDefineEntity.getTaskDealClass();
					String dealParam = taskDefineEntity.getTaskDealParam();
					if(StringUtils.isNotEmpty(dealClass)){
						IAutoTaskSV autoTaskSV = (IAutoTaskSV) Class.forName(dealClass).newInstance();
						autoTaskSV.afterDeal(taskInsEntity,dealParam);
					}
					CfgProcessInsEntity processInsEntity = processInsSV.getProcessById(taskInsEntity.getProcessInsId());
					createNextTask(processInsEntity, taskInsEntity);
					taskInsEntity.setTaskState(CommConstants.Process.TASK_STATE_CANCEL);
				}catch (Exception e){
					logger.error(e);
					taskInsEntity.setTaskState(CommConstants.Process.TASK_STATE_ERROR);
					taskInsEntity.setRemarks("流程处理失败："+e.getMessage());
				}
				dao.updateById(taskInsEntity);
			}
		}
	}

	/**
	 * 处理已过期的任务
	 * */
	public void dealExpireTask() throws Exception{
		Map map = new HashMap();
		map.put("taskState",CommConstants.Process.TASK_STATE_WAIT);
		List<CfgTaskInsEntity> taskInsEntityList = dao.getExpireTask(map);
		if(taskInsEntityList!=null && taskInsEntityList.size()>0){
			for(CfgTaskInsEntity taskInsEntity : taskInsEntityList){
				try {
					CfgProcessInsEntity processInsEntity = processInsSV.getProcessById(taskInsEntity.getProcessInsId());
					createNextTask(processInsEntity, taskInsEntity);
					taskInsEntity.setTaskState(CommConstants.Process.TASK_STATE_CANCEL);
					taskInsEntity.setOverTimeFlag(1);
				}catch (Exception e){
					logger.error(e);
					taskInsEntity.setTaskState(CommConstants.Process.TASK_STATE_ERROR);
					taskInsEntity.setRemarks(e.getMessage());
				}
				dao.updateById(taskInsEntity);
			}
		}
	}

	public List<Map> queryUserTask(Map params) throws Exception{
		//获取当前用户待处理的任务
		UserInfoInterface userInfoInterface = SessionManager.getUser();
		long operatorId = userInfoInterface.getUserId();
		List<Station> stationList = SecManage.getStationByOperId(operatorId);
		params.put("operatorId",operatorId);
		params.put("stations",stationList);
		return dao.queryUserTask(params);
	}

	private CfgTaskInsEntity createTask(CfgProcessInsEntity processInsEntity, CfgTaskInsEntity taskInsEntity, CfgProcessPointEntity pointRelEntity) throws Exception{
		CfgTaskDefineEntity taskDefineEntity = taskDefineSV.getTaskDefineById(pointRelEntity.getPointObjId());
		CfgTaskInsEntity nextTaskInsEntity = null;
		if(taskDefineEntity!=null) {
			Date currDate = DateUtils.getCurrentDate();
			nextTaskInsEntity = new CfgTaskInsEntity();
			nextTaskInsEntity.setTaskInsId(nextTaskInsEntity.newId());
			if(taskInsEntity!=null){
				nextTaskInsEntity.setProcessInsId(taskInsEntity.getProcessInsId());
				nextTaskInsEntity.setProcessId(taskInsEntity.getProcessId());
				nextTaskInsEntity.setPrevTaskInsId(taskInsEntity.getTaskInsId());
				nextTaskInsEntity.setCreator(taskInsEntity.getDealOpId());
			}else{
				nextTaskInsEntity.setProcessInsId(processInsEntity.getProcessInsId());
				nextTaskInsEntity.setProcessId(processInsEntity.getProcessId());
				nextTaskInsEntity.setPrevTaskInsId(-1L);
				nextTaskInsEntity.setCreator(processInsEntity.getCreator());
			}

			nextTaskInsEntity.setTaskId(pointRelEntity.getPointObjId());
			nextTaskInsEntity.setPointId(pointRelEntity.getPointId());
			nextTaskInsEntity.setTaskName(taskDefineEntity.getTaskName());
			nextTaskInsEntity.setTaskKey(taskDefineEntity.getTaskKey());
			nextTaskInsEntity.setTaskState(CommConstants.Process.TASK_STATE_WAIT);
			nextTaskInsEntity.setTaskType(taskDefineEntity.getTaskType());
			nextTaskInsEntity.setPlanStartTime(currDate);
			if(StringUtils.isNotEmpty(taskDefineEntity.getPlanTime())){
				nextTaskInsEntity.setPlanEndTime(DateUtils.getNextValidTimeAfter(currDate,taskDefineEntity.getPlanTime()));
			}
			nextTaskInsEntity.setState(CommConstants.State.STATE_NORMAL);
			nextTaskInsEntity.setCreateDate(currDate);
		}

		return nextTaskInsEntity;
	}

	/**
	 * 创建子流程任务
	 * */
	private CfgTaskInsEntity createProcessTask(CfgProcessInsEntity processInsEntity,CfgTaskInsEntity taskInsEntity,CfgProcessPointEntity pointRelEntity) throws Exception{
		CfgProcessDefineEntity processDefineEntity = processDefineSV.getValidEntityByProcessDefineId(pointRelEntity.getPointObjId());
		CfgTaskInsEntity nextTaskInsEntity = null;
		if(processDefineEntity!=null) {
			Date currDate = DateUtils.getCurrentDate();
			nextTaskInsEntity = new CfgTaskInsEntity();
			nextTaskInsEntity.setTaskInsId(nextTaskInsEntity.newId());
			if(taskInsEntity==null){
				nextTaskInsEntity.setProcessInsId(taskInsEntity.getProcessInsId());
				nextTaskInsEntity.setProcessId(taskInsEntity.getProcessId());
				nextTaskInsEntity.setPrevTaskInsId(taskInsEntity.getTaskInsId());
				nextTaskInsEntity.setCreator(taskInsEntity.getDealOpId());
			}else{
				nextTaskInsEntity.setProcessInsId(processInsEntity.getProcessInsId());
				nextTaskInsEntity.setProcessId(processInsEntity.getProcessId());
				nextTaskInsEntity.setPrevTaskInsId(-1L);
				nextTaskInsEntity.setCreator(processInsEntity.getCreator());
			}

			nextTaskInsEntity.setTaskId(pointRelEntity.getPointObjId());
			nextTaskInsEntity.setPointId(pointRelEntity.getPointId());
			nextTaskInsEntity.setTaskName(processDefineEntity.getProcessName());
			nextTaskInsEntity.setTaskKey(processDefineEntity.getProcessKey());
			nextTaskInsEntity.setTaskState(CommConstants.Process.TASK_STATE_WAIT_CHILD_PROCESS);
			nextTaskInsEntity.setTaskType(CommConstants.Process.POINT_TYPE_PROCESS);
			nextTaskInsEntity.setPlanStartTime(currDate);
			nextTaskInsEntity.setPlanEndTime(DateUtils.getMaxDate());
			nextTaskInsEntity.setState(CommConstants.State.STATE_NORMAL);
			nextTaskInsEntity.setCreateDate(currDate);
		}

		return nextTaskInsEntity;
	}

	/**
	 * 获取下一任务节点
	 * */
	private List<CfgProcessPointEntity> getNextPoint(CfgProcessInsEntity processInsEntity,CfgTaskInsEntity taskInsEntity) throws Exception{
		List<CfgProcessPointEntity> retLists = new ArrayList<CfgProcessPointEntity>();
		//根据当前流程编号获取任务节点编号
		long pointId = -1;
		if(taskInsEntity==null){
			CfgProcessPointEntity pointEntity = processPointSV.getStartPoint(processInsEntity.getProcessId());
			pointId = pointEntity.getPointId();
		}else{
			pointId = taskInsEntity.getPointId();
		}

		//根据规则编号获取规则处理类
		if(pointId>0) {
			//获取任务节点
			List<CfgProcessPointRuleEntity> pointRuleEntityList = pointRuleSV.getPointRuleEntity(pointId);
			if(pointRuleEntityList!=null && pointRuleEntityList.size()>0){
				for(CfgProcessPointRuleEntity pointRuleEntity : pointRuleEntityList){
					boolean addFlag = true;
					if(pointRuleEntity.getRuleId()!=null && pointRuleEntity.getRuleId()>0){
						CfgProcessRuleEntity processRuleEntity = processRuleSV.getRuleEntityByRuleId(pointRuleEntity.getRuleId());
						if(processRuleEntity!=null){
							String className = processRuleEntity.getDealClass();
							IProcessRuleSV processRuleSV = (IProcessRuleSV) Class.forName(className).newInstance();
							addFlag = processRuleSV.validate(taskInsEntity,pointRuleEntity.getRuleParam(),pointRuleEntity.getRuleValue());
						}
					}
					if(addFlag) {
						retLists.add(processPointSV.getPointRelEntityByPointId(pointRuleEntity.getNextPointId()));
					}
				}
			}else{
				ExceptionUtil.throwBusinessException("无法找到下一节点");
			}
		}
		return retLists;
	}
}