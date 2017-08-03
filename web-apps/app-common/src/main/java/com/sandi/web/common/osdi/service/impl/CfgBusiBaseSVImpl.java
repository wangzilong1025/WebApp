package com.sandi.web.common.osdi.service.impl;

import com.sandi.web.common.osdi.dao.ICfgBusiBaseDao;
import com.sandi.web.common.osdi.service.interfaces.ICfgBusiBaseSV;
import com.sandi.web.common.persistence.dao.CrudDao;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.http.entity.CfgBusiBase;
import com.sandi.web.utils.http.entity.CfgBusiEventRel;
import com.sandi.web.utils.http.entity.CfgBusiSrvRel;
import com.sandi.web.utils.http.entity.CfgSrvParamMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CfgBusiBaseSVImpl implements ICfgBusiBaseSV {
	@Autowired
	private ICfgBusiBaseDao dao;

	public CrudDao getDao() {
		return dao;
	}

	@Override
	public CfgBusiBase getBusiBase(String busiId) throws Exception {
		CfgBusiBase cfgBusiBase = null;
		if (StringUtils.isNotEmpty(busiId)) {
			Map param = new HashMap();
			param.put("busiId", busiId);
			List<CfgBusiBase> cfgBusiBases = dao.getBusiBaseEntity(param);
			if (cfgBusiBases != null && cfgBusiBases.size() > 0) {
				cfgBusiBase = cfgBusiBases.get(0);
			}
			if (cfgBusiBase != null) {
				List<CfgBusiSrvRel> cfgBusiSrvRels = this.getSrvListByBusiId(busiId);
				cfgBusiBase.setBusiSrvRels(cfgBusiSrvRels);
				Map<String, List<CfgBusiEventRel>> eventMap = this.getSrvEventsMap(busiId);
				cfgBusiBase.setEventMap(eventMap);
				Map<String, List<CfgSrvParamMapping>> paramMap = this.getSrvParamMapping(busiId);
				cfgBusiBase.setParamMap(paramMap);
			}
		}
		return cfgBusiBase;
	}

	private List<CfgBusiSrvRel> getSrvListByBusiId(String busiId) throws Exception {
		Map param = new HashMap();
		param.put("busiId", busiId);
		List<CfgBusiSrvRel> busiSrvRels = dao.getSrvListByBusiId(param);
		return busiSrvRels;
	}

	private Map<String, List<CfgBusiEventRel>> getSrvEventsMap(String busiId) throws Exception {
		Map param = new HashMap();
		param.put("busiId", busiId);
		List<CfgBusiEventRel> busiEventRels = dao.getEventListByBusiId(param);
		Map<String, List<CfgBusiEventRel>> eventsMap = new HashMap<>();
		if (busiEventRels != null && busiEventRels.size() > 0) {
			for (CfgBusiEventRel rel : busiEventRels) {
				int eventType = rel.getEventType();
				String key = busiId +":" + rel.getSrvId() + ":" + eventType;
				if (!eventsMap.containsKey(key)) {
					eventsMap.put(key, new ArrayList<CfgBusiEventRel>());
				}
				eventsMap.get(key).add(rel);
			}
		}
		return eventsMap;
	}

	private Map<String, List<CfgSrvParamMapping>> getSrvParamMapping(String busiId) throws Exception {
		Map<String, List<CfgSrvParamMapping>> paramMap = new HashMap<>();
		Map param = new HashMap();
		param.put("busiId", busiId);
		List<CfgSrvParamMapping> cfgSrvParamMappings = dao.getSrvParamMappingByBusiId(param);
		if (cfgSrvParamMappings != null && cfgSrvParamMappings.size() > 0) {
			for (CfgSrvParamMapping mapping : cfgSrvParamMappings) {
				String key = mapping.getSrvId();
				if (!paramMap.containsKey(key)) {
					paramMap.put(key, new ArrayList<CfgSrvParamMapping>());
				}
				paramMap.get(key).add(mapping);
			}
		}
		return paramMap;
	}
}