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
        // 也可以从数据库中查
        filterMap.put("/user/register","anon");
        filterMap.put("/user/emailCode/*","anon");
        filterMap.put("/user/jobList","anon");
        filterMap.put("/user/job/*","anon");
        filterMap.put("/user/getAllCompanies","anon");
        filterMap.put("/user/companyDetail/*","anon");
        filterMap.put("/user/postedJobs/*","anon");
        filterMap.put("/user/searchJobs","anon");
        filterMap.put("/user/getJobTypeNum","anon");
        filterMap.put("/user/login","anon");

        filterMap.put("/company/register","anon");
        filterMap.put("/company/emailCode/*","anon");
        filterMap.put("/company/jobList","anon");
        filterMap.put("/company/job/*","anon");
        filterMap.put("/company/getAllCompanies","anon");
        filterMap.put("/company/companyDetail/*","anon");
        filterMap.put("/company/postedJobs/*","anon");
        filterMap.put("/company/searchJobs","anon");
        filterMap.put("/company/getJobTypeNum","anon");
        filterMap.put("/company/login","anon");

        filterMap.put("/user/**","roles[user]");
        filterMap.put("/company/**","roles[companyuser]");
        filterFactoryBean.setFilterChainDefinitionMap(filterMap);

        // 4.5 设置: 没有认证时返回还未登录的信息
        filterFactoryBean.setLoginUrl("/validate/noLogin");
        // 4.6 设置: 没有权限时返回权限不够的信息
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
