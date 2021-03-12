package com.qh.common.core.utils;


import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * @author wyb
 */
public class ExceptionUtil {

    private ExceptionUtil() {
        //添加空构造方法,放置new调用
    }

    public static String getPrintStackTrace(Exception e,HttpServletRequest request) {
        return getErrorInfo(e, request);
    }

    public static String getPrintStackTrace(Exception e){
        return getErrorInfo(e,null);
    }

    /**
     * 记录异常信息
     * @param be 异常
     * @param request 请求
     * @return 返回结果
     */
    private static String getErrorInfo(Exception be, HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("---------------------------------------错误信息分割线-------------------------------------------------------");
        sb.append("\n");
        sb.append("请求时间："+ DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        sb.append("\n");
        if (request != null){
            String queryString = request.getQueryString();
            queryString = queryString != null ? (request.getRequestURI() + "?" + queryString) : request.getRequestURI();
            sb.append("url:"+queryString);
            sb.append("\n");
        }
        if (be instanceof  BizException){
            //BusinessException
            sb.append("错误码：");
            sb.append(((BizException) be).getCode());
            sb.append("  错误信息：");
            sb.append(be.getMessage());
        }else {
            //other exception
            sb.append("错误信息:");
            sb.append(CodeEnum.FAILURE.getMsg());
            sb.append("\n");
            StringWriter sw = new StringWriter();
            be.printStackTrace(new PrintWriter(sw, true));
            sb.append("异常栈信息:"+sw.toString());
        }
        return sb.toString();
    }

}
