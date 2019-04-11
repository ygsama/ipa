package io.github.ygsama.oauth2server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * 资源访问过滤器 <br>
 * 重写了{@link AbstractSecurityInterceptor} 接口 <br>
 * 默认的过滤器实现是{@link FilterSecurityInterceptor}
 */
@Component
@Slf4j
public class LoginSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private final LoginSecurityMetadataSource loginSecurityMetadataSource;
    private final LoginAccessDecisionManager loginAccessDecisionManager;

    @Autowired
    public LoginSecurityInterceptor(LoginSecurityMetadataSource loginSecurityMetadataSource, LoginAccessDecisionManager loginAccessDecisionManager) {
        this.loginSecurityMetadataSource = loginSecurityMetadataSource;
        this.loginAccessDecisionManager = loginAccessDecisionManager;
    }

    @PostConstruct
    public void initSetManager() {
        super.setAccessDecisionManager(loginAccessDecisionManager);
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.loginSecurityMetadataSource;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("[自定义过滤器]: {}", " doFilter");
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
