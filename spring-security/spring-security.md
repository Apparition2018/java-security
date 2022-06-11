# Spring Security

---
## 参考网站
1. [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
2. [Spring Security 中文文档](https://www.chendalei.com/spring-security-Reference/spring-security-Reference-5.2.0.RELEASE-zh.html)
3. [Spring Security | Baeldung](https://www.baeldung.com/category/spring/spring-security/)
4. [Spring Security | 尚硅谷](https://www.bilibili.com/video/BV15a411A7kP?p=1)
5. [Spring Security | 木兮同学](https://blog.csdn.net/qq_36221788/category_11009647.html)
6. [Spring Security without the WebSecurityConfigurerAdapter](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
---
## 主要功能
1. [身份认证 Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/index.html)
2. [授权 Authorization](https://docs.spring.io/spring-security/reference/servlet/authorization/index.html)
3. [防止漏洞利用](https://docs.spring.io/spring-security/reference/servlet/exploits/index.html)
4. [集成](https://docs.spring.io/spring-security/reference/servlet/integrations/index.html)
---
## 基本原理
- [Spring Security 本质是一个过滤器链](https://docs.spring.io/spring-security/reference/servlet/configuration/xml-namespace.html#ns-custom-filters)
```
CsrfFilter
UsernamePasswordAuthenticationFilter      处理身份认证提交的表单
RememberMeAuthenticationFilter
ExceptionTranslationFilter                处理过滤器链中抛出的 AccessDeniedException 和 AuthenticationException
FilterSecurityInterceptor                 方法级的权限过滤器，基本位于过滤链的最底部
```
- 过滤器如何进行加载
```
1. 配置过滤器 DelegatingFilterProxy
    1. void doFilter(ServletRequest, ServletResponse, FilterChain)
    2. Filter initDelegate(WebApplicationContext wac)
    3. Filter delegate = (Filter)wac.getBean(targetBeanName, Filter.class)
2. FilterChainProxy
    1. void doFilter(ServletRequest, ServletResponse, FilterChain)
    2. void doFilterInternal(ServletRequest, ServletResponse, FilterChain)
    3. List<Filter> filters = this.getFilters((HttpServletRequest)firewallRequest)
`````
---
## 认证
>### 设置用户名和密码三种方式
>1. application.properties
>```
>spring.security.user.name=user
>spring.security.user.password=123456
>```
>2. AuthenticationManagerBuilder#inMemoryAuthentication()
>```java
>@Configuration
>public class SecurityConfig extends WebSecurityConfigurerAdapter {
>   @Bean
>   PasswordEncoder passwordEncoder() {
>       return new BCryptPasswordEncoder();
>   }
>   @Override
>   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
>       BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
>       String encodePwd = passwordEncoder.encode("123");
>       auth.inMemoryAuthentication().withUser("user").password(encodePwd).roles("admin");
>   }
>}
>```
>3. AuthenticationManagerBuilder#userDetailsService()
>   1. @see SecurityConfig#configure
>   2. @see MyUserDetailService#loadUserByUsername
>### Remember-Me
>1. 建表：JdbcTokenRepositoryImpl.CREATE_TABLE_SQL
>2. 配置 SecurityConfig
>```java
>@Configuration
>public class SecurityConfig extends WebSecurityConfigurerAdapter {
>   private final DataSource dataSource;
>   public SecurityConfig(DataSource dataSource) {
>       this.dataSource = dataSource;
>   }
>   @Bean
>   public PersistentTokenRepository persistentTokenRepository() {
>       JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
>       jdbcTokenRepository.setDataSource(dataSource);
>       return jdbcTokenRepository;
>   }
>   @Override
>   protected void configure(HttpSecurity httpSecurity) throws Exception {
>       httpSecurity.rememberMe()
>           .tokenRepository(persistentTokenRepository())
>           .userDetailsService(userDetailsService)
>           .tokenValiditySeconds(3600 * 6);
>   }
>}
>```
## 授权
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        httpSecurity
                // 过滤请求
                .authorizeRequests()
                .antMatchers("/", "/test/hello", "/user/login").anonymous()
                .antMatchers("/test/index").hasAnyAuthority("admin")
                .antMatchers("/test/index").hasAnyRole("sale")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
    }
}
```
---
## 注解
1. @Secure
2. [@Pre 和 @Post 注解](https://docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html#el-pre-post-annotations)
    ```
    @PreAuthorize       方法执行前权限验证
    @PostAuthorize      方法执行后权限验证
    @PreFilter          集合数组类型参数过滤
    @PostFilter         集合数组类型返值过滤
    ```
3. [常见内置表达式](https://docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html#el-common-built-in)

| 注解                                        | 开启                                                 | JSR标准 | 允许SpEL表达式 |
|:------------------------------------------|:---------------------------------------------------|:------|:----------|
| @PreAuthorize<br/>@PostAuthorize          | @EnableGlobalMethodSecurity(securedEnabled = true) | No    | Yes       |
| @RolesAllowed<br/>@PermitAll</br>@DenyAll | @EnableGlobalMethodSecurity(prePostEnabled = true) | Yes   | No        |
| @Secured                                  | @EnableGlobalMethodSecurity(jsr250Enabled = true)  | No    | No        |
---
## 防止漏洞
1. [跨站请求伪造 CSRF](https://www.bilibili.com/video/BV15a411A7kP?p=19)
    - [浅谈 CSRF 攻击方式](https://www.cnblogs.com/hyddd/archive/2009/04/09/1432744.html)
    1. 有状态 API
    ```html
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    ```
    2. 无状态 API 
    ```
    httpSecurity.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    ```
    ```javascript
    const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');
    fetch(url, {
        method: 'POST',
        headers: { 'X-XSRF-TOKEN': csrfToken },
    })
    ```