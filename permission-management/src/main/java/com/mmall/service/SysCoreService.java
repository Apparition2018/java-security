package com.mmall.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.mmall.beans.CacheKeyConstants;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.model.SysAcl;
import com.mmall.model.SysUser;
import com.mmall.util.JsonMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SysCoreService
 *
 * @author Arsenal
 * created on 2019/7/18 12:18
 */
@Service
public class SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysCacheService sysCacheService;

    // 获取当前用户权限列表
    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    // 获取角色已分配权限点
    public List<SysAcl> getRoleAclList(int roleId) {
        // 根据角色id获取权限id
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        // 根据权限id获取权限
        return sysAclMapper.getByIdList(aclIdList);
    }

    // 获取某用户权限列表
    public List<SysAcl> getUserAclList(int userId) {
        // 如果是超级管理员，返回所有权限
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        // 根据用户id获取角色id
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        // 根据角色id获取权限id
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        // 根据权限id获取权限
        return sysAclMapper.getByIdList(userAclIdList);
    }

    // 是否为超级管理员
    private boolean isSuperAdmin() {
        // 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
        // 可以是配置文件获取，可以指定某个用户，也可以指定某个角色
        SysUser sysUser = RequestHolder.getCurrentUser();
        return sysUser.getMail().contains("admin");
    }

    // 判断是否有权限访问这个url
    public boolean hasUrlAcl(String url) {
        // 如果是超级管理员，则有权限
        if (isSuperAdmin()) {
            return true;
        }
        // 根据url查找权限
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        // 查找不到权限，说明权限管理不关心这个url，所以可以访问
        if (CollectionUtils.isEmpty(aclList)) {
            return true;
        }

        // 获取当前用户权限列表(cache)
        List<SysAcl> userAclList = getCurrentUserAclListFromCache();
        // List<SysAcl> userAclList  →  Set<Integer> userAclIdSet
        Set<Integer> userAclIdSet = userAclList.stream().map(SysAcl::getId).collect(Collectors.toSet());

        boolean hasValidAcl = false;
        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
        for (SysAcl acl : aclList) {
            // 权限无效，continue
            if (acl == null || acl.getStatus() != 1) {
                continue;
            }
            // 判断一个用户是否具有某个权限点的访问权限
            hasValidAcl = true;
            if (userAclIdSet.contains(acl.getId())) {
                return true;
            }
        }
        return !hasValidAcl;
    }

    // 获取当前用户权限列表(cache)
    private List<SysAcl> getCurrentUserAclListFromCache() {
        int userId = RequestHolder.getCurrentUser().getId();
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(userId));
        if (StringUtils.isBlank(cacheValue)) {
            List<SysAcl> aclList = getCurrentUserAclList();
            if (CollectionUtils.isNotEmpty(aclList)) {
                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyConstants.USER_ACLS, String.valueOf(userId));
            }
            return aclList;
        }
        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
        });
    }
}
