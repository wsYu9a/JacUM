package com.example.shiro.config;

import com.example.shiro.pojo.User;
import com.example.shiro.service.UserService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了 -> 授权 doGetAuthorizationInfo");
        //1 创建对象，存储当前登录的用户的权限和角色
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //2 获取用户id
        User p = (User) principals.getPrimaryPrincipal();
        Integer uid = p.getId();
        //3 查询角色
        String userRole = userService.getUserRole(uid);
        //4 查询权限
        List<String> rolePerms = userService.getRolePerms(uid);
        //5 存储角色
        info.addRole(userRole);
        //6 存储权限
        info.addStringPermissions(rolePerms);
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了 -> 认证 doGetAuthenticationInfo");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //数据库获取用户信息
        User user = userService.getByUserName(userToken.getUsername());

        if (null == user) {
            return null;
        } else {
            String password = user.getPassword();
            //密码认证，shiro完成
            return new SimpleAuthenticationInfo(user, password, getName());
        }
    }
}
