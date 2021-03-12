package com.qh.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.qh.common.core.constant.Constants;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.IdUtils;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.sign.Base64;
import com.qh.common.core.web.domain.R;
import com.qh.common.redis.service.RedisService;
import com.qh.gateway.enums.ValidateCodeEnum;
import com.qh.gateway.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码实现处理
 *
 * @author 
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisService redisService;

    // 验证码类型
    private String captchaType = "math";

    /**
     * 生成验证码
     */
    @Override
    public R<JSONObject> createCapcha()
    {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisService.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            throw new BizException(ValidateCodeEnum.CREATE_CODE_FAIL);
        }

        JSONObject data = new JSONObject();
        data.put("uuid", uuid);
        data.put("img", Base64.encode(os.toByteArray()));
        return R.ok(data);
    }

    /**
     * 校验验证码
     */
    @Override
    public void checkCapcha(String code, String uuid)
    {
        if (StringUtils.isEmpty(code))
        {
            throw new BizException(ValidateCodeEnum.CODE_EMPTY);
        }
        if (StringUtils.isEmpty(uuid))
        {
            throw new BizException(ValidateCodeEnum.CODE_EXPIRE);
        }
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisService.getCacheObject(verifyKey);
        redisService.deleteObject(verifyKey);

        if (!code.equalsIgnoreCase(captcha))
        {
            throw new BizException(ValidateCodeEnum.CODE_ERROR);
        }
    }
}
