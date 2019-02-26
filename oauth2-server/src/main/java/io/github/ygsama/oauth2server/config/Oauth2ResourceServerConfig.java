package io.github.ygsama.oauth2server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * 配置资源服务器
 */
@EnableResourceServer
@Configuration
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    /**
     * 用于配置对受保护的资源的访问规则
     * 默认情况下所有不在/oauth/**下的资源都是受保护的资源
     * {@link OAuth2WebSecurityExpressionHandler}
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers().antMatchers("/haha/**")
//                .and()
//                .authorizeRequests()
//                .anyRequest().authenticated();

        http
                .authorizeRequests()
                .antMatchers("/oauth/**","/login/**", "/login","/logout","/re")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                ;
    }
}
