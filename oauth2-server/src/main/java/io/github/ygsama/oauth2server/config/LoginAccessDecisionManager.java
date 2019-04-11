package io.github.ygsama.oauth2server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * AccessDecisionManager 授权决策管理器
 * {@link AccessDecisionManager} 鉴权决策管理器<br>
 * 主体的权限对象{@link Authentication} 保存在一个 {@link GrantedAuthority} 的对象数组中。<br>
 * 鉴权决策管理器 遍历这个数组进行授权判断。<br>
 * 鉴权决策管理器 被{@link AbstractSecurityInterceptor} 调用用来鉴权 <br>
 * 框架默认实现是 {@link UnanimousBased}
 * <p>
 */
@Slf4j
@Service
public class LoginAccessDecisionManager implements AccessDecisionManager {

    /**
     * 权限鉴定
     */
    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        if (configAttributes == null) {
            return;
        }
        Iterator<ConfigAttribute> it = configAttributes.iterator();

        log.info("[资源权限]: {}", configAttributes);
        log.info("[用户权限]: {}", authentication.getAuthorities());
        while (it.hasNext()) {
            // 资源的权限
            ConfigAttribute resourceAttr = it.next();
            String resourceRole = "ROLE_" + ((SecurityConfig) resourceAttr).getAttribute();

            // 用户的权限
            for (GrantedAuthority userAuth : authentication.getAuthorities()) {
                log.info("[资源角色==用户角色] ？ {} == {}", resourceRole.trim(), userAuth.getAuthority().trim());
                if (resourceRole.trim().equals(userAuth.getAuthority().trim())) {
                    return;
                }
            }
        }

        throw new AccessDeniedException("权限不足");
    }


    /**
     * 方法在启动的时候被AbstractSecurityInterceptor调用，
     * 决定了AccessDecisionManager，是否可以执行传递ConfigAttribute
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * 被安全拦截器的实现调用，包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型。
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
