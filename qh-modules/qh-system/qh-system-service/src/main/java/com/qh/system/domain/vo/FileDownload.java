package com.qh.system.domain.vo;

import lombok.Data;

/**
 * @Author: 汪俊杰
 * @Date: 2021/2/1 11:12
 * @Description: 下载请求
 */
@Data
public class FileDownload {
    /**
     * 真实路径
     */
    private String url;

    /**
     * 下载的文件名
     */
    private String fileName;
}
