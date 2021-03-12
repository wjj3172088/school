package com.qh.common.core.utils.oss;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.qh.common.core.config.SystemOssImagesConfig;
import com.qh.common.core.domain.AttachmentImgVo;
import com.qh.common.core.utils.JsonMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * @author ljs
 * @date 2019年9月9日 下午4:43:38
 */
public class PicUtils {

    @Autowired
    SystemOssImagesConfig systemOssImagesConfig;

    private static String frist(String img) {
        if (img.startsWith("/")) {
            return img;
        }
        return "/".concat(img);
    }

    public String respacDomain(String url) {
        return url.replace(systemOssImagesConfig.getDomain(), "");
    }

    public static String delFristPrefix(String url) {
        if (url.startsWith("/")) {
            return url.substring(1);
        }
        return url;
    }


    /**
     * 图片信息增加前缀
     *
     * @param goodsImg
     * @return String
     * @throws
     * @Title:
     * @Description: TODO
     */
    public String businessImg(String goodsImg) {
        if (StringUtils.isBlank(goodsImg)) {
            return "";
        }
        if (verifyImgUrl(goodsImg)) {
            return goodsImg;
        }
        return systemOssImagesConfig.getDomain().concat(frist(goodsImg));
    }

    /**
     * 用户图片信息 @Title: @Description: TODO @param userImg @return @return
     * String @throws
     */
    public String userImg(String userImg) {
        if (StringUtils.isBlank(userImg)) {
            return "";
        }
        if (verifyImgUrl(userImg)) {
            return userImg;
        }
        return systemOssImagesConfig.getDomain().concat(frist(userImg));
    }

    private static boolean verifyImgUrl(String url) {
        return (StringUtils.isBlank(url) || url.length() < 5 || "http".equals(url.substring(0, 4).toLowerCase()) || "https".equals(url.substring(0, 5).toLowerCase()));
    }

    /**
     * @param image
     * @return String
     * @throws
     * @Title: 给图片增加HTTP域名前缀
     * @Description: TODO
     */
    public List<String> imageAddFristDomain(String image) {
        try {
            if (StringUtils.isBlank(image)) {
                return null;
            }
            List<String> imageList = JSON.parseArray(image, String.class);
            if (imageList == null || imageList.size() == 0) {
                return null;
            }
            return imageAddFristDomain(imageList);

        } catch (Exception e) {
            // TODO: handle exception
            //logger.error("", e);
            return null;
        }
    }

