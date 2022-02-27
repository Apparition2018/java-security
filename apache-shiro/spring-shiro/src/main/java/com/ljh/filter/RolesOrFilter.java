package com.ljh.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * RolesOrFilter
 * 自定义过滤器：只要含有其中一个角色则认证通过
 *
 * @author com.ljh
 * created on 2021/3/1 12:03
 */
public class RolesOrFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        String[] roles = (String[]) o;
        if (roles == null || roles.length == 0) {
            return true;
        }
        for (String role : roles) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }
}
