package com.sandi.web.common.bp.service.interfaces;

import com.sandi.web.common.bp.entity.EsBpDataRowEntity;
import java.util.List;

public interface IEsBpDataRowSV {

    public List<EsBpDataRowEntity> queryEsBpDataRow(EsBpDataRowEntity esBpDataRowEntity) throws Exception;

    public void saveEsBpDataRow(EsBpDataRowEntity esBpDataRowEntity) throws Exception;

    public void saveEsBpDataRow(List<EsBpDataRowEntity> esBpDataRowEntityList) throws Exception;
}