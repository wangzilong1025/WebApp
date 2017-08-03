package com.sandi.web.utils.ftp;

import com.sandi.web.utils.common.ConcurrentCapacity;
import com.sandi.web.utils.common.JedisUtils;
import com.sandi.web.utils.common.StringUtils;
import com.sandi.web.utils.ftp.entity.CfgFtp;
import com.sandi.web.utils.ftp.entity.CfgFtpPath;
import com.sandi.web.utils.ftp.entity.FileType;
import com.sandi.web.utils.security.K;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15049 on 2017-07-01.
 */
public class FtpUtil {

    public static final String FTP_CACHE_NAME = "CfgFtpCache";
    public static final String FTP_PATH_CACHE_NAME = "CfgFtpPathCache";

    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    private static int TIMEOUT_SECONDS;
    private static int CONCURRENT_CAPACITY;
    private static int CONCURRENT_ACQUIRE_TIMEOUT_SECONDS;
    private static ConcurrentCapacity SEM = null;

    static {
        TIMEOUT_SECONDS = 120;
        CONCURRENT_CAPACITY = 10;
        CONCURRENT_ACQUIRE_TIMEOUT_SECONDS = 10;
        TIMEOUT_SECONDS = Integer.getInteger("ftp.timeoutSeconds", 120).intValue();
        logger.info("ftp TIMEOUT_SECONDS : " + TIMEOUT_SECONDS);
        CONCURRENT_CAPACITY = Integer.getInteger("ftp.concurrentCapacity", 10).intValue();
        CONCURRENT_ACQUIRE_TIMEOUT_SECONDS = Integer.getInteger("ftp.concurrentCapacityAcquireTimeoutSeconds", 3).intValue();
        SEM = new ConcurrentCapacity(CONCURRENT_CAPACITY, CONCURRENT_ACQUIRE_TIMEOUT_SECONDS);
        logger.info("ftp\u5E76\u53D1\u5BB9\u91CF" + CONCURRENT_CAPACITY + "\u4E2A");
        logger.info("ftp\u5E76\u53D1\u5BB9\u91CFacquire\u8D85\u65F6(\u79D2)" + CONCURRENT_ACQUIRE_TIMEOUT_SECONDS);
    }

    private static CfgFtp getCfgFtp(String ftpCode) {
        return (CfgFtp) JedisUtils.getObject(FTP_CACHE_NAME + "_" + ftpCode);
    }

    private static CfgFtpPath getCfgFtpPath(String ftpPathCode) {
        return (CfgFtpPath) JedisUtils.getObject(FTP_PATH_CACHE_NAME + "_" + ftpPathCode);
    }


    private static FTPClient getFTPClient(String ftpPathCode) throws Exception {

        CfgFtpPath cfgFtpPath = getCfgFtpPath(ftpPathCode);
        CfgFtp cfgFtp = null;
        if (cfgFtpPath != null) {
            cfgFtp = getCfgFtp(cfgFtpPath.getFtpCode());
        }
        if (cfgFtp == null || cfgFtpPath == null) {
            throw new Exception("请校验FTP[" + ftpPathCode + "]是否配置正确，并已加载到缓存！");
        }
        FTPClient ftpClient = new FTPClient();
        ftpClient.setDefaultTimeout(TIMEOUT_SECONDS * 1000);
        boolean remoteVerificationEnabled = true;
        boolean localPasv = false;
        String ip = cfgFtp.getHostIp();

        if (ip.startsWith("{PASV}")) {
            remoteVerificationEnabled = false;
            ip = StringUtils.substringAfter(ip, "{PASV}");
        } else if (ip.startsWith("{PORT}")) {
            remoteVerificationEnabled = true;
            ip = StringUtils.substringAfter(ip, "{PORT}");
        } else if (ip.startsWith("{LOCAL_PASV}")) {
            localPasv = true;
            ip = StringUtils.substringAfter(ip, "{LOCAL_PASV}");
        }
        ftpClient.connect(ip, cfgFtp.getPort());
        ftpClient.setRemoteVerificationEnabled(remoteVerificationEnabled);

        ftpClient.login(cfgFtp.getUsername(), K.k(cfgFtp.getPassword()));
        int reply = ftpClient.getReplyCode();
        logger.info("ftpReply:" + reply);
        if (localPasv) {
            ftpClient.enterLocalPassiveMode();
        }
        return ftpClient;
    }


