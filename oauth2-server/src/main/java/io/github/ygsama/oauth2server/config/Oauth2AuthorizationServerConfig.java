package io.github.ygsama.oauth2server.config;

import io.github.ygsama.oauth2server.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 配置授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;

    /**
     * 顶级身份管理者: {@link AuthenticationManager}
     * 用来从请求中获取client_id,client_secret，组装成一个UsernamePasswordAuthenticationToken作为身份标识
     *   其实现类是 {@link ProviderManager}
     *     内部维护的Provider数组中{@link DaoAuthenticationProvider}
     *       内置一个{@link UserDetailsService}接口
     *       {@link UserDetailsService}才是获取用户详细信息的最终接口
     * 经过前置校验和身份封装之后，便到达了{@link TokenEndpoint}
     *   其内部依赖{@link TokenGranter} 来颁发token, 包含5种授权模式
     *     其抽象类中 {@link AbstractTokenGranter}
     *       通过{@link AuthorizationServerTokenServices}来创建、刷新、获取token
     *         其默认的实现类{@link DefaultTokenServices}
     *           会调用tokenStore对创建的token和相关信息存储到对应的实现类中
     *
     * {@link ResourceServerConfigurer}，内部关联了{@link ResourceServerSecurityConfigurer}和{@link HttpSecurity}
     * {@link ResourceServerSecurityConfigurer}
     *   创建{@link OAuth2AuthenticationProcessingFilter}，即OAuth2核心过滤器
     *     获取access_token并填入security context
     *   且为{@link OAuth2AuthenticationProcessingFilter}提供了{@link AuthenticationManager}实现类
     *     即{@link OAuth2AuthenticationManager}
     *       内部维护了{@link ClientDetailsService}
     *       和{@link ResourceServerTokenServices}
     *         调用这个tokenServices来加载、读取token
     *   设置了{@link TokenExtractor}默认的实现{@link BearerTokenExtractor}
     *     分离出请求中包含的token。可以使用多种方式携带token，header，uri参数，表单
     *   相关的异常处理器，可以重写相关实现来自定义异常
     *     重写的异常handler，在{@link EnableResourceServer}配置类中被覆盖
     */
    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * 配置AuthorizationServer安全认证的相关信息
     * 创建ClientCredentialsTokenEndpointFilter 客户端身份认证核心过滤器
     * 请求到达/oauth/token之前经过了ClientCredentialsTokenEndpointFilter这个过滤器
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    /**
     * 配置OAuth2的客户端相关信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 注册客户端
        clients.inMemory()
                .withClient("my-client-1")
                .secret("$2a$10$0jyHr4rGRdQw.X9mrLkVROdQI8.qnWJ1Sl8ly.yzK0bp06aaAkL9W")
                .authorizedGrantTypes("authorization_code", "refresh_token","password")
                .scopes("read", "write", "execute","select")
                .redirectUris("http://www.baidu.com");
//                .redirectUris("http://localhost:8088/login/oauth2/code/callback");
    }

    /**
     *  配置AuthorizationServerEndpointsConfigurer众多相关类，
     *  包括配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(new InMemoryTokenStore())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .reuseRefreshTokens(true);
        super.configure(endpoints);
//        endpoints
//                .tokenStore(new InMemoryTokenStore())
////                .tokenStore(tokenStore())
//                .authenticationManager(authenticationManager)
//                .userDetailsService(userDetailService) //必须加入 不然无法刷新token
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
//                .reuseRefreshTokens(true);
    }

//    @Bean
//    public TokenStore tokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }

    public static void main(String[] args) {
        System.out.println(new org.apache.tomcat.util.codec.binary.Base64().encodeAsString("my-client-1:12345678".getBytes()));
        System.out.println(java.util.Base64.getEncoder().encodeToString("my-client-1:12345678".getBytes()));
    }
}
