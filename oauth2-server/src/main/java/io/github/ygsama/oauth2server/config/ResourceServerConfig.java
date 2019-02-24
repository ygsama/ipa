package io.github.ygsama.oauth2server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 配置资源服务器
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }

    /**
     * 用于配置对受保护的资源的访问规则
     * 默认情况下所有不在/oauth/**下的资源都是受保护的资源
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers()
//                .antMatchers("/protect/**")
//                .and()
//                .authorizeRequests()
//                .anyRequest().authenticated();
        http
                .authorizeRequests()
                .antMatchers("/favicon.ico","/login","/oauth/authorize","/re")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
