package com.mr.tusstar.config.Shiro.Realm;

import com.mr.tusstar.entity.User;
import com.mr.tusstar.service.UserService;
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
 * @create 2020-07-19 21:21
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 授权
     * @param principalCollection 当前用户的标识信息
     * @return 当前用户的权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();
        if (principal.equals("user")) {
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addRole("user");
            return authorizationInfo;
        }
        else {
            return null;
        }
    }

    /**
     * 认证
     * @param authenticationToken 登录令牌
     * @return AuthenticationInfo
     * @throws AuthenticationException 登录令牌(例如用户名或密码)错误
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1. 获取 Token(令牌)
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        // 2. 查询数据库中的用户信息
        User user = userService.queryByPhoneAndPassword(userToken.getUsername(), String.valueOf(userToken.getPassword()));
        // 没有这个人时抛出异常
        if (user == null) {
            // System.out.println("用户表中不存在该用户!");
            return null;
        }
        else {
            // 设置 Session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userPhone", user.getPhone());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userType", "user");

            // principal: 认证的实体信息
            String principal = "user";
            // realmName: 当前 realm对象的 name
            String realmName = getName();
            // credentials: 密码/数字证书等
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, user.getPassword(), realmName);
            return authenticationInfo;
        }
    }
}