    /**
     * 上传文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @param localBakFileName
     * @param input
     * @param fileType
     * @throws Exception
     */
    public static void upload(String ftpPathCode, String remoteFileName, String localBakFileName, InputStream input, FileType fileType) throws Exception {
        FTPClient ftpClient = null;
        boolean isAcquire = false;
        try {
            ftpClient = getFTPClient(ftpPathCode);
            ftpClient.setFileType(fileType.getValue());
            CfgFtpPath cfgFtpPath = getCfgFtpPath(ftpPathCode);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            {
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = input.read(bytes)) > 0) {
                    bos.write(bytes, 0, length);
                }
                bytes = null;
                bos.close();
            }

            ftpClient.changeWorkingDirectory(wrapper(cfgFtpPath.getRemotePath()));
            isAcquire = SEM.acquire();
            boolean isSuccess = ftpClient.storeFile(wrapper(remoteFileName), new ByteArrayInputStream(bos.toByteArray()));
            if (!isSuccess) {
                String remoteAddress = null;
                int port = 0;
                try {
                    remoteAddress = ftpClient.getRemoteAddress().getHostAddress();
                    port = ftpClient.getRemotePort();
                } catch (Exception ex) {
                    logger.error("", ex);
                }
                throw new Exception("上传流到服务器失败:" + remoteAddress + ":" + port+"; "+ ftpClient.getReplyString());
            } else {
                if (cfgFtpPath.getHisFlag() != null && cfgFtpPath.getHisFlag() == 1) {
                    String localBakPath = cfgFtpPath.getLocalPathHis();
                    if (localBakPath != null) {
                        File bakPath = new File(localBakPath);
                        if (!bakPath.exists()) {
                            bakPath.mkdirs();
                        }

                        File bakFile = new File(localBakPath + "/" + localBakFileName);
                        FileOutputStream fileOutputStream = new FileOutputStream(bakFile);
                        fileOutputStream.write(bos.toByteArray());
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
    }

    /**
     * 上传文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @param input
     * @param fileType
     * @throws Exception
     */
    public static void upload(String ftpPathCode, String remoteFileName, InputStream input, FileType fileType) throws Exception {
        String localBakFileName = remoteFileName;
        upload(ftpPathCode, remoteFileName, localBakFileName, input, fileType);

    }

    /**
     * 上传文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @param input
     * @throws Exception
     */
    public static void upload(String ftpPathCode, String remoteFileName, InputStream input) throws Exception {
        String localBakFileName = remoteFileName;
        upload(ftpPathCode, remoteFileName, localBakFileName, input, FileType.BIN);
    }

    /**
     * 上传文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @param localBakFileName
     * @param input
     * @throws Exception
     */
    public static void upload(String ftpPathCode, String remoteFileName, String localBakFileName, InputStream input) throws Exception {
        upload(ftpPathCode, remoteFileName, localBakFileName, input, FileType.BIN);
    }


    /**
     * 下载文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @param remoteBakFileName
     * @param output
     * @param fileType
     * @throws Exception
     */
    public static void download(String ftpPathCode, String remoteFileName, String remoteBakFileName, OutputStream output, FileType fileType) throws Exception {
        FTPClient ftpClient = null;
        boolean isAcquire = false;
        try {
            ftpClient = getFTPClient(ftpPathCode);
            ftpClient.setFileType(fileType.getValue());
            CfgFtpPath cfgFtpPath = getCfgFtpPath(ftpPathCode);

            ftpClient.changeWorkingDirectory(wrapper(cfgFtpPath.getRemotePath()));
            isAcquire = SEM.acquire();
            boolean isSuccess = ftpClient.retrieveFile(wrapper(remoteFileName), output);
            if (!isSuccess) {
                String remoteAddress = null;
                int port = 0;
                try {
                    remoteAddress = ftpClient.getRemoteAddress().getHostAddress();
                    port = ftpClient.getRemotePort();
                } catch (Exception ex) {
                    logger.error("", ex);
                }
                throw new Exception("下载流到服务器失败:" + remoteAddress + ":" + port);
            } else {
                if (cfgFtpPath.getHisFlag() != null && cfgFtpPath.getHisFlag() == 1) {
                    String remoteBakPath = cfgFtpPath.getRemotePathHis();
                    if (remoteBakPath != null) {
                        if (ftpClient.listNames(remoteBakPath).length == 0) {
                            ftpClient.makeDirectory(remoteBakPath);
                        }
                        ftpClient.rename(wrapper(remoteFileName), wrapper(remoteBakPath + "/" + remoteBakFileName));
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
    }

    /**
     * 下载文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @param output
     * @param fileType
     * @throws Exception
     */
    public static void download(String ftpPathCode, String remoteFileName, OutputStream output, FileType fileType) throws Exception {
        download(ftpPathCode, remoteFileName, remoteFileName, output, fileType);
    }

    /**
     * 下载文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @param output
     * @throws Exception
     */
    public static void download(String ftpPathCode, String remoteFileName, OutputStream output) throws Exception {
        download(ftpPathCode, remoteFileName, remoteFileName, output, FileType.BIN);
    }

    /**
     * 下载文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @param remoteBakFileName
     * @param output
     * @throws Exception
     */
    public static void download(String ftpPathCode, String remoteFileName, String remoteBakFileName, OutputStream output) throws Exception {
        download(ftpPathCode, remoteFileName, remoteBakFileName, output, FileType.BIN);
    }


    /**
     * 列出目录下的文件
     *
     * @param ftpPathCode
     * @param pathName
     * @return
     * @throws Exception
     */
    public static String[] listNames(String ftpPathCode, String pathName) throws Exception {
        FTPClient ftpClient = null;
        boolean isAcquire = false;
        String fileNames[] = null;
        try {
            ftpClient = getFTPClient(ftpPathCode);
            isAcquire = SEM.acquire();
            fileNames = ftpClient.listNames(pathName);
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
        return fileNames;
    }

    /**
     * 列出目录下的文件
     *
     * @param ftpPathCode
     * @return
     * @throws Exception
     */
    public static String[] listNames(String ftpPathCode) throws Exception {
        return listNames(ftpPathCode, null);
    }

    /**
     * 列出目录下面的文件
     *
     * @param ftpPathCode
     * @param pathName
     * @return
     * @throws Exception
     */
    public static String[] listFiles(String ftpPathCode, String pathName) throws Exception {
        FTPClient ftpClient = null;
        boolean isAcquire = false;
        String fileNames[] = null;
        try {
            ftpClient = getFTPClient(ftpPathCode);
            isAcquire = SEM.acquire();
            FTPFile ftpFiles[] = ftpClient.listFiles(pathName);
            List<String> list = new ArrayList();
            for (int i = 0; i < ftpFiles.length; i++) {
                if (ftpFiles[i].isFile()) {
                    list.add(ftpFiles[i].getName());
                }
            }
            fileNames = (String[]) (String[]) list.toArray(new String[0]);
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
        return fileNames;
    }

    /**
     * 列出目录下的文件
     *
     * @param ftpPathCode
     * @return
     * @throws Exception
     */
    public static String[] listFiles(String ftpPathCode) throws Exception {
        return listFiles(ftpPathCode, null);
    }


    /**
     * 获取远程文件
     *
     * @param ftpPathCode
     * @param fileName
     * @return
     * @throws Exception
     */
    public static FTPFile getFtpFile(String ftpPathCode, String fileName) throws Exception {
        FTPClient ftpClient = null;
        boolean isAcquire = false;
        FTPFile ftpfile = null;
        try {
            ftpClient = getFTPClient(ftpPathCode);
            isAcquire = SEM.acquire();
            FTPFile ftpFiles[] = ftpClient.listFiles(fileName);
            for (int i = 0; i < ftpFiles.length; i++) {
                if (ftpFiles[i].isFile() && ftpFiles[i].getName().equals(fileName)) {
                    ftpfile = ftpFiles[i];
                    break;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
        return ftpfile;
    }


    /**
     * 列出目录下的文件夹
     *
     * @param ftpPathCode
     * @param pathName
     * @return
     * @throws Exception
     */
    public static String[] listDirs(String ftpPathCode, String pathName) throws Exception {
        FTPClient ftpClient = null;
        boolean isAcquire = false;
        String fileNames[] = null;
        try {
            ftpClient = getFTPClient(ftpPathCode);
            isAcquire = SEM.acquire();
            FTPFile ftpFiles[] = ftpClient.listFiles(pathName);
            List<String> list = new ArrayList();
            for (int i = 0; i < ftpFiles.length; i++) {
                if (ftpFiles[i].isDirectory()) {
                    list.add(ftpFiles[i].getName());
                }
            }
            fileNames = (String[]) (String[]) list.toArray(new String[0]);
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
        return fileNames;
    }

    /**
     * 列出目录下的文件夹
     *
     * @param ftpPathCode
     * @return
     * @throws Exception
     */
    public static String[] listDirs(String ftpPathCode) throws Exception {
        return listDirs(ftpPathCode, null);
    }


    /**
     * 创建文件夹
     *
     * @param ftpPathCode
     * @param dir
     * @return
     * @throws Exception
     */
    public static boolean mkdir(String ftpPathCode, String dir) throws Exception {
        FTPClient ftpClient = null;
        boolean isAcquire = false;
        boolean flag;
        try {
            ftpClient = getFTPClient(ftpPathCode);
            isAcquire = SEM.acquire();
            flag = ftpClient.makeDirectory(dir);
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
        return flag;
    }


    /**
     * 读远程文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @param fileType
     * @return
     * @throws Exception
     */
    public static InputStream readRemote(String ftpPathCode, String remoteFileName, FileType fileType) throws Exception {
        FTPClient ftpClient = null;
        boolean isAcquire = false;
        InputStream inputstream;
        try {
            ftpClient.setFileType(fileType.getValue());
            ftpClient = getFTPClient(ftpPathCode);
            isAcquire = SEM.acquire();
            inputstream = ftpClient.retrieveFileStream(wrapper(remoteFileName));
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
        return inputstream;
    }

    /**
     * 读远程文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    public static InputStream readRemote(String ftpPathCode, String remoteFileName) throws Exception {
        return readRemote(ftpPathCode, remoteFileName, FileType.BIN);
    }

    /**
     * 重命名远程文件
     *
     * @param ftpPathCode
     * @param oldRemoteFileName
     * @param newRemoteFileName
     * @return
     * @throws Exception
     */
    public static boolean rename(String ftpPathCode, String oldRemoteFileName, String newRemoteFileName) throws Exception {
        FTPClient ftpClient = null;
        boolean isAcquire = false;
        boolean flag = false;
        try {
            ftpClient = getFTPClient(ftpPathCode);
            isAcquire = SEM.acquire();
            flag = ftpClient.rename(wrapper(oldRemoteFileName), wrapper(newRemoteFileName));
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
        return flag;
    }


    /**
     * 删除远程文件
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    public static boolean delete(String ftpPathCode, String remoteFileName) throws Exception {
        FTPClient ftpClient = null;
        boolean isAcquire = false;
        boolean flag = false;
        try {
            ftpClient = getFTPClient(ftpPathCode);
            isAcquire = SEM.acquire();
            flag = ftpClient.deleteFile(wrapper(remoteFileName));
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
        return flag;
    }


    /**
     * 湖区远程文件的OutputStream
     *
     * @param ftpPathCode
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    public static OutputStream getOutputStream(String ftpPathCode, String remoteFileName) throws Exception {

        FTPClient ftpClient = null;
        boolean isAcquire = false;
        OutputStream outputstream;
        try {
            ftpClient = getFTPClient(ftpPathCode);
            isAcquire = SEM.acquire();
            outputstream = ftpClient.storeFileStream(wrapper(remoteFileName));
        } catch (Exception e) {
            throw e;
        } finally {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
            if (isAcquire) {
                SEM.release();
            }
        }
        return outputstream;
    }

    private static String wrapper(String str)
            throws Exception {
        return new String(str.getBytes(), "ISO-8859-1");
    }

}
