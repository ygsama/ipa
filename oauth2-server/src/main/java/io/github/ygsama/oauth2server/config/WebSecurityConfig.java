package io.github.ygsama.oauth2server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security 配置文件
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginAuthenticationProvider provider;

    /**
     * AuthenticationManagerBuilder
     * 用户认证的配置
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
//        auth.inMemoryAuthentication().withUser("zhangsan").password("$2a$10$qsJ/Oy1RmUxFA.YtDT8RJ.Y2kU3U4z0jvd35YmiMOAPpD.nZUIRMC").roles("USER");

//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("zhangsan").password("$2a$10$qsJ/Oy1RmUxFA.YtDT8RJ.Y2kU3U4z0jvd35YmiMOAPpD.nZUIRMC").roles("USER")
//                .and()
//                .withUser("admin").password("password").roles("ADMIN", "USER");

        auth.authenticationProvider(provider);  // 自定义provider
//        auth.eraseCredentials(false);           // 不删除凭据，以便记住用户
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * WebSecurity
     * 忽略静态资源
     * 添加FilterChainBuilder
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/plugins/**", "/favicon.ico");
//                .and()
//                .addSecurityFilterChainBuilder();
    }


    /**
     * HttpSecurity
     * 配置角色权限
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .requestMatchers()
//                .antMatchers("/login","/oauth/authorize","/re")
//                .and()
                .authorizeRequests()
                .antMatchers("/login","/oauth/authorize","/re").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/**").hasRole("USER")
                .and()
                .formLogin()
                .and()
                .httpBasic();
//                .usernameParameter("username") // default is username
//                .passwordParameter("password") // default is password
//                .loginPage("/authentication/login") // default is /login with an HTTP get
//                .failureUrl("/authentication/login?failed") // default is /login?error
//                .loginProcessingUrl("/authentication/login/process"); // default is /login;
    }


//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        User.UserBuilder builder = User.builder();
//        UserDetails user = builder.username("zhangsan").password("$2a$10$GStfEJEyoSHiSxnoP3SbD.R8XRowP1QKOdi.N6/iFEwEJWTQqlSba").roles("USER").build();
//        UserDetails admin = builder.username("lisi").password("$2a$10$GStfEJEyoSHiSxnoP3SbD.R8XRowP1QKOdi.N6/iFEwEJWTQqlSba").roles("USER", "ADMIN").build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));
        System.out.println(bCryptPasswordEncoder.encode("12345678"));
    }
}
