package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncBusiFrameRelDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncBusiFrameRelSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CfgDyncBusiFrameRelSVImpl implements ICfgDyncBusiFrameRelSV {
	@Autowired
	private ICfgDyncBusiFrameRelDao dao;
}