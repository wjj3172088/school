package com.qh.common.core.utils.file;

import com.qh.common.core.config.SystemOssImagesConfig;
import com.qh.common.core.enums.BizType;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.http.DateUtils;
import com.qh.common.core.utils.http.HttpClientUtils;
import com.qh.common.core.utils.oss.PicUtils;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.core.web.domain.Attachment;
import com.qh.common.core.web.domain.FileResult;
import com.qh.common.core.web.domain.UploadFile;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件上传帮助类
 * 
 * @版权 广州万物信息科技有限公司
 * @author Loren
 * huangdq 12/2迁移
 * @DateTime 2017年7月6日 上午11:01:35
 * @Comment
 */
public class SysFileUtils {

    /** 文件根目录  */
    public static String filePath="/jjhdata/java/qinghai";

    /**
     * 单个文件上传处理
     * 
     * @author Loren
     * @DateTime 2017年7月5日 上午11:39:33
     * @serverComment
     *
     * @param multipartFile
     *            文件
     * @param bizType
     *            文件关联业务类型{@link BizType}
     * @param compression
     *            是否壓縮
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static FileResult upload(MultipartFile multipartFile, String bizType, boolean compression)
        throws IllegalStateException, IOException {
        if (multipartFile != null) {
            if (!multipartFile.isEmpty()) {
                FileResult fileResult = new FileResult();
                String originalFilename = multipartFile.getOriginalFilename();
                String fileType = getFileType(originalFilename);
                UploadFile uploadFile = createUploadFile(fileType, bizType);
                String absoluteFileName = uploadFile.getAbsoluteFileName();

                File target = new File(absoluteFileName);

                multipartFile.transferTo(target);
                fileResult.setSourceFileName(originalFilename);
                fileResult.setNewFile(uploadFile.getBizTypeFileName());

                if (compression) {
                    // 壓縮
                    byte[] bytes = FileUtils.readFileToByteArray(target);
                    bytes = PicUtils.compressPicForScale(bytes, 200);
                    FileUtils.writeByteArrayToFile(target, bytes);
                }

                return fileResult;
            }
        }
        return null;
    }

    public static FileResult upload(MultipartFile multipartFile, String bizType)
        throws IllegalStateException, IOException {
        return upload(multipartFile, bizType, false);
    }

    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 封装成附件对象
     * 
     * @author Loren
     * @DateTime 2017年7月6日 上午11:33:59
     * @serverComment
     *
     * @param fileResult
     * @return null or {@link Attachment}
     */
    public static Attachment getAttachement(FileResult fileResult) {
        if (fileResult != null) {
            Attachment attachment = new Attachment();
//            attachment.setRootPath(filePath);
            attachment.setRootPath("scs-app-cs0");
//            BizType bizType = getBizType(fileResult.getNewFile());
//            attachment.setBizType(bizType.value());
            String filePath = fileResult.getUrl().trim();
            attachment.setFilePath(filePath);
//            String fileType = getFileType(fileResult.getNewFile());
//            attachment.setFileType(fileType);
//            attachment.setSourceFileName(fileResult.getSourceFileName());
            return attachment;
        } else {
            return null;
        }
    }

    /**
     * 批量封装成附件对象
     * 
     * @author Loren
     * @DateTime 2017年7月6日 上午11:34:25
     * @serverComment
     *
     * @param fileResults
     * @return list
     */
    public static List<Attachment> getListAttachement(FileResult[] fileResults) {
        List<Attachment> attachments = new ArrayList<>();
        if (fileResults != null && fileResults.length > 0) {
            for (FileResult fileResult : fileResults) {
                Attachment attachement = getAttachement(fileResult);
                attachments.add(attachement);
            }
        }
        return attachments;
    }

    public static void deleteAttachment(String fullFilePath) {
        File file = new File(fullFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void deleteAttachment(Attachment attachment) {
        deleteAttachment(attachment.getFullFilePath());
    }

    public static void deleteAttachment(List<Attachment> attachments) {
        if (attachments != null && attachments.size() > 0) {
            for (Attachment attachment : attachments) {
                deleteAttachment(attachment);
            }
        }
    }

    /**
     * @author mds
     * @DateTime 2019年8月23日 下午9:47:50
     * @serverComment 处理文件夹路径
     * @param bizType
     * @param fileType
     * @return
     * @throws IOException
     */
    private static StringBuilder createDir(String bizType, String fileType) throws IOException {
        String format = DateUtils.format(DateUtils.yyyyMMdd);
        StringBuilder fileName = new StringBuilder();
        fileName.append(bizType).append("/").append(format);
        // 拼接根目录
        String parentDir = filePath + "/" + fileName.toString();
        // 创建文件
        createNoExistDir(parentDir);
        fileName.append("/").append(UUIDG.generate()).append(".").append(fileType);
        return fileName;
    }

    private static UploadFile createUploadFile(String fileType, String bizType) throws IOException {
        StringBuilder newFileName = createDir(bizType, fileType);
        UploadFile uploadFile = new UploadFile();
        // 相对路径处理
        uploadFile.setBizTypeFileName(newFileName.toString());
        newFileName.insert(0, "/").insert(0, filePath); // 拼接绝对路径
        uploadFile.setAbsoluteFileName(newFileName.toString());
        return uploadFile;
    }

    public static UploadFile createUploadFilePath(String fileType, String bizType, String fileName) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());
        StringBuilder newFileName = new StringBuilder();
        newFileName.append(bizType).append("/").append(format);
        // 拼接根目录
        String parentDir = filePath + "/" + newFileName.toString();
        // 创建文件
        createNoExistDir(parentDir);
        newFileName.append("/").append(fileName).append(UUIDG.generate()).append(".").append(fileType);
        UploadFile uploadFile = new UploadFile();
        // 相对路径处理
        uploadFile.setBizTypeFileName(newFileName.toString());
        newFileName.insert(0, "/").insert(0, filePath); // 拼接绝对路径
        uploadFile.setAbsoluteFileName(newFileName.toString());
        return uploadFile;
    }

