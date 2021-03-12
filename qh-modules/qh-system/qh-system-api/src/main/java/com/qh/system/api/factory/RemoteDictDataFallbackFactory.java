package com.qh.system.api.factory;

import com.qh.common.core.web.domain.R;
import com.qh.system.api.RemoteDictDataService;
import com.qh.system.api.domain.SysDictData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/17 21:45
 * @Description:
 */
@Component
public class RemoteDictDataFallbackFactory implements FallbackFactory<RemoteDictDataService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteDictDataFallbackFactory.class);

    @Override
    public RemoteDictDataService create(Throwable throwable) {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteDictDataService() {
            @Override
            public R<SysDictData> getDictData(String dictCode, String itemVal) {
                return null;
            }

            @Override
            public R<SysDictData> getDictDataByItemName(String dictCode, String itemName) {
                return null;
            }

            @Override
            public R<List<SysDictData>> getDictDataByCode(String dictCode) {
                return null;
            }
        };
    }
}
