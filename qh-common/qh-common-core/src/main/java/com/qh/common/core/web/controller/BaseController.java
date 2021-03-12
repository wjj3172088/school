package com.qh.common.core.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qh.common.core.constant.Constants;
import com.qh.common.core.utils.DateUtils;
import com.qh.common.core.utils.ServletUtils;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.web.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * web层通用数据处理
 * 
 * @author 
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 封装分页对象
     */
    public static<T> IPage<T> getPage()
    {
        Integer pageNum = ServletUtils.getParameterToInt(Constants.PAGE_NUM);
        Integer pageSize = ServletUtils.getParameterToInt(Constants.PAGE_SIZE);
        //装载分页page，没值默认设置第一页，每页显示10条
        Page<T> page = new Page<>(pageNum==null?1:pageNum , pageSize==null?10:pageSize);

        String orderByColumn = ServletUtils.getParameter(Constants.ORDER_BY_COLUMN);
        if(StringUtils.isNotBlank(orderByColumn)){
            String isAsc = ServletUtils.getParameter(Constants.IS_ASC, "asc");
            page.addOrder(new OrderItem(orderByColumn, Constants.ASC.equals(isAsc)));
        }
        return page;
    }

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected R toResult(int rows)
    {
        return toResult(rows>0, null);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @param msg 错误消息
     * @return 操作结果
     */
    protected R toResult(int rows, String msg)
    {
        return toResult(rows>0, msg);
    }

    /**
     * 响应返回结果
     *
     * @param result 操作结果
     * @return 操作结果
     */
    protected R toResult(boolean result)
    {
        return toResult(result, null);
    }

    /**
     * 响应返回结果
     *
     * @param result 操作结果
     * @param msg 错误消息
     * @return 操作结果
     */
    protected R toResult(boolean result, String msg)
    {
        return result ? R.ok() : R.fail(msg);
    }
}
