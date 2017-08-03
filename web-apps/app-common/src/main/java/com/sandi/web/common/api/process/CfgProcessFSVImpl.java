package com.sandi.web.common.api.process;

import com.alibaba.dubbo.config.annotation.Service;
import com.sandi.web.common.process.service.interfaces.ICfgProcessDefineSV;
import com.sandi.web.common.process.service.interfaces.ICfgProcessRuleSV;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.utils.api.process.ICfgProcessFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.response.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dizl on 2017/2/21.
 */
@Service
public class CfgProcessFSVImpl implements ICfgProcessFSV {
    private static final Logger logger = Logger.getLogger(CfgProcessFSVImpl.class);
    @Autowired
    private ICfgProcessDefineSV processDefineSV;
    @Autowired
    private ICfgProcessRuleSV processRuleSV;

    @Override
    /**
     * 查询满足条件的工作流配置数据
     * */
    public String queryProcessInfo(String params) {
        Response response = new Response();
        try{
            Map map = JsonUtil.json2Map(params);
            if(map.containsKey("startDate")){
                map.put("startDate", DateUtils.parseDate(map.get("startDate")));
            }
            if(map.containsKey("endDate")){
                map.put("endDate", DateUtils.parseDate(map.get("endDate")));
            }
            Map retMap = new HashMap();
            retMap.put("list",processDefineSV.queryProcessInfo(map));
            response.setData(retMap);
            response.setCode(Response.SUCCESS);
        }catch (Exception e){
            logger.error(e);
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }

    /**
     * 查询有效的流程数据
     *
     * @param params
     */
    @Override
    public String queryValidProcessInfo(String params) {
        Response response = new Response();
        try{
            Map map = JsonUtil.json2Map(params);
            Map retMap = new HashMap();
            retMap.put("list",processDefineSV.queryValidProcessInfo(map));
            response.setData(retMap);
            response.setCode(Response.SUCCESS);
        }catch (Exception e){
            logger.error(e);
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }

    /**
     * 新增或变更流程配置信息
     *
     * @param params
     */
    @Override
    public String editProcess(String params) {
        Response response = new Response();
        try{
            Map param = JsonUtil.json2Map(params);
            if(param!=null && param.containsKey("operType")){
                String operType = param.get("operType").toString();
                if(StringUtils.equalsIgnoreCase("new",operType)){//新建流程
                    String data = param.get("data").toString();
                    System.out.println(data);
                    processDefineSV.createProcess(data);
                }else if(StringUtils.equalsIgnoreCase("use",operType)){//启用流程
                    long processId = Long.parseLong(String.valueOf(param.get("processId")));
                    processDefineSV.useProcess(processId);
                }
                else if(StringUtils.equalsIgnoreCase("update",operType)){//修改流程
                    long processId = Long.parseLong(String.valueOf(param.get("processId")));
                    String data = param.get("data").toString();
                    processDefineSV.updateProcess(processId,data);
                }else if(StringUtils.equalsIgnoreCase("delete",operType)){//删除流程
                    long processId = Long.parseLong(String.valueOf(param.get("processId")));
                    processDefineSV.deleteProcess(processId);
                }
                response.setCode(Response.SUCCESS);
            }else{
                throw new Exception("请求参数为空");
            }
        }catch (Exception e){
            logger.error(e);
            e.printStackTrace();
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }

    /**
     * 查询流程规则
     *
     * @param params
     */
    @Override
    public String queryProcessRule(String params) {
        Response response = new Response();
        try{
            Map map = JsonUtil.json2Map(params);
            Map retMap = new HashMap();
            retMap.put("list",processRuleSV.getRuleEntityList(map));
            response.setData(retMap);
            response.setCode(Response.SUCCESS);
        }catch (Exception e){
            logger.error(e);
            response.setCode(Response.ERROR);
            response.setMessage(e.getMessage());
        }
        return response.toString();
    }
}
