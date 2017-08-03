package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncRuleDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncRuleSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncRuleSVImpl implements ICfgDyncRuleSV {
	@Autowired
	private ICfgDyncRuleDao dao;
}