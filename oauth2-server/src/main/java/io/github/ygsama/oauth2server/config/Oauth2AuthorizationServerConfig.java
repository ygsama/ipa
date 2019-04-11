package io.github.ygsama.oauth2server.config;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Spring Security Oauth2 登录认证配置类
 * <p>
 * 顶级身份管理者: {@link AuthenticationManager}
 * 用来从请求中获取client_id,client_secret，组装成一个UsernamePasswordAuthenticationToken作为身份标识
 * --其实现类是 {@link ProviderManager}
 * ----内部维护的Provider数组中{@link DaoAuthenticationProvider}
 * ------内置了{@link UserDetailsService}接口实现，它是获取用户详细信息的最终接口
 * <p>
 * 经过前置校验和身份封装之后，便到达了{@link TokenEndpoint}
 * --其内部依赖{@link TokenGranter} 来颁发token, 包含5种授权模式
 * ----其抽象类中 {@link AbstractTokenGranter}
 * ------通过{@link AuthorizationServerTokenServices}来创建、刷新、获取token
 * --------其默认的实现类{@link DefaultTokenServices}，会调用tokenStore对创建的token和相关信息存储到对应的实现类中
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final UserDetailsService loginUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public Oauth2AuthorizationServerConfig(UserDetailsService loginUserDetailsService, AuthenticationManager authenticationManager) {
        this.loginUserDetailsService = loginUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

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
        clients.inMemory()
                .withClient("my-client-1")
                .secret("{bcrypt}$2a$10$0jyHr4rGRdQw.X9mrLkVROdQI8.qnWJ1Sl8ly.yzK0bp06aaAkL9W")
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .scopes("all")
                .redirectUris("http://localhost:9090/callback");
    }

    /**
     * 配置AuthorizationServerEndpointsConfigurer众多相关类，
     * 包括配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .userDetailsService(loginUserDetailsService)
                .tokenStore(new InMemoryTokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
                .reuseRefreshTokens(false);
        //        endpoints
//                .tokenStore(new InMemoryTokenStore())
//                .getAccessTokenConverter(accessTokenConverter())
//                .authenticationManager(authenticationManager)
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
//                .reuseRefreshTokens(true);
//        super.configure(endpoints);
//        endpoints
//                .tokenStore(new InMemoryTokenStore())
////                .tokenStore(tokenStore())
//                .authenticationManager(authenticationManager)
//                .userDetailsService(userDetailService) //必须加入 不然无法刷新token
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
//                .reuseRefreshTokens(true);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("secret");
        return converter;
    }

    public static void main(String[] args) {
        System.out.println(new Base64().encodeAsString("my-client-1:12345678".getBytes()));
        System.out.println(java.util.Base64.getEncoder().encodeToString("my-client-1:12345678".getBytes()));

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("secret");
        Jwt decode = JwtHelper.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTEzNjYxMTIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJlNTk4MTU4ZS00OWJlLTQwNjgtYTdiNi02NDcwNDE5Y2E3NDAiLCJjbGllbnRfaWQiOiJteS1jbGllbnQtMSIsInNjb3BlIjpbImFsbCJdfQ.fZqMGddQM00gaUAJKtb4ly4sXAhiiGPCjvM65y2sYAc");
        System.out.println(decode);
    }
}
