# Apache Shiro

## 参考网站
1. [shiro笔记一](https://blog.csdn.net/u011781521/article/details/55094751)
2. [shiro笔记二](https://blog.csdn.net/u011781521/article/details/74857440)
3. [shiro笔记三](https://blog.csdn.net/u011781521/article/details/74892074)
4. [shiro笔记四](https://blog.csdn.net/u011781521/article/details/74907197)
5. [shiro笔记五](https://blog.csdn.net/u011781521/article/details/75172983)
---
## 主要功能
![功能和特征](doc/Concerns&Features.png)
1. Primary Concerns
    ```
    1. Authentication       身份认证
    2. Authorization        授权
    3. Session Management   会话管理
    4. Cryptography         加密
    ```
2. Supporting Features
    ```
    1. Web Support          Web 支持
    2. Caching              缓存
    3. Concurrency          并发
    4. Testing              测试
    5. "Run As"             一个用户"假装"为另外一个用户的身份
    6. Remember Me          记住用户身份，使得下次不需要再登录
    ```
- Shiro 不会去维护用户、维护权限，需要我们自己去设计和提供，然后通过相应的接口注入给 Shiro
---
## 主要组件
![主要组件](./doc/主要组件.png)
```
1. Subject              主体
   - 与当前应用交互的任何东西都可以是 Subject，所有 Subject 都绑定到 SecurityManager
   - 门面
2. SecurityManager      安全管理器
   - 所有与安全有关的操作都会与 SecurityManager 交互；Shiro 的核心，也负责与其它组件进行交互
   - 前端控制器
3. Realm                域
   - Shiro 从 Realm 获取认证和授权的相关数据（用户、角色、权限），并完成相应的认证和授权操作
   - 安全数据源
```
---
## 架构
![架构](./doc/架构.jpg)
```
1. Subject              主体
2. Security Manager     安全管理器
3. Authenticator        认证器
   - Subject 的信息由 AuthenticationToken 来储存，由 AuthenticationStrategy 进行认证
   - 用户可以自定义实现
4. Authorizer           授权器
5. Session Manager      会话管理器
   - DefaultSessionManager             JavaSE 环境
   - ServletContainerSessionManager    Web 环境，直接使用 Servlet 会话
   - DefaultWebSessionManager          Web 环境，自己维护会话
6. SessionDao           CRUD
7. Cache Manger         缓存管理器
8. Pluggable Realms     可扩展领域
   - 可配置1个或多个，如果只有1个 Realm，则无需配置 AuthenticationStrategy ?
9. Cryptography         加密
```
---
## Authentication 认证
![Authentication 流程图](./doc/Authentication.png)

---
## Authorization 授权
![Authorization 流程图](./doc/Authorization.png)

```
1. Subject     主体
2. Resource    资源
3. Permission  许可
4. Role        角色

Subject → Role → Permission
```
---
## [Filter](https://www.cnblogs.com/yoohot/p/6085830.html)
![Filter](./doc/Filter.png)
1. [内置 Filter](https://blog.csdn.net/l_cl_java/article/details/80425396)
    1. 认证 Filter：
    ```
        anon        AnonymousFilter                 可匿名
        authc       FormAuthenticationFilter        身份认证
        authBasic   BasicHttpAuthenticationFilter   HTTP Basic 认证
        user        UserFilter                      当前存在用户；开启 RememberMe 后，关闭浏览器重新访问是 user，而不是 authc
        logout      LogoutFilter                    退出登录
    ```
    2. 授权 Filter：
    ```
        perms       PermissionsAuthorizationFilter  权限
        roles       RolesAuthorizationFilter        角色
        ssl         SslFilter                       HTTPS
        port        PortFilter                      端口
        rest        HttpMethodPermissionFilter      HttpMethod；如 rest[user] 相当于 perms[user:get]，perms[user:post] 等
    ```
2. 自定义 Filter
    1. 认证 Filter extends AuthenticatingFilter
    2. 授权 Filter extends AuthorizationFilter
---
## Session Manager
![Session Manager & Security Manager](./doc/SessionManager&SecurityManager.png)
- Shiro 提供了完整的企业级会话管理功能，不依赖于底层容器 (Tomcat)，JavaSE JavaEE 环境都可以使用。
- 提供了会话管理，会话事监听，会话存储/持久化，容器无关的集群，失效过期支持，对Web的透明支持，SSO单点登录等
- 自定义 SessionManager extends DefaultWebSessionManager
---
## Session Dao
![Session Dao](./doc/SessionDao.png)
- 自定义 SessionDao extends AbstractSessionDAO
- 第三方 SessionDao 可使用 org.crazycake:shiro-redis
---
## Cache Manager
![Cache Manager](./doc/CacheManager1.png)
![Cache Manager](./doc/CacheManager2.png)
- 自定义 CacheManager extends CacheManager
- 第三方 CacheManager 可使用 org.crazycake:shiro-redis
---
## Realm
![Realm](./doc/Realm.png)

---
## 主要代码编写
1. User, Role, Permission
    - 自定义 用户，角色，权限 
2. AuthRealm
    - 自定义认证和授权的Realm，继承于 AuthorizingRealm
3. CredentialMatcher
    - 自定义密码校验规则，继承于 SimpleCredentialsMatcher
4. ShiroConfiguration
    - CredentialsMatcher
    - AuthRealm
        - 设置 CredentialsMatcher
        - 设置 CacheManager
    - SessionDao（可选）
    - SessionManager（可选）
        - 设置 SessionDao
    - CacheManager（可选）
    - RememberMeManager（可选）
    - SecurityManager
        - 设置 AuthRealm
        - 设置 SessionManager（可选）
        - 设置 CacheManager（可选）
        - 设置 RememberMeManager（可选）
    - ShiroFilterFactoryBean
        - 注入 SecurityManager
        - 设置 url，如 loginUrl，successUrl，unauthorizedUrl 等
        - 设置 FilterChainDefinition，哪个请求使用哪个 Filter
    - 注解权限控制 @RequiresRoles，@RequiresPermissions 等
        - DefaultAdvisorAutoProxyCreator，代理生成器，相当于切面
        - AuthorizationAttributeSourceAdvisor，相当于切点
            - 注入 SecurityManager
5. 注解
   - @RequiresRoles
   - @RequiresPermissions
>#### 参考网站
>1. [Shiro 权限管理框架（一）：Shiro的基本使用](https://www.guitu18.com/post/2019/07/26/43.html)
>2. [Shiro 权限管理框架（二）：Shiro结合Redis实现分布式环境下的Session共享](https://www.guitu18.com/post/2019/07/28/44.html)
>3. [Shiro 权限管理框架（三）：Shiro中权限过滤器的初始化流程和实现原理](https://www.guitu18.com/post/2019/08/01/45.html)
>4. [Shiro 权限管理框架（四）：深入分析 Shiro 中的 Session 管理](https://www.guitu18.com/post/2019/08/08/46.html)
>5. [Shiro 权限管理框架（五）：自定义 Filter 实现及其问题排查记录](https://www.guitu18.com/post/2020/01/06/64.html)
---
## 项目启动遇到的问题
1. [找不到 jsp 页面](https://www.pianshen.com/article/733790820/)
2. [maven 多子模块项目整合 jsp](https://blog.csdn.net/hp_yangpeng/article/details/89067596)
---