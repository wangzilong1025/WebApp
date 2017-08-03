package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncFramePageDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncFramePageSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncFramePageSVImpl implements ICfgDyncFramePageSV {
	@Autowired
	private ICfgDyncFramePageDao dao;
}