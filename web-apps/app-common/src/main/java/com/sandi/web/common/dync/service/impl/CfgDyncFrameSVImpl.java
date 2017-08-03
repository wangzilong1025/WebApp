package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncFrameDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncFrameSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncFrameSVImpl implements ICfgDyncFrameSV {
	@Autowired
	private ICfgDyncFrameDao dao;
}