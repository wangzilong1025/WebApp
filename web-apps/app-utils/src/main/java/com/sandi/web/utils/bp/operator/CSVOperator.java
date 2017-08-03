/**
 * $Id: CSVOperator.java,v 1.0 17/2/16 下午2:36 zhangruiping Exp $
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.utils.bp.operator;

import com.sandi.web.utils.bp.entity.CfgBpTemplate;
import com.sandi.web.utils.bp.entity.CfgWriter;
import com.sandi.web.utils.bp.entity.EsBpData;
import com.sandi.web.utils.elec.ElecUitl;
import com.sandi.web.utils.elec.entity.ElecInst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangruiping
 * @version $Id: CSVOperator.java,v 1.1 17/2/16 下午2:36 zhangruiping Exp $
 *          Created on 17/2/16 下午2:36
 */
public class CSVOperator extends FileOperator {

    private static final Logger logger = LoggerFactory.getLogger(CSVOperator.class);

    /**
     * 进行文件解析
     * excel： sheet row col
     * txt     0     row col
     * cvs     0     row col
     *
     * @param cfgBpTemplateEntity
     * @param esBpDataEntity
     * @param elecInst
     * @return
     * @throws Exception
     */
    @Override
    public List<List<List<String>>> doAnalyse(CfgBpTemplate cfgBpTemplateEntity, EsBpData esBpDataEntity, ElecInst elecInst) throws Exception {

        String colSplit = ",";
        String fileEncode = "utf-8";
        if (cfgBpTemplateEntity.getFileEncode() != null) {
            fileEncode = cfgBpTemplateEntity.getFileEncode();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ElecUitl.download(elecInst, outputStream);
        } catch (Exception e) {
            logger.error("文件下载失败！", e);
            throw new Exception("文件解析时下载文件失败！");
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, fileEncode));

        String line = null;
        List<List<String>> rowList = new ArrayList<List<String>>();
        while ((line = bufferedReader.readLine()) != null) {
            List<String> colList = new ArrayList<String>();
            String cols[] = line.split(colSplit);
            for (int j = 0; j < cols.length; j++) {
                colList.add(cols[j]);
            }
            rowList.add(colList);
        }
        List<List<List<String>>> file = new ArrayList<List<List<String>>>();
        file.add(rowList);
        return file;
    }


    /**
     * 数据生成文件
     *
     * @param headers
     * @param data
     * @param outputStream
     * @param cfgWriter
     * @throws Exception
     */
    @Override
    public void doWrite(List<List<List<String>>> headers, List<List<List<String>>> data, OutputStream outputStream, CfgWriter cfgWriter) throws Exception {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, cfgWriter.getFileEncode() == null ? "gbk" : cfgWriter.getFileEncode()));
        String colSplit = ",";
        StringBuilder line = null;

        for(int i=0;i<headers.size();i++){
            List<List<String>> header =(headers != null && headers.size() > i) ? headers.get(i) : null;
            List<List<String>> sheet = (data != null && data.size() > i) ? data.get(i) : null;

            if(header != null){
                for(List<String> row : header) {
                    line = new StringBuilder("");
                    for (String col : row) {
                        line.append(col);
                        line.append(colSplit);
                    }
                    if (line.length() > 0) {
                        line.substring(0, line.length() - colSplit.length());
                    }
                    if (line.length() > 0) {
                        bufferedWriter.write(line.toString());
                        bufferedWriter.newLine();
                    }
                }
            }

            if(sheet != null){
                for(List<String> row : sheet) {
                    line = new StringBuilder("");
                    for (String col : row) {
                        line.append(col);
                        line.append(colSplit);
                    }
                    if (line.length() > 0) {
                        line.substring(0, line.length() - colSplit.length());
                    }
                    if (line.length() > 0) {
                        bufferedWriter.write(line.toString());
                        bufferedWriter.newLine();
                    }
                }
            }
        }
    }
}