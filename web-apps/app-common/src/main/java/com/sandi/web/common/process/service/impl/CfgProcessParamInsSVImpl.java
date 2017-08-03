package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.dao.ICfgProcessParamInsDao;
import com.sandi.web.common.process.entity.CfgProcessInsEntity;
import com.sandi.web.common.process.entity.CfgProcessParamEntity;
import com.sandi.web.common.process.entity.CfgProcessParamInsEntity;
import com.sandi.web.common.process.service.interfaces.ICfgProcessParamInsSV;
import com.sandi.web.common.process.service.interfaces.ICfgProcessParamSV;
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
public class CfgProcessParamInsSVImpl implements ICfgProcessParamInsSV {
	@Autowired
	private ICfgProcessParamInsDao dao;
	@Autowired
	private ICfgProcessParamSV cfgProcessParamSV;

	@Override
	public void startProcess(CfgProcessInsEntity processInsEntity, Map params) throws Exception {
		List<CfgProcessParamEntity> processParamEntityList = cfgProcessParamSV.getParamEntityByProcessId(processInsEntity.getProcessId());
		List<CfgProcessParamInsEntity> processParamInsEntityList = new ArrayList<CfgProcessParamInsEntity>();
		if(processParamEntityList!=null && processParamEntityList.size()>0){
			for(CfgProcessParamEntity paramEntity : processParamEntityList){
				CfgProcessParamInsEntity entity = new CfgProcessParamInsEntity();
				entity.setParamInsId(entity.newId());
				entity.setProcessId(processInsEntity.getProcessId());
				entity.setProcessInsId(processInsEntity.getProcessInsId());
				entity.setParamId(paramEntity.getParamId());
				entity.setParamCode(paramEntity.getParamCode());
				entity.setParamName(paramEntity.getParamName());
				entity.setParamType(paramEntity.getParamType());
				//从map中获取参数值
				if(params.containsKey(paramEntity.getParamCode())){
					Object obj = params.get(paramEntity.getParamCode());
					if(obj!=null){
						if(paramEntity.getParamType()== CommConstants.Process.PARAM_TYPE_STRING){
							entity.setParamValue(String.valueOf(obj));
						}else if(paramEntity.getParamType()==CommConstants.Process.PARAM_TYPE_DATE){
							if(obj instanceof Date) {
								Date date = (Date) obj;
								entity.setParamValue(DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss"));
							}else{
								entity.setParamValue(String.valueOf(obj));
							}
						}else if(paramEntity.getParamType()==CommConstants.Process.PARAM_TYPE_NUMBER){
							entity.setParamValue(String.valueOf(obj));
						}else{
							entity.setParamValue(JsonUtil.beanToJsonString(obj));
						}
					}
				}

				entity.setState(CommConstants.State.STATE_NORMAL);
				entity.setCreator(processInsEntity.getCreator());
				entity.setCreateDate(processInsEntity.getCreateDate());
				processParamInsEntityList.add(entity);
			}
			if(processParamInsEntityList.size()>0){
				dao.save(processParamInsEntityList);
			}
		}
	}

	/**
	 * 根据流程编号获取流程参数
	 *
	 * @param processInsId
	 */
	@Override
	public List<CfgProcessParamInsEntity> getParamEntityByProcessInsId(long processInsId) throws Exception {
		CfgProcessParamInsEntity entity = new CfgProcessParamInsEntity();
		entity.setProcessInsId(processInsId);
		entity.setState(CommConstants.State.STATE_NORMAL);
		return dao.findByEntity(entity);
	}

	/**
	 * 完成流程
	 *
	 * @param processInsId
	 */
	@Override
	public void cancelProcess(long processInsId) throws Exception {
		CfgProcessParamInsEntity entity = new CfgProcessParamInsEntity();
		entity.setProcessInsId(processInsId);
		dao.deleteByEntity(entity);
	}
}