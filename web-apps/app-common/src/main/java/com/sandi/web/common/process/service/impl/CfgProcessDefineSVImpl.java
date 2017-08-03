package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.id.IdGeneratorFactory;
import com.sandi.web.common.process.dao.ICfgProcessDefineDao;
import com.sandi.web.common.process.entity.*;
import com.sandi.web.common.process.service.interfaces.*;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.sec.SessionManager;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import org.apache.axis.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CfgProcessDefineSVImpl implements ICfgProcessDefineSV {
	@Autowired
	private ICfgProcessDefineDao dao;
	@Autowired
	private ICfgProcessParamSV processParamSV;
	@Autowired
	private ICfgTaskDefineSV taskDefineSV;
	@Autowired
	private ICfgTaskParamSV taskParamSV;
	@Autowired
	private ICfgTaskAssignSV taskAssignSV;
	@Autowired
	private ICfgProcessPointSV processPointSV;
	@Autowired
	private ICfgProcessPointRuleSV processPointRuleSV;


	@Override
	public CfgProcessDefineEntity getEntityById(long processId) throws Exception {
		return dao.findById(processId);
	}

	public List<CfgProcessDefineEntity> getEntityByProcessDefineId(long processDefineId) throws Exception{
		CfgProcessDefineEntity entity = new CfgProcessDefineEntity();
		entity.setProcessDefineId(processDefineId);
		return dao.findByEntity(entity);
	}

	@Override
	public List<CfgProcessDefineEntity> getEntityByName(String processName) throws Exception {
		CfgProcessDefineEntity entity = new CfgProcessDefineEntity();
		entity.setProcessName(processName);
		return dao.findLike(entity);
	}

	public List<CfgProcessDefineEntity> queryProcessInfo(Map params) throws Exception{
		return dao.queryProcessInfo(params);
	}

	public CfgProcessDefineEntity getValidEntityByProcessDefineId(long processDefineId) throws Exception{
		CfgProcessDefineEntity entity = new CfgProcessDefineEntity();
		entity.setProcessDefineId(processDefineId);
		entity.setState(CommConstants.Process.PROCESS_STATE_USE);
		List<CfgProcessDefineEntity> lists = dao.findByEntity(entity);
		if(lists!=null && lists.size()>0){
			return lists.get(0);
		}
		return null;
	}

	public List<CfgProcessDefineEntity> queryValidProcessInfo(Map params) throws Exception{
		return dao.queryValidProcessInfo(params);
	}

	/**
	 * 创建新流程
	 * @param data
	 */
	@Override
	public long createProcess(String data) throws Exception {
		Map dataMap = JsonUtil.json2Map(data);
		Map statesMap = (Map)dataMap.get("states");
		Map pathsMap = (Map)dataMap.get("paths");
		Map propsMap = (Map)dataMap.get("props");
		if(propsMap!=null && propsMap.containsKey("props")){
			propsMap = (Map)propsMap.get("props");
		}
		//解析流程信息
		CfgProcessDefineEntity processDefineEntity = dealProcess(propsMap,data);
		//设置流程编号、流程定义编号
		processDefineEntity.setProcessId(processDefineEntity.newId());
		processDefineEntity.setProcessDefineId(IdGeneratorFactory.newId("CFG_PROCESS_DEFINE_ID"));
		processDefineEntity.setState(CommConstants.Process.PROCESS_STATE_WAIT);

		//解析流程参数
		if(propsMap.containsKey("processParam") && propsMap.get("processParam")!=null){
			//解析processParam
			dealProcessParam(processDefineEntity, getPropsListValue(propsMap,"processParam"));
		}

		//处理流程节点
		List<CfgProcessPointEntity> pointEntityList = dealPoint(statesMap,processDefineEntity);
		//处理流程节点关系
		dealPointRule(pathsMap,pointEntityList,processDefineEntity);
		dao.save(processDefineEntity);

		return processDefineEntity.getProcessId();
	}

	/**
	 * 修改流程
	 *
	 * @param processId
	 * @param data
	 */
	@Override
	public long updateProcess(long processId, String data) throws Exception {
		CfgProcessDefineEntity oldProcessDefineEntity = dao.findById(processId);
		//解析报文
		Map dataMap = JsonUtil.json2Map(data);
		Map statesMap = (Map)dataMap.get("states");
		Map pathsMap = (Map)dataMap.get("paths");
		Map propsMap = (Map)dataMap.get("props");
		if(propsMap!=null && propsMap.containsKey("props")){
			propsMap = (Map)propsMap.get("props");
		}
		CfgProcessDefineEntity newProcessDefineEntity = dealProcess(propsMap,data);
		//处理老数据
		if(oldProcessDefineEntity.getState()==CommConstants.Process.PROCESS_STATE_WAIT){//如果是未启用的直接进行修改
			processParamSV.deleteByProcessId(processId);//删除流程参数数据
			processPointSV.deleteByProcessId(processId);//删除流程节点数据
			processPointRuleSV.deleteByProcessId(processId);//删除流程规则数据
			taskDefineSV.deleteByProcessId(processId);//删除流程任务数据
			taskAssignSV.deleteByProcessId(processId);//删除任务处理人数据
			taskParamSV.deleteByProcessId(processId);//删除任务参数数据
			newProcessDefineEntity.setProcessId(processId);
		}else{
			newProcessDefineEntity.setProcessId(newProcessDefineEntity.newId());
		}

		newProcessDefineEntity.setProcessDefineId(oldProcessDefineEntity.getProcessDefineId());
		newProcessDefineEntity.setState(CommConstants.Process.PROCESS_STATE_WAIT);
		//解析流程参数
		if(propsMap.containsKey("processParam") && propsMap.get("processParam")!=null){
			//解析processParam
			dealProcessParam(newProcessDefineEntity, getPropsListValue(propsMap,"processParam"));
		}
		//处理新数据
		List<CfgProcessPointEntity> pointEntityList = dealPoint(statesMap,newProcessDefineEntity);
		//处理流程节点关系
		dealPointRule(pathsMap,pointEntityList,newProcessDefineEntity);
		if(oldProcessDefineEntity.getState()==CommConstants.Process.PROCESS_STATE_WAIT){
			dao.updateById(newProcessDefineEntity);
		}else{
			dao.save(newProcessDefineEntity);
		}

		return newProcessDefineEntity.getProcessId();
	}

	/**
	 * 删除流程
	 *
	 * @param processId
	 */
	@Override
	public void deleteProcess(long processId) throws Exception {
		//将当前流程标记为已删除
		Date currDate = DateUtils.getCurrentDate();
		CfgProcessDefineEntity processDefineEntity = dao.findById(processId);
		processDefineEntity.setState(CommConstants.Process.PROCESS_STATE_DEL);
		processDefineEntity.setExpireDate(currDate);
		processDefineEntity.setDoneDate(currDate);
		UserInfoInterface userInfoInterface = SessionManager.getUser();
		if(userInfoInterface!=null){
			processDefineEntity.setOpId(userInfoInterface.getUserId());
		}

		dao.updateById(processDefineEntity);
	}

	/**
	 * 启用流程
	 *
	 * @param processId
	 */
	@Override
	public void useProcess(long processId) throws Exception {
		UserInfoInterface userInfo = SessionManager.getUser();
		Date currDate = DateUtils.getCurrentDate();

		CfgProcessDefineEntity processDefineEntity = dao.findById(processId);
		//查看是否有正在使用的流程
		CfgProcessDefineEntity entity = new CfgProcessDefineEntity();
		entity.setProcessDefineId(processDefineEntity.getProcessDefineId());
		entity.setState(CommConstants.Process.PROCESS_STATE_USE);
		List<CfgProcessDefineEntity> lists = dao.findByEntity(entity);
		if(lists!=null && lists.size()>0){
			//停用已经启用的流程
			CfgProcessDefineEntity oldEntity = lists.get(0);
			oldEntity.setState(CommConstants.Process.PROCESS_STATE_CANCEL);
			oldEntity.setDoneDate(currDate);
			if(userInfo!=null) {
				oldEntity.setOpId(userInfo.getUserId());
			}
			dao.updateById(oldEntity);
		}
		//启用当前流程
		processDefineEntity.setState(CommConstants.Process.PROCESS_STATE_USE);
		processDefineEntity.setDoneDate(currDate);
		if(userInfo!=null) {
			processDefineEntity.setOpId(userInfo.getUserId());
		}
		dao.updateById(processDefineEntity);
	}

	/**
	 * 解析生成流程数据
	 * */
	private CfgProcessDefineEntity dealProcess(Map propsMap,String data) throws Exception{
		Date currDate = DateUtils.getCurrentDate();
		UserInfoInterface userInfoInterface = SessionManager.getUser();
		CfgProcessDefineEntity processDefineEntity = new CfgProcessDefineEntity();
		if(propsMap.containsKey("processName") && propsMap.get("processName")!=null){//流程名称
			processDefineEntity.setProcessName(getPropsValue(propsMap,"processName"));
		}
		if(propsMap.containsKey("validDate") && propsMap.get("validDate")!=null){//生效时间
			processDefineEntity.setValidDate(DateUtils.parseDate(getPropsValue(propsMap,"validDate")));
		}
		if(propsMap.containsKey("expireDate") && propsMap.get("expireDate")!=null){//失效时间
			processDefineEntity.setExpireDate(DateUtils.parseDate(getPropsValue(propsMap,"expireDate")));
		}
		if(propsMap.containsKey("processDesc") && propsMap.get("processDesc")!=null){//流程描述
			processDefineEntity.setProcessDesc(getPropsValue(propsMap,"processDesc"));
		}
		if(propsMap.containsKey("processType") && propsMap.get("processType")!=null){//流程类型
			processDefineEntity.setProcessType(Integer.valueOf(getPropsValue(propsMap,"processType")));
		}else{
			processDefineEntity.setProcessType(CommConstants.Process.PROCESS_TYPE_OTHER);
		}
		if(processDefineEntity.getValidDate()==null){
			processDefineEntity.setValidDate(currDate);
		}
		if(processDefineEntity.getExpireDate()==null){
			processDefineEntity.setExpireDate(DateUtils.getMaxDate());
		}

		if(data.length()>80000){
			processDefineEntity.setProcessJson1(data.substring(0,2000));
			processDefineEntity.setProcessJson2(data.substring(2000,4000));
			processDefineEntity.setProcessJson3(data.substring(4000,6000));
			processDefineEntity.setProcessJson4(data.substring(6000,8000));
			processDefineEntity.setProcessJson5(data.substring(8000));
		}else if(data.length()>6000){
			processDefineEntity.setProcessJson1(data.substring(0,2000));
			processDefineEntity.setProcessJson2(data.substring(2000,4000));
			processDefineEntity.setProcessJson3(data.substring(4000,6000));
			processDefineEntity.setProcessJson4(data.substring(6000));
			processDefineEntity.setProcessJson5("");
		}else if(data.length()>4000) {
			processDefineEntity.setProcessJson1(data.substring(0, 2000));
			processDefineEntity.setProcessJson2(data.substring(2000, 4000));
			processDefineEntity.setProcessJson3(data.substring(4000));
			processDefineEntity.setProcessJson4("");
			processDefineEntity.setProcessJson5("");
		}else if(data.length()>2000){
			processDefineEntity.setProcessJson1(data.substring(0, 2000));
			processDefineEntity.setProcessJson2(data.substring(2000));
			processDefineEntity.setProcessJson3("");
			processDefineEntity.setProcessJson4("");
			processDefineEntity.setProcessJson5("");
		}else{
			processDefineEntity.setProcessJson1(data);
			processDefineEntity.setProcessJson2("");
			processDefineEntity.setProcessJson3("");
			processDefineEntity.setProcessJson4("");
			processDefineEntity.setProcessJson5("");
		}

		if(userInfoInterface!=null) {
			processDefineEntity.setCreator(userInfoInterface.getUserId());
			processDefineEntity.setOpId(userInfoInterface.getUserId());
		}
		processDefineEntity.setDoneDate(currDate);
		processDefineEntity.setCreateDate(currDate);

		return processDefineEntity;
	}

	/**
	 * 解析流程参数
	 * */
	private void dealProcessParam(CfgProcessDefineEntity processDefineEntity,List<Map> paramDatas) throws Exception{
		List<CfgProcessParamEntity> retLists = new ArrayList<CfgProcessParamEntity>();
		if(paramDatas!=null && paramDatas.size()>0){
			for(Map paramData : paramDatas){
				CfgProcessParamEntity paramEntity = new CfgProcessParamEntity();
				paramEntity.setParamId(paramEntity.newId());
				paramEntity.setProcessId(processDefineEntity.getProcessId());
				if(paramData.containsKey("paramCode")){
					paramEntity.setParamCode(String.valueOf(paramData.get("paramCode")));
				}
				if(paramData.containsKey("paramName")){
					paramEntity.setParamName(String.valueOf(paramData.get("paramName")));
				}
				if(paramData.containsKey("defaultValue") && paramData.get("defaultValue")!=null){
					if(StringUtils.isNotEmpty(paramData.get("defaultValue").toString())) {
						paramEntity.setDefaultValue(JsonUtil.beanToJsonString(paramData.get("defaultValue").toString()));
					}
				}
				if(paramData.containsKey("paramType")){
					paramEntity.setParamType(Integer.valueOf(String.valueOf(paramData.get("paramType"))));
				}
				paramEntity.setState(CommConstants.State.STATE_NORMAL);
				paramEntity.setCreateDate(processDefineEntity.getCreateDate());
				paramEntity.setCreator(processDefineEntity.getCreator());
				paramEntity.setDoneDate(processDefineEntity.getDoneDate());
				paramEntity.setOpId(processDefineEntity.getOpId());

				retLists.add(paramEntity);
			}
		}
		if(retLists!=null && retLists.size()>0){
			processParamSV.saveEntity(retLists);
		}
	}

	/**
	 * 解析流程节点数据
	 * */
	private List<CfgProcessPointEntity> dealPoint(Map statesMap,CfgProcessDefineEntity processDefineEntity) throws Exception{
		List<CfgProcessPointEntity> pointEntityList = new ArrayList<CfgProcessPointEntity>();
		if(statesMap!=null){
			Iterator<String> stateKeySet = statesMap.keySet().iterator();
			while(stateKeySet.hasNext()){
				String key = stateKeySet.next();
				Map rectMap = (Map)statesMap.get(key);
				String type = String.valueOf(rectMap.get("type"));//类型
				Map propsMap = (Map)rectMap.get("props");
				CfgProcessPointEntity pointRelEntity = new CfgProcessPointEntity();

				pointRelEntity.setPointId(pointRelEntity.newId());
				pointRelEntity.setProcessId(processDefineEntity.getProcessId());
				pointRelEntity.setPointName(getPropsValue(propsMap,"text"));
				pointRelEntity.setStatesName(key);
				pointRelEntity.setState(CommConstants.State.STATE_NORMAL);
				pointRelEntity.setCreateDate(processDefineEntity.getCreateDate());
				pointRelEntity.setCreator(processDefineEntity.getCreator());
				pointRelEntity.setOpId(processDefineEntity.getOpId());
				pointRelEntity.setDoneDate(processDefineEntity.getDoneDate());

				if(StringUtils.equalsIgnoreCase("start",type)){//开始节点
					pointRelEntity.setPointType(CommConstants.Process.POINT_TYPE_START);
					pointRelEntity.setPointObjId(-1L);
				}else if(StringUtils.equalsIgnoreCase("end",type)){//结束节点
					pointRelEntity.setPointType(CommConstants.Process.POINT_TYPE_END);
					pointRelEntity.setPointObjId(-1L);
				}else if(StringUtils.equalsIgnoreCase("task",type)){//普通任务
					pointRelEntity.setPointType(CommConstants.Process.POINT_TYPE_TASK);
					//设置任务编号
					pointRelEntity.setPointObjId(dealTask(propsMap,0,pointRelEntity));
				}else if(StringUtils.equalsIgnoreCase("childProcess",type)){//子流程
					pointRelEntity.setPointType(CommConstants.Process.POINT_TYPE_PROCESS);
					//设置流程编号
					String childProcessId = getPropsValue(propsMap,"childProcess");
					if(StringUtils.isNotEmpty(childProcessId)) {
						pointRelEntity.setPointObjId(Long.parseLong(childProcessId));
					}
				}
				pointEntityList.add(pointRelEntity);
			}
		}
		if(pointEntityList.size()>0){
			processPointSV.saveEntity(pointEntityList);
		}
		return pointEntityList;
	}

	/***
	 * 处理流程任务
	 * */
	private long dealTask(Map propsMap,int taskType,CfgProcessPointEntity pointRelEntity) throws Exception{
		CfgTaskDefineEntity taskDefineEntity = new CfgTaskDefineEntity();
		taskDefineEntity.setTaskId(taskDefineEntity.newId());
		taskDefineEntity.setProcessId(pointRelEntity.getProcessId());
		taskDefineEntity.setTaskName(getPropsValue(propsMap,"text"));
		taskDefineEntity.setTaskDesc(getPropsValue(propsMap,"taskDesc"));
		taskDefineEntity.setPlanTime(getPropsValue(propsMap,"planTime"));
		String autoDealFlag = getPropsValue(propsMap,"autoDealFlag");
		if(StringUtils.isNotEmpty(autoDealFlag)) {
			taskDefineEntity.setAutoDealFlag(Integer.valueOf(autoDealFlag));
		}
		taskDefineEntity.setTaskType(taskType);
		taskDefineEntity.setTaskDealClass(getPropsValue(propsMap,"taskDealClass"));
		taskDefineEntity.setTaskDealParam(getPropsValue(propsMap,"taskDealParam"));
		taskDefineEntity.setState(CommConstants.State.STATE_NORMAL);
		taskDefineEntity.setCreator(pointRelEntity.getCreator());
		taskDefineEntity.setCreateDate(pointRelEntity.getCreateDate());
		taskDefineEntity.setOpId(pointRelEntity.getOpId());
		taskDefineEntity.setDoneDate(pointRelEntity.getDoneDate());
		//处理任务参数
		if(propsMap.containsKey("taskParam") && propsMap.get("taskParam")!=null){
			dealTaskParam(getPropsListValue(propsMap,"taskParam"),taskDefineEntity);
		}
		//处理节点处理人
		if(propsMap.containsKey("taskDealPerson") && propsMap.get("taskDealPerson")!=null){
			dealTaskAssign(getPropsListValue(propsMap,"taskDealPerson"),taskDefineEntity);
		}

		//保存任务数据
		taskDefineSV.saveEntity(taskDefineEntity);

		return taskDefineEntity.getTaskId();
	}

	private void dealTaskParam(List<Map> taskParams,CfgTaskDefineEntity taskDefineEntity) throws Exception{
		List<CfgTaskParamEntity> retLists = new ArrayList<CfgTaskParamEntity>();
		if(taskParams!=null && taskParams.size()>0){
			for(Map paramData : taskParams){
				CfgTaskParamEntity paramEntity = new CfgTaskParamEntity();
				paramEntity.setTaskParamId(paramEntity.newId());
				paramEntity.setTaskId(taskDefineEntity.getTaskId());
				paramEntity.setProcessId(taskDefineEntity.getProcessId());
				if(paramData.containsKey("paramCode")){
					paramEntity.setParamCode(String.valueOf(paramData.get("paramCode")));
				}
				if(paramData.containsKey("paramName")){
					paramEntity.setParamName(String.valueOf(paramData.get("paramName")));
				}
				if(paramData.containsKey("defaultValue")){
					paramEntity.setDefaultValue(JsonUtil.beanToJsonString(paramData.get("defaultValue")));
				}
				if(paramData.containsKey("paramType")){
					paramEntity.setParamType(Integer.valueOf(String.valueOf(paramData.get("paramType"))));
				}
				paramEntity.setState(CommConstants.State.STATE_NORMAL);
				paramEntity.setCreateDate(taskDefineEntity.getCreateDate());
				paramEntity.setCreator(taskDefineEntity.getCreator());
				paramEntity.setDoneDate(taskDefineEntity.getDoneDate());
				paramEntity.setOpId(taskDefineEntity.getOpId());

				retLists.add(paramEntity);
			}
		}
		if(retLists!=null && retLists.size()>0){
			taskParamSV.saveEntity(retLists);
		}
	}

	//处理任务处理人信息
	private void dealTaskAssign(List<Map> dataParams,CfgTaskDefineEntity taskDefineEntity) throws Exception{
		List<CfgTaskAssignEntity> taskAssignEntityList = new ArrayList<CfgTaskAssignEntity>();
 		if(dataParams!=null && dataParams.size()>0){
			for(Map dataParam : dataParams){
				CfgTaskAssignEntity taskAssignEntity = new CfgTaskAssignEntity();
				taskAssignEntity.setTaskAssignId(taskAssignEntity.newId());
				taskAssignEntity.setTaskId(taskDefineEntity.getTaskId());
				taskAssignEntity.setProcessId(taskDefineEntity.getProcessId());
				taskAssignEntity.setTaskHandlerType(getPropsValue(dataParam,"handlerType"));
				taskAssignEntity.setTaskHandler(getPropsValue(dataParam,"handler"));
				String readOnly = getPropsValue(dataParam,"readOnly");
				if(StringUtils.isNotEmpty(readOnly)) {
					taskAssignEntity.setReadOnlyFlag(Integer.valueOf(readOnly));
				}else{
					taskAssignEntity.setReadOnlyFlag(0);//不是只读
				}
				taskAssignEntity.setState(CommConstants.State.STATE_NORMAL);
				taskAssignEntity.setCreator(taskDefineEntity.getCreator());
				taskAssignEntity.setCreateDate(taskDefineEntity.getCreateDate());
				taskAssignEntity.setOpId(taskDefineEntity.getOpId());
				taskAssignEntity.setDoneDate(taskDefineEntity.getDoneDate());

				taskAssignEntityList.add(taskAssignEntity);
			}
		}
		if(taskAssignEntityList.size()>0){
			taskAssignSV.saveEntity(taskAssignEntityList);
		}
	}


	/**
	 * 处理任务节点规则数据
	 * */
	private void dealPointRule(Map pathsMap,List<CfgProcessPointEntity> pointEntityList,CfgProcessDefineEntity processDefineEntity) throws Exception{
		List<CfgProcessPointRuleEntity> pointRuleEntityList = new ArrayList<CfgProcessPointRuleEntity>();
		if(pathsMap!=null && pathsMap.size()>0){
			Iterator<String> keyIterator = pathsMap.keySet().iterator();
			while(keyIterator.hasNext()){
				String key = keyIterator.next();
				Map paramMap = (Map)pathsMap.get(key);
				Map propsMap = (Map)paramMap.get("props");
				String from = getPropsValue(paramMap,"from");
				String to = getPropsValue(paramMap,"to");
				if(StringUtils.isNotEmpty(from) && StringUtils.isNotEmpty(to)){
					CfgProcessPointEntity fromPointEntity = null;
					CfgProcessPointEntity toPointEntity = null;
					for(CfgProcessPointEntity pointEntity : pointEntityList){
						if(StringUtils.equalsIgnoreCase(pointEntity.getStatesName(),from)){
							fromPointEntity = pointEntity;
						}else if(StringUtils.equalsIgnoreCase(pointEntity.getStatesName(),to)){
							toPointEntity = pointEntity;
						}
					}
					if(fromPointEntity!=null && toPointEntity!=null){
						CfgProcessPointRuleEntity pointRuleEntity = new CfgProcessPointRuleEntity();
						pointRuleEntity.setPointRuleId(pointRuleEntity.newId());
						pointRuleEntity.setPointId(fromPointEntity.getPointId());
						pointRuleEntity.setProcessId(processDefineEntity.getProcessId());
						pointRuleEntity.setNextPointId(toPointEntity.getPointId());
						String ruleId = getPropsValue(propsMap,"ruleId");
						if(StringUtils.isNotEmpty(ruleId)){
							pointRuleEntity.setRuleId(Long.parseLong(ruleId));//规则编号
							pointRuleEntity.setRuleParam(getPropsValue(propsMap,"ruleParam"));
							pointRuleEntity.setRuleValue(getPropsValue(propsMap,"ruleValue"));
						}
						pointRuleEntity.setState(CommConstants.State.STATE_NORMAL);
						pointRuleEntity.setCreateDate(processDefineEntity.getCreateDate());
						pointRuleEntity.setCreator(processDefineEntity.getCreator());
						pointRuleEntity.setDoneDate(processDefineEntity.getDoneDate());
						pointRuleEntity.setOpId(processDefineEntity.getOpId());

						pointRuleEntityList.add(pointRuleEntity);
					}
				}
			}
		}
		if(pointRuleEntityList.size()>0){
			processPointRuleSV.saveEntity(pointRuleEntityList);
		}
	}


	private String getPropsValue(Map propsMap,String key){
		if(propsMap!=null && propsMap.containsKey(key)){
			Object obj = propsMap.get(key);
			if(obj instanceof Map){
				return ((Map) obj).get("value").toString();
			}
			return obj.toString();
		}
		return null;
	}

	private List getPropsListValue(Map propsMap,String key){
		if(propsMap!=null && propsMap.containsKey(key)){
			Object obj = propsMap.get(key);
			if(obj instanceof Map){
				obj = ((Map) obj).get("value");
			}
			if(obj instanceof List){
				return (List)obj;
			}
		}
		return null;
	}
}