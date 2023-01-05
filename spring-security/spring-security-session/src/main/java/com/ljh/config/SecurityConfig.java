package com.ljh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * SecurityConfig
 *
 * @author ljh
 * created on 2022/2/10 14:43
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;

    public SecurityConfig(UserDetailsService userDetailsService, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 建表语句 JdbcTokenRepositoryImpl.CREATE_TABLE_SQL
        // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * RuoYi (SecurityConfig)
     * <pre>
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     * </pre>
     *
     * @see <a href="https://blog.csdn.net/qq_36221788/article/details/115497887">HttpSecurity 详解</a>
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Form Login：https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html
        // Form Login：https://www.baeldung.com/spring-security-login
        httpSecurity
                .formLogin()
                .loginPage("/iLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/user/login")
                .failureUrl("/authentication/login?failed")
                .defaultSuccessUrl("/success.html")
                .permitAll();

        // Logout：https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html
        // CSRF Logging Out：https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#servlet-considerations-csrf-logout
        // Logout：https://www.baeldung.com/spring-security-logout
        // Manual Logout：https://www.baeldung.com/spring-security-manual-logout
        // Custom Logout Handler：https://www.baeldung.com/spring-security-custom-logout-handler
        httpSecurity
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/test/hello")
                .permitAll();

        // 授权 http servlet request：https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "/test/hello").permitAll()
                .antMatchers("/test/index").hasAnyAuthority("admin")
                .antMatchers("/test/index").hasAnyRole("sale")
                .anyRequest().authenticated();

        // remember-me：https://docs.spring.io/spring-security/reference/servlet/authentication/rememberme.html
        // remember-me 基于cookie：https://www.baeldung.com/spring-security-remember-me
        // remember-me 基于持久化：https://www.baeldung.com/spring-security-persistent-remember-me
//        httpSecurity
//                .rememberMe()
//                .key("remember-me")
//                .tokenRepository(persistentTokenRepository())
//                .userDetailsService(userDetailsService)
//                .tokenValiditySeconds(3600 * 6);

        httpSecurity
                // CSRF：https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html
                // CSRF Protection：https://www.baeldung.com/spring-security-csrf
                // .csrf().disable()
                // 异常处理
                .exceptionHandling()
                // 拒绝访问页面
                .accessDeniedPage("/403.html");
    }
}
