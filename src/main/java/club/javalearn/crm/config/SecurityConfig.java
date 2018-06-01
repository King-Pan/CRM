package club.javalearn.crm.config;

import club.javalearn.crm.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * crm
 *
 * @author king-pan
 * @date 2018-05-31
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private static final String KEY = "javalearn.club";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/assets/**").permitAll()
                .antMatchers( "/loginPage").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                //登录后可以访问任何路径
                .anyRequest().authenticated();

                http.formLogin().loginPage("/loginPage");
                http.logout().logoutSuccessUrl("/login?logout");
                //开启了自动配置的记住我功能
                http.rememberMe();
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
        auth.userDetailsService(detailsService());
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 加密
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService detailsService(){
        return new UserServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(detailsService());
        // 设置密码加密方式
        //authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
