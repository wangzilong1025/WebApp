package com.sandi.web.common.bp.service.impl;

import com.sandi.web.common.bp.BpProcess;
import com.sandi.web.common.bp.Constants;
import com.sandi.web.common.bp.dao.*;
import com.sandi.web.common.bp.entity.*;
import com.sandi.web.common.bp.service.interfaces.ICfgBpTemplateSV;
import com.sandi.web.common.bp.service.interfaces.IEsBpDataColSV;
import com.sandi.web.common.bp.service.interfaces.IEsBpDataRowSV;
import com.sandi.web.common.bp.service.interfaces.IEsBpDataSV;
import com.sandi.web.common.elec.dao.IElecInstDao;
import com.sandi.web.common.elec.entity.ElecInstEntity;
import com.sandi.web.common.persistence.entity.Page;
import com.sandi.web.common.persistence.entity.Rank;
import com.sandi.web.utils.bp.FileOperateFactory;
import com.sandi.web.utils.bp.entity.CfgBpTemplate;
import com.sandi.web.utils.bp.entity.EsBpData;
import com.sandi.web.utils.bp.operator.FileOperator;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.elec.entity.ElecInst;
import com.sandi.web.utils.sec.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("esBpDataSV")
public class EsBpDataSVImpl implements IEsBpDataSV {

    private static final Logger logger = LoggerFactory.getLogger(EsBpDataSVImpl.class);

    @Autowired
    private IEsBpDataDao esBpDataDao;

    @Autowired
    private IEsBpDataRowDao esBpDataRowDao;

    @Autowired
    private IEsBpDataColDao esBpDataColDao;

    @Autowired
    private IEsBpDataRowHisDao esBpDataRowHisDao;

    @Autowired
    private IEsBpDataColHisDao esBpDataColHisDao;

    @Autowired
    private IElecInstDao elecInstDao;

    @Resource
    private IEsBpDataSV esBpDataSV;

    @Autowired
    private IEsBpDataRowSV esBpDataRowSV;

    @Autowired
    private IEsBpDataColSV esBpDataColSV;

    @Autowired
    private ICfgBpTemplateSV cfgBpTemplateSV;

    /**
     * 根据条件查询es_bp_data
     *
     * @param esBpDataEntity
     * @return
     * @throws Exception
     */
    @Override
    public List<EsBpDataEntity> queryEsBpData(EsBpDataEntity esBpDataEntity) throws Exception {
        return esBpDataDao.findByEntity(esBpDataEntity);
    }

    @Override
    public List<Map<String, Object>> queryEsBpData(Map<String, Object> queryParam, Page page) throws Exception {
        return esBpDataDao.queryEsBpData(queryParam, page);
    }

    /**
     * 保存实体
     *
     * @param esBpDataEntity
     * @throws Exception
     */
    @Override
    public void saveEsBpData(EsBpDataEntity esBpDataEntity) throws Exception {
        esBpDataEntity.setCreateDate(new Date());
        esBpDataEntity.setCreator(SessionManager.getUser().getUserId());
        esBpDataDao.save(esBpDataEntity);
    }


