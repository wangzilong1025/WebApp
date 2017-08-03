package com.sandi.web.common.process.service.impl;

import com.sandi.web.common.process.dao.ICfgProcessRuleDao;
import com.sandi.web.common.process.entity.CfgProcessRuleEntity;
import com.sandi.web.common.process.service.interfaces.ICfgProcessRuleSV;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.common.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CfgProcessRuleSVImpl implements ICfgProcessRuleSV {
	@Autowired
	private ICfgProcessRuleDao dao;

	@Override
	public CfgProcessRuleEntity getRuleEntityByRuleId(long ruleId) throws Exception {
		CfgProcessRuleEntity ruleEntity = new CfgProcessRuleEntity();
		ruleEntity.setRuleId(ruleId);
		ruleEntity.setState(CommConstants.State.STATE_NORMAL);
		List<CfgProcessRuleEntity> retLists = dao.findByEntity(ruleEntity);
		if(retLists!=null && retLists.size()>0){
			return retLists.get(0);
		}else{
			ExceptionUtil.throwBusinessException("根据规则编号"+ruleId+"无法找到规则配置数据");
			return null;
		}
	}

	/**
	 * 获取有效的规则数据
	 */
	@Override
	public List<CfgProcessRuleEntity> getRuleEntityList(Map params) throws Exception {
		CfgProcessRuleEntity entity = new CfgProcessRuleEntity();
		entity.setState(CommConstants.State.STATE_NORMAL);
		return dao.findByEntity(entity);
	}
}