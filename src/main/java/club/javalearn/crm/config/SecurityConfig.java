package club.javalearn.crm.config;

import club.javalearn.crm.security.AccessDecisionManagerImpl;
import club.javalearn.crm.security.DefaultAccessDeniedHandler;
import club.javalearn.crm.security.logout.DefaultLogoutSuccessHandler;
import club.javalearn.crm.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * crm
 *
 * @author king-pan
 * @date 2018-05-31
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private static final String KEY = "javalearn.club";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;


    @Autowired
    private SecurityProperties securityProperties;


    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "**/favicon.ico", "/assets/**").permitAll();
        //开启自动配置的登录功能,如果没有登录，没有权限就会跳转到登录页面
        http.formLogin().loginPage("/loginPage").loginProcessingUrl("/login").permitAll();
        //开启自动配置的注销功能
        //1、访问/logout 表示用户注销，清空session
        http.logout().logoutSuccessHandler(new DefaultLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl())).deleteCookies("JSESSIONID").permitAll();
        //开启了自动配置的记住我功能
        http.rememberMe().tokenRepository(persistentTokenRepository());
        http.csrf().disable().exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
        //自定义过滤器
        MyFilterSecurityInterceptor filterSecurityInterceptor =
                new MyFilterSecurityInterceptor(securityMetadataSource, accessDecisionManager(), authenticationManagerBean());
        //在适当的地方加入
        http.addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class);
    }



    /**
     * 投票器
     */
    private AccessDecisionManager accessDecisionManager() {
        AccessDecisionManager accessDecisionManager = new AccessDecisionManagerImpl();
        return accessDecisionManager;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() {
        AuthenticationManager authenticationManager = null;
        try {
            authenticationManager = super.authenticationManagerBean();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authenticationManager;
    }

    /**
     * 认证信息管理
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception 异常
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(myAuthenticationProvider());
    }

    /**
     * 注入我们自己的AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider myAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        // 设置密码加密方式
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 加密
        return new BCryptPasswordEncoder();
    }

    @Bean
    AccessDeniedHandler getAccessDeniedHandler() {
        return new DefaultAccessDeniedHandler();
    }

    /**
     * 自定义过滤器
     */
    public static class MyFilterSecurityInterceptor extends FilterSecurityInterceptor {

        private MyFilterSecurityInterceptor(FilterInvocationSecurityMetadataSource securityMetadataSource, AccessDecisionManager accessDecisionManager, AuthenticationManager authenticationManager) {
            this.setSecurityMetadataSource(securityMetadataSource);
            this.setAccessDecisionManager(accessDecisionManager);
            this.setAuthenticationManager(authenticationManager);

        }
    }

    /**
     * 记住我功能的token存取器配置
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
		// 第一次需要执行，创建表
        //tokenRepository_setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
