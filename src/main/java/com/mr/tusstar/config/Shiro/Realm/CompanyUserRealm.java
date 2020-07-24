package com.mr.tusstar.config.Shiro.Realm;

import com.mr.tusstar.entity.CompanyUser;
import com.mr.tusstar.service.CompanyUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Qi
 * @create 2020-07-19 21:22
 */
public class CompanyUserRealm extends AuthorizingRealm {

    @Autowired
    CompanyUserService companyUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();
        if (principal.equals("companyuser")) {
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addRole("companyuser");
            return authorizationInfo;
        }
        else {
            return null;
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1. 获取 Token(令牌)
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        // 2. 查询数据库中的用户信息
        CompanyUser companyUser = companyUserService.queryByEmailAndPassword(userToken.getUsername(), String.valueOf(userToken.getPassword()));
        // 没有这个人时抛出异常
        if (companyUser == null) {
            // System.out.println("企业用户表中不存在该用户!");
            return null;
        }
        else {
            // 设置 Session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("companyEmail", companyUser.getEmail());
            session.setAttribute("companyName", companyUserService.selectNameByEmail(companyUser.getEmail()));
            session.setAttribute("userType", "company");
            session.setAttribute("companyId", companyUser.getId());

            // principal: 认证的实体信息
            String principal = "companyuser";
            // realmName: 当前 realm对象的 name
            String realmName = getName();
            // credentials: 密码/数字证书等
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, companyUser.getPassword(), realmName);
            return authenticationInfo;
        }
    }
}
