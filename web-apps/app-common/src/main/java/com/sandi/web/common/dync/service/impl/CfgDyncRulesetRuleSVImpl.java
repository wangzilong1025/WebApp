package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.ICfgDyncRulesetRuleDao;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncRulesetRuleSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CfgDyncRulesetRuleSVImpl implements ICfgDyncRulesetRuleSV {
	@Autowired
	private ICfgDyncRulesetRuleDao dao;
}