package com.qh.basic.model.response.newsgroup;

import com.qh.basic.domain.ScNewsGroup;
import com.qh.basic.domain.ScNewsInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2021/1/21 09:46
 * @Description:
 */
@Data
public class NewsGroupDetailReponse {
    /**
     * 资讯组信息
     */
    private ScNewsGroup newsGroup;

    /**
     * 资讯信息列表
     */
    private List<ScNewsInfo> newsInfoList;
}
