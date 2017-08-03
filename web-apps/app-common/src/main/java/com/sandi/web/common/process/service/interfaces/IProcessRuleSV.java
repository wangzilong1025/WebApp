package com.sandi.web.common.process.service.interfaces;


import com.sandi.web.common.process.entity.CfgTaskInsEntity;

import java.util.Map;

/**
 * Created by dizl on 2017/2/20.
 */
public interface IProcessRuleSV {
    public boolean validate(CfgTaskInsEntity taskInsEntity, String ruleParam, String ruleValue) throws Exception;
}
