package com.sandi.web.common.bp.service.impl;

import com.sandi.web.common.bp.dao.IEsBpDataRowDao;
import com.sandi.web.common.bp.entity.EsBpDataRowEntity;
import com.sandi.web.common.bp.service.interfaces.IEsBpDataRowSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EsBpDataRowSVImpl implements IEsBpDataRowSV {
    @Autowired
    private IEsBpDataRowDao esBpDataRowDao;

    @Override
    public List<EsBpDataRowEntity> queryEsBpDataRow(EsBpDataRowEntity esBpDataRowEntity) throws Exception {
        return esBpDataRowDao.findByEntity(esBpDataRowEntity);
    }

    @Override
    public void saveEsBpDataRow(EsBpDataRowEntity esBpDataRowEntity) throws Exception {
        esBpDataRowDao.save(esBpDataRowEntity);
    }

    @Override
    public void saveEsBpDataRow(List<EsBpDataRowEntity> esBpDataRowEntityList) throws Exception {
        for (EsBpDataRowEntity esBpDataRowEntity : esBpDataRowEntityList) {
            esBpDataRowDao.save(esBpDataRowEntity);
        }
    }
}