package io.github.ygsama.resourceserver.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 配置资源服务器
 */
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

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
        http.requestMatchers().antMatchers("/src/**")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}
