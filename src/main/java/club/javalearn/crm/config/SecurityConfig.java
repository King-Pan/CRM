package club.javalearn.crm.config;

import club.javalearn.crm.security.DefaultAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * crm
 *
 * @author king-pan
 * @date 2018-05-31
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private static final String KEY = "javalearn.club";

    @Autowired
    private UserDetailsService userDetailsService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/assets/**").permitAll()
                .antMatchers( HttpMethod.POST,"/login").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                //登录后可以访问任何路径
                .anyRequest().authenticated();

                http.formLogin().loginPage("/loginPage").loginProcessingUrl("/login").permitAll();
                http.logout().logoutSuccessUrl("/login?logout");
                //开启了自动配置的记住我功能
                http.rememberMe();
                http.csrf().disable().exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());

                //开启自动配置的注销功能
                //1、访问/logout 表示用户注销，清空session
                //and().logout().permitAll().and()
                //开启自动配置的登录功能,如果没有登录，没有权限就会跳转到登录页面
                //.formLogin()
                //.usernameParameter("userName").passwordParameter("password").loginPage("/login").defaultSuccessUrl("/index").permitAll()
                //.failureUrl("/login?error").permitAll();
    }
    //.loginPage("/login").loginProcessingUrl("/login")

    /**
     * 认证信息管理
     * @param auth
     * @throws Exception
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
    public AuthenticationProvider myAuthenticationProvider(){
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
}
