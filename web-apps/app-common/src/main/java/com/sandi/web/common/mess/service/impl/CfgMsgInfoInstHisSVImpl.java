package com.sandi.web.common.mess.service.impl;

import com.sandi.web.common.mess.dao.ICfgMsgInfoInstHisDao;
import com.sandi.web.common.mess.entity.CfgMsgInfoInstHisEntity;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgInfoInstHisSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfgMsgInfoInstHisSVImpl implements ICfgMsgInfoInstHisSV {
	@Autowired
	private ICfgMsgInfoInstHisDao dao;

	/**
	 * 保存数据
	 * */
	public void saveEntity(List<CfgMsgInfoInstHisEntity> msgInfoInstHisEntityList) throws Exception{
		dao.save(msgInfoInstHisEntityList);
	}

	public void saveEntity(CfgMsgInfoInstHisEntity entity) throws Exception{
		dao.save(entity);
	}
}