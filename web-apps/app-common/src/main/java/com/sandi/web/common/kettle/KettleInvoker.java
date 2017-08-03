/**
 * $Id: KettleInvoker.java,v 1.0 2015/11/20 10:10 09:55:18 zhangrp Exp $
 * <p/>
 * Copyright 2015 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.kettle;

import com.sandi.web.utils.config.Global;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.filerep.KettleFileRepository;
import org.pentaho.di.repository.filerep.KettleFileRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * @author zhangrp
 * @version $Id: KettleInvoker.java,v 1.1 2015/11/20 10:10 zhangrp Exp $
 *          Created on 2015/11/20 10:10
 */
public class KettleInvoker {

    private static final Logger logger = LoggerFactory.getLogger(KettleInvoker.class);
    private static KettleFileRepository kettleFileRepository = null;

    static {
        try {
            KettleEnvironment.init();
            KettleFileRepositoryMeta fileMeta = new KettleFileRepositoryMeta();
            fileMeta.setBaseDirectory(Global.getConfig("kettle.repository"));
            kettleFileRepository = new KettleFileRepository();
            kettleFileRepository.init(fileMeta);
            kettleFileRepository.connect("admin", "");
        } catch (Exception e) {
            logger.error("Kettle初始化失败！");
        }
    }

    public static void invoker(String fileName, Map<String, String> param) throws Exception {
        String path = "/";
        if (fileName.indexOf("/") > 0) {
            path = fileName.substring(0, path.lastIndexOf("/") + 1);
            fileName = fileName.substring(path.lastIndexOf("/") + 1);
        }
        if (!fileName.endsWith(".ktr") && !fileName.endsWith(".kjb")) {
            throw new Exception("调用失败！文件格式不符合规范，只能是ktr和kjb！");
        }
        RepositoryDirectoryInterface directory = kettleFileRepository.loadRepositoryDirectoryTree();
        directory = directory.findDirectory(path);

        if (fileName.endsWith(".ktr")) {
            fileName = fileName.substring(0, fileName.length() - 4);
            TransMeta transMeta = kettleFileRepository.loadTransformation(fileName, directory, null, true, null);
            //TransMeta transMeta = kettleFileRepository.loadTransformation(kettleFileRepository.getTransformationID(fileName, directory), null);
            Trans trans = new Trans(transMeta);
            trans.setLogLevel(LogLevel.DETAILED);
            if (param != null) {
                Set<String> keySet = param.keySet();
                Iterator<String> keyIterable = keySet.iterator();
                while (keyIterable.hasNext()) {
                    String key = keyIterable.next();
                    String value = param.get(key);
                    trans.setParameterValue(key, value);
                }
            }
            trans.execute(new String[0]);
            trans.waitUntilFinished();
            if (trans.getErrors() > 0) {
                logger.error("执行失败！");
                throw new Exception(KettleLogStore.getInstance().getAppender().dump());
            }
        } else if (fileName.endsWith(".kjb")) {
            fileName = fileName.substring(0, fileName.length() - 4);

            JobMeta jobMeta = kettleFileRepository.loadJob(fileName, directory, null, null);
            Job job = new Job(kettleFileRepository, jobMeta);
            job.setLogLevel(LogLevel.DETAILED);

            if (param != null) {
                Set<String> keySet = param.keySet();
                Iterator<String> keyIterable = keySet.iterator();
                while (keyIterable.hasNext()) {
                    String key = keyIterable.next();
                    String value = param.get(key);
                    job.setParameterValue(key, value);
                }
            }
            job.start();
            job.waitUntilFinished();// 等待直到数据结束
            if (job.getErrors() > 0) {
                logger.error("执行失败！");
                throw new Exception(KettleLogStore.getInstance().getAppender().dump());
            }
        }
    }


    @Override
    protected void finalize() throws Throwable {
        if (kettleFileRepository != null) {
            kettleFileRepository.disconnect();
        }
        super.finalize();
    }
}
