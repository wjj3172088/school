package com.qh.common.security.config;

import com.qh.common.core.utils.ReUtil;
import com.qh.common.security.annotation.CheckBackendToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Configurable
@ConfigurationProperties(prefix = "security.oauth2.client.check-urls")
public class CheckAuthConfig implements InitializingBean {

    @Autowired
    private WebApplicationContext applicationContext;

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
    private static final String ASTERISK = "*";

    @Getter
    @Setter
    private List<String> checkUrls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        map.keySet().forEach(mappingInfo -> {
            HandlerMethod handlerMethod = map.get(mappingInfo);
            // 把接口类所有URL放入到集合中
            CheckBackendToken controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), CheckBackendToken.class);
            CheckBackendToken method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), CheckBackendToken.class);

            if (method != null) {
                if (method.required()) {
                    // 优先使用方法上的注解，加入到集合中
                    Optional.of(method)
                            .ifPresent(checkBackendToken -> mappingInfo
                                    .getPatternsCondition()
                                    .getPatterns()
                                    .forEach(url -> checkUrls.add(ReUtil.replaceAll(url, PATTERN, ASTERISK))));
                }
            } else if (controller != null && controller.required()) {
                // 如果方法上没有注解，则使用类注解，加入到集合中
                Optional.of(controller)
                        .ifPresent(checkBackendToken -> mappingInfo
                                .getPatternsCondition()
                                .getPatterns()
                                .forEach(url -> checkUrls.add(ReUtil.replaceAll(url, PATTERN, ASTERISK))));
            }


        });
    }
}
