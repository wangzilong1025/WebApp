package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncButtonDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncButtonSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncButtonSVImpl implements ICfgDyncButtonSV {
	@Autowired
	private ICfgDyncButtonDao dao;
}