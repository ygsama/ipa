package io.github.ygsama.oauth2server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * AbstractSecurityInterceptor
 * 资源访问拦截器
 * 默认的过滤器是FilterSecurityInterceptor
 */
@Component
public class LoginSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoginSecurityInterceptor.class);

    @Autowired
    private LoginSecurityMetadataSource loginSecurityMetadataSource;

    @Autowired
    private LoginAccessDecisionManager loginAccessDecisionManager;


    @PostConstruct
    public void initSetManager(){
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
        log.info("[LoginSecurityInterceptor]: {}"," doFilter");
        FilterInvocation fi = new FilterInvocation( request, response, chain );
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try{
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }finally{
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("[LoginSecurityInterceptor]: {}"," init");
    }

    @Override
    public void destroy() {
        log.info("[LoginSecurityInterceptor]: {}"," destroy");
    }
}
