package com.qh.common.security.service;

import com.qh.common.core.enums.CodeEnum;
import com.qh.common.core.enums.UserStatus;
import com.qh.common.core.exception.BizException;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.web.domain.R;
import com.qh.common.security.domain.LoginUser;
import com.qh.system.api.RemoteUserService;
import com.qh.system.api.domain.SysUser;
import com.qh.system.api.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户信息处理
 *
 * @author
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        R<UserInfo> userResult = remoteUserService.getUserInfo(username);
        checkUser(userResult, username);
        return getUserDetails(userResult);
    }

    public void checkUser(R<UserInfo> userResult, String username) {
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            log.info("登录用户：{} 不存在.", username);
            throw new BizException(CodeEnum.USER_NOT_FOUND, username);
        } else if (UserStatus.DISABLE.getCode().equals(userResult.getData().getSysUser().getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new BizException(CodeEnum.USER_ACCOUNT_DISABLE, username);
        }
    }

    private UserDetails getUserDetails(R<UserInfo> result) {
        UserInfo info = result.getData();
        Set<String> dbAuthsSet = new HashSet<String>();
        if (StringUtils.isNotEmpty(info.getRoles())) {
            // 获取角色
            dbAuthsSet.addAll(info.getRoles());
            // 获取权限
            dbAuthsSet.addAll(info.getPermissions());
        }

        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        SysUser user = info.getSysUser();
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new BizException(CodeEnum.INVOKE_INTERFACE_FAIL, "请先设置该用户的登录密码");
        }

        return new LoginUser(user.getUserId(), user.getUserName(), user.getPassword(), user.getOrgId(), user.getOrgName(), true,
                true,
                true, true,
                authorities);
    }
}
