package com.ljh;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * AuthTest
 * Shiro安全框架入门：https://www.imooc.com/learn/977
 *
 * @author Arsenal
 * created on 2021/2/6 23:37
 */
public class AuthTest {

    private final SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("Mark", "123456", "admin", "user");
    }

    @Test
    public void testAuth() {
        // 1.构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
        // 登录
        subject.login(token);
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        // 检查角色
        subject.checkRole("admin");
        // 检查权限
        subject.checkRoles("admin", "user");

        // 登出
        subject.logout();
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
    }
}
