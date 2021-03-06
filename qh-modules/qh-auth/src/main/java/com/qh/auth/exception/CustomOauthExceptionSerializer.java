package com.qh.auth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 自定义异常返回
 *
 * @author 
 **/
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException>
{
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(CustomOauthExceptionSerializer.class);

    public static final String BAD_CREDENTIALS = "Bad credentials";

    public CustomOauthExceptionSerializer()
    {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("code", CodeEnum.FAILURE.getCode());
        if (StringUtils.equals(e.getMessage(), BAD_CREDENTIALS))
        {
            jsonGenerator.writeStringField("msg", "用户名或密码错误");
        }
        else
        {
            log.warn("oauth2 认证异常 {} ", e);
            jsonGenerator.writeStringField("msg", e.getMessage());
        }
        jsonGenerator.writeEndObject();
    }
}