package com.ljh.shiro.realm;

import com.ljh.dao.UserDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * CustomRealm
 *
 * @author Arsenal
 * created on 2021/2/7 1:05
 */
public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserDao userDao;

    Map<String, String> userMap = new HashMap<>(16);

    {
        // 凭证加盐再md5
        userMap.put("Mark", new Md5Hash("123456", "salt").toString());
        super.setName("customRealm");
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userName = (String) principalCollection.getPrimaryPrincipal();
        // 从数据库或缓存中获取角色数据
        Set<String> roles = this.getRolesByUserName(userName);
        Set<String> permissions = this.getPermissionsByRoles(roles);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 模拟数据库查询角色
     *
     * @param userName 用户名
     * @return 角色集合
     */
    private Set<String> getRolesByUserName(String userName) {
//        List<String> roles = userDao.queryRolesByUserName(userName);
//        return new HashSet<>(roles);
        Set<String> set = new HashSet<>();
        set.add("admin");
        set.add("user");
        return set;
    }

    /**
     * 模拟数据库查询权限
     *
     * @param roles 角色集合
     * @return 权限集合
     */
    private Set<String> getPermissionsByRoles(Set<String> roles) {
        Set<String> sets = new HashSet<>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.从主体传过来的认证信息中，获得用户名
        String userName = (String) authenticationToken.getPrincipal();

        // 2.通过用户名到数据库中获得凭证
        String password = this.getPasswordByUserName(userName);
        if (password == null) {
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, password, "customRealm");
        // 设置凭证的盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("salt"));
        return authenticationInfo;
    }

    /**
     * 模拟数据库查询凭证
     *
     * @param userName 用户名
     * @return 凭证
     */
    private String getPasswordByUserName(String userName) {
//        User user = userDao.getUserByUserName(userName);
//        if (user != null) {
//            return user.getPassword();
//        }
//        return null;
        return userMap.get(userName);
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123", ByteSource.Util.bytes("salt"));
        System.out.println(md5Hash);
    }
}
