package com.qh.common.core.web.domain;


import com.qh.common.core.enums.ErrorType;

public class ResultJsonFactory {

    /**
     *消息提示
     * @param mes
     * @return
     */
    public static String toERROR(String mes){
        String json_s ="{\"msg\": \""+mes+"\",\"code\":"+ ErrorType.SUCCESS.getCodeVal()+"}";
        return json_s;
    }

    public static String toSUCCSEE(String mes){
        String json_s ="{\"msg\": \""+mes+"\",\"code\":"+ErrorType.ERROR.getCodeVal()+"}";
        return json_s;
    }

}
