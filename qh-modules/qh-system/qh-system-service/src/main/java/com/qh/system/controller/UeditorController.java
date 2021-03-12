package com.qh.system.controller;

import com.qh.common.core.config.SystemOssImagesConfig;
import com.qh.common.core.enums.ErrorType;
import com.qh.common.core.utils.oss.AliOSS;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import com.qh.common.core.web.domain.ResData;
import com.qh.system.domain.vo.FileDownload;
import com.qh.system.domain.vo.ImageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Description: 后台图片上传类
 * @Author: huangdaoquan
 * @Date: 2020/11/20 9:41
 * @return
 */
@Controller
@Slf4j
@RequestMapping("/ueditor")
public class UeditorController extends BaseController {

    @Autowired
    SystemOssImagesConfig systemOssImagesConfig;

    @Autowired
    private AliOSS aliOSS;

    @RequestMapping(method = RequestMethod.POST, value = "/imgUpdate")
    @ResponseBody
    public R<ImageResult> imgUpdate(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return R.fail("请选择文件后再上传");
            }
            System.out.print(systemOssImagesConfig);
            ResData res = aliOSS.ImgUpdateOriginal(file, "UEI-", 1024 * 1024 * 3);
            String ImgName;
            if (res.getCode() == ErrorType.SUCCESS.getCodeVal()) {
                //上传成功
                ImgName = res.getMsg();
            } else if (res.getCode() == ErrorType.NO_FILE.getCodeVal()) {
                return R.fail("上传失败 图片不存在!");
            } else {
                return R.fail("上传失败 请检查图片大小及格式!");
            }
            String imgUrl = systemOssImagesConfig.getBaseImgUrl() + systemOssImagesConfig.getUeditor() + ImgName;
            ImageResult imageResult = new ImageResult();
            imageResult.setState("SUCCESS");
            imageResult.setUrl(imgUrl);
            imageResult.setTitle(systemOssImagesConfig.getBaseImgUrl() + systemOssImagesConfig.getUeditor());
            imageResult.setOriginal(ImgName);
            return R.ok(imageResult);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return R.fail("图片上传发生未知异常，请检查图片是否符合大小规范或联系管理员" + e.getMessage());
        }

    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/fileUpload")
    @ResponseBody
    public R<ImageResult> fileUpload(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return R.fail("请选择文件后再上传");
            }
            System.out.print(systemOssImagesConfig);
            ResData res = aliOSS.FileUpdateOriginal(file, "FILE-", 1024 * 1024 * 20);

            String orginalFileName = file.getOriginalFilename();
            String fileName;
            if (res.getCode() == ErrorType.SUCCESS.getCodeVal()) {
                //上传成功
                fileName = res.getMsg();
            } else if (res.getCode() == ErrorType.NO_FILE.getCodeVal()) {
                return R.fail("上传失败 文件不存在!");
            } else {
                return R.fail("上传失败 请检查文件大小!");
            }
            String fileUrl = systemOssImagesConfig.getBaseImgUrl() + systemOssImagesConfig.getUeditor() + fileName;
            ImageResult imageResult = new ImageResult();
            imageResult.setState("SUCCESS");
            imageResult.setUrl(fileUrl);
            imageResult.setTitle(systemOssImagesConfig.getBaseImgUrl() + systemOssImagesConfig.getUeditor());
            imageResult.setOriginal(fileName);
            imageResult.setFileName(orginalFileName);
            return R.ok(imageResult);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return R.fail("文件上传发生未知异常，请检查文件是否符合大小规范或联系管理员" + e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param fileDownload 下载请求
     * @param response     相应对象
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/downloadFile")
    public void downloadFile(FileDownload fileDownload, HttpServletResponse response) {
        CloseableHttpResponse httpResponse = null;
        try {
            String fileName=fileDownload.getFileName();
            CloseableHttpClient httpclient = HttpClients.createDefault();
            //获取要下载的文件
            HttpGet httpGet = new HttpGet(fileDownload.getUrl());
            httpResponse = httpclient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                String contentType = httpResponse.getEntity().getContentType().getValue();
                response.setContentType(contentType);
                //设置文件名称
                fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);

                StreamUtils.copy(httpResponse.getEntity().getContent(), response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //必须关闭流，否则下载的文件会提示网络失败
                response.getOutputStream().close();
            } catch (IOException e) {
            }
        }
    }
}
