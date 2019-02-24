package io.github.ygsama.oauth2server.config;

import com.alibaba.fastjson.JSONObject;
import io.github.ygsama.oauth2server.security.UserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 自定义Provider，用于登录认证
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(LoginAuthenticationProvider.class);

    @Autowired
    private UserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // http请求的账户密码
        String userName = authentication.getName();
        String password = (String)authentication.getCredentials();

        // 数据库根据用户名查询
        UserDetails userDetails = userDetailService.loadUserByUsername(userName);

        log.info("[http请求的账户密码]: {}/{}", userName, password);
        log.info("[数据库查询的账户密码]：{} ", JSONObject.toJSONString(userDetails));

        if(userDetails==null) {
            throw new BadCredentialsException("用户名未找到");
        }

        if(!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }

        // 获取权限集合
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        return new UsernamePasswordAuthenticationToken(userName,password,authorities);
    }

    // 是否支持当前的provider
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
