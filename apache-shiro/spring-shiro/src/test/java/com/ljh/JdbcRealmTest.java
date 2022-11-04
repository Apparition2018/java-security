package com.ljh;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * JdbcRealmTest
 *
 * @author Arsenal
 * created on 2021/2/7 0:09
 */
public class JdbcRealmTest {

    private final DruidDataSource dataSource = new DruidDataSource();

    @Before
    public void initDataSource() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testJdbcRealm() {
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        // 查询密码
        String sql = "select password from user where username = ?";
        jdbcRealm.setAuthenticationQuery(sql);
        // 查询角色
        sql = "select r.rname from user u join user_role ur on u.uid = ur.uid join role r on ur.rid = r.rid where u.username = ?";
        jdbcRealm.setUserRolesQuery(sql);
        // 查询权限
        sql = "select p.name from permission p join permission_role pr on p.pid = pr.pid join role r on r.rid = pr.rid where r.rname = ?";
        jdbcRealm.setPermissionsQuery(sql);

        // 1.构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("admin", new Md5Hash("123", "salt").toString());
        subject.login(token);
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        // 检查角色
        subject.checkRole("admin");
        // 检查权限
        subject.checkPermission("add");

        subject.logout();
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
    }
}
