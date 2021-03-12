package com.qh.basic.utils.push;

import com.qh.basic.enums.ActionStatusEnum;
import com.qh.basic.service.IActionHandlerCallBackService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: 汪俊杰
 * @Date: 2020/11/30 11:02
 * @Description:
 */
@Setter
@Getter
@NoArgsConstructor
public class ActionData {
    private volatile ActionStatusEnum status = ActionStatusEnum.INIT;
    /**
     * 处理的类型
     */
    private String signMessage;
    /**
     * 具体的数据
     */
    private Object data;


    private IActionHandlerCallBackService callback;
}
