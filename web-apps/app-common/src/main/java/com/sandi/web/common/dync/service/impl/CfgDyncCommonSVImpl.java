package com.sandi.web.common.dync.service.impl;

import com.sandi.web.common.dync.dao.*;
import com.sandi.web.common.dync.entity.*;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncBusiFrameRelSV;
import com.sandi.web.common.dync.service.interfaces.ICfgDyncCommonSV;
import com.sandi.web.common.persistence.entity.Page;
import com.sandi.web.common.persistence.entity.Rank;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.sec.SessionManager;
import com.sandi.web.utils.sec.entity.UserInfoInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CfgDyncCommonSVImpl implements ICfgDyncCommonSV {
    private static Logger log = Logger.getLogger(CfgDyncCommonSVImpl.class);

    @Autowired
    private ICfgDyncBusiFrameRelSV cfgDyncBusiFrameRelSV;
    @Autowired
    private ICfgDyncBusiFrameRelDao cfgDyncBusiFrameRelDao;
    @Autowired
    private ICfgDyncPageTemplateDao cfgDyncPageTemplateDao;
    @Autowired
    private ICfgDyncFrameDao cfgDyncFrameDao;
    @Autowired
    private ICfgDyncFramePageDao cfgDyncFramePageDao;
    @Autowired
    private ICfgDyncPageDao cfgDyncPageDao;
    @Autowired
    private ICfgDyncPageAreaDao cfgDyncPageAreaDao;
    @Autowired
    private ICfgDyncAreaDao cfgDyncAreaDao;
    @Autowired
    private ICfgDyncAreaAttrDao cfgDyncAreaAttrDao;
    @Autowired
    private ICfgDyncAttrDao cfgDyncAttrDao;
    @Autowired
    private ICfgDyncRuleDao cfgDyncRuleDao;
    @Autowired
    private ICfgDyncButtonDao cfgDyncButtonDao;
    @Autowired
    private ICfgDyncRuleExpDao cfgDyncRuleExpDao;
    @Autowired
    private ICfgDyncRulesetDao cfgDyncRulesetDao;
    @Autowired
    private ICfgDyncButtonsetDao cfgDyncButtonsetDao;

    @Autowired
    private ICfgDyncButtonsetButtonDao cfgDyncButtonsetButtonDao;

    @Autowired
    private ICfgDyncRulesetRuleDao cfgDyncRulesetRuleDao;

    /**
     * 根据条件获取请求的url路径
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public String getRequestUrl(String requestJson) throws Exception {
        CfgDyncPageTemplateEntity templateEntity;
        Map<String, Object> inMap = JsonUtil.json2Map(requestJson);
        Long busiFrameId;
        Long pageTemplateId;
        if (inMap.containsKey(CommConstants.Dync.BUSI_FRAME_ID)) {
            busiFrameId = Long.parseLong((String) inMap.get(CommConstants.Dync.BUSI_FRAME_ID));
            CfgDyncBusiFrameRelEntity relEntity = cfgDyncBusiFrameRelDao.findById(busiFrameId);
            if (relEntity == null) {
                throw new Exception("根据busiFrameId:" + busiFrameId + ",没有获取到cfg_dync_busi_frame_rel配置!");
            }
            pageTemplateId = relEntity.getPageTemplateId();
            templateEntity = cfgDyncPageTemplateDao.findById(pageTemplateId);
        } else {
            if (!inMap.containsKey(CommConstants.Dync.BUSI_ID) || !inMap.containsKey(CommConstants.Dync.OPERATE_ID)) {
                throw new Exception("busiId和operateId不能为空!");
            }
            String busiId = (String) inMap.get(CommConstants.Dync.BUSI_ID);
            String operateId = (String) inMap.get(CommConstants.Dync.OPERATE_ID);
            int moduleId = 1;//默认为PC端
            if (StringUtils.isNotEmpty((String) inMap.get(CommConstants.Dync.MODULE_ID))) {
                moduleId = Integer.parseInt((String) inMap.get(CommConstants.Dync.MODULE_ID));
            }
            CfgDyncBusiFrameRelEntity entity = new CfgDyncBusiFrameRelEntity();
            entity.setBusiId(busiId);
            entity.setOperateId(operateId);
            entity.setModuleId(moduleId);
            List<CfgDyncBusiFrameRelEntity> entityList = cfgDyncBusiFrameRelDao.findByEntity(entity);
            if (entityList == null || entityList.size() == 0) {
                throw new Exception("根据busiId:" + busiId + ",operateId:" + operateId + ",moduleId:" + moduleId + "没有获取到cfg_dync_busi_frame_rel配置!");
            }
            pageTemplateId = entityList.get(0).getPageTemplateId();
            busiFrameId = entityList.get(0).getBusiFrameId();
            templateEntity = cfgDyncPageTemplateDao.findById(pageTemplateId);
        }
        if (templateEntity == null) {
            throw new Exception("根据pageTemplateId:" + pageTemplateId + ",没有获取到cfg_dync_page_template配置!");
        }
        Map<String, Object> reMap = new HashMap<String, Object>();
        reMap.put("template", templateEntity);
        reMap.put("busiFrameId", busiFrameId);
        return JsonUtil.beanToJsonString(reMap);
    }

    /**
     * 获取动态表单数据
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public String getBusiFrameEntity(String param) throws Exception {
        Map<String, Object> inMap = JsonUtil.json2Map(param);
        Map<String, Object> rtMap = new HashMap();
        List<CfgDyncFrameEntity> frameEntityList = null;
        if (inMap.containsKey(CommConstants.Dync.BUSI_FRAME_ID)) {
            long busiFrameId = Long.parseLong(inMap.get(CommConstants.Dync.BUSI_FRAME_ID).toString());
            CfgDyncFrameEntity cfgDyncFrameEntity = new CfgDyncFrameEntity();
            cfgDyncFrameEntity.setBusiFrameId(busiFrameId);
            frameEntityList = cfgDyncFrameDao.queryCfgDyncFrame(cfgDyncFrameEntity);
            if (frameEntityList == null || frameEntityList.size() == 0) {
                throw new Exception("根据state:1,busiFrameId:" + busiFrameId + ",没有获取到cfg_dync_frame配置!");
            }
            long frameId = frameEntityList.get(0).getFrameId();
            CfgDyncFramePageEntity cfgDyncFramePageEntity = new CfgDyncFramePageEntity();
            cfgDyncFramePageEntity.setFrameId(frameId);
            cfgDyncFramePageEntity.setState(CommConstants.State.STATE_NORMAL);
            cfgDyncFramePageEntity.setRank(new Rank("sortId"));//排序
            List<CfgDyncFramePageEntity> cfgDyncFramePageEntities = cfgDyncFramePageDao.findByEntity(cfgDyncFramePageEntity);
            if (cfgDyncFramePageEntities != null && cfgDyncFramePageEntities.size() > 0) {
                for (CfgDyncFramePageEntity framePageEntity : cfgDyncFramePageEntities) {
                    CfgDyncPageEntity cfgDyncPageEntity = new CfgDyncPageEntity();
                    cfgDyncPageEntity.setPageId(framePageEntity.getPageId());
                    cfgDyncPageEntity.setState(CommConstants.State.STATE_NORMAL);
                    List<CfgDyncPageEntity> cfgDyncPageEntities = cfgDyncPageDao.findByEntity(cfgDyncPageEntity);
                    if (cfgDyncPageEntities != null && cfgDyncPageEntities.size() > 0) {
                        CfgDyncPageAreaEntity cfgDyncPageAreaEntity = new CfgDyncPageAreaEntity();
                        cfgDyncPageAreaEntity.setPageId(cfgDyncPageEntities.get(0).getPageId());
                        cfgDyncPageAreaEntity.setState(CommConstants.State.STATE_NORMAL);
                        cfgDyncPageAreaEntity.setRank(new Rank("sortId"));
                        List<CfgDyncPageAreaEntity> cfgDyncPageAreaEntities = cfgDyncPageAreaDao.findByEntity(cfgDyncPageAreaEntity);
                        if (cfgDyncPageAreaEntities != null && cfgDyncPageAreaEntities.size() > 0) {
                            for (CfgDyncPageAreaEntity pageAreaEntity : cfgDyncPageAreaEntities) {
                                CfgDyncAreaEntity cfgDyncAreaEntity = new CfgDyncAreaEntity();
                                cfgDyncAreaEntity.setAreaId(pageAreaEntity.getAreaId());
                                cfgDyncAreaEntity.setState(CommConstants.State.STATE_NORMAL);
                                List<CfgDyncAreaEntity> cfgDyncAreaEntities = cfgDyncAreaDao.findByEntity(cfgDyncAreaEntity);
                                if (cfgDyncAreaEntities != null && cfgDyncAreaEntities.size() > 0) {
                                    CfgDyncAreaAttrEntity cfgDyncAreaAttrEntity = new CfgDyncAreaAttrEntity();
                                    cfgDyncAreaAttrEntity.setAreaId(cfgDyncAreaEntities.get(0).getAreaId());
                                    cfgDyncAreaAttrEntity.setState(CommConstants.State.STATE_NORMAL);
                                    cfgDyncAreaAttrEntity.setRank(new Rank("sortId"));
                                    List<CfgDyncAreaAttrEntity> cfgDyncAreaAttrEntities = cfgDyncAreaAttrDao.findByEntity(cfgDyncAreaAttrEntity);
                                    if (cfgDyncAreaAttrEntities != null && cfgDyncAreaAttrEntities.size() > 0) {
                                        for (CfgDyncAreaAttrEntity areaAttrEntity : cfgDyncAreaAttrEntities) {
                                            CfgDyncAttrEntity cfgDyncAttrEntity = new CfgDyncAttrEntity();
                                            cfgDyncAttrEntity.setAttrId(areaAttrEntity.getAttrId());
                                            cfgDyncAttrEntity.setState(CommConstants.State.STATE_NORMAL);
                                            List<CfgDyncAttrEntity> cfgDyncAttrEntities = cfgDyncAttrDao.findByEntity(cfgDyncAttrEntity);
                                            if (cfgDyncAttrEntities != null && cfgDyncAttrEntities.size() > 0) {
                                                if (cfgDyncAttrEntities.get(0).getRulesetId() != null && cfgDyncAttrEntities.get(0).getRulesetId() > 0) {
                                                    List<CfgDyncRuleEntity> cfgDyncRuleEntities = cfgDyncRuleDao.getRuleByRulesetId(cfgDyncAttrEntities.get(0).getRulesetId());
                                                    cfgDyncAttrEntities.get(0).setCfgDyncRuleEntities(cfgDyncRuleEntities);
                                                }
                                                if (cfgDyncAttrEntities.get(0).getRegexpId() != null && cfgDyncAttrEntities.get(0).getRegexpId() > 0) {
                                                    CfgDyncRuleExpEntity cfgDyncRuleExpEntity = cfgDyncRuleExpDao.findById(cfgDyncAttrEntities.get(0).getRegexpId());
                                                    cfgDyncAttrEntities.get(0).setCfgDyncRuleExpEntity(cfgDyncRuleExpEntity);
                                                }
                                                areaAttrEntity.setCfgDyncAttrEntity(cfgDyncAttrEntities.get(0));
                                            }
                                            if (areaAttrEntity.getRulesetId() != null && areaAttrEntity.getRulesetId() > 0) {
                                                List<CfgDyncRuleEntity> cfgDyncRuleEntities = cfgDyncRuleDao.getRuleByRulesetId(areaAttrEntity.getRulesetId());
                                                areaAttrEntity.setCfgDyncRuleEntities(cfgDyncRuleEntities);
                                            }
                                        }
                                    }
                                    cfgDyncAreaEntities.get(0).setCfgDyncAreaAttrEntities(cfgDyncAreaAttrEntities);
                                    if (cfgDyncAreaEntities.get(0).getRulesetId() != null && cfgDyncAreaEntities.get(0).getRulesetId() > 0) {
                                        List<CfgDyncRuleEntity> cfgDyncRuleEntities = cfgDyncRuleDao.getRuleByRulesetId(cfgDyncAreaEntities.get(0).getRulesetId());
                                        cfgDyncAreaEntities.get(0).setCfgDyncRuleEntities(cfgDyncRuleEntities);
                                    }
                                    if (cfgDyncAreaEntities.get(0).getButtonsetId() != null && cfgDyncAreaEntities.get(0).getButtonsetId() > 0) {
                                        List<CfgDyncButtonEntity> cfgDyncButtonEntities = cfgDyncButtonDao.getButtonByButtonsetId(cfgDyncAreaEntities.get(0).getButtonsetId());
                                        cfgDyncAreaEntities.get(0).setCfgDyncButtonEntities(cfgDyncButtonEntities);
                                    }
                                    pageAreaEntity.setCfgDyncAreaEntity(cfgDyncAreaEntities.get(0));
                                }
                            }
                        }
                        cfgDyncPageEntities.get(0).setCfgDyncPageAreaEntities(cfgDyncPageAreaEntities);
                        if (cfgDyncPageEntities.get(0).getRulesetId() != null && cfgDyncPageEntities.get(0).getRulesetId() > 0) {
                            List<CfgDyncRuleEntity> cfgDyncRuleEntities = cfgDyncRuleDao.getRuleByRulesetId(cfgDyncPageEntities.get(0).getRulesetId());
                            cfgDyncPageEntities.get(0).setCfgDyncRuleEntities(cfgDyncRuleEntities);
                        }
                        if (cfgDyncPageEntities.get(0).getButtonsetId() != null && cfgDyncPageEntities.get(0).getButtonsetId() > 0) {
                            List<CfgDyncButtonEntity> cfgDyncButtonEntities = cfgDyncButtonDao.getButtonByButtonsetId(cfgDyncPageEntities.get(0).getButtonsetId());
                            cfgDyncPageEntities.get(0).setCfgDyncButtonEntities(cfgDyncButtonEntities);
                        }
                        framePageEntity.setCfgDyncPageEntity(cfgDyncPageEntities.get(0));
                    }

                    if (framePageEntity.getRulesetId() != null && framePageEntity.getRulesetId() > 0) {
                        List<CfgDyncRuleEntity> cfgDyncRuleEntities = cfgDyncRuleDao.getRuleByRulesetId(cfgDyncPageEntities.get(0).getRulesetId());
                        framePageEntity.setCfgDyncRuleEntities(cfgDyncRuleEntities);
                    }
                }
            }
            frameEntityList.get(0).setCfgDyncFramePageEntities(cfgDyncFramePageEntities);
            if (frameEntityList.get(0).getRulesetId() != null && frameEntityList.get(0).getRulesetId() > 0) {
                List<CfgDyncRuleEntity> cfgDyncRuleEntities = cfgDyncRuleDao.getRuleByRulesetId(frameEntityList.get(0).getRulesetId());
                frameEntityList.get(0).setCfgDyncRuleEntities(cfgDyncRuleEntities);
            }
            if (frameEntityList.get(0).getButtonsetId() != null && frameEntityList.get(0).getButtonsetId() > 0) {
                List<CfgDyncButtonEntity> cfgDyncButtonEntities = cfgDyncButtonDao.getButtonByButtonsetId(frameEntityList.get(0).getButtonsetId());
                frameEntityList.get(0).setCfgDyncButtonEntities(cfgDyncButtonEntities);
            }
        }
        if (frameEntityList != null && frameEntityList.size() > 0) {
            rtMap.put("dyncData", frameEntityList);
        }
        return JsonUtil.beanToJsonString(rtMap);
    }


    /**
     * 查询框架模板
     *
     * @param cfgDyncPageTemplateEntity
     * @return
     */
    @Override
    public List<CfgDyncPageTemplateEntity> getCfgDyncPageTemplate(CfgDyncPageTemplateEntity cfgDyncPageTemplateEntity) throws Exception {
        return this.cfgDyncPageTemplateDao.findByEntity(cfgDyncPageTemplateEntity);
    }

    /**
     * 查询规则集
     *
     * @param cfgDyncRulesetEntity
     * @return
     */
    @Override
    public List<CfgDyncRulesetEntity> getCfgDyncRuleset(CfgDyncRulesetEntity cfgDyncRulesetEntity) throws Exception {
        return this.cfgDyncRulesetDao.findByEntity(cfgDyncRulesetEntity);
    }

    /**
     * 查询按钮集
     *
     * @param cfgDyncButtonsetEntity
     * @return
     */
    @Override
    public List<CfgDyncButtonsetEntity> getCfgDyncButtonset(CfgDyncButtonsetEntity cfgDyncButtonsetEntity) throws Exception {
        return this.cfgDyncButtonsetDao.findByEntity(cfgDyncButtonsetEntity);
    }


    @Override
    public void saveButtonset(Map<String, Object> paramMap) throws Exception {
        String buttonsetName = null;
        Long buttonsetId = null;
        String remark = null;
        String[] buttons = null;

        UserInfoInterface userInfoInterface = SessionManager.getUser();

        if (paramMap.containsKey("buttonsetName") && paramMap.get("buttonsetName") != null && !"".equals(paramMap.get("buttonsetName").toString())) {
            buttonsetName = paramMap.get("buttonsetName").toString();
        }

        if (paramMap.containsKey("buttonsetId") && paramMap.get("buttonsetId") != null && !"".equals(paramMap.get("buttonsetId").toString())) {
            buttonsetId = Long.valueOf(paramMap.get("buttonsetId").toString());
        }
        if (paramMap.containsKey("buttons") && paramMap.get("buttons") != null && !"".equals(paramMap.get("buttons").toString())) {
            buttons = paramMap.get("buttons").toString().split(",");
        }
        if (paramMap.containsKey("remark") && paramMap.get("remark") != null && !"".equals(paramMap.get("remark").toString())) {
            remark = paramMap.get("remark").toString();
        }

        if (buttonsetName == null || remark == null) {
            throw new Exception("按钮集名称和描述不能为空！");
        }
        CfgDyncButtonsetEntity cfgDyncButtonsetEntity = new CfgDyncButtonsetEntity();
        if (buttonsetId == null) {
            cfgDyncButtonsetEntity.setButtonsetId(cfgDyncButtonsetEntity.newId());
            cfgDyncButtonsetEntity.setCreateDate(new Date());
            cfgDyncButtonsetEntity.setCreator(userInfoInterface.getUserId());
            cfgDyncButtonsetEntity.setDoneDate(new Date());
            cfgDyncButtonsetEntity.setOpId(userInfoInterface.getUserId());
            cfgDyncButtonsetEntity.setState(1);
            cfgDyncButtonsetEntity.setButtonsetName(buttonsetName);
            cfgDyncButtonsetEntity.setRemark(remark);
            cfgDyncButtonsetDao.save(cfgDyncButtonsetEntity);
        } else {
            cfgDyncButtonsetEntity.setButtonsetId(buttonsetId);
            cfgDyncButtonsetEntity.setDoneDate(new Date());
            cfgDyncButtonsetEntity.setOpId(userInfoInterface.getUserId());
            cfgDyncButtonsetEntity.setState(1);
            cfgDyncButtonsetEntity.setButtonsetName(buttonsetName);
            cfgDyncButtonsetEntity.setRemark(remark);
            cfgDyncButtonsetDao.updateById(cfgDyncButtonsetEntity);

            CfgDyncButtonsetButtonEntity cfgDyncButtonsetButtonEntity = new CfgDyncButtonsetButtonEntity();
            cfgDyncButtonsetButtonEntity.setButtonsetId(buttonsetId);
            cfgDyncButtonsetButtonDao.deleteByEntity(cfgDyncButtonsetButtonEntity);
        }

        for (int i = 0; i < buttons.length; i++) {
            if (!"".equals(buttons[i].trim())) {
                CfgDyncButtonsetButtonEntity cfgDyncButtonsetButtonEntity = new CfgDyncButtonsetButtonEntity();
                cfgDyncButtonsetButtonEntity.setRelatId(cfgDyncButtonsetButtonEntity.newId());
                cfgDyncButtonsetButtonEntity.setButtonsetId(cfgDyncButtonsetEntity.getButtonsetId());
                cfgDyncButtonsetButtonEntity.setButtonId(Long.valueOf(buttons[i]));
                cfgDyncButtonsetButtonEntity.setSortId(i);
                cfgDyncButtonsetButtonEntity.setState(1);
                cfgDyncButtonsetButtonEntity.setRemark("");
                cfgDyncButtonsetButtonEntity.setCreateDate(new Date());
                cfgDyncButtonsetButtonEntity.setDoneDate(new Date());
                cfgDyncButtonsetButtonEntity.setCreator(userInfoInterface.getUserId());
                cfgDyncButtonsetButtonEntity.setOpId(userInfoInterface.getUserId());
                cfgDyncButtonsetButtonDao.save(cfgDyncButtonsetButtonEntity);
            }
        }

    }


    @Override
    public void saveRuleset(Map<String, Object> paramMap) throws Exception {
        String rulesetName = null;
        Long rulesetId = null;
        String remark = null;
        String[] rules = null;
        Integer rulesetType = null;

        UserInfoInterface userInfoInterface = SessionManager.getUser();

        if (paramMap.containsKey("rulesetName") && paramMap.get("rulesetName") != null && !"".equals(paramMap.get("rulesetName").toString())) {
            rulesetName = paramMap.get("rulesetName").toString();
        }

        if (paramMap.containsKey("rulesetId") && paramMap.get("rulesetId") != null && !"".equals(paramMap.get("rulesetId").toString())) {
            rulesetId = Long.valueOf(paramMap.get("rulesetId").toString());
        }

        if (paramMap.containsKey("rulesetType") && paramMap.get("rulesetType") != null && !"".equals(paramMap.get("rulesetType").toString())) {
            rulesetType = Integer.valueOf(paramMap.get("rulesetType").toString());
        }

        if (paramMap.containsKey("rules") && paramMap.get("rules") != null && !"".equals(paramMap.get("rules").toString())) {
            rules = paramMap.get("rules").toString().split(",");
        }
        if (paramMap.containsKey("remark") && paramMap.get("remark") != null && !"".equals(paramMap.get("remark").toString())) {
            remark = paramMap.get("remark").toString();
        }

        if (rulesetName == null || remark == null) {
            throw new Exception("规则集名称和描述不能为空！");
        }

        if (rules == null || rules == null) {
            throw new Exception("规则不能为空");
        }

        if (rulesetType == null) {
            throw new Exception("规则集类型不能为空");
        }

        CfgDyncRulesetEntity cfgDyncRulesetEntity = new CfgDyncRulesetEntity();
        if (rulesetId == null) {
            cfgDyncRulesetEntity.setRulesetId(cfgDyncRulesetEntity.newId());
            cfgDyncRulesetEntity.setCreateDate(new Date());
            cfgDyncRulesetEntity.setCreator(userInfoInterface.getUserId());
            cfgDyncRulesetEntity.setDoneDate(new Date());
            cfgDyncRulesetEntity.setOpId(userInfoInterface.getUserId());
            cfgDyncRulesetEntity.setState(1);
            cfgDyncRulesetEntity.setRulesetName(rulesetName);
            cfgDyncRulesetEntity.setRemark(remark);
            cfgDyncRulesetEntity.setRulesetType(rulesetType);
            cfgDyncRulesetDao.save(cfgDyncRulesetEntity);
        } else {
            cfgDyncRulesetEntity.setRulesetId(rulesetId);
            cfgDyncRulesetEntity.setDoneDate(new Date());
            cfgDyncRulesetEntity.setOpId(userInfoInterface.getUserId());
            cfgDyncRulesetEntity.setState(1);
            cfgDyncRulesetEntity.setRulesetName(rulesetName);
            cfgDyncRulesetEntity.setRemark(remark);
            cfgDyncRulesetEntity.setRulesetType(rulesetType);
            cfgDyncRulesetDao.updateById(cfgDyncRulesetEntity);

            CfgDyncRulesetRuleEntity cfgDyncRulesetRuleEntity = new CfgDyncRulesetRuleEntity();
            cfgDyncRulesetRuleEntity.setRulesetId(rulesetId);
            cfgDyncRulesetRuleDao.deleteByEntity(cfgDyncRulesetRuleEntity);
        }

        for (int i = 0; i < rules.length; i++) {
            if (!"".equals(rules[i].trim())) {
                String rule[] = rules[i].split(";");
                CfgDyncRulesetRuleEntity cfgDyncRulesetRuleEntity = new CfgDyncRulesetRuleEntity();
                cfgDyncRulesetRuleEntity.setRelatId(cfgDyncRulesetRuleEntity.newId());
                cfgDyncRulesetRuleEntity.setRulesetId(cfgDyncRulesetEntity.getRulesetId());
                cfgDyncRulesetRuleEntity.setRuleId(Long.valueOf(rule[1]));
                cfgDyncRulesetRuleEntity.setRuleTriggerType(rule[0]);
                cfgDyncRulesetRuleEntity.setSortId(i);
                cfgDyncRulesetRuleEntity.setState(1);
                cfgDyncRulesetRuleEntity.setRemark("");
                cfgDyncRulesetRuleEntity.setCreateDate(new Date());
                cfgDyncRulesetRuleEntity.setDoneDate(new Date());
                cfgDyncRulesetRuleEntity.setCreator(userInfoInterface.getUserId());
                cfgDyncRulesetRuleEntity.setOpId(userInfoInterface.getUserId());
                cfgDyncRulesetRuleDao.save(cfgDyncRulesetRuleEntity);
            }
        }
    }

    @Override
    public void saveButton(CfgDyncButtonEntity cfgDyncButtonEntity) throws Exception {

        UserInfoInterface userInfoInterface = SessionManager.getUser();


        if (cfgDyncButtonEntity.getButtonText() == null || "".equals(cfgDyncButtonEntity.getButtonText())) {
            throw new Exception("按钮名称不能为空！");
        }
        if (cfgDyncButtonEntity.getFileType() == null) {
            throw new Exception("文件类型不能为空！");
        }

        if (cfgDyncButtonEntity.getClickFunc() == null || "".equals(cfgDyncButtonEntity.getClickFunc())) {
            throw new Exception("按钮调用方法不能空！");
        }

        if (cfgDyncButtonEntity.getFileType() == 1 && (cfgDyncButtonEntity.getFileName() == null || "".equals(cfgDyncButtonEntity.getFileName()))) {
            throw new Exception("文件名称不能为空！");
        }

        if (cfgDyncButtonEntity.getFileType() == 2 && (cfgDyncButtonEntity.getFileContent() == null || "".equals(cfgDyncButtonEntity.getFileContent()))) {
            throw new Exception("文件内容不能为空！");
        }

        cfgDyncButtonEntity.setState(1);
        if (cfgDyncButtonEntity.getButtonId() == null) {
            cfgDyncButtonEntity.setCreateDate(new Date());
            cfgDyncButtonEntity.setDoneDate(new Date());
            cfgDyncButtonEntity.setCreator(userInfoInterface.getUserId());
            cfgDyncButtonEntity.setOpId(userInfoInterface.getUserId());
            cfgDyncButtonEntity.setButtonId(cfgDyncButtonEntity.newId());
            cfgDyncButtonDao.save(cfgDyncButtonEntity);
        } else {

            CfgDyncButtonEntity cfgDyncButtonEntityQuery = new CfgDyncButtonEntity();
            cfgDyncButtonEntityQuery.setButtonId(cfgDyncButtonEntity.getButtonId());
            List<CfgDyncButtonEntity> cfgDyncButtonEntityList = cfgDyncButtonDao.findByEntity(cfgDyncButtonEntityQuery);
            if (cfgDyncButtonEntityList.size() == 0) {
                cfgDyncButtonEntity.setCreateDate(new Date());
                cfgDyncButtonEntity.setDoneDate(new Date());
                cfgDyncButtonEntity.setCreator(userInfoInterface.getUserId());
                cfgDyncButtonEntity.setOpId(userInfoInterface.getUserId());
                cfgDyncButtonDao.save(cfgDyncButtonEntity);
            } else {
                cfgDyncButtonEntity.setDoneDate(new Date());
                cfgDyncButtonEntity.setOpId(userInfoInterface.getUserId());
                cfgDyncButtonDao.updateById(cfgDyncButtonEntity);
            }

        }
    }

    @Override
    public void saveRule(CfgDyncRuleEntity cfgDyncRuleEntity) throws Exception {
        UserInfoInterface userInfoInterface = SessionManager.getUser();
        if (cfgDyncRuleEntity.getRuleName() == null || "".equals(cfgDyncRuleEntity.getRuleName())) {
            throw new Exception("规则名称不能为空！");
        }
        if (cfgDyncRuleEntity.getFileType() == null) {
            throw new Exception("文件类型不能为空！");
        }

        if (cfgDyncRuleEntity.getFuncName() == null || "".equals(cfgDyncRuleEntity.getFuncName())) {
            throw new Exception("规则调用方法不能空！");
        }

        if (cfgDyncRuleEntity.getRuleType() == null || "".equals(cfgDyncRuleEntity.getRuleType())) {
            throw new Exception("规则类型不能为空！");
        }

        if (cfgDyncRuleEntity.getFileType() == 1 && (cfgDyncRuleEntity.getFileName() == null || "".equals(cfgDyncRuleEntity.getFileName()))) {
            throw new Exception("文件名称不能为空！");
        }

        if (cfgDyncRuleEntity.getFileType() == 2 && (cfgDyncRuleEntity.getFileContent() == null || "".equals(cfgDyncRuleEntity.getFileContent()))) {
            throw new Exception("文件内容不能为空！");
        }

        cfgDyncRuleEntity.setState(1);
        if (cfgDyncRuleEntity.getRuleId() == null) {
            cfgDyncRuleEntity.setCreateDate(new Date());
            cfgDyncRuleEntity.setDoneDate(new Date());
            cfgDyncRuleEntity.setCreator(userInfoInterface.getUserId());
            cfgDyncRuleEntity.setOpId(userInfoInterface.getUserId());
            cfgDyncRuleEntity.setRuleId(cfgDyncRuleEntity.newId());
            cfgDyncRuleDao.save(cfgDyncRuleEntity);
        } else {
            CfgDyncRuleEntity cfgDyncRuleEntityQuery = new CfgDyncRuleEntity();
            cfgDyncRuleEntityQuery.setRuleId(cfgDyncRuleEntity.getRuleId());
            List<CfgDyncRuleEntity> cfgDyncRuleEntityList = cfgDyncRuleDao.findByEntity(cfgDyncRuleEntityQuery);
            if (cfgDyncRuleEntityList.size() == 0) {
                cfgDyncRuleEntity.setCreateDate(new Date());
                cfgDyncRuleEntity.setDoneDate(new Date());
                cfgDyncRuleEntity.setCreator(userInfoInterface.getUserId());
                cfgDyncRuleEntity.setOpId(userInfoInterface.getUserId());
                cfgDyncRuleDao.save(cfgDyncRuleEntity);
            } else {
                cfgDyncRuleEntity.setDoneDate(new Date());
                cfgDyncRuleEntity.setOpId(userInfoInterface.getUserId());
                cfgDyncRuleDao.updateById(cfgDyncRuleEntity);
            }
        }
    }

    @Override
    public List<CfgDyncButtonEntity> getCfgDyncButton(CfgDyncButtonEntity cfgDyncButtonEntity) throws Exception {
        cfgDyncButtonEntity.setState(1);
        return this.cfgDyncButtonDao.findByEntity(cfgDyncButtonEntity);
    }


    @Override
    public List<CfgDyncRuleEntity> getCfgDyncRule(CfgDyncRuleEntity cfgDyncRuleEntity) throws Exception {
        cfgDyncRuleEntity.setState(1);
        return this.cfgDyncRuleDao.findByEntity(cfgDyncRuleEntity);
    }

    /**
     * 查询正则
     *
     * @param cfgDyncRuleExpEntity
     * @return
     */
    @Override
    public List<CfgDyncRuleExpEntity> getCfgDyncRuleExp(CfgDyncRuleExpEntity cfgDyncRuleExpEntity) throws Exception {
        return this.cfgDyncRuleExpDao.findByEntity(cfgDyncRuleExpEntity);
    }

    /**
     * 获取业务配置数据
     *
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getCfgDyncData(Map<String, Object> data) throws Exception {
        if (data.get("busiFrameId") == null) {
            throw new Exception("busiFrameId不能为空！");
        }
        Long busiFrameId = Long.valueOf(data.get("busiFrameId").toString());

        CfgDyncBusiFrameRelEntity cfgDyncBusiFrameRelEntity = getCfgDyncBusiFrameRelEntityByBusiFrameId(busiFrameId);
        CfgDyncFrameEntity cfgDyncFrameEntity = getCfgDyncFrameEntityByBusiFrameId(busiFrameId);
        CfgDyncPageTemplateEntity cfgDyncPageTemplateEntity = getCfgDyncPageTemplateEntityByTemplateId(cfgDyncBusiFrameRelEntity.getPageTemplateId());

        Map<String, Object> frame = new HashMap<String, Object>();
        frame.put("text", cfgDyncBusiFrameRelEntity.getRemark());
        frame.put("type", (cfgDyncPageTemplateEntity.getTemplateType() == 1 ? "wizardPage" : (cfgDyncPageTemplateEntity.getTemplateType() == 2 ? "singlePage" : "tabPage")));
        frame.put("ptype", "root");
        frame.put("index", 0);
        frame.put("expanded", true);
        frame.put("cfgDyncBusiFrameRel", cfgDyncBusiFrameRelEntity);
        frame.put("cfgDyncFrame", cfgDyncFrameEntity);
        frame.put("items", getCfgPageData(cfgDyncFrameEntity.getFrameId()));


        return frame;
    }

    private List<Map<String, Object>> getCfgPageData(Long frameId) throws Exception {
        if (frameId == null) {
            throw new Exception("框架ID[frameId]不能为空！");
        }
        List<Map<String, Object>> pages = new ArrayList<Map<String, Object>>();
        CfgDyncFramePageEntity cfgDyncFramePageEntityQuery = new CfgDyncFramePageEntity();
        cfgDyncFramePageEntityQuery.setFrameId(frameId);
        cfgDyncFramePageEntityQuery.setState(1);
        cfgDyncFramePageEntityQuery.setRank(new Rank("sortId"));
        List<CfgDyncFramePageEntity> cfgDyncFramePageEntityList = cfgDyncFramePageDao.findByEntity(cfgDyncFramePageEntityQuery);
        for (CfgDyncFramePageEntity cfgDyncFramePageEntity : cfgDyncFramePageEntityList) {
            CfgDyncPageEntity cfgDyncPageEntity = null;
            CfgDyncPageEntity cfgDyncPageEntityQuery = new CfgDyncPageEntity();
            cfgDyncPageEntityQuery.setPageId(cfgDyncFramePageEntity.getPageId());
            cfgDyncPageEntityQuery.setState(1);
            List<CfgDyncPageEntity> cfgDyncPageEntityList = cfgDyncPageDao.findByEntity(cfgDyncPageEntityQuery);
            if (cfgDyncPageEntityList.size() == 0) {
                cfgDyncPageEntity = new CfgDyncPageEntity();
            } else {
                cfgDyncPageEntity = cfgDyncPageEntityList.get(0);
            }
            Map<String, Object> page = new HashMap<String, Object>();
            page.put("text", cfgDyncFramePageEntity.getPageTitle());
            page.put("type", "page" + cfgDyncPageEntity.getPageType());
            page.put("ptype", "frame");
            page.put("index", cfgDyncFramePageEntity.getSortId());
            page.put("expanded", true);
            page.put("cfgDyncFramePage", cfgDyncFramePageEntity);
            page.put("cfgDyncPage", cfgDyncPageEntity);
            page.put("items", getCfgAreaData(cfgDyncPageEntity.getPageId()));
            pages.add(page);
        }
        return pages;
    }

    private List<Map<String, Object>> getCfgAreaData(Long pageId) throws Exception {
        if (pageId == null) {
            throw new Exception("页面ID[pageId]不能为空！");
        }
        List<Map<String, Object>> areas = new ArrayList<Map<String, Object>>();
        CfgDyncPageAreaEntity cfgDyncPageAreaEntityQuery = new CfgDyncPageAreaEntity();
        cfgDyncPageAreaEntityQuery.setPageId(pageId);
        cfgDyncPageAreaEntityQuery.setState(1);
        cfgDyncPageAreaEntityQuery.setRank(new Rank("sortId"));
        List<CfgDyncPageAreaEntity> cfgDyncPageAreaEntityList = cfgDyncPageAreaDao.findByEntity(cfgDyncPageAreaEntityQuery);
        for (CfgDyncPageAreaEntity cfgDyncPageAreaEntity : cfgDyncPageAreaEntityList) {

            CfgDyncAreaEntity cfgDyncAreaEntity = null;
            CfgDyncAreaEntity cfgDyncAreaEntityQuery = new CfgDyncAreaEntity();
            cfgDyncAreaEntityQuery.setAreaId(cfgDyncPageAreaEntity.getAreaId());
            cfgDyncAreaEntityQuery.setState(1);
            List<CfgDyncAreaEntity> cfgDyncAreaEntityList = cfgDyncAreaDao.findByEntity(cfgDyncAreaEntityQuery);
            if (cfgDyncAreaEntityList.size() == 0) {
                cfgDyncAreaEntity = new CfgDyncAreaEntity();
            } else {
                cfgDyncAreaEntity = cfgDyncAreaEntityList.get(0);
            }
            Map<String, Object> area = new HashMap<String, Object>();
            area.put("text", cfgDyncAreaEntity.getAreaName());
            area.put("type", "area");
            area.put("ptype", "page");
            area.put("index", cfgDyncPageAreaEntity.getSortId());
            area.put("expanded", true);
            area.put("cfgDyncArea", cfgDyncAreaEntity);
            area.put("cfgDyncPageArea", cfgDyncPageAreaEntity);
            area.put("items", getCfgAttrData(cfgDyncAreaEntity.getAreaId()));
            areas.add(area);
        }
        return areas;
    }

    private List<Map<String, Object>> getCfgAttrData(Long areaId) throws Exception {
        if (areaId == null) {
            throw new Exception("域ID[areaId]不能为空！");
        }
        List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
        CfgDyncAreaAttrEntity cfgDyncAreaAttrEntityQuery = new CfgDyncAreaAttrEntity();
        cfgDyncAreaAttrEntityQuery.setAreaId(areaId);
        cfgDyncAreaAttrEntityQuery.setState(1);
        cfgDyncAreaAttrEntityQuery.setRank(new Rank("sortId"));
        List<CfgDyncAreaAttrEntity> cfgDyncAreaAttrEntityList = cfgDyncAreaAttrDao.findByEntity(cfgDyncAreaAttrEntityQuery);
        for (CfgDyncAreaAttrEntity cfgDyncAreaAttrEntity : cfgDyncAreaAttrEntityList) {

            CfgDyncAttrEntity cfgDyncAttrEntity = null;
            CfgDyncAttrEntity cfgDyncAttrEntityQuery = new CfgDyncAttrEntity();
            cfgDyncAttrEntityQuery.setAttrId(cfgDyncAreaAttrEntity.getAttrId());
            cfgDyncAttrEntityQuery.setState(1);
            List<CfgDyncAttrEntity> cfgDyncAttrEntityList = cfgDyncAttrDao.findByEntity(cfgDyncAttrEntityQuery);
            if (cfgDyncAttrEntityList.size() == 0) {
                cfgDyncAttrEntity = new CfgDyncAttrEntity();
            } else {
                cfgDyncAttrEntity = cfgDyncAttrEntityList.get(0);
            }
            Map<String, Object> attr = new HashMap<String, Object>();
            attr.put("text", cfgDyncAttrEntity.getAttrName());
            attr.put("type", cfgDyncAttrEntity.getEditType());
            attr.put("ptype", "area");
            attr.put("index", cfgDyncAreaAttrEntity.getSortId());
            attr.put("cfgDyncAreaAttr", cfgDyncAreaAttrEntity);
            attr.put("cfgDyncAttr", cfgDyncAttrEntity);
            attrs.add(attr);
        }
        return attrs;
    }


    private CfgDyncPageTemplateEntity getCfgDyncPageTemplateEntityByTemplateId(Long templateId) throws Exception {
        if (templateId == null) {
            throw new Exception("无法识别的模板类型");
        }
        CfgDyncPageTemplateEntity cfgDyncPageTemplateEntityQuery = new CfgDyncPageTemplateEntity();
        cfgDyncPageTemplateEntityQuery.setTemplateId(templateId);
        cfgDyncPageTemplateEntityQuery.setState(1);
        List<CfgDyncPageTemplateEntity> cfgDyncPageTemplateEntityList = cfgDyncPageTemplateDao.findByEntity(cfgDyncPageTemplateEntityQuery);
        if (cfgDyncPageTemplateEntityList.size() == 0) {
            throw new Exception("无法识别的模板类型");
        }
        return cfgDyncPageTemplateEntityList.get(0);
    }


    private CfgDyncFrameEntity getCfgDyncFrameEntityByBusiFrameId(Long busiFrameId) throws Exception {
        CfgDyncFrameEntity cfgDyncFrameEntityQuery = new CfgDyncFrameEntity();
        cfgDyncFrameEntityQuery.setBusiFrameId(busiFrameId);
        List<CfgDyncFrameEntity> cfgDyncFrameEntityList = cfgDyncFrameDao.queryCfgDyncFrame(cfgDyncFrameEntityQuery);

        if (cfgDyncFrameEntityList.size() == 0) {
            throw new Exception("获取数据异常，根据busiFrameId[" + busiFrameId + "]未找到对应的业务配置！");
        }
        return cfgDyncFrameEntityList.get(0);
    }

    private CfgDyncBusiFrameRelEntity getCfgDyncBusiFrameRelEntityByBusiFrameId(Long busiFrameId) throws Exception {
        CfgDyncBusiFrameRelEntity cfgDyncBusiFrameRelEntityQuery = new CfgDyncBusiFrameRelEntity();
        cfgDyncBusiFrameRelEntityQuery.setBusiFrameId(busiFrameId);
        cfgDyncBusiFrameRelEntityQuery.setState(1);
        List<CfgDyncBusiFrameRelEntity> cfgDyncBusiFrameRelEntityList = cfgDyncBusiFrameRelDao.findByEntity(cfgDyncBusiFrameRelEntityQuery);
        cfgDyncBusiFrameRelEntityQuery.setState(2);
        cfgDyncBusiFrameRelEntityList.addAll(cfgDyncBusiFrameRelDao.findByEntity(cfgDyncBusiFrameRelEntityQuery));
        if (cfgDyncBusiFrameRelEntityList.size() == 0) {
            throw new Exception("获取数据异常，根据busiFrameId[" + busiFrameId + "]未找到对应的业务配置！");
        }
        return cfgDyncBusiFrameRelEntityList.get(0);
    }


    /**
     * 保存动态表单配置数据
     *
     * @param data
     * @throws Exception
     */
    @Override
    public Long saveCfgDyncData(Map<String, Object> data) throws Exception {
        UserInfoInterface userInfoInterface = SessionManager.getUser();
        if (userInfoInterface == null) {
            throw new Exception("未获取到用户信息！");
        }
        if (data.get("cfgDyncBusiFrameRel") == null || data.get("cfgDyncFrame") == null) {
            throw new Exception("未找到框架配置数据！");
        }
        CfgDyncBusiFrameRelEntity cfgDyncBusiFrameRelEntity = JsonUtil.changeObject(data.get("cfgDyncBusiFrameRel"), CfgDyncBusiFrameRelEntity.class);
        CfgDyncFrameEntity cfgDyncFrameEntity = JsonUtil.changeObject(data.get("cfgDyncFrame"), CfgDyncFrameEntity.class);
        checkCfgDyncBusiFrameRelEntity(cfgDyncBusiFrameRelEntity);
        checkCfgDyncFrameEntity(cfgDyncFrameEntity);

        boolean deletePage = true;
        //新增框架
        if (cfgDyncBusiFrameRelEntity.getBusiFrameId() == null) {
            //新增 CFG_DYNC_BUSI_FRAME_REL 和 CFG_DYNC_FRAME
            //设置state=2为锁定状态
            initCfgDyncBusiFrameRelEntity(cfgDyncBusiFrameRelEntity, "new", userInfoInterface);
            initCfgDyncFrameEntity(cfgDyncFrameEntity, "new", userInfoInterface);

            cfgDyncBusiFrameRelEntity.setBusiFrameId(cfgDyncBusiFrameRelEntity.newId());
            cfgDyncFrameEntity.setBusiFrameId(cfgDyncBusiFrameRelEntity.getBusiFrameId());
            cfgDyncFrameEntity.setFrameId(cfgDyncFrameEntity.newId());
            cfgDyncFrameEntity.setVersionId(new Date().getTime());

            cfgDyncBusiFrameRelDao.save(cfgDyncBusiFrameRelEntity);
            cfgDyncFrameDao.save(cfgDyncFrameEntity);
        } else {
            //判断CFG_DYNC_BUSI_FRAME_REL是否在锁定状态
            if (cfgDyncBusiFrameRelEntity.getState() == null || cfgDyncBusiFrameRelEntity.getState() != 2) {
                throw new Exception("业务【" + cfgDyncBusiFrameRelEntity.getRemark() + "】未锁定！");
            }
            if (cfgDyncFrameEntity.getFrameId() == null || cfgDyncFrameEntity.getBusiFrameId() != cfgDyncBusiFrameRelEntity.getBusiFrameId()) {
                throw new Exception("数据异常!请校验框架ID和框架业务关联ID");
            }
            //判断CFG_DYNC_FRAME的state是否为1
            //如果为1，则标示是历史的记录需要插入一条新的，如果为2则表示可在当前记录修改
            if (cfgDyncFrameEntity.getState() == null) {
                throw new Exception("数据异常，未找到框架状态！");
            }
            //如果是锁定后修改
            if (cfgDyncFrameEntity.getState() == 1) {
                initCfgDyncFrameEntity(cfgDyncFrameEntity, "new", userInfoInterface);
                cfgDyncFrameEntity.setBusiFrameId(cfgDyncBusiFrameRelEntity.getBusiFrameId());
                cfgDyncFrameEntity.setFrameId(cfgDyncFrameEntity.newId());
                cfgDyncFrameEntity.setVersionId(new Date().getTime());
                cfgDyncFrameDao.save(cfgDyncFrameEntity);
                deletePage = false;
            } else {
                initCfgDyncFrameEntity(cfgDyncFrameEntity, "edit", userInfoInterface);
                cfgDyncFrameDao.updateById(cfgDyncFrameEntity);
            }
            initCfgDyncBusiFrameRelEntity(cfgDyncBusiFrameRelEntity, "edit", userInfoInterface);
            cfgDyncBusiFrameRelDao.updateById(cfgDyncBusiFrameRelEntity);
        }


        List<Map<String, Object>> pageList = (List<Map<String, Object>>) data.get("items");
        if (pageList == null || pageList.size() < 0) {
            throw new Exception("未找到页面数据！");
        }

        //页面处理前先删除 CFG_DYNC_FRAME_PAGE 中的记录
        {
            CfgDyncFramePageEntity temp = new CfgDyncFramePageEntity();
            temp.setFrameId(cfgDyncFrameEntity.getFrameId());
            cfgDyncFramePageDao.deleteByEntity(temp);
        }

        //保存页面数据
        for (Map<String, Object> page : pageList) {
            dealPageData(page, deletePage, cfgDyncFrameEntity, userInfoInterface);
        }

        return cfgDyncBusiFrameRelEntity.getBusiFrameId();
    }

    /**
     * 处理页面数据
     *
     * @param page
     * @param cfgDyncFrameEntity
     * @param userInfoInterface
     * @throws Exception
     */
    private void dealPageData(Map<String, Object> page, Boolean deletePage, CfgDyncFrameEntity cfgDyncFrameEntity, UserInfoInterface userInfoInterface) throws Exception {
        if (page.get("index") == null) {
            throw new Exception("未找到页面序列数据！");
        }
        if (page.get("cfgDyncFramePage") == null || page.get("cfgDyncPage") == null) {
            throw new Exception("未找到页面配置数据！");
        }
        int index = Integer.valueOf(page.get("index").toString());
        CfgDyncFramePageEntity cfgDyncFramePageEntity = JsonUtil.changeObject(page.get("cfgDyncFramePage"), CfgDyncFramePageEntity.class);
        CfgDyncPageEntity cfgDyncPageEntity = JsonUtil.changeObject(page.get("cfgDyncPage"), CfgDyncPageEntity.class);
        checkCfgDyncFramePageEntity(cfgDyncFramePageEntity);
        checkCfgDyncPageEntity(cfgDyncPageEntity);

        cfgDyncFramePageEntity.setRelatId(cfgDyncFramePageEntity.newId());
        cfgDyncFramePageEntity.setFrameId(cfgDyncFrameEntity.getFrameId());
        cfgDyncFramePageEntity.setSortId(index);
        initCfgDyncFramePageEntity(cfgDyncFramePageEntity, userInfoInterface);


        //清除page
        if (cfgDyncPageEntity.getPageId() != null && deletePage == true) {
            //根据pageId清除 CFG_DYNC_PAGE， CFG_DYNC_PAGE_AREA，CFG_DYNC_AREA， CFG_DYNC_AREA_ATTR，CFG_DYNC_ATTR
            deletePateDataByPageId(cfgDyncPageEntity.getPageId());
        }


        Long newPageId = cfgDyncPageEntity.newId();


        //page复用逻辑
        if (page.get("createNewPage") != null && cfgDyncPageEntity.getPageId() != null) {
            String createNewPage = page.get("createNewPage").toString();
            //处理级联Frame的更新
            String busiFrameIds[] = createNewPage.split(",");
            for (int i = 0; i < busiFrameIds.length; i++) {
                if (!"".equals(busiFrameIds[i].trim())) {
                    dealFrameRelateNewPage(userInfoInterface, cfgDyncPageEntity.getPageId(), newPageId, Long.valueOf(busiFrameIds[i].trim()));
                }
            }
        }

        //每次都new出page
        cfgDyncPageEntity.setPageId(newPageId);
        cfgDyncFramePageEntity.setPageId(cfgDyncPageEntity.getPageId());
        cfgDyncFramePageDao.save(cfgDyncFramePageEntity);

        initCfgDyncPageEntity(cfgDyncPageEntity, userInfoInterface);
        cfgDyncPageDao.save(cfgDyncPageEntity);


        String pageType = page.get("type") == null ? "" : page.get("type").toString();
        List<Map<String, Object>> areaList = (List<Map<String, Object>>) page.get("items");

        if (pageType.equals("page1")) {
            if (areaList == null || areaList.size() < 0) {
                throw new Exception("配置页面下必须要存在域！");
            }
            //保存域的数据
            for (Map<String, Object> area : areaList) {
                dealAreaData(area, userInfoInterface, cfgDyncPageEntity);
            }
        }
        if ((pageType.equals("page2") || pageType.equals("page3")) && (areaList != null && areaList.size() > 0)) {
            throw new Exception("非配置页面下不能存在域！");
        }

    }


    private void dealFrameRelateNewPage(UserInfoInterface userInfoInterface, Long oldPageId, Long newPageId, Long busiFrameId) throws Exception {
        //CFG_DYNC_BUSI_FRAME_REL 锁定业务
        CfgDyncBusiFrameRelEntity cfgDyncBusiFrameRelEntity = cfgDyncBusiFrameRelDao.findById(busiFrameId);
        if (cfgDyncBusiFrameRelEntity == null) {
            throw new Exception("关联修改时根据busiFrameId未找到数据！");
        }
        //锁定该业务
        if (cfgDyncBusiFrameRelEntity.getState() != 2) {
            cfgDyncBusiFrameRelEntity.setDoneDate(new Date());
            cfgDyncBusiFrameRelEntity.setOpId(userInfoInterface.getUserId());
            cfgDyncBusiFrameRelEntity.setState(2);
            cfgDyncBusiFrameRelDao.updateById(cfgDyncBusiFrameRelEntity);
        }


        CfgDyncFrameEntity cfgDyncFrameEntityQuery = new CfgDyncFrameEntity();
        cfgDyncFrameEntityQuery.setBusiFrameId(busiFrameId);
        List<CfgDyncFrameEntity> cfgDyncFrameEntityList = cfgDyncFrameDao.queryCfgDyncFrame(cfgDyncFrameEntityQuery);
        if (cfgDyncFrameEntityList.size() == 0) {
            throw new Exception("获取数据异常，根据busiFrameId[" + busiFrameId + "]未找到对应的业务配置！");
        }
        CfgDyncFrameEntity cfgDyncFrameEntity = cfgDyncFrameEntityList.get(0);


        CfgDyncFramePageEntity cfgDyncFramePageEntityQuery = new CfgDyncFramePageEntity();
        cfgDyncFramePageEntityQuery.setPageId(oldPageId);
        cfgDyncFramePageEntityQuery.setFrameId(cfgDyncFrameEntity.getFrameId());
        List<CfgDyncFramePageEntity> cfgDyncFramePageEntityList = cfgDyncFramePageDao.findByEntity(cfgDyncFramePageEntityQuery);
        if (cfgDyncFramePageEntityList.size() == 0) {
            throw new Exception("获取数据异常，根据frameId[" + cfgDyncFrameEntity.getFrameId() + "]和pageId[" + oldPageId + "]未找到对应的业务配置！");
        }
        CfgDyncFramePageEntity cfgDyncFramePageEntity = cfgDyncFramePageEntityList.get(0);


        //未锁定时直接锁定
        if (cfgDyncFrameEntity.getState() == 1) {
            //未锁定直接插入新记录
        } else if (cfgDyncFrameEntity.getState() == 2) {
            //如果已经锁定先删除记录
            cfgDyncFrameDao.deleteById(cfgDyncFrameEntity.getFrameId());
            cfgDyncFramePageDao.deleteById(cfgDyncFramePageEntity.getRelatId());
        }

        //插入新记录
        cfgDyncFrameEntity.setState(2);
        cfgDyncFrameEntity.setFrameId(cfgDyncFrameEntity.newId());
        cfgDyncFrameEntity.setVersionId(new Date().getTime());
        cfgDyncFrameDao.save(cfgDyncFrameEntity);

        //CFG_DYNC_FRAME_PAGE插入一条新记录
        cfgDyncFramePageEntity.setPageId(newPageId);
        cfgDyncFramePageEntity.setFrameId(cfgDyncFrameEntity.getFrameId());
        cfgDyncFramePageEntity.setRelatId(cfgDyncFramePageEntity.newId());
        cfgDyncFramePageEntity.setDoneDate(new Date());
        cfgDyncFramePageEntity.setCreateDate(new Date());
        cfgDyncFramePageEntity.setCreator(userInfoInterface.getUserId());
        cfgDyncFramePageEntity.setOpId(userInfoInterface.getUserId());
        cfgDyncFramePageDao.save(cfgDyncFramePageEntity);
    }


    /**
     * 处理域数据
     *
     * @param area
     * @param userInfoInterface
     * @throws Exception
     */
    private void dealAreaData(Map<String, Object> area, UserInfoInterface userInfoInterface, CfgDyncPageEntity cfgDyncPageEntity) throws Exception {
        if (area.get("index") == null) {
            throw new Exception("未找到域序列数据！");
        }
        if (area.get("cfgDyncPageArea") == null || area.get("cfgDyncArea") == null) {
            throw new Exception("未找到域配置数据！");
        }
        CfgDyncPageAreaEntity cfgDyncPageAreaEntity = JsonUtil.changeObject(area.get("cfgDyncPageArea"), CfgDyncPageAreaEntity.class);
        CfgDyncAreaEntity cfgDyncAreaEntity = JsonUtil.changeObject(area.get("cfgDyncArea"), CfgDyncAreaEntity.class);
        checkCfgDyncPageAreaEntity(cfgDyncPageAreaEntity);
        checkCfgDyncAreaEntity(cfgDyncAreaEntity);

        cfgDyncAreaEntity.setAreaId(cfgDyncAreaEntity.newId());
        cfgDyncPageAreaEntity.setRelatId(cfgDyncPageAreaEntity.newId());

        cfgDyncPageAreaEntity.setAreaId(cfgDyncAreaEntity.getAreaId());
        cfgDyncPageAreaEntity.setPageId(cfgDyncPageEntity.getPageId());
        cfgDyncPageAreaEntity.setSortId(Integer.valueOf("" + area.get("index")));

        initCfgDyncPageAreaEntity(cfgDyncPageAreaEntity, userInfoInterface);
        cfgDyncPageAreaDao.save(cfgDyncPageAreaEntity);

        initCfgDyncAreaEntity(cfgDyncAreaEntity, userInfoInterface);
        cfgDyncAreaDao.save(cfgDyncAreaEntity);


        List<Map<String, Object>> attrList = (List<Map<String, Object>>) area.get("items");
        if (attrList == null || attrList.size() < 0) {
            throw new Exception("页面下面必须要存在域！");
        }
        //保存域的数据
        for (Map<String, Object> attr : attrList) {
            dealAttrData(attr, userInfoInterface, cfgDyncAreaEntity);
        }
    }


    private void dealAttrData(Map<String, Object> attr, UserInfoInterface userInfoInterface, CfgDyncAreaEntity cfgDyncAreaEntity) throws Exception {
        if (attr.get("index") == null) {
            throw new Exception("未找到域序列数据！");
        }
        if (attr.get("cfgDyncAreaAttr") == null || attr.get("cfgDyncAttr") == null) {
            throw new Exception("未找到域配置数据！");
        }
        CfgDyncAreaAttrEntity cfgDyncAreaAttrEntity = JsonUtil.changeObject(attr.get("cfgDyncAreaAttr"), CfgDyncAreaAttrEntity.class);
        CfgDyncAttrEntity cfgDyncAttrEntity = JsonUtil.changeObject(attr.get("cfgDyncAttr"), CfgDyncAttrEntity.class);
        checkCfgDyncAreaAttrEntity(cfgDyncAreaAttrEntity);
        checkCfgDyncAttrEntity(cfgDyncAttrEntity);

        cfgDyncAttrEntity.setAttrId(cfgDyncAttrEntity.newId());
        cfgDyncAreaAttrEntity.setRelatId(cfgDyncAreaAttrEntity.newId());

        cfgDyncAreaAttrEntity.setAreaId(cfgDyncAreaEntity.getAreaId());
        cfgDyncAreaAttrEntity.setAttrId(cfgDyncAttrEntity.getAttrId());
        cfgDyncAreaAttrEntity.setSortId(Integer.valueOf("" + attr.get("index")));

        initCfgDyncAreaAttrEntity(cfgDyncAreaAttrEntity, userInfoInterface);
        cfgDyncAreaAttrDao.save(cfgDyncAreaAttrEntity);

        initCfgDyncAttrEntity(cfgDyncAttrEntity, userInfoInterface);
        cfgDyncAttrDao.save(cfgDyncAttrEntity);
    }


    private void deletePateDataByPageId(Long pageId) throws Exception {
        if (pageId == null) {
            throw new Exception("页面ID为空！");
        }
        cfgDyncAttrDao.deleteByPageId(pageId);
        cfgDyncAreaAttrDao.deleteByPageId(pageId);
        cfgDyncAreaDao.deleteByPageId(pageId);
        cfgDyncPageAreaDao.deleteByPageId(pageId);
        cfgDyncPageDao.deleteByPageId(pageId);
    }

    private void initCfgDyncAttrEntity(CfgDyncAttrEntity cfgDyncAttrEntity, UserInfoInterface userInfoInterface) {
        cfgDyncAttrEntity.setState(1);
        if (cfgDyncAttrEntity.getCreateDate() == null) {
            cfgDyncAttrEntity.setCreateDate(new Date());
        }
        if (cfgDyncAttrEntity.getCreator() == null) {
            cfgDyncAttrEntity.setCreator(userInfoInterface.getUserId());
        }
        cfgDyncAttrEntity.setDoneDate(new Date());
        cfgDyncAttrEntity.setOpId(userInfoInterface.getUserId());
    }

    private void initCfgDyncAreaAttrEntity(CfgDyncAreaAttrEntity cfgDyncAreaAttrEntity, UserInfoInterface userInfoInterface) {
        cfgDyncAreaAttrEntity.setState(1);
        if (cfgDyncAreaAttrEntity.getCreateDate() == null) {
            cfgDyncAreaAttrEntity.setCreateDate(new Date());
        }
        if (cfgDyncAreaAttrEntity.getCreator() == null) {
            cfgDyncAreaAttrEntity.setCreator(userInfoInterface.getUserId());
        }
        cfgDyncAreaAttrEntity.setDoneDate(new Date());
        cfgDyncAreaAttrEntity.setOpId(userInfoInterface.getUserId());
    }


    private void initCfgDyncAreaEntity(CfgDyncAreaEntity cfgDyncAreaEntity, UserInfoInterface userInfoInterface) {
        cfgDyncAreaEntity.setState(1);
        if (cfgDyncAreaEntity.getCreateDate() == null) {
            cfgDyncAreaEntity.setCreateDate(new Date());
        }
        if (cfgDyncAreaEntity.getCreator() == null) {
            cfgDyncAreaEntity.setCreator(userInfoInterface.getUserId());
        }
        cfgDyncAreaEntity.setDoneDate(new Date());
        cfgDyncAreaEntity.setOpId(userInfoInterface.getUserId());
    }

    private void initCfgDyncPageAreaEntity(CfgDyncPageAreaEntity cfgDyncPageAreaEntity, UserInfoInterface userInfoInterface) {
        cfgDyncPageAreaEntity.setState(1);
        if (cfgDyncPageAreaEntity.getCreateDate() == null) {
            cfgDyncPageAreaEntity.setCreateDate(new Date());
        }
        if (cfgDyncPageAreaEntity.getCreator() == null) {
            cfgDyncPageAreaEntity.setCreator(userInfoInterface.getUserId());
        }
        cfgDyncPageAreaEntity.setDoneDate(new Date());
        cfgDyncPageAreaEntity.setOpId(userInfoInterface.getUserId());
    }

    private void initCfgDyncPageEntity(CfgDyncPageEntity cfgDyncPageEntity, UserInfoInterface userInfoInterface) {
        cfgDyncPageEntity.setState(1);
        if (cfgDyncPageEntity.getCreateDate() == null) {
            cfgDyncPageEntity.setCreateDate(new Date());
        }
        if (cfgDyncPageEntity.getCreator() == null) {
            cfgDyncPageEntity.setCreator(userInfoInterface.getUserId());
        }
        cfgDyncPageEntity.setDoneDate(new Date());
        cfgDyncPageEntity.setOpId(userInfoInterface.getUserId());
    }

    private void initCfgDyncBusiFrameRelEntity(CfgDyncBusiFrameRelEntity cfgDyncBusiFrameRelEntity, String type, UserInfoInterface userInfoInterface) {
        if (type.equals("new")) {
            cfgDyncBusiFrameRelEntity.setCreateDate(new Date());
            cfgDyncBusiFrameRelEntity.setDoneDate(new Date());
            cfgDyncBusiFrameRelEntity.setState(2);
            cfgDyncBusiFrameRelEntity.setCreator(userInfoInterface.getUserId());
            cfgDyncBusiFrameRelEntity.setOpId(userInfoInterface.getUserId());
        } else {
            cfgDyncBusiFrameRelEntity.setDoneDate(new Date());
            cfgDyncBusiFrameRelEntity.setOpId(userInfoInterface.getUserId());
        }
    }

    private void initCfgDyncFrameEntity(CfgDyncFrameEntity cfgDyncFrameEntity, String type, UserInfoInterface userInfoInterface) {
        if (type.equals("new")) {
            cfgDyncFrameEntity.setState(2);
            cfgDyncFrameEntity.setCreateDate(new Date());
            cfgDyncFrameEntity.setDoneDate(new Date());
            cfgDyncFrameEntity.setCreator(userInfoInterface.getUserId());
            cfgDyncFrameEntity.setOpId(userInfoInterface.getUserId());
        } else {
            cfgDyncFrameEntity.setDoneDate(new Date());
            cfgDyncFrameEntity.setOpId(userInfoInterface.getUserId());
        }
    }

    private void initCfgDyncFramePageEntity(CfgDyncFramePageEntity cfgDyncFramePageEntity, UserInfoInterface userInfoInterface) {
        cfgDyncFramePageEntity.setState(1);
        if (cfgDyncFramePageEntity.getCreateDate() == null) {
            cfgDyncFramePageEntity.setCreateDate(new Date());
        }
        if (cfgDyncFramePageEntity.getCreator() == null) {
            cfgDyncFramePageEntity.setCreator(userInfoInterface.getUserId());
        }
        cfgDyncFramePageEntity.setDoneDate(new Date());
        cfgDyncFramePageEntity.setOpId(userInfoInterface.getUserId());
    }


    private void checkCfgDyncAreaAttrEntity(CfgDyncAreaAttrEntity cfgDyncAreaAttrEntity) throws Exception {
        if (cfgDyncAreaAttrEntity.getRowSpan() == null) {
            throw new Exception("属性占的行数不能为空！");
        }
        if (cfgDyncAreaAttrEntity.getColSpan() == null) {
            throw new Exception("属性占的列数不能为空！");
        }
        if (cfgDyncAreaAttrEntity.getIsNullable() == null) {
            throw new Exception("属性是否可为null不能为空！");
        }
        if (cfgDyncAreaAttrEntity.getIsEditable() == null) {
            throw new Exception("属性是否可编辑不能为空！");
        }
        if (cfgDyncAreaAttrEntity.getIsVisible() == null) {
            throw new Exception("属性是否可见不能为空！");
        }
    }

    private void checkCfgDyncAttrEntity(CfgDyncAttrEntity cfgDyncAttrEntity) throws Exception {
        if (cfgDyncAttrEntity.getAttrName() == null) {
            throw new Exception("属性名称不能为空！");
        }
        if (cfgDyncAttrEntity.getAttrCode() == null) {
            throw new Exception("属性编码不能为空！");
        }
        if (cfgDyncAttrEntity.getEditType() == null) {
            throw new Exception("属性类型不能为空！");
        }
    }


    private void checkCfgDyncAreaEntity(CfgDyncAreaEntity cfgDyncAreaEntity) throws Exception {
        if (cfgDyncAreaEntity.getAreaCode() == null) {
            throw new Exception("域编码不能为空！");
        }
        if (cfgDyncAreaEntity.getAreaType() == null) {
            throw new Exception("域类型不能为空！");
        }
        if (cfgDyncAreaEntity.getAreaName() == null) {
            throw new Exception("域名称不能为空！");
        }
    }

    private void checkCfgDyncPageAreaEntity(CfgDyncPageAreaEntity cfgDyncPageAreaEntity) throws Exception {
        if (cfgDyncPageAreaEntity.getIsShowTitle() == null) {
            throw new Exception("域是否显示标题不能为空！");
        }
        if (cfgDyncPageAreaEntity.getIsEditable() == null) {
            throw new Exception("域是否可编辑不能为空！");
        }
        if (cfgDyncPageAreaEntity.getIsDisplay() == null) {
            throw new Exception("域是否显示不能为空！");
        }
    }

    private void checkCfgDyncPageEntity(CfgDyncPageEntity cfgDyncPageEntity) throws Exception {
        if (cfgDyncPageEntity.getPageName() == null) {
            throw new Exception("页面名称不能为空！");
        }
        if (cfgDyncPageEntity.getPageType() == null) {
            throw new Exception("页面类型不能为空！");
        }
    }

    private void checkCfgDyncFramePageEntity(CfgDyncFramePageEntity cfgDyncFramePageEntity) throws Exception {
        if (cfgDyncFramePageEntity.getPageTitle() == null) {
            throw new Exception("页面标题不能为空！");
        }
        if (cfgDyncFramePageEntity.getPageCode() == null) {
            throw new Exception("页面编码不能为空！");
        }
        if (cfgDyncFramePageEntity.getIsDisplay() == null) {
            throw new Exception("页面是否显示不能为空！");
        }
    }

    private void checkCfgDyncBusiFrameRelEntity(CfgDyncBusiFrameRelEntity cfgDyncBusiFrameRelEntity) throws Exception {

        if (cfgDyncBusiFrameRelEntity.getPageTemplateId() == null) {
            throw new Exception("框架模板编号为空！");
        }
        if (cfgDyncBusiFrameRelEntity.getBusiId() == null) {
            throw new Exception("框架业务编码为空！");
        }
        if (cfgDyncBusiFrameRelEntity.getOperateId() == null) {
            throw new Exception("框架操作编号为空！");
        }
        if (cfgDyncBusiFrameRelEntity.getModuleId() == null || (cfgDyncBusiFrameRelEntity.getModuleId() != 1 && cfgDyncBusiFrameRelEntity.getModuleId() != 2)) {
            throw new Exception("框架数据操作来源不为手机也不为PC端！");
        }
    }

    private void checkCfgDyncFrameEntity(CfgDyncFrameEntity cfgDyncFrameEntity) throws Exception {
        if (cfgDyncFrameEntity.getVersion() == null || "".equals(cfgDyncFrameEntity.getVersion())) {
            throw new Exception("框架版本号不嫩为空！");
        }
    }


    /**
     * 查询已配置的业务数据
     *
     * @param queryParam
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> queryCfgDyncBusiData(Map<String, Object> queryParam, Page page) throws Exception {
        return cfgDyncBusiFrameRelDao.queryCfgDyncBusiData(queryParam, page);
    }


    /**
     * 启用业务
     *
     * @param busiFrameId
     * @throws Exception
     */
    @Override
    public void startBusi(Long busiFrameId) throws Exception {
        UserInfoInterface userInfoInterface = SessionManager.getUser();
        CfgDyncBusiFrameRelEntity cfgDyncBusiFrameRelEntity = new CfgDyncBusiFrameRelEntity();
        cfgDyncBusiFrameRelEntity.setState(1);
        cfgDyncBusiFrameRelEntity.setBusiFrameId(busiFrameId);
        cfgDyncBusiFrameRelEntity.setOpId(userInfoInterface.getUserId());
        cfgDyncBusiFrameRelEntity.setDoneDate(new Date());
        cfgDyncBusiFrameRelDao.updateById(cfgDyncBusiFrameRelEntity);


        CfgDyncFrameEntity cfgDyncFrameEntity = new CfgDyncFrameEntity();
        cfgDyncFrameEntity.setState(1);
        cfgDyncFrameEntity.setOpId(userInfoInterface.getUserId());
        cfgDyncFrameEntity.setBusiFrameId(busiFrameId);
        cfgDyncFrameDao.startBusi(cfgDyncFrameEntity);

    }

    /**
     * 锁定业务
     *
     * @param busiFrameId
     * @throws Exception
     */
    @Override
    public void lockBusi(Long busiFrameId) throws Exception {
        UserInfoInterface userInfoInterface = SessionManager.getUser();
        CfgDyncBusiFrameRelEntity cfgDyncBusiFrameRelEntity = new CfgDyncBusiFrameRelEntity();
        cfgDyncBusiFrameRelEntity.setState(2);
        cfgDyncBusiFrameRelEntity.setBusiFrameId(busiFrameId);
        cfgDyncBusiFrameRelEntity.setOpId(userInfoInterface.getUserId());
        cfgDyncBusiFrameRelEntity.setDoneDate(new Date());
        cfgDyncBusiFrameRelDao.updateById(cfgDyncBusiFrameRelEntity);
    }


    /**
     * 获取页面和框架之间的复用关系
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> queryPageFrameRelation(Map<String, Object> param) throws Exception {
        if (param.get("pageIds") == null || "".equals(param.get("pageIds").toString())) {
            throw new Exception("未找到pageIds！");
        }
        String pageIds[] = param.get("pageIds").toString().split(",");
        List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
        for (String pageId : pageIds) {
            if (pageId != null && !pageId.equals("")) {
                Map<String, Object> para = new HashMap<String, Object>();
                para.put("pageId", pageId);
                links.addAll(cfgDyncFrameDao.queryPageFrameRelation(para));
            }
        }
        Map<String, Object> reMap = new HashMap<String, Object>();
        reMap.put("links", links);
        return reMap;
    }

    /**
     * 查询已配置的按钮集
     *
     * @param queryParam
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> queryCfgButtonsetData(Map<String, Object> queryParam, Page page) throws Exception {
        return cfgDyncButtonsetDao.queryCfgButtonsetData(queryParam, page);
    }

    @Override
    public List<Map<String, Object>> queryCfgRulesetData(Map<String, Object> queryParam, Page page) throws Exception {
        return cfgDyncRulesetDao.queryCfgRulesetData(queryParam, page);
    }

    @Override
    public void delCfgDyncButton(Long buttonId) throws Exception {
        UserInfoInterface userInfoInterface = SessionManager.getUser();
        CfgDyncButtonEntity cfgDyncButtonEntity = new CfgDyncButtonEntity();
        cfgDyncButtonEntity.setButtonId(buttonId);
        cfgDyncButtonEntity.setState(0);
        cfgDyncButtonEntity.setOpId(userInfoInterface.getUserId());
        cfgDyncButtonEntity.setDoneDate(new Date());
        cfgDyncButtonDao.updateById(cfgDyncButtonEntity);
    }

    @Override
    public void delButtonset(Long buttonsetId) throws Exception {
        UserInfoInterface userInfoInterface = SessionManager.getUser();
        CfgDyncButtonsetEntity cfgDyncButtonsetEntity = new CfgDyncButtonsetEntity();
        cfgDyncButtonsetEntity.setButtonsetId(buttonsetId);
        cfgDyncButtonsetEntity.setState(0);
        cfgDyncButtonsetEntity.setOpId(userInfoInterface.getUserId());
        cfgDyncButtonsetEntity.setDoneDate(new Date());
        cfgDyncButtonsetDao.updateById(cfgDyncButtonsetEntity);
    }

    @Override
    public void delRuleset(Long rulesetId) throws Exception {
        UserInfoInterface userInfoInterface = SessionManager.getUser();
        CfgDyncRulesetEntity cfgDyncRulesetEntity = new CfgDyncRulesetEntity();
        cfgDyncRulesetEntity.setRulesetId(rulesetId);
        cfgDyncRulesetEntity.setState(0);
        cfgDyncRulesetEntity.setOpId(userInfoInterface.getUserId());
        cfgDyncRulesetEntity.setDoneDate(new Date());
        cfgDyncRulesetDao.updateById(cfgDyncRulesetEntity);
    }

    @Override
    public void delCfgDyncRule(Long ruleId) throws Exception {
        UserInfoInterface userInfoInterface = SessionManager.getUser();
        CfgDyncRuleEntity cfgDyncRuleEntity = new CfgDyncRuleEntity();
        cfgDyncRuleEntity.setRuleId(ruleId);
        cfgDyncRuleEntity.setState(0);
        cfgDyncRuleEntity.setOpId(userInfoInterface.getUserId());
        cfgDyncRuleEntity.setDoneDate(new Date());
        cfgDyncRuleDao.updateById(cfgDyncRuleEntity);
    }
}