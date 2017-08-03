package com.sandi.web.common.osdi.service.impl;

import com.sandi.web.common.osdi.dao.ICfgBusiSrvRelDao;
import com.sandi.web.common.osdi.service.interfaces.ICfgBusiSrvRelSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CfgBusiSrvRelSVImpl implements ICfgBusiSrvRelSV {
	@Autowired
	private ICfgBusiSrvRelDao dao;

}