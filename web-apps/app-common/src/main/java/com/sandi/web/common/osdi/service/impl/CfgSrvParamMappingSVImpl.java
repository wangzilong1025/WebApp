package com.sandi.web.common.osdi.service.impl;

import com.sandi.web.common.osdi.dao.ICfgSrvParamMappingDao;
import com.sandi.web.common.osdi.service.interfaces.ICfgSrvParamMappingSV;
import com.sandi.web.common.persistence.dao.CrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CfgSrvParamMappingSVImpl implements ICfgSrvParamMappingSV {
	@Autowired
	private ICfgSrvParamMappingDao dao;

	public CrudDao getDao() {
		return dao;
	}
}