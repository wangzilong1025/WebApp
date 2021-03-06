package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncButtonsetButtonDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncButtonsetButtonSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncButtonsetButtonSVImpl implements ICfgDyncButtonsetButtonSV {
	@Autowired
	private ICfgDyncButtonsetButtonDao dao;
}