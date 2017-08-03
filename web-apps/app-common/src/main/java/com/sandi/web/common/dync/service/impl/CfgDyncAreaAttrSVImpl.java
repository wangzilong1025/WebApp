package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncAreaAttrDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncAreaAttrSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncAreaAttrSVImpl implements ICfgDyncAreaAttrSV {
	@Autowired
	private ICfgDyncAreaAttrDao dao;
}