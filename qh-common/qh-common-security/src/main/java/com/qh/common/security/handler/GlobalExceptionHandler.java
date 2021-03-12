package com.qh.common.security.handler;

import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.ExceptionUtil;
import com.qh.common.core.web.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理未知异常
     * @param request 请求
     * @param exception 异常
     * @return 处理后的返回数据
     */
    @ExceptionHandler(Exception.class)
    public R handleException(HttpServletRequest request, Exception exception) {
        log.error(ExceptionUtil.getPrintStackTrace(exception, request));
        return R.fail();
    }

    /**
     * 处理业务异常
     * @param request 请求
     * @param e 业务异常
     * @return 处理后的数据
     */
    @ExceptionHandler(value = BizException.class)
    public R handleBizException(HttpServletRequest request, BizException e){
        log.error(ExceptionUtil.getPrintStackTrace(e, request));
        return R.fail(e);
    }

    /**
     * ---------------------------------------------------------------
     * 以下是系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e)
    {
        log.error(ExceptionUtil.getPrintStackTrace(e));
        return R.fail(CodeEnum.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handleAuthorizationException(AccessDeniedException e)
    {
        log.error(ExceptionUtil.getPrintStackTrace(e));
        return R.fail(CodeEnum.FORBIDDEN);
    }

    @ExceptionHandler(AccountExpiredException.class)
    public R handleAccountExpiredException(AccountExpiredException e)
    {
        log.error(ExceptionUtil.getPrintStackTrace(e));
        return R.fail(CodeEnum.UN_LOGIN);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public R validatedBindException(BindException e)
    {
        log.error(ExceptionUtil.getPrintStackTrace(e));
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return R.fail(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e)
    {
        log.error(ExceptionUtil.getPrintStackTrace(e));
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return R.fail(message);
    }

}
