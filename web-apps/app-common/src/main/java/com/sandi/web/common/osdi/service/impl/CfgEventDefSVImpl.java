package com.sandi.web.common.osdi.service.impl;

import com.sandi.web.common.osdi.dao.ICfgEventDefDao;
import com.sandi.web.common.osdi.service.interfaces.ICfgEventDefSV;
import com.sandi.web.common.persistence.dao.CrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CfgEventDefSVImpl implements ICfgEventDefSV {
	@Autowired
	private ICfgEventDefDao dao;

	public CrudDao getDao() {
		return dao;
	}
}