    /**
     * 解析文件入库
     *
     * @param esBpDataEntityList
     * @throws Exception
     */
    @Override
    public void analyseFiles(List<EsBpDataEntity> esBpDataEntityList) throws Exception {
        for (EsBpDataEntity esBpDataEntity : esBpDataEntityList) {

            try {
                esBpDataEntity.setState(Constants.FileState.IN_ANALYSE);
                esBpDataEntity.setDoneDate(new Date());
                esBpDataSV.updateEsBpDataThroughNewTransactional(esBpDataEntity);
                esBpDataSV.analyseFile(esBpDataEntity);
                esBpDataEntity.setDoneDate(new Date());
                esBpDataEntity.setState(Constants.FileState.WAIT_BUSINESS);
            } catch (Exception e) {
                esBpDataEntity.setDoneDate(new Date());
                logger.error("处理失败DATA_ID=" + esBpDataEntity.getDataId() + "", e);
                esBpDataEntity.setState(Constants.FileState.ANALYSE_ERROR);
                esBpDataEntity.setRemark(e.getMessage());
            }
            esBpDataDao.updateById(esBpDataEntity);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateEsBpDataThroughNewTransactional(EsBpDataEntity esBpDataEntity) throws Exception {
        esBpDataDao.updateById(esBpDataEntity);
    }

    /**
     * 解析文件入库
     *
     * @param esBpDataEntity
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void analyseFile(EsBpDataEntity esBpDataEntity) throws Exception {

        if (esBpDataEntity.getTemplateId() == null) {
            throw new Exception("数据异常，未找到文件模板！");
        }

        CfgBpTemplateEntity cfgBpTemplateEntityQuery = new CfgBpTemplateEntity();
        cfgBpTemplateEntityQuery.setTemplateId(esBpDataEntity.getTemplateId());
        List<CfgBpTemplateEntity> cfgBpTemplateEntityList = cfgBpTemplateSV.queryCfgBpTemplate(cfgBpTemplateEntityQuery);
        if (cfgBpTemplateEntityList.size() != 1) {
            throw new Exception("根据template_id[" + esBpDataEntity.getTemplateId() + "]在CFG_BP_TEMPLATE表中未找到或找到多条相关配置");
        }
        CfgBpTemplateEntity cfgBpTemplateEntity = cfgBpTemplateEntityList.get(0);
        FileOperator fileOperator = FileOperateFactory.getFileOperator(cfgBpTemplateEntity.getTemplateType());

        ElecInstEntity elecInstEntityQuery = new ElecInstEntity();
        elecInstEntityQuery.setFileInputId(esBpDataEntity.getFileInputId());
        elecInstEntityQuery.setFileTypeId(cfgBpTemplateEntity.getFileTypeId());
        List<ElecInstEntity> elecInstEntityList = elecInstDao.findByEntity(elecInstEntityQuery);

        List<EsBpDataRowEntity> esBpDataRowEntityList = null;
        List<EsBpDataColEntity> esBpDataColEntityList = null;


        long headerLines = cfgBpTemplateEntity.getHeaderLines() == null ? 0 : cfgBpTemplateEntity.getHeaderLines();
        long dataTotal = 0;

        int rownum = 0;
        for (ElecInstEntity elecInstEntity : elecInstEntityList) {
            ElecInst elecInst = JsonUtil.json2Object(JsonUtil.object2Json(elecInstEntity), ElecInst.class);
            CfgBpTemplate cfgBpTemplate = JsonUtil.json2Object(JsonUtil.object2Json(cfgBpTemplateEntity), CfgBpTemplate.class);
            EsBpData esBpData = JsonUtil.json2Object(JsonUtil.object2Json(esBpDataEntity), EsBpData.class);

            List<List<List<String>>> fileData = fileOperator.doAnalyse(cfgBpTemplate, esBpData, elecInst);
            esBpDataRowEntityList = new ArrayList<EsBpDataRowEntity>();
            esBpDataColEntityList = new ArrayList<EsBpDataColEntity>();
            for (List<List<String>> shell : fileData) {
                long i = 0;
                for (List<String> row : shell) {
                    i++;
                    EsBpDataRowEntity esBpDataRowEntity = new EsBpDataRowEntity();
                    long rowId = esBpDataRowEntity.newId();
                    esBpDataRowEntity.setRowId(rowId);
                    esBpDataRowEntity.setDataId(esBpDataEntity.getDataId());
                    esBpDataRowEntity.setRowNum(rownum++);
                    esBpDataRowEntity.setCreateDate(new Date());
                    if (i <= headerLines) {
                        esBpDataRowEntity.setRowType(com.sandi.web.utils.bp.Constants.RowType.HEADER);
                        esBpDataRowEntity.setState(Constants.RowState.SUCCESS);
                    } else {
                        esBpDataRowEntity.setRowType(com.sandi.web.utils.bp.Constants.RowType.CONTENT);
                        esBpDataRowEntity.setState(Constants.RowState.WAIT);
                        dataTotal++;
                    }
                    int colnum = 0;
                    for (String col : row) {
                        EsBpDataColEntity esBpDataColEntity = new EsBpDataColEntity();
                        esBpDataColEntity.setColId(esBpDataColEntity.newId());
                        esBpDataColEntity.setRowId(rowId);
                        esBpDataColEntity.setDataId(esBpDataEntity.getDataId());
                        esBpDataColEntity.setColNum(colnum++);
                        esBpDataColEntity.setColValue(col);
                        esBpDataColEntity.setCreateDate(new Date());
                        esBpDataColEntityList.add(esBpDataColEntity);
                    }
                    esBpDataRowEntityList.add(esBpDataRowEntity);
                    if (cfgBpTemplateEntity.getDealCount() != null && cfgBpTemplateEntity.getDealCount() > 0 && esBpDataRowEntityList.size() > cfgBpTemplateEntity.getDealCount()) {
                        esBpDataSV.saveAnalyseResult(esBpDataRowEntityList, esBpDataColEntityList);
                        esBpDataRowEntityList = new ArrayList<EsBpDataRowEntity>();
                        esBpDataColEntityList = new ArrayList<EsBpDataColEntity>();
                    }
                }
            }
            if (esBpDataRowEntityList.size() > 0 || esBpDataColEntityList.size() > 0) {
                esBpDataSV.saveAnalyseResult(esBpDataRowEntityList, esBpDataColEntityList);
            }
        }
        esBpDataEntity.setHeaderTotal(headerLines);
        esBpDataEntity.setDataTotal(dataTotal);

    }


    /**
     * 保存数据
     *
     * @param esBpDataRowEntityList
     * @param esBpDataColEntityList
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAnalyseResult(List<EsBpDataRowEntity> esBpDataRowEntityList, List<EsBpDataColEntity> esBpDataColEntityList) throws Exception {
        esBpDataRowSV.saveEsBpDataRow(esBpDataRowEntityList);
        esBpDataColSV.saveEsBpDataCol(esBpDataColEntityList);
    }


    /**
     * 业务处理
     *
     * @param esBpDataEntityList
     * @throws Exception
     */
    @Override
    public void processFiles(List<EsBpDataEntity> esBpDataEntityList) throws Exception {
        DateFormat dateformat = new SimpleDateFormat("HHmmss");
        String nowDate = dateformat.format(new Date());
        for (EsBpDataEntity esBpDataEntity : esBpDataEntityList) {
            CfgBpTemplateEntity cfgBpTemplateEntityQuery = new CfgBpTemplateEntity();
            cfgBpTemplateEntityQuery.setTemplateId(esBpDataEntity.getTemplateId());
            List<CfgBpTemplateEntity> cfgBpTemplateEntityList = cfgBpTemplateSV.queryCfgBpTemplate(cfgBpTemplateEntityQuery);
            if (cfgBpTemplateEntityList.size() > 0) {
                CfgBpTemplateEntity cfgBpTemplateEntity = cfgBpTemplateEntityList.get(0);
                if (cfgBpTemplateEntity.getDealStartTime() != null && nowDate.compareTo(cfgBpTemplateEntity.getDealStartTime()) < 0) {
                    continue;
                }
                if (cfgBpTemplateEntity.getDealEndTime() != null && nowDate.compareTo(cfgBpTemplateEntity.getDealEndTime()) > 0) {
                    continue;
                }
            }
            try {
                esBpDataEntity.setState(Constants.FileState.IN_BUSINESS);
                esBpDataEntity.setDoneDate(new Date());
                esBpDataSV.updateEsBpDataThroughNewTransactional(esBpDataEntity);
                esBpDataSV.processFile(esBpDataEntity);
                esBpDataEntity.setDoneDate(new Date());
                esBpDataEntity.setState(Constants.FileState.BUSINESS_SUCCESS);
            } catch (Exception e) {
                esBpDataEntity.setDoneDate(new Date());
                logger.error("处理失败DATA_ID=" + esBpDataEntity.getDataId() + "", e);
                esBpDataEntity.setState(Constants.FileState.BUSINESS_ERROR);
                esBpDataEntity.setRemark(e.getMessage());
            }
            esBpDataDao.updateById(esBpDataEntity);


            //move to his
            EsBpDataRowEntity esBpDataRowEntity = new EsBpDataRowEntity();
            esBpDataRowEntity.setDataId(esBpDataEntity.getDataId());
            esBpDataRowDao.deleteByEntity(esBpDataRowEntity);

            EsBpDataColEntity esBpDataColEntity = new EsBpDataColEntity();
            esBpDataColEntity.setDataId(esBpDataEntity.getDataId());
            esBpDataColDao.deleteByEntity(esBpDataColEntity);
        }
    }

    /**
     * 业务处理
     *
     * @param esBpDataEntity
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processFile(EsBpDataEntity esBpDataEntity) throws Exception {

        CfgBpTemplateEntity cfgBpTemplateEntityQuery = new CfgBpTemplateEntity();
        cfgBpTemplateEntityQuery.setTemplateId(esBpDataEntity.getTemplateId());
        List<CfgBpTemplateEntity> cfgBpTemplateEntityList = cfgBpTemplateSV.queryCfgBpTemplate(cfgBpTemplateEntityQuery);
        if (cfgBpTemplateEntityList.size() != 1) {
            throw new Exception("根据template_id[" + esBpDataEntity.getTemplateId() + "]在CFG_BP_TEMPLATE表中未找到或找到多条相关配置");
        }
        CfgBpTemplateEntity cfgBpTemplateEntity = cfgBpTemplateEntityList.get(0);
        if (cfgBpTemplateEntity.getDealClass() == null) {
            throw new Exception("请确认该template_id[" + esBpDataEntity.getTemplateId() + "]是否配置了业务处理类！");
        }
        BpProcess bpProcess = null;
        try {
            bpProcess = (BpProcess) Class.forName(cfgBpTemplateEntity.getDealClass()).newInstance();
        } catch (Exception e) {
            throw new Exception("该template_id[" + esBpDataEntity.getTemplateId() + "]配置的业务处理类无法实例化，请确认！");
        }

        EsBpDataRowEntity esBpDataRowEntityQuery = new EsBpDataRowEntity();
        esBpDataRowEntityQuery.setState(Constants.RowState.WAIT);
        esBpDataRowEntityQuery.setDataId(esBpDataEntity.getDataId());
        esBpDataRowEntityQuery.setRowType(com.sandi.web.utils.bp.Constants.RowType.CONTENT);
        List<EsBpDataRowEntity> esBpDataRowEntityList = esBpDataRowSV.queryEsBpDataRow(esBpDataRowEntityQuery);
        long success = 0;
        long error = 0;
        for (EsBpDataRowEntity esBpDataRowEntity : esBpDataRowEntityList) {
            try {
                bpProcess.processData(esBpDataEntity, esBpDataRowEntity);
                esBpDataRowEntity.setState(Constants.RowState.SUCCESS);
                success++;
            } catch (Exception e) {
                error++;
                esBpDataRowEntity.setState(Constants.RowState.ERROR);
                esBpDataRowEntity.setRemark(e.getMessage());
                logger.error("处理当前行失败！DATA_ID=" + esBpDataRowEntity.getDataId() + ",ROW_ID=" + esBpDataRowEntity.getRowId(), e);
            }
            esBpDataRowDao.updateById(esBpDataRowEntity);
        }
        esBpDataEntity.setDataSuccess(success);
        esBpDataEntity.setDataError(error);
    }


    /**
     * 获取处理失败数据
     *
     * @param dataId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> queryErrorData(Long dataId) throws Exception {

        Rank rank = new Rank("rowNum");
        EsBpDataRowHisEntity esBpDataRowHisEntityQuery = new EsBpDataRowHisEntity();
        esBpDataRowHisEntityQuery.setState(Constants.RowState.ERROR);
        esBpDataRowHisEntityQuery.setDataId(dataId);
        esBpDataRowHisEntityQuery.setRowType(com.sandi.web.utils.bp.Constants.RowType.CONTENT);
        esBpDataRowHisEntityQuery.setRank(rank);
        List<EsBpDataRowHisEntity> esBpDataRowEntityContentList = esBpDataRowHisDao.findByEntity(esBpDataRowHisEntityQuery);


        EsBpDataRowHisEntity esBpDataRowEntityQuery2 = new EsBpDataRowHisEntity();
        esBpDataRowEntityQuery2.setState(Constants.RowState.SUCCESS);
        esBpDataRowEntityQuery2.setDataId(dataId);
        esBpDataRowEntityQuery2.setRowType(com.sandi.web.utils.bp.Constants.RowType.HEADER);
        esBpDataRowEntityQuery2.setRank(rank);
        List<EsBpDataRowHisEntity> esBpDataRowEntityHeaderList = esBpDataRowHisDao.findByEntity(esBpDataRowEntityQuery2);


        List<List<String>> header = new ArrayList<List<String>>();
        for (EsBpDataRowHisEntity esBpDataRowEntity : esBpDataRowEntityHeaderList) {
            String[] cols = new String[esBpDataRowEntity.getEsBpDataColHisEntityList().size() + 2];
            for (EsBpDataColHisEntity esBpDataColEntity : esBpDataRowEntity.getEsBpDataColHisEntityList()) {
                cols[esBpDataColEntity.getColNum()] = esBpDataColEntity.getColValue();
            }
            cols[esBpDataRowEntity.getEsBpDataColHisEntityList().size()] = "行编号";
            cols[esBpDataRowEntity.getEsBpDataColHisEntityList().size() + 1] = "备注";
            header.add(Arrays.asList(cols));
        }

        List<List<String>> content = new ArrayList<List<String>>();
        for (EsBpDataRowHisEntity esBpDataRowEntity : esBpDataRowEntityContentList) {
            String[] cols = new String[esBpDataRowEntity.getEsBpDataColHisEntityList().size() + 2];
            for (EsBpDataColHisEntity esBpDataColEntity : esBpDataRowEntity.getEsBpDataColHisEntityList()) {
                cols[esBpDataColEntity.getColNum()] = esBpDataColEntity.getColValue();
            }
            cols[esBpDataRowEntity.getEsBpDataColHisEntityList().size()] = esBpDataRowEntity.getRowNum().toString();
            cols[esBpDataRowEntity.getEsBpDataColHisEntityList().size() + 1] = esBpDataRowEntity.getRemark();
            content.add(Arrays.asList(cols));
        }

        List<List<List<String>>> contents = new ArrayList<List<List<String>>>();
        contents.add(content);

        List<List<List<String>>> headers = new ArrayList<List<List<String>>>();
        headers.add(header);

        Map<String, Object> remap = new HashMap<String, Object>();
        remap.put("data", contents);
        remap.put("headers", headers);
        return remap;
    }
}