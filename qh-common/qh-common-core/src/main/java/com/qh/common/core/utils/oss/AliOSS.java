package com.qh.common.core.utils.oss;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.qh.common.core.config.SystemOssImagesConfig;
import com.qh.common.core.enums.ErrorType;
import com.qh.common.core.utils.http.UUIDG;
import com.qh.common.core.web.domain.ResData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class AliOSS {

    @Autowired
    SystemOssImagesConfig systemOssImagesConfig;

    //Bucket 中的内容
    public void getBucketlistObjects(String endpoint) {
        OSSClient ossClient = new OSSClient(endpoint, systemOssImagesConfig.getAccessKeyId(), systemOssImagesConfig.getAccessKeySecret());
        // 查看Bucket中的Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
        ObjectListing objectListing = ossClient.listObjects(systemOssImagesConfig.getBucketName());
        List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
        System.out.println("您有以下Object：");
        for (OSSObjectSummary object : objectSummary) {
            System.out.println("\t" + object.getKey());
        }
    }

    //Bucket 信息
    public void getBucketCont(String endpoint) {
        OSSClient ossClient = new OSSClient(endpoint, systemOssImagesConfig.getAccessKeyId(), systemOssImagesConfig.getAccessKeySecret());
        BucketInfo info = ossClient.getBucketInfo(systemOssImagesConfig.getBucketName());
        System.out.println("Bucket " + systemOssImagesConfig.getBucketName() + "的信息如下：");
        System.out.println("\t数据中心：" + info.getBucket().getLocation());
        System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
        System.out.println("\t用户标志：" + info.getBucket().getOwner());
    }

    //判断Bucket是否存在
    public void doesExist(String endpoint) {
        OSSClient ossClient = new OSSClient(endpoint, systemOssImagesConfig.getAccessKeyId(), systemOssImagesConfig.getAccessKeySecret());
        // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
        if (ossClient.doesBucketExist(systemOssImagesConfig.getBucketName())) {
            System.out.println("您已经创建Bucket：" + systemOssImagesConfig.getBucketName() + "。");
        } else {
            System.out.println("您的Bucket不存在，创建Bucket：" + systemOssImagesConfig.getBucketName() + "。");
            // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            ossClient.createBucket(systemOssImagesConfig.getBucketName());
        }
    }

    //oss UEditor 图片资源
    public HashMap getOSSList(String endpoint, String start) {
        int total = 0;              // 总数
        ObjectListing objectListing;
        HashMap map = new HashMap();

        OSSClient ossClient = new OSSClient("http://oss-cn-hangzhou.aliyuncs.com", systemOssImagesConfig.getAccessKeyId(), systemOssImagesConfig.getAccessKeySecret());
        // 指定前缀。
        final String keyPrefix = endpoint;
        // 列举包含指定前缀的文件。默认列举100个文件。
        final int maxKeys = 20;
        String nextMarker = null;
        String list = "";
        do {
            objectListing = ossClient.listObjects(new ListObjectsRequest(systemOssImagesConfig.getBucketName()).withPrefix(keyPrefix).withMaxKeys(maxKeys).withMarker(nextMarker));
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();

            //是否在分页范围内
            int startInt = Integer.valueOf(start);
            if (startInt >= total && startInt < total + maxKeys) {
                for (OSSObjectSummary s : sums) {
                    list += "{\"url\":\"https://app.zhejiangqinghai.com/" + s.getKey() + "\"},";
                }
            }
            nextMarker = objectListing.getNextMarker();
            total += sums.size();
        } while (objectListing.isTruncated());

        // 关闭OSSClient
        ossClient.shutdown();
        //返回
        list = list.substring(0, list.length() - 1);       // 去除最后一个 ,
        map.put("imgList", list);
        map.put("total", total);
        return map;
    }

    // 创建OSSClient实例。
    public OSSClient newOss(String endpoint) {
        return new OSSClient(endpoint, systemOssImagesConfig.getAccessKeyId(), systemOssImagesConfig.getAccessKeySecret());
    }

    //关闭OSSClient。
    public void shutdownOss(OSSClient ossClient) {
        ossClient.shutdown();
    }

    //上传
    public void putObject(OSSClient ossClient, String fileKey, File files) {
        try {
            ossClient.putObject(systemOssImagesConfig.getBucketName(), fileKey, files);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    //上传
    public void putObject(OSSClient ossClient, String fileKey, InputStream files) {
        try {
            ossClient.putObject(systemOssImagesConfig.getBucketName(), fileKey, files);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    //上传 单个
    public void putObjectOnce(String endpoint, String fileKey, File files) {
        OSSClient ossClient = new OSSClient(endpoint, systemOssImagesConfig.getAccessKeyId(), systemOssImagesConfig.getAccessKeySecret());
        try {
            ossClient.putObject(systemOssImagesConfig.getBucketName(), fileKey, files);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }

    //上传 单个
    public void putObjectOnce(String endpoint, String fileKey, InputStream files) {
        OSSClient ossClient = new OSSClient(endpoint, systemOssImagesConfig.getAccessKeyId(), systemOssImagesConfig.getAccessKeySecret());
        try {
            ossClient.putObject(systemOssImagesConfig.getBucketName(), fileKey, files);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }

    //上传 单个
    public int putObjectOnce_2(String endpoint, String fileKey, InputStream files) {
        OSSClient ossClient = new OSSClient(endpoint, systemOssImagesConfig.getAccessKeyId(), systemOssImagesConfig.getAccessKeySecret());
        try {
            ossClient.putObject(systemOssImagesConfig.getBucketName(), fileKey, files);
        } catch (OSSException oe) {
            oe.printStackTrace();
            return 0;
        } catch (ClientException ce) {
            ce.printStackTrace();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            ossClient.shutdown();
        }
        return 1;
    }

    //下载 单个
    public void GetObject(String endpoint, String objectName, String LocalFile) {
        OSSClient ossClient = new OSSClient(endpoint, systemOssImagesConfig.getAccessKeyId(), systemOssImagesConfig.getAccessKeySecret());
        try {
            ossClient.getObject(new GetObjectRequest(systemOssImagesConfig.getBucketName(), objectName), new File(LocalFile + "cs.png"));
        } finally {
            ossClient.shutdown();
        }
    }

    //删除 单次
    public void deleteObjectOnce(String endpoint, String fileKey) {
        OSSClient ossClient = new OSSClient(endpoint, systemOssImagesConfig.getAccessKeyId(), systemOssImagesConfig.getAccessKeySecret());
        try {
            ossClient.deleteObject(systemOssImagesConfig.getBucketName(), fileKey);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }

    //删除
    public void deleteObject(OSSClient ossClient, String fileKey) {
        try {
            ossClient.deleteObject(systemOssImagesConfig.getBucketName(), fileKey);
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    //上传 违规图片 不压缩  2 文件不存在
    public ResData ImgUpdateOriginal(MultipartFile img, String name, int MaxSize) {
        ResData resData = new ResData();
        if (img != null && !img.isEmpty()) {
            if (img.getSize() > MaxSize) {
                resData.setErrorMsg("文件不得大于" + MaxSize / 1024 + "kb! 大小为：" + img.getSize() / 1024 + "kb");
                return resData;
            }
            //图片处理
            String fileName = img.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            if (suffix.equals(".jpg") || suffix.equals(".gif") || suffix.equals(".bmp") || suffix.equals(".png") || suffix.equals(".jpeg") || suffix.equals(".PNG") || suffix.equals(".JPG") || suffix.equals(".JPEG")) {
            } else {
                resData.setErrorMsg("仅支持*.jpg、*.gif、*.bmp、*.png、*.jpeg");
                return resData;
            }
            String imgUrl = name + UUIDG.getTimeRand() + suffix;
            //图片不压缩
            try {
                putObjectOnce(systemOssImagesConfig.getPoint(), systemOssImagesConfig.getUeditor() + imgUrl, img.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                resData.setErrorMsg("上传失败");
                return resData;
            }
            resData.setCode(0);
            resData.setMsg(imgUrl);
            return resData;
        } else {
            resData.setMsg("文件不存在!");
            resData.setCode(ErrorType.NO_FILE.getCodeVal());
            return resData;
        }
    }

    //上传 违规文件 不压缩  2 文件不存在
    public ResData FileUpdateOriginal(MultipartFile file, String name, int MaxSize) {
        ResData resData = new ResData();
        if (file != null && !file.isEmpty()) {
            if (file.getSize() > MaxSize) {
                resData.setErrorMsg("文件不得大于" + MaxSize / 1024 + "kb! 大小为：" + file.getSize() / 1024 + "kb");
                return resData;
            }
            //图片处理
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String fileUrl = name + UUIDG.getTimeRand() + suffix;
            //图片不压缩
            try {
                putObjectOnce(systemOssImagesConfig.getPoint(), systemOssImagesConfig.getUeditor() + fileUrl, file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                resData.setErrorMsg("上传失败");
                return resData;
            }
            resData.setCode(0);
            resData.setMsg(fileUrl);
            return resData;
        } else {
            resData.setMsg("文件不存在!");
            resData.setCode(ErrorType.NO_FILE.getCodeVal());
            return resData;
        }
    }
}
