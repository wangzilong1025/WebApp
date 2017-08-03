package com.sandi.web.common.osdi.service.impl;

import com.sandi.web.common.osdi.dao.ICfgEventRelDao;
import com.sandi.web.common.osdi.service.interfaces.ICfgEventRelSV;
import com.sandi.web.common.persistence.dao.CrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CfgEventRelSVImpl implements ICfgEventRelSV {
	@Autowired
	private ICfgEventRelDao dao;

	public CrudDao getDao() {
		return dao;
	}
}