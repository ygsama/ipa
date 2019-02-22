package io.github.ygsama.securitydemo.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 *  AccessDecisionManager 授权决策管理器
 *
 *  主体的权限保存到 Authentication 里
 *  Authentication 对象保存在一个 GrantedAuthority 的对象数组中。
 *  AccessDecisionManager 遍历数组进行授权判断。
 *  AccessDecisionManager 被 AbstractSecurityInterceptor 调用，作最终访问控制的决定
 */
@Service
public class CustomerAccessDecisionManager implements AccessDecisionManager {


    /**
     * 权限鉴定是由AccessDecisionManager 接口中的decide() 方法来完成
     */
    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        if( configAttributes == null ) {
            return ;
        }
        Iterator<ConfigAttribute> ite = configAttributes.iterator();

        while( ite.hasNext()){
            ConfigAttribute ca = ite.next();
            String needRole = "ROLE_"+((SecurityConfig)ca).getAttribute();

            //ga 为用户的权限。 ca needRole 为访问相应的资源应该具有的权限。
            for( GrantedAuthority ga: authentication.getAuthorities()){
                if(needRole.trim().equals(ga.getAuthority().trim())){
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