    /**
     * 解析从老后台上传的图片数组转换为可以用List图片集合
     *
     * @param imageList 如：将["curriculum/1/1.png","curriculum/1/2.png"] 转为List<String>的http:www.curriculum/1/1.png格式
     * @return
     */
    public List<String> imageAddFristDomain(List<String> imageList) {
        try {
            if (imageList == null || imageList.isEmpty()) {
                return null;
            }
            for (int i = 0; i < imageList.size(); i++) {
                imageList.set(i, businessImg(imageList.get(i)));
            }
            return imageList;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    /**
     * 解析从老后台上传的图片数组转换为可以用String图片集合
     *
     * @param image 如：将curriculum/1/1.png 转为String>http:www.curriculum/1/1.png格式
     * @return
     */
    public String imageFristDomain(String image) {
        if (StringUtils.isBlank(image)) {
            return null;
        }
        try {
            List<String> list = new ArrayList<>();
            list.add(image);
            return businessImg(list.get(0));
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    /**
     * 解析从APP上传的图片数组转换为可以用String图片集合
     *
     * @param image 如：将[{"attId":"1","bucket":"cs0","filename":"common/1/1","format":"jpg"}] 转为String>http:www.curriculum/1/1.png格式
     * @return
     */
    public String imageFristDomainApp(String image) {
        if (StringUtils.isBlank(image)) {
            return null;
        }
        try {
            image = image.replace("[", "").replace("]", "");
            AttachmentImgVo attachmentImgVo = JSON.parseObject(image, AttachmentImgVo.class);
            return systemOssImagesConfig.getBaseImgUrl().concat(frist(attachmentImgVo.getFilename()));
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }


    /**
     * 解析从APP上传的图片数组转换为可以用String图片集合
     *
     * @param image 如：将[{"attId":"1","bucket":"cs0","filename":"common/1/1","format":"jpg"}] 转为String>http:www.curriculum/1/1.png格式
     * @return
     */
    public List<String> imageListFristDomainApp(String image) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(image)) {
            return list;
        }
        try {
            List<AttachmentImgVo> pictureList = JsonMapper.defaultMapper().fromJson(image, new TypeReference<List<AttachmentImgVo>>() {
            });
            if (!CollectionUtils.isEmpty(pictureList)) {
                pictureList.forEach(x -> {
                    String data = systemOssImagesConfig.getBaseImgUrl().concat(frist(x.getFilename()));
                    list.add(data);
                });
            }
            return list;
        } catch (Exception e) {
            // TODO: handle exception
            return list;
        }
    }


    public static void main(String[] args) throws IOException {
        byte[] bytes = FileUtils.readFileToByteArray(new File("D:\\test1.jpg"));
        bytes = compressPicForScale(bytes, 200);// 图片小于200kb
        FileUtils.writeByteArrayToFile(new File("D:\\1.jpg"), bytes);

        // PicUtils.commpressPicForScale("D:/2.jpg", "D:/22.jpg", 200, 0.8);
    }

    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {

        if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }

        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / 1024);
        try {
            //logger.debug("【图片压缩】 图片原大小={}kb", srcSize / 1024);
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream).scale(accuracy).outputQuality(1).toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
                accuracy = getAccuracy(imageBytes.length / 1024);
                //logger.debug("size:{}", imageBytes.length);
            }
            //logger.debug("【图片压缩】 压缩后大小={}kb", imageBytes.length / 1024);

        } catch (Exception e) {
            //logger.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 1024) {
            accuracy = 0.8;
        } else if (size < 2048) {
            accuracy = 0.7;
        } else if (size < 4096) {
            accuracy = 0.6;
        } else {
            accuracy = 0.5;
        }
        return accuracy;
    }

    /**
     * 按照 宽高 比例压缩
     *
     * @param imgIs 待压缩图片输入流
     * @param scale 压缩刻度
     * @param out   输出
     * @return 压缩后图片数据
     * @throws IOException 压缩图片过程中出错
     */
    public static byte[] compress(byte[] srcImgData, double scale) throws IOException {
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(srcImgData));
        int width = (int) (bi.getWidth() * scale); // 源图宽度
        int height = (int) (bi.getHeight() * scale); // 源图高度

        Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ImageIO.write(tag, "JPEG", bOut);

        return bOut.toByteArray();
    }

    /**
     * 根据指定大小和指定精度压缩图片    
     *
     * @param srcPath     源图片地址
     * @param desPath      目标图片地址     
     * @param desFilesize 指定图片大小，单位kb     
     * @param accuracy    精度，递归压缩的比率，建议小于0.9     
     * @return     
     */
    public static String commpressPicForScale(String srcPath, String desPath, long desFileSize, double accuracy) {
        if (StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(srcPath)) {
            return null;
        }
        if (!new File(srcPath).exists()) {
            return null;
        }
        try {
            File srcFile = new File(srcPath);
            long srcFileSize = srcFile.length();
            //logger.debug("源图片：" + srcPath + "，大小：" + srcFileSize / 1024 + "kb");

            // 1、先转换成jpg
            Thumbnails.of(srcPath).scale(1f).toFile(desPath);
            // 递归压缩，直到目标文件大小小于desFileSize
            commpressPicCycle(desPath, desFileSize, accuracy);

            File desFile = new File(desPath);
            //logger.debug("目标图片：" + desPath + "，大小" + desFile.length() / 1024 + "kb");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return desPath;
    }

    private static void commpressPicCycle(String desPath, long desFileSize, double accuracy) throws IOException {
        File srcFileJPG = new File(desPath);
        long srcFileSizeJPG = srcFileJPG.length();
        // 2、判断大小，如果小于500kb，不压缩；如果大于等于500kb，压缩
        //logger.info("size:{}", srcFileSizeJPG);
        if (srcFileSizeJPG <= desFileSize * 1024) {
            return;
        }
        // 计算宽高
        BufferedImage bim = ImageIO.read(srcFileJPG);
        int srcWdith = bim.getWidth();
        int srcHeigth = bim.getHeight();
        int desWidth = new BigDecimal(srcWdith).multiply(new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(srcHeigth).multiply(new BigDecimal(accuracy)).intValue();

        Thumbnails.of(desPath).size(desWidth, desHeight).outputQuality(accuracy).toFile(desPath);
        commpressPicCycle(desPath, desFileSize, accuracy);
    }

}
