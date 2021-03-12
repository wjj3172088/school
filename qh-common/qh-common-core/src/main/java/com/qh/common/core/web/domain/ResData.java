package com.qh.common.core.web.domain;



import com.qh.common.core.enums.ErrorType;

import java.io.Serializable;

/**
 * @author zgf
 * @版权:广州万物信息科技有限公司
 * @date 2018年8月22日
 * @Description: TODO 接口返回封装对象
 */
public class ResData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ResData() {
        super();
    }

    public ResData(ErrorType mobileCode, String msg) {
        this.code = mobileCode.getCodeVal();
        this.msg = msg;
    }

    public ResData(ErrorType mobileCode) {
        this.code = mobileCode.getCodeVal();
    }


    /**
     * 错误代码
     */
    private Integer code = ErrorType.SUCCESS.getCodeVal();

    /**
     * 返回信息
     */
    private String msg = ErrorType.SUCCESS.getCodeMsg();

    /**
     * 返回数据对象
     */
    private Object result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResData setErrorMsg(ResData res , String msg) {
        res.setCode(ErrorType.ERROR.getCodeVal());
        res.setMsg(msg);
        return res;
    }
    
    public void setErrorMsg(String msg) {
        this.setMsg(msg);
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    
    
    //登录类型错误
    public ResData setTokenErrorMsg(ResData res , String msg) {
        res.setCode(ErrorType.TOKEN_ERROR.getCodeVal());
        res.setMsg(msg);
        return res;
    }
    
    
    
    

}
