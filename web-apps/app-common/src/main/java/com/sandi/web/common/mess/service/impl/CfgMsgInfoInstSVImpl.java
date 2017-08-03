package com.sandi.web.common.mess.service.impl;

import com.sandi.web.common.mess.dao.ICfgMsgInfoInstDao;
import com.sandi.web.common.mess.entity.CfgMsgInfoInstEntity;
import com.sandi.web.common.mess.entity.CfgMsgInfoInstHisEntity;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgInfoInstHisSV;
import com.sandi.web.common.mess.service.interfaces.ICfgMsgInfoInstSV;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.common.utils.SendMsgUtil;
import com.sandi.web.utils.sec.SessionManager;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CfgMsgInfoInstSVImpl implements ICfgMsgInfoInstSV {
	@Autowired
	private ICfgMsgInfoInstDao dao;
	@Autowired
	private ICfgMsgInfoInstHisSV instHisSV;

	@Override
	public void saveEntity(List<CfgMsgInfoInstEntity> entityList) throws Exception {
		dao.save(entityList);
	}

	@Override
	public List<CfgMsgInfoInstEntity> getSendMsg() throws Exception {
		return dao.getSendMsg();
	}

	@Override
	public List<CfgMsgInfoInstEntity> getWebInfoList() throws Exception {
		UserInfoInterface userInfo = SessionManager.getUser();
		if(userInfo!=null){
			Map map = new HashMap();
			map.put("userId",userInfo.getUserId());

			return dao.getWebInfoList(map);
		}
		return null;
	}

	@Override
	public int getWebInfoCount() throws Exception {
		UserInfoInterface userInfo = SessionManager.getUser();
		if(userInfo!=null){
			Map map = new HashMap();
			map.put("userId",userInfo.getUserId());

			return dao.getWebInfoCount(map);
		}
		return 0;
	}

	@Override
	public List<CfgMsgInfoInstEntity> getAppInfoList() throws Exception {
		UserInfoInterface userInfo = SessionManager.getUser();
		if(userInfo!=null){
			Map map = new HashMap();
			map.put("userId",userInfo.getUserId());

			return dao.getAppInfoList(map);
		}
		return null;
	}

	@Override
	public int getAppInfoCount() throws Exception {
		UserInfoInterface userInfo = SessionManager.getUser();
		if(userInfo!=null){
			Map map = new HashMap();
			map.put("userId",userInfo.getUserId());

			return dao.getAppInfoCount(map);
		}
		return 0;
	}

	@Override
	public void deleteEntity(List<CfgMsgInfoInstEntity> entityList) throws Exception {
		if(entityList!=null && entityList.size()>0){
			for(CfgMsgInfoInstEntity entity : entityList){
				dao.deleteById(entity.getMsgInfoId());
			}
		}
	}

	public void readMess(long msgInfoId) throws Exception{
		CfgMsgInfoInstEntity msgInfoInstEntity = dao.findById(msgInfoId);
		if(msgInfoInstEntity!=null) {
			Date currDate = DateUtils.getCurrentDate();
			CfgMsgInfoInstHisEntity hisEntity = SendMsgUtil.copy2His(msgInfoInstEntity);
			if (msgInfoInstEntity.getSendType() == 3) {//周期性发送
				boolean sendFlag = true;
				if (msgInfoInstEntity.getSendMaxCount() != null && msgInfoInstEntity.getSendMaxCount() > 0 && msgInfoInstEntity.getSendMaxCount() <= msgInfoInstEntity.getSendCount()) {//已经达到发送次数
					sendFlag = false;
				}
				if (msgInfoInstEntity.getMsgExpireDate() != null && msgInfoInstEntity.getMsgExpireDate().compareTo(currDate) < 0) {
					sendFlag = false;
				}
				if (sendFlag) {
					CfgMsgInfoInstEntity newEntity = SendMsgUtil.copy(msgInfoInstEntity);
					newEntity.setMsgInfoId(newEntity.newId());
					newEntity.setSendCount(newEntity.getSendCount() + 1);
					newEntity.setSendDate(DateUtils.getNextValidTimeAfter(newEntity.getSendDate(), newEntity.getSendRate()));
					newEntity.setCreateDate(currDate);

					dao.save(newEntity);
				}
			}
			dao.deleteById(msgInfoId);
			instHisSV.saveEntity(hisEntity);
		}
	}
}