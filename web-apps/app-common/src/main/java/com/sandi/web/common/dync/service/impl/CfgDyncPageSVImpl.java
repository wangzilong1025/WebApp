package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncPageDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncPageSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncPageSVImpl implements ICfgDyncPageSV {
	@Autowired
	private ICfgDyncPageDao dao;
}