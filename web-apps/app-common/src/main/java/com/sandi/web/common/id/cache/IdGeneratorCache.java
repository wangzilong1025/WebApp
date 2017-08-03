package com.sandi.web.common.id.cache;

import com.sandi.web.common.cache.DefaultCache;
import com.sandi.web.common.id.IdGeneratorFactory;
import com.sandi.web.common.id.entity.IdGeneratorEntity;
import com.sandi.web.common.id.service.interfaces.IIdGeneratorSV;
import com.sandi.web.utils.common.SpringContextHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2015/6/11.
 */
public class IdGeneratorCache extends DefaultCache {
    public static final String CACHE_NAME = "IdGeneratorCache";

    @Override
    protected Map getData() throws Exception {
        Map<String, IdGeneratorEntity> map = new HashMap<String, IdGeneratorEntity>();
        IIdGeneratorSV idGeneratorSV = SpringContextHolder.getBean(IIdGeneratorSV.class);
        List<IdGeneratorEntity> idGeneratorEntitieList = idGeneratorSV.getAllIdGeneratorEntity();
        if (idGeneratorEntitieList != null && idGeneratorEntitieList.size() > 0) {
            for (IdGeneratorEntity idGeneratorEntity : idGeneratorEntitieList) {
                map.put(idGeneratorEntity.getTableName(), idGeneratorEntity);
            }
            //系统初始化时，加载主键
            IdGeneratorFactory.loadNewId(idGeneratorEntitieList);
        }

        return map;
    }
}
