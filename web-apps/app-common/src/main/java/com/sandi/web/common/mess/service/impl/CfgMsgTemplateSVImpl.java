package com.sandi.web.common.mess.service.impl;

import com.sandi.web.common.mess.dao.ICfgMsgTemplateDao;
import com.sandi.web.common.mess.entity.CfgMsgTemplateEntity;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgTemplateSV;
import com.sandi.web.common.utils.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfgMsgTemplateSVImpl implements ICfgMsgTemplateSV {
	@Autowired
	private ICfgMsgTemplateDao dao;

	@Override
	public List<CfgMsgTemplateEntity> getEntityByMsgId(Long cfgMsgId) throws Exception {
		CfgMsgTemplateEntity entity = new CfgMsgTemplateEntity();
		entity.setMsgId(cfgMsgId);
		entity.setState(CommConstants.State.STATE_NORMAL);
		return dao.findByEntity(entity);
	}

	public CfgMsgTemplateEntity getEntityByMsgIdAndMsgType(Long cfgMsgId,String msgType) throws Exception{
		CfgMsgTemplateEntity entity = new CfgMsgTemplateEntity();
		entity.setMsgId(cfgMsgId);
		entity.setMsgType(msgType);
		entity.setState(CommConstants.State.STATE_NORMAL);
		List<CfgMsgTemplateEntity> lists = dao.findByEntity(entity);
		if(lists!=null && lists.size()>0){
			return lists.get(0);
		}
		return null;
	}
}