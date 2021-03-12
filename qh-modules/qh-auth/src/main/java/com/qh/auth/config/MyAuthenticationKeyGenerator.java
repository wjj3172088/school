package com.qh.auth.config;

import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

public class MyAuthenticationKeyGenerator extends DefaultAuthenticationKeyGenerator {

    private static final String SCOPE = "scope";

    private static final String CLIENT_ID = "client_id";

    private static final String UUID = "uuid";

    private static final String USERNAME = "username";

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        Map<String, String> values = new LinkedHashMap<>();
        OAuth2Request authorizationRequest = authentication.getOAuth2Request();
        if (!authentication.isClientOnly()) {
            values.put(USERNAME, authentication.getName());
        }
        values.put(CLIENT_ID, authorizationRequest.getClientId());

//        // 将uuid也加入到KEY的生成中，这样不同PC登录同一账号，生成的KEY不一样，不影响各自登录状态
//        Map<String, String> requestParameters = authorizationRequest.getRequestParameters();
//        if (requestParameters != null && requestParameters.containsKey("uuid")) {
//            values.put(UUID, requestParameters.get("uuid"));
//        }

        if (authorizationRequest.getScope() != null) {
            values.put(SCOPE, OAuth2Utils.formatParameterList(new TreeSet<>(authorizationRequest.getScope())));
        }

        // 如果是多租户系统，这里要区分租户ID 条件
        return generateKey(values);
    }
}