package com.sandi.web.common.bp.service.interfaces;

import com.sandi.web.common.bp.entity.EsBpDataColEntity;
import com.sandi.web.common.bp.entity.EsBpDataEntity;
import com.sandi.web.common.bp.entity.EsBpDataRowEntity;
import com.sandi.web.common.persistence.entity.Page;
import java.util.List;
import java.util.Map;

public interface IEsBpDataSV {
    /**
     * 根据条件查询es_bp_data
     *
     * @return
     * @throws Exception
     */
    public List<EsBpDataEntity> queryEsBpData(EsBpDataEntity esBpDataEntity) throws Exception;

    public List<Map<String, Object>> queryEsBpData(Map<String, Object> queryParam, Page page) throws Exception;

    /**
     * 保存实体
     *
     * @param esBpDataEntity
     * @throws Exception
     */
    public void saveEsBpData(EsBpDataEntity esBpDataEntity) throws Exception;


    public void updateEsBpDataThroughNewTransactional(EsBpDataEntity esBpDataEntity) throws Exception;

    /**
     * 解析文件入库
     *
     * @param esBpDataEntityList
     * @throws Exception
     */
    public void analyseFiles(List<EsBpDataEntity> esBpDataEntityList) throws Exception;

    /**
     * 解析文件入库
     *
     * @param esBpDataEntity
     * @throws Exception
     */
    public void analyseFile(EsBpDataEntity esBpDataEntity) throws Exception;


    /**
     * 保存数据
     *
     * @param esBpDataRowEntityList
     * @param esBpDataColEntityList
     * @throws Exception
     */
    public void saveAnalyseResult(List<EsBpDataRowEntity> esBpDataRowEntityList, List<EsBpDataColEntity> esBpDataColEntityList) throws Exception;

    /**
     * 业务处理
     * @param esBpDataEntityList
     * @throws Exception
     */
    public void processFiles(List<EsBpDataEntity> esBpDataEntityList) throws Exception;


    /**
     * 业务处理
     * @param esBpDataEntity
     * @throws Exception
     */
    public void processFile(EsBpDataEntity esBpDataEntity) throws Exception;


    /**
     * 获取处理失败数据
     * @param dataId
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryErrorData(Long dataId) throws Exception;
}