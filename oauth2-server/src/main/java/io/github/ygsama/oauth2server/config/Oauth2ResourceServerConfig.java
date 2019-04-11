package io.github.ygsama.oauth2server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security Oauth2 资源服务配置类
 * <p>
 * {@link ResourceServerConfigurer}，内部关联了{@link ResourceServerSecurityConfigurer}和{@link HttpSecurity}
 * {@link ResourceServerSecurityConfigurer}
 * --创建{@link OAuth2AuthenticationProcessingFilter}，即OAuth2核心过滤器
 * ----获取access_token并填入security context
 * --且为{@link OAuth2AuthenticationProcessingFilter}提供了{@link AuthenticationManager}实现类
 * ----即{@link OAuth2AuthenticationManager}
 * -------内部维护了{@link ClientDetailsService}和{@link ResourceServerTokenServices}
 * ---------调用这个tokenServices来加载、读取token
 * --设置{@link TokenExtractor}默认的实现{@link BearerTokenExtractor}
 * ----分离出请求中包含的token。可以使用多种方式携带token，header，uri参数，表单
 * --相关的异常处理器，可以重写相关实现来自定义异常
 * ----重写的异常handler，在{@link EnableResourceServer}配置类中被覆盖
 */
@EnableResourceServer
@Configuration
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 用于配置对受保护的资源的访问规则
     * 默认情况下所有不在/oauth/**下的资源都是受保护的资源
     * {@link OAuth2WebSecurityExpressionHandler}
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/swagger-ui.html", "/v2/**", "/webjars/**", "/swagger-resources/**").permitAll()
                .antMatchers("/sys/callback/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .formLogin().permitAll() //允许为与基于表单的登录相关联的所有URL授予对所有用户的访问权限。
                .and()
                .httpBasic()
                .and()
                .logout().permitAll()
                .logoutUrl("/my/logout")
                .logoutSuccessUrl("/my/index")
                .invalidateHttpSession(true);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
