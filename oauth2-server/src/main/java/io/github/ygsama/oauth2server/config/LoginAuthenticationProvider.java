package io.github.ygsama.oauth2server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.Collection;


/**
 * 登录认证的Provider是{@link AuthenticationProvider} 的自定义实现 <br>
 * Provider默认实现是 {@link DaoAuthenticationProvider} <br>
 * <p>
 * {@link BearerTokenAuthenticationFilter} 装填{@link Authentication}对象 <br>
 * <p>
 * {@link UsernamePasswordAuthenticationFilter} --> {@link AccessDecisionManager} --> {@link AuthenticationProvider} <br>
 */
@Component
@Slf4j
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService loginUserDetailsService;

    @Autowired()
    public LoginAuthenticationProvider(LoginUserDetailsServiceImpl loginUserDetailsService) {
        this.loginUserDetailsService = loginUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // http请求的账户密码
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // 数据库根据用户名查询
        UserDetails userDetails = loginUserDetailsService.loadUserByUsername(username);

        log.info("[http请求的账户密码]: {}/{}", username, password);
        log.info("[数据库查询的账户密码]：{} ", userDetails);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (userDetails == null) {
            throw new BadCredentialsException("用户名未找到");
        }
        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        log.info("[设置authorities] : {}", authorities);

        // TODO: Spring Cloud 分布式改造时，权限模块放在网关，业务服务模块与权限模块解耦
        // TODO: 权限模块：登录后需要将<username,userDetails>放入Redis缓存；其他业务请求需要添加username到http请求头中
        // TODO: 业务模块：根据请求头中的key去缓存中获取userDetails
        return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
