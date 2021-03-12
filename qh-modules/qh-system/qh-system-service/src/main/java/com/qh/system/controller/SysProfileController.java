package com.qh.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.qh.common.core.config.SystemOssImagesConfig;
import com.qh.common.core.enums.ErrorType;
import com.qh.common.core.utils.oss.AliOSS;
import com.qh.common.core.web.controller.BaseController;
import com.qh.common.core.web.domain.R;
import com.qh.common.core.web.domain.ResData;
import com.qh.common.log.annotation.Log;
import com.qh.common.log.enums.BusinessType;
import com.qh.common.security.annotation.CheckBackendToken;
import com.qh.common.security.utils.SecurityUtils;
import com.qh.system.api.domain.SysUser;
import com.qh.system.domain.vo.ImageResult;
import com.qh.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人信息 业务处理
 * 
 * @author
 */
@RestController
@RequestMapping("/user/profile")
@CheckBackendToken
public class SysProfileController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    SystemOssImagesConfig systemOssImagesConfig;

    @Autowired
    private AliOSS aliOSS;

    /**
     * 个人信息
     */
    @GetMapping
    public R<JSONObject> profile()
    {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", user);
        jsonObject.put("roleGroup", userService.selectUserRoleGroup(username));
        jsonObject.put("postGroup", userService.selectUserPostGroup(username));
        return R.ok(jsonObject);
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public R updateProfile(@RequestBody SysUser user)
    {
        return toResult(userService.updateUserInfo(user), "修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public R updatePwd(String oldPassword, String newPassword)
    {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        String password = user.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password))
        {
            return R.fail("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password))
        {
            return R.fail("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(username, SecurityUtils.encryptPassword(newPassword)) > 0)
        {
            return R.ok();
        }
        return R.fail("修改密码异常，请联系管理员");
    }

    @RequestMapping(method = RequestMethod.POST , value = "/avatar")
    @ResponseBody
    public R<ImageResult> avatar(MultipartFile file){
        String username = SecurityUtils.getUsername();

        ResData res = aliOSS.ImgUpdateOriginal(file,"Avatar-",1024*1024*3);
        String ImgName;
        if (res.getCode()== ErrorType.SUCCESS.getCodeVal()){
            //上传成功
            ImgName = res.getMsg();
        }else if (res.getCode()==ErrorType.NO_FILE.getCodeVal()){
            return R.fail("上传失败 图片不存在!");
        }else {
            return R.fail("上传失败 请检图片大小及格式!");
        }
        String imgUrl=systemOssImagesConfig.getBaseImgUrl() + systemOssImagesConfig.getUeditor()+ImgName;
        ImageResult imageResult =  new ImageResult();
        imageResult.setState("SUCCESS");
        imageResult.setUrl(imgUrl);
        imageResult.setTitle(systemOssImagesConfig.getBaseImgUrl() + systemOssImagesConfig.getUeditor() );
        imageResult.setOriginal(ImgName);
        if(userService.updateUserAvatar(username,imgUrl)){
            return R.ok(imageResult);
        }else{
            return R.fail("上传失败 请确认当前登录信息是否准确!");
        }
    }
}
