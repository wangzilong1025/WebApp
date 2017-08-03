package com.sandi.web.common.mess.service.impl;

import com.sandi.web.common.mess.dao.ICfgMsgCollectDateDao;
import com.sandi.web.common.mess.entity.CfgMsgCollectDateEntity;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgCollectDateSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CfgMsgCollectDateSVImpl implements ICfgMsgCollectDateSV {
	@Autowired
	private ICfgMsgCollectDateDao dao;

	@Override
	public CfgMsgCollectDateEntity getCfgMsgCollectDateEntity(Long busiMsgId) throws Exception {
		return dao.findById(busiMsgId);
	}

	@Override
	public void saveEntity(CfgMsgCollectDateEntity entity) throws Exception {
		dao.save(entity);
	}

	@Override
	public void updateEntity(CfgMsgCollectDateEntity entity) throws Exception {
		dao.updateById(entity);
	}
}