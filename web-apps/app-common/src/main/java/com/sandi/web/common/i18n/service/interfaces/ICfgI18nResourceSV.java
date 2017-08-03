package com.sandi.web.common.i18n.service.interfaces;

import com.sandi.web.common.i18n.entity.CfgI18nResourceEntity;
import java.util.List;

/**
 * Created by dizl on 2015/6/10.
 */
public interface ICfgI18nResourceSV {
    public CfgI18nResourceEntity getCfgI18nResouce(String resKey) throws Exception;

    public List<CfgI18nResourceEntity> getAllCfgI18nResouce() throws Exception;
}
