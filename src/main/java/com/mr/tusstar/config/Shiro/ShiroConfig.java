package com.mr.tusstar.config.Shiro;

import com.mr.tusstar.config.Shiro.Realm.CompanyUserRealm;
import com.mr.tusstar.config.Shiro.Realm.UserRealm;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Qi
 * @create 2020-07-19 21:19
 */

@Configuration
public class ShiroConfig {

    // 1. 注册自定义的Realm对象
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }
    @Bean
    public CompanyUserRealm companyUserRealm() {
        return new CompanyUserRealm();
    }

    // 2. 创建实现了Authenticator的modularRealmAuthenticator类的对象(用于多个Realm的情况)
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        ModularRealmAuthenticator modularRealmAuthenticator=new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    // 3. 注册 DefaultWebSecurityManager
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        // 3.1 创建安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 3.2 设置 Authenticator
        securityManager.setAuthenticator(modularRealmAuthenticator());
        // 3.3 关联 Realm (多个Realm需要使用列表)
        List<Realm> realms = new LinkedList<>();
        realms.add(userRealm());
        realms.add(companyUserRealm());
        // 3.4 注入Realm到securityManager中
        securityManager.setRealms(realms);
        return securityManager;
    }

    // 4. 设置 ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        // 4.1 创建 Shiro 过滤工厂 Bean
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 4.2 给过滤工厂设置安全管理器
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 4.3 添加 Shiro 的内置过滤器
        Map<String, String> filterMap = new LinkedHashMap<>();

        // 4.4 添加过滤器映射
        // 从数据库中查
        // filterMap.put("/user/listRoles","roles[user]"); 用于测试普通角色权限
        // filterMap.put("/company/listRoles","roles[companyuser]"); // 用于测试企业角色权限
        filterFactoryBean.setFilterChainDefinitionMap(filterMap);

        // 4.5 设置: 没有认证时跳转到登录页面
        filterFactoryBean.setLoginUrl("/validate/noLogin");
        // 4.6 设置: 没有授权时跳转到未授权页面
        filterFactoryBean.setUnauthorizedUrl("/validate/noAuthz");

        return filterFactoryBean;
    }

    /**
     * 开启权限验证注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
