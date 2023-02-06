# Spring Security

---
## Reference
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Security 中文文档](https://www.chendalei.com/spring-security-Reference/spring-security-Reference-5.2.0.RELEASE-zh.html)
- [Spring Security | Baeldung](https://www.baeldung.com/category/spring/spring-security/)
- [Spring Security | 尚硅谷](https://www.bilibili.com/video/BV15a411A7kP?p=1)
- [Spring Security | 木兮同学](https://blog.csdn.net/qq_36221788/category_11009647.html)
- [Spring Security without the WebSecurityConfigurerAdapter](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
- [Spring Security 身份认证之 AuthenticationProvider](https://blog.csdn.net/qq_43753724/article/details/122979973)
---
## 主要功能
1. [身份认证 Authentication](#Authentication)
2. [授权 Authorization](#Authorization)
3. [防止漏洞利用](#Against)
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
## <a id="Authentication">[认证](https://docs.spring.io/spring-security/reference/servlet/authentication/index.html)</a>
### 设置用户名和密码三种方式
1. application.properties
```properties
spring.security.user.name=user
spring.security.user.password=123456
```
2. AuthenticationManagerBuilder#inMemoryAuthentication()
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePwd = passwordEncoder.encode("123");
        auth.inMemoryAuthentication().withUser("user").password(encodePwd).roles("admin");
    }
}
```
3. AuthenticationManagerBuilder#userDetailsService()
    1. @see [SecurityConfig#configure(AuthenticationManagerBuilder auth)](spring-security-session/src/main/java/com/ljh/config/SecurityConfig.java)
    2. @see [MyUserDetailService#loadUserByUsername](spring-security-session/src/main/java/com/ljh/service/MyUserDetailService.java)
### Remember-Me
1. 建表：JdbcTokenRepositoryImpl.CREATE_TABLE_SQL
2. 配置 [SecurityConfig](spring-security-session/src/main/java/com/ljh/config/SecurityConfig.java)
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.rememberMe()
            .tokenRepository(persistentTokenRepository())
            .userDetailsService(userDetailsService)
            .tokenValiditySeconds(3600 * 6);
    }
}
```
- Reference
    - [Remember-Me Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/rememberme.html)
    - [Remember Me 基于 Cookie](https://www.baeldung.com/spring-security-remember-me)
    - [Remember Me 基于持久化](https://www.baeldung.com/spring-security-persistent-remember-me)
---
## <a id="Authorization">[授权](https://docs.spring.io/spring-security/reference/servlet/authorization/index.html)</a>
### [授权 HTTP 请求](https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html)
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        httpSecurity
                // authorizeRequests() 将被弃用
                .authorizeHttpRequests()
                .antMatchers("/", "/test/hello", "/user/login").anonymous()
                .antMatchers("/test/index").hasAnyAuthority("admin")
                .antMatchers("/test/index").hasAnyRole("sale")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
    }
}
```
### 注解
1. @Secured
2. [@Pre & @Post](https://docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html#el-pre-post-annotations)
```
@PreAuthorize        方法执行前权限验证
@PostAuthorize       方法执行后权限验证
@PreFilter           集合数组类型参数过滤
@PostFilter          集合数组类型返值过滤
```
3. [常见内置表达式](https://docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html#el-common-built-in)

| 注解                                        | 开启                                                 | JSR标准 | 允许SpEL表达式 |
|:------------------------------------------|:---------------------------------------------------|:------|:----------|
| @PreAuthorize<br/>@PostAuthorize          | @EnableGlobalMethodSecurity(securedEnabled = true) | No    | Yes       |
| @RolesAllowed<br/>@PermitAll<br/>@DenyAll | @EnableGlobalMethodSecurity(prePostEnabled = true) | Yes   | No        |
| @Secured                                  | @EnableGlobalMethodSecurity(jsr250Enabled = true)  | No    | No        |
---
---
## <a id="Against">[防止漏洞攻击](https://docs.spring.io/spring-security/reference/servlet/exploits/index.html)</a>
### [跨站请求伪造 CSRF (Cross-Site Request Forgery)](https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html)
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
- Reference
    - [A Guide to CSRF Protection in Spring Security](https://www.baeldung.com/spring-security-csrf)
    - [CSRF Protection with Spring MVC and Thymeleaf](https://www.baeldung.com/csrf-thymeleaf-with-spring-security)
    - [尚硅谷-SpringSecurity-CSRF功能](https://www.bilibili.com/video/BV15a411A7kP?p=19) 
    - [浅谈 CSRF 攻击方式](https://www.cnblogs.com/hyddd/archive/2009/04/09/1432744.html)
### [跨站脚本 XSS (Cross-Site Script)](https://www.baeldung.com/spring-prevent-xss)
1. [X-XSS-Protection](https://docs.spring.io/spring-security/reference/servlet/exploits/headers.html#servlet-headers-xss-protection)
2. [Content Security Policy (CSP)](https://docs.spring.io/spring-security/reference/servlet/exploits/headers.html#servlet-headers-xss-protection)
---
