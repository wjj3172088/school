package com.qh.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.common.core.constant.UserConstants;
import com.qh.common.core.utils.StringUtils;
import com.qh.common.core.utils.poi.ExcelUtil;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.annotation.CheckBackendToken;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.domain.SysRole;
import com.qh.system.api.domain.SysUser;
import com.qh.system.api.model.UserInfo;
import com.qh.system.service.ISysPermissionService;
import com.qh.system.service.ISysPostService;
import com.qh.system.service.ISysRoleService;
import com.qh.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户信息
 * 
 * @author
 */
@RestController
@RequestMapping("/user")
@CheckBackendToken
public class SysUserController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private ISysPermissionService permissionService;

    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public R<IPage<SysUser>> list(SysUser user)
    {
        IPage<SysUser> data = userService.selectUserListByPage(getPage(), user);
        return R.ok(data);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user) throws IOException
    {
        List<SysUser> list = userService.selectUserListAll(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public R importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return R.fail(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info/{username}")
    @CheckBackendToken(required = false)
    public R info(@PathVariable("username") String username)
    {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser))
        {
            return R.fail("用户名或密码错误");
        }
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(sysUser.getUserId());
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(sysUser.getUserId());
        UserInfo sysUserVo = new UserInfo();
        sysUserVo.setSysUser(sysUser);
        sysUserVo.setRoles(roles);
        sysUserVo.setPermissions(permissions);
        return R.ok(sysUserVo);
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R getInfo()
    {
        Long userId = SecurityUtils.getLoginUser().getUserId();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(userId);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(userId);

        Map<String, Object> data = new HashMap<>();
        data.put("user", userService.selectUserById(userId));
        data.put("roles", roles);
        data.put("permissions", permissions);
        return R.ok(data);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = { "/", "/{userId}" })
    public R getInfo(@PathVariable(value = "userId", required = false) Long userId)
    {
        Map<String, Object> data = new HashMap<>();


        List<SysRole> roles = roleService.selectRoleAll();
        data.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        data.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId))
        {
            data.put("data", userService.selectUserById(userId));
            data.put("postIds", postService.selectPostListByUserId(userId));
            data.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return R.ok(data);
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@Validated @RequestBody SysUser user)
    {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return R.fail("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return R.fail("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return R.fail("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setCreateTime(new Date());
        return toResult(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@Validated @RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return R.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return R.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return toResult(userService.edit(user));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public R resetPwd(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toResult(userService.updateUserInfo(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R changeStatus(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        return toResult(userService.updateUserInfo(user));
    }
}
