package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncPageTemplateDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncPageTemplateSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncPageTemplateSVImpl implements ICfgDyncPageTemplateSV {
	@Autowired
	private ICfgDyncPageTemplateDao dao;
}