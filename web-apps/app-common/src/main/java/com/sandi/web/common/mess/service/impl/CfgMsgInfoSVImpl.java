package com.sandi.web.common.mess.service.impl;

import com.sandi.web.common.mess.dao.ICfgMsgInfoDao;
import com.sandi.web.common.mess.entity.CfgMsgInfoEntity;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgInfoSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CfgMsgInfoSVImpl implements ICfgMsgInfoSV {
	@Autowired
	private ICfgMsgInfoDao dao;

	@Override
	public List<CfgMsgInfoEntity> getCfgMsgInfoBySendLevel(String sendLevel) throws Exception {
		CfgMsgInfoEntity entity = new CfgMsgInfoEntity();
		entity.setState(CommConstants.State.STATE_NORMAL);
		entity.setSendLevel(sendLevel);
		entity.setEffectiveDate(DateUtils.getCurrentDate());
		entity.setExpireDate(DateUtils.getCurrentDate());
		return dao.findLike(entity);
	}
}