package com.sandi.web.common.bp.service.interfaces;

import com.sandi.web.common.bp.entity.EsBpDataColEntity;
import java.util.List;

public interface IEsBpDataColSV {
    public List<EsBpDataColEntity> queryEsBpDataCol(EsBpDataColEntity esBpDataColEntity) throws Exception;

    public void saveEsBpDataCol(EsBpDataColEntity esBpDataColEntity) throws Exception;

    public void saveEsBpDataCol(List<EsBpDataColEntity> esBpDataColEntityList) throws Exception;
}