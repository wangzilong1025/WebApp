/**
 * $Id: BpFileAnlyseJob.java,v 1.0 17/2/16 上午11:22 zhangruiping Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.bp.job;

import com.sandi.web.common.bp.Constants;
import com.sandi.web.common.bp.entity.EsBpDataEntity;
import com.sandi.web.common.bp.service.interfaces.IEsBpDataSV;
import com.sandi.web.common.persistence.entity.Rank;
import com.sandi.web.common.quartz.DefaultQuartzJob;
import com.sandi.web.common.utils.CommConstants;
import com.sandi.web.utils.common.SpringContextHolder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author zhangruiping
 * @version $Id: BpFileAnlyseJob.java,v 1.1 17/2/16 上午11:22 zhangruiping Exp $
 *          Created on 17/2/16 上午11:22
 */
public class BpFileAnalyseJob extends DefaultQuartzJob {

    private static final Logger logger = LoggerFactory.getLogger(BpFileAnalyseJob.class);

    @Override
    protected void doJob(JobExecutionContext jobContext) throws JobExecutionException {
        try {
            IEsBpDataSV esBpDataSV = SpringContextHolder.getBean(IEsBpDataSV.class);

            EsBpDataEntity esBpDataEntityQuery = new EsBpDataEntity();
            esBpDataEntityQuery.setState(Constants.FileState.WAIT_ANALYSE);
            Rank[] ranks = new Rank[2];
            ranks[0] = new Rank("priority");
            ranks[0].setRankType(CommConstants.OrderType.DESC);
            ranks[1] = new Rank("createDate");
            esBpDataEntityQuery.setRanks(ranks);
            List<EsBpDataEntity> esBpDataEntityList = esBpDataSV.queryEsBpData(esBpDataEntityQuery);
            esBpDataSV.analyseFiles(esBpDataEntityList);

        } catch (Exception e) {
            logger.error("批量业务，文件解析，任务处理失败！", e);
            throw new JobExecutionException(e);
        }
    }
}
