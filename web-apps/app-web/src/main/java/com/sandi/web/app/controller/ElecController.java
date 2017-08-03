/**
 * $Id: ElecController.java,v 1.0 16/9/12 上午10:20 zhangruiping Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.app.controller;

import com.sandi.web.app.dubbo.DubboManage;
import com.sandi.web.utils.api.elec.IElecFSV;
import com.sandi.web.utils.common.JsonUtil;
import com.sandi.web.utils.elec.ElecUitl;
import com.sandi.web.utils.elec.entity.ElecInst;
import com.sandi.web.utils.http.entity.CfgSrvBase;
import com.sandi.web.utils.response.Response;
import com.sandi.web.utils.sec.SessionManager;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.image.ToolkitImage;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangruiping
 * @version $Id: ElecController.java,v 1.1 16/9/12 上午10:20 zhangruiping Exp $
 *          Created on 16/9/12 上午10:20
 */
@Controller
@RequestMapping(value = "elec")
public class ElecController {

    private static final Logger logger = LoggerFactory.getLogger(ElecController.class);

    @RequestMapping("/upload")
    @ResponseBody
    public void upload(@RequestParam(value = "fileInputId") Long fileInputId,
                       @RequestParam(value = "fileTypeId") Long fileTypeId,
                       @RequestParam(value = "expireDate") String expireDate,
                       @RequestParam(value = "effectiveDate") String effectiveDate,
                       @RequestParam(value = "moduleId") Integer moduleId,
                       @RequestParam MultipartFile[] files,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String sessionId = request.getRequestedSessionId();
            SessionManager.checkLogin(sessionId);

            for (MultipartFile multipartFile : files) {
                logger.info("fileName: " + multipartFile.getOriginalFilename());
                logger.info("isEmpty: " + multipartFile.isEmpty());
                if (!multipartFile.isEmpty()) {

                    ElecInst elecInst = new ElecInst();
                    elecInst.setFileInputId(fileInputId);
                    elecInst.setFileTypeId(fileTypeId);
                    elecInst.setModuleId(moduleId);

                    String[] parsePatterns = {"yyyy-MM-dd"};
                    if (expireDate != null && !"".equals(expireDate)) {
                        elecInst.setExpireDate(DateUtils.parseDate(expireDate, parsePatterns));
                    }
                    if (effectiveDate != null && !"".equals(effectiveDate)) {
                        elecInst.setEffectiveDate(DateUtils.parseDate(effectiveDate, parsePatterns));
                    }

                    if (elecInst.getFileTypeId() == null || elecInst.getFileInputId() == null) {
                        throw new Exception("文件输入ID和文件类型ID都不能为空！");
                    }
                    String originalFilename = multipartFile.getOriginalFilename();
                    elecInst.setFileName(originalFilename);
                    elecInst.setFileSuffix(originalFilename.substring(originalFilename.lastIndexOf(".")));
                    ElecUitl.upload(elecInst, multipartFile.getInputStream());

                    CfgSrvBase cfgSrvBase = new CfgSrvBase();
                    cfgSrvBase.setSrvId("IELECFSV_SAVEELECINST");
                    cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.elec.IElecFSV");
                    IElecFSV elecFSV = (IElecFSV) DubboManage.getServer(cfgSrvBase);
                    String res = elecFSV.saveElecInst(JsonUtil.beanToJsonString(elecInst));
                    Response re = JsonUtil.json2Object(res, Response.class);
                    if (!re.getErrorInfo().getCode().equals(Response.SUCCESS)) {
                        throw new Exception("保存实例数据失败！");
                    }
                }
            }
            map.put("status", "0");
        } catch (Exception e) {
            logger.error("上传文件失败！", e);
            map.put("status", "1");
            map.put("message", e.getMessage());
        } finally {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.object2Json(map));
        }
    }


    @RequestMapping("/del")
    @ResponseBody
    public void del(@RequestParam(value = "elecInstId") Long elecInstId,
                    @RequestParam(value = "fileTypeId") Long fileTypeId,
                    @RequestParam(value = "fileSaveName") String fileSaveName,
                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String sessionId = request.getRequestedSessionId();
            SessionManager.checkLogin(sessionId);

            ElecInst elecInst = new ElecInst();
            elecInst.setElecInstId(elecInstId);
            elecInst.setFileTypeId(fileTypeId);
            elecInst.setFileSaveName(fileSaveName);

            if (elecInst.getFileTypeId() == null || elecInst.getElecInstId() == null) {
                throw new Exception("资料实例ID和文件类型ID都不能为空！");
            }

            ElecUitl.del(elecInst);

            CfgSrvBase cfgSrvBase = new CfgSrvBase();
            cfgSrvBase.setSrvId("IELECFSV_SAVEELECINST");
            cfgSrvBase.setSrvPackage("com.sandi.web.utils.api.elec.IElecFSV");
            IElecFSV elecFSV = (IElecFSV) DubboManage.getServer(cfgSrvBase);
            String res = elecFSV.delElecInst(JsonUtil.beanToJsonString(elecInst));
            Response re = JsonUtil.json2Object(res, Response.class);
            if (!re.getErrorInfo().getCode().equals(Response.SUCCESS)) {
                throw new Exception("删除电子资料实例数据失败！");
            }

            map.put("status", "0");
        } catch (Exception e) {
            logger.error("删除电子资料实例数据失败！", e);
            map.put("status", "1");
            map.put("message", e.getMessage());
        } finally {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.object2Json(map));
        }
    }


    @RequestMapping(value = "/download")
    public void fileDownload(@RequestParam("fileTypeId") Long fileTypeId,
                             @RequestParam("fileSaveName") String fileSaveName,
                             @RequestParam("fileName") String fileName,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sessionId = request.getRequestedSessionId();
        SessionManager.checkLogin(sessionId);

        try {
            fileName = URLDecoder.decode(URLDecoder.decode(fileName, "UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
        ElecInst elecInst = new ElecInst();
        elecInst.setFileTypeId(fileTypeId);
        elecInst.setFileSaveName(fileSaveName);
        if (fileTypeId == null || fileSaveName == null) {
            throw new Exception("文件类型ID和文件保存名不能为空都不能为空！");
        }
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("expires", "0");
        response.reset();
        response.resetBuffer();
        response.setContentType("application/octet-stream");
        String agent = request.getHeader("USER-AGENT");
        try {
            if (agent != null && agent.indexOf("Firefox") != -1) {
                response.addHeader("Content-Disposition", "attachment; filename*=\"utf8''" + fileName + "\"");
            } else {
                response.addHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            OutputStream outputStream = response.getOutputStream();
            ElecUitl.download(elecInst, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("", e);
        }
    }


    @RequestMapping("/sealDeal")
    @ResponseBody
    public void del(@RequestParam(value = "imgData") String imgData,
                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String sessionId = request.getRequestedSessionId();
            SessionManager.checkLogin(sessionId);


            //data:image/png;base64,

            String imageHeader = imgData.substring(0, imgData.indexOf(",") + 1);
            imgData = imgData.substring(imgData.indexOf(",") + 1);
            String imgType = imageHeader.split(";")[0].split("/")[1];


            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(imgData);
            InputStream inputStream = new ByteArrayInputStream(b);
            Image sourceImage = ImageIO.read(inputStream);
            ImageFilter filter = new RGBImageFilter() {
                private int redAlpha = 100;
                private int greenAlpha = 100;
                private int blueAlpha = 100;

                @Override
                public final int filterRGB(int x, int y, int rgb) {
                    DirectColorModel directColorModel = (DirectColorModel) ColorModel.getRGBdefault();
                    int red = directColorModel.getRed(rgb);
                    int green = directColorModel.getGreen(rgb);
                    int blue = directColorModel.getBlue(rgb);
                    int alpha = directColorModel.getAlpha(rgb);

                    int reAlpha = alpha;
                    if (red >= redAlpha && green >= greenAlpha && blue >= blueAlpha) {
                        reAlpha = 0;
                    }
                    return reAlpha << 24 | red << 16 | green << 8 | blue;
                }
            };
            ImageProducer ip = new FilteredImageSource(sourceImage.getSource(), filter);
            ToolkitImage targetImage = (ToolkitImage) Toolkit.getDefaultToolkit().createImage(ip);


            BufferedImage bufImg = new BufferedImage(targetImage.getWidth(null), targetImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bufImg.createGraphics();
            g.drawImage(targetImage, 0, 0, null);
            g.dispose();


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufImg, imgType, baos);
            byte[] bytes = baos.toByteArray();

            map.put("data", imageHeader + new BASE64Encoder().encodeBuffer(bytes).trim().replaceAll("\n", ""));
            map.put("status", "0");
        } catch (Exception e) {
            logger.error("签章处理失败！", e);
            map.put("status", "1");
            map.put("message", e.getMessage());
        } finally {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.object2Json(map));
        }
    }


}