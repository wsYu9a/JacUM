package com.example.shiro.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
    /**
     * 自定义Realm对象
     */
    @Autowired
    private UserRealm userRealm;

    /**
     *安全管理器
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        //创建了一个 DefaultWebSecurityManager 对象 securityManager
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联userRealm
        securityManager.setRealm(userRealm);
        //设置rememberMe
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * cookie 属性设置
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("ShiroTest_RememberMe");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        return cookie;
    }

    /**
     * Shiro的cookie管理对象
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey("1234567890987654".getBytes());
        return cookieRememberMeManager;
    }

    /**
     * 自定义角色过滤器
     * @return
     */
    @Bean("shirFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        //创建了一个 ShiroFilterFactoryBean 对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //未经身份验证时，将重定向到 /login 这个URL,如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");

        //添加shiro内置过滤器
        /*
           anon 无需认证可访问
           authc 必须认证才能访问
           user  需有rememberMe功能才可使用
           perms 有对应权限可访问
           roles  有对应角色才可访问
         */
        LinkedHashMap<String,String> fc = new LinkedHashMap<>();

        //拦截
        fc.put("/testAdmin", "roles[admin]");
        fc.put("/add","perms[user:add]");
        fc.put("/update","perms[user:update]");
        fc.put("/select","perms[user:select]");
        fc.put("/delete","perms[user:delete]");

        //认证
        fc.put("/login", "anon");
        fc.put("/logout","logout");
        fc.put("/**", "authc");

        //rememberMe
        fc.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(fc);


        return shiroFilterFactoryBean;
    }




}
