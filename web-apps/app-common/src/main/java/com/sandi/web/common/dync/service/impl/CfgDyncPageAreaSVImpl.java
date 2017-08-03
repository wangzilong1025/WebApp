package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncPageAreaDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncPageAreaSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncPageAreaSVImpl implements ICfgDyncPageAreaSV {
	@Autowired
	private ICfgDyncPageAreaDao dao;
}