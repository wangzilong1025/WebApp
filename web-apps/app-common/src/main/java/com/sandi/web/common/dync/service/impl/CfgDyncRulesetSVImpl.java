package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncRulesetDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncRulesetSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncRulesetSVImpl implements ICfgDyncRulesetSV {
	@Autowired
	private ICfgDyncRulesetDao dao;
}