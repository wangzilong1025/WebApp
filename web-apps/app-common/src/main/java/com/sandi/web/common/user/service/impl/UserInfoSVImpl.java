package com.sandi.web.common.user.service.impl;

import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.common.persistence.service.CrudServiceImpl;
import com.sandi.web.common.user.dao.IUserInfoDao;
import com.sandi.web.common.user.entity.UserInfoEntity;
import com.sandi.web.common.user.entity.UserInfoEntityEntity;
import com.sandi.web.common.user.service.interfaces.IUserInfoSV;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoSVImpl extends CrudServiceImpl implements IUserInfoSV{
	@Autowired
	private IUserInfoDao dao;

	public CrudDao getDao() {
		return dao;
	}
}