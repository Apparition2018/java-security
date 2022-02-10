# Spring Security

---
## 参考网站
1. [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
2. [Spring Security 中文文档](https://www.chendalei.com/spring-security-Reference/spring-security-Reference-5.2.0.RELEASE-zh.html)
3. [Spring Security | 尚硅谷](https://www.bilibili.com/video/BV15a411A7kP?p=1)
4. [Spring Security | 木兮同学](https://blog.csdn.net/qq_36221788/category_11009647.html)
---
## 主要功能
1. [身份认证 Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/index.html)
2. [授权 Authorization](https://docs.spring.io/spring-security/reference/servlet/authorization/index.html)
3. [防止漏洞利用](https://docs.spring.io/spring-security/reference/servlet/exploits/index.html)
4. [集成](https://docs.spring.io/spring-security/reference/servlet/integrations/index.html)
---
## 基本原理
1. Spring Security 本质是一个过滤器链
```
FilterSecurityInterceptor               方法级的权限过滤器，基本位于过滤链的最底部
ExceptionTranslationFilter              异常过滤器，用来处理在认证授权过程中抛出的异常
UsernamePasswordAuthenticationFilter    对 /login 的 POST 请求做拦截，检验表单中的用户名和密码
```
2. 过滤器如何进行加载
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
3. UserDetailService
```
1. 创建类 extends UsernamePasswordAuthenticationFilter，重写三个方法
2. 创建类 implements UserDetailService，编写查询 org.springframework.security.core.userdetails.User
```
4. PasswordEncoder：密码校验和密码加密流程
---
## 认证
*设置用户名和密码三种方式*
1. application.properties
```
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
    1. @see SecurityConfig#configure
    2. @see MyUserDetailService#loadUserByUsername
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
## 注释
1. @Secure
2. [@Pre 和 @Post 注释](https://docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html#el-pre-post-annotations)
3. [常见内置表达式](https://docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html#el-common-built-in)