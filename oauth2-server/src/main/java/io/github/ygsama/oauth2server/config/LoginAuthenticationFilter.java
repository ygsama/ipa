package io.github.ygsama.oauth2server.config;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 登录过滤器 <br/>
 * TODO:重写/oauth/**接口，以支持application/json请求 <br/>
 * TODO:解决/oauth/** OPTIONS权限问题
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
}
