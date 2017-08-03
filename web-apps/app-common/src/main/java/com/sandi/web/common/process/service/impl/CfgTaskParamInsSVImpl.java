package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.dao.ICfgTaskParamInsDao;
import com.sandi.web.common.process.entity.CfgTaskInsEntity;
import com.sandi.web.common.process.entity.CfgTaskParamEntity;
import com.sandi.web.common.process.entity.CfgTaskParamInsEntity;
import com.sandi.web.common.process.service.interfaces.ICfgTaskParamInsSV;
import com.sandi.web.common.process.service.interfaces.ICfgTaskParamSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.utils.common.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CfgTaskParamInsSVImpl implements ICfgTaskParamInsSV {
	@Autowired
	private ICfgTaskParamInsDao dao;
	@Autowired
	private ICfgTaskParamSV taskParamSV;

	@Override
	public void completeTask(CfgTaskInsEntity taskInsEntity, Map params) throws Exception {
		List<CfgTaskParamEntity> taskParamEntityList = taskParamSV.getParamEntityByTaskId(taskInsEntity.getTaskId());
		List<CfgTaskParamInsEntity> taskParamInsEntityList = new ArrayList<CfgTaskParamInsEntity>();
		if(taskParamEntityList!=null && taskParamEntityList.size()>0) {
			for (CfgTaskParamEntity paramEntity : taskParamEntityList) {
				CfgTaskParamInsEntity entity = new CfgTaskParamInsEntity();
				entity.setParamInsId(entity.newId());
				entity.setTaskId(taskInsEntity.getTaskId());
				entity.setTaskInsId(taskInsEntity.getTaskInsId());
				entity.setParamId(paramEntity.getTaskParamId());
				entity.setParamCode(paramEntity.getParamCode());
				entity.setParamName(paramEntity.getParamName());
				entity.setParamType(paramEntity.getParamType());
				//从map中获取参数值
				if (params.containsKey(paramEntity.getParamCode())) {
					Object obj = params.get(paramEntity.getParamCode());
					if (obj != null) {
						if (paramEntity.getParamType() == CommConstants.Process.PARAM_TYPE_STRING) {
							entity.setParamValue(String.valueOf(obj));
						} else if (paramEntity.getParamType() == CommConstants.Process.PARAM_TYPE_DATE) {
							if (obj instanceof Date) {
								Date date = (Date) obj;
								entity.setParamValue(DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss"));
							} else {
								entity.setParamValue(String.valueOf(obj));
							}
						} else if (paramEntity.getParamType() == CommConstants.Process.PARAM_TYPE_NUMBER) {
							entity.setParamValue(String.valueOf(obj));
						} else {
							entity.setParamValue(JsonUtil.beanToJsonString(obj));
						}
					}
				}

				entity.setState(CommConstants.State.STATE_NORMAL);
				entity.setCreator(taskInsEntity.getCreator());
				entity.setCreateDate(taskInsEntity.getCreateDate());
				taskParamInsEntityList.add(entity);
			}
			if (taskParamInsEntityList.size() > 0) {
				dao.save(taskParamInsEntityList);
			}
		}
	}

	/**
	 * 根据任务编号查询任务参数
	 *
	 * @param taskInsId
	 */
	@Override
	public List<CfgTaskParamInsEntity> getParamEntityByTaskInsId(Long taskInsId) throws Exception {
		CfgTaskParamInsEntity entity = new CfgTaskParamInsEntity();
		entity.setTaskInsId(taskInsId);
		return dao.findByEntity(entity);
	}

	/**
	 * 流程完成
	 *
	 * @param taskInsId
	 */
	@Override
	public void cancelProcess(long taskInsId) throws Exception {
		CfgTaskParamInsEntity entity = new CfgTaskParamInsEntity();
		entity.setTaskInsId(taskInsId);
		dao.deleteByEntity(entity);
	}
}