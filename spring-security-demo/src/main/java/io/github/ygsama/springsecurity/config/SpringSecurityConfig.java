package io.github.ygsama.springsecurity.config;

import io.github.ygsama.springsecurity.config.authentication.UserAuthenticationProvider;
import io.github.ygsama.springsecurity.config.authorize.AuthorizeSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

/**
 * spring security 配置文件
 * 注意观察 INFO 级别日志 ： 以" Creating filter chain: ...."
 * <p>
 * [org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter,
 * org.springframework.security.web.context.SecurityContextPersistenceFilter,
 * org.springframework.security.web.header.HeaderWriterFilter,
 * org.springframework.security.web.authentication.logout.LogoutFilter,
 * org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter,
 * org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter,
 * org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter,
 * org.springframework.security.web.authentication.www.BasicAuthenticationFilter,
 * org.springframework.security.web.savedrequest.RequestCacheAwareFilter@204624aa,
 * org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter,
 * org.springframework.security.web.authentication.AnonymousAuthenticationFilter,
 * org.springframework.security.web.session.SessionManagementFilter,
 * org.springframework.security.web.access.ExceptionTranslationFilter,
 * org.springframework.security.web.access.intercept.FilterSecurityInterceptor]
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserAuthenticationProvider userAuthenticationProvider;


    /**
     * 启动时，不拦截静态文件
     * Creating filter chain: Ant [pattern='/js/**'], []
     * Creating filter chain: Ant [pattern='/css/**'], []
     * Creating filter chain: Ant [pattern='/images/**'], []
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    /**
     * 配置的请求走这过滤器链，此处配置是第一步认证，还需要第二步鉴权
     * 此处 permitAll() 相当于匿名免认证，框架会默认他是一个匿名角色
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //访问首页不需要认证
                .antMatchers("/", "/swagger-ui.html", "/v2/**", "/webjars/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()  // 其他页面需要认证
                .and()
                .logout().permitAll()    //退出不需要权限
                .and()
                .httpBasic()
                .and()
                .formLogin().permitAll()    //支持表单登陆
                .and()
                .csrf().disable()
                .addFilterAt(new AuthorizeSecurityInterceptor(), FilterSecurityInterceptor.class)
        ;   //关闭默认的csrf认证
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(userAuthenticationProvider);  // 自定义provider
        auth.eraseCredentials(false);           // 不删除凭据，以便记住用户
    }


    /**
     * 手动在拦截器中配置注册一个单例的bean对象，避免每次都重新生成
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