    private static void createNoExistDir(String verydatPath) throws IOException {
        File file = new File(verydatPath);
        if (false == file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 设置请求头 设置apk请求头
     * 
     * @param att
     * @param response
     * @throws Exception
     */
    public static void downApk(Attachment att, HttpServletResponse response) throws Exception {
        String fileName = att.getSourceFileName();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        downloadFile(response.getOutputStream(), att.getFullFilePath());
    }

    /**
     * @author mds
     * @DateTime 2017年9月11日 下午5:08:20
     * @serverComment 文件下载
     * @param out
     *            输出流
     * @param filePath
     *            文件路径
     * @throws Exception
     */
    public static void downloadFile(OutputStream out, String filePath) throws Exception {
        InputStream inputStream = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                inputStream = new FileInputStream(file);
                byte[] buf = new byte[128];
                int count;
                while ((count = inputStream.read(buf, 0, buf.length)) > 0) {
                    out.write(buf, 0, count);
                }
            } else {
                throw new Exception(filePath + " 文件不存在!");
            }
        } catch (Exception e) {
            throw new Exception("文件读取异常");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * @author mds
     * @DateTime 2019年8月24日 上午9:54:51
     * @serverComment base64图片内容转图片
     * @param baseStr
     * @param bizType
     * @return
     * @throws Exception
     */
    public static Attachment strToImgFile(String baseStr, BizType bizType) throws Exception {
        if (StringUtils.isBlank(baseStr)) {
            throw new NullPointerException("图片内容为空");
        }
        String fileType = "png";
        Attachment att = new Attachment();
        att.setAttId(UUIDG.generate());
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            // 解密
            byte[] bArr = decoder.decodeBuffer(baseStr);
            // 处理数据
            for (int i = 0; i < bArr.length; ++i) {
                if (bArr[i] < 0) {
                    bArr[i] += 256;
                }
            }
            // 文件夹路径
            StringBuilder fileName = createDir(bizType.name(), fileType);
            att.setBizType(bizType.name());
            att.setFileType(fileType);
            att.setFilePath(fileName.toString());
            fileName.insert(0, "/").insert(0, filePath); // 拼接绝对路径
            File file = new File(fileName.toString());
            att.setSourceFileName(file.getName());
            att.setRootPath(filePath);
            out = new FileOutputStream(file);
            out.write(bArr);

        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        return att;
    }

    /**
     * @author mds
     * @DateTime 2019年8月24日 上午10:26:51
     * @serverComment 图片转为base64
     * @param att
     * @return
     * @throws Exception
     */
    public static String imgFileToStr(Attachment att) throws Exception {
        if (att == null) {
            throw new NullPointerException("Attachment is null");
        }
        // 字节数组
        byte[] bCache = new byte[2048];
        int readSize = 0;// 每次读取的字节长度
        int totalSize = 0;// 总字节长度
        ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(att.getFullFilePath());
            // 一次性读取2048字节
            while ((readSize = inputStream.read(bCache)) > 0) {
                totalSize += readSize;
                /*
                 * if (totalSize > PROTECTED_LENGTH) {
                 * LOGGER.error("输入流超出1M大小限制"); return null; }
                 */
                // 将bcache中读取的input数据写入infoStream
                infoStream.write(bCache, 0, readSize);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(infoStream.toByteArray());
    }

    /**
     * @author ljs
     * @serverComment url 转为附件
     * @param url
     * @param bizType
     * @return
     * @throws Exception
     */
    public static Attachment urlToImgFile(String url, BizType bizType) throws Exception {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("图片内容为空");
        }
        String fileType = "png";
        Attachment att = new Attachment();
        att.setAttId(UUIDG.generate());

        OutputStream out = null;
        try {
            byte[] datas = HttpClientUtils.downloadFile(url);
            // 文件夹路径
            StringBuilder fileName = createDir(bizType.name(), fileType);
            att.setBizType(bizType.name());
            att.setFileType(fileType);
            att.setFilePath(fileName.toString());
            fileName.insert(0, "/").insert(0, filePath); // 拼接绝对路径
            File file = new File(fileName.toString());
            att.setSourceFileName(file.getName());
            att.setRootPath(filePath);
            out = new FileOutputStream(file);
            out.write(datas);
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        return att;
    }
}
