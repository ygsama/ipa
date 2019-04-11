package io.github.ygsama.oauth2server.config;

import io.github.ygsama.oauth2server.domain.SysPermissionDO;
import io.github.ygsama.oauth2server.repository.SysPermissionMapper;
import io.github.ygsama.oauth2server.repository.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 权限资源映射的数据源,实现了动态权限验证<br>
 * 这里重写并实现了基于数据库的权限数据源 <br>
 * 实现了 {@link FilterInvocationSecurityMetadataSource}接口 <br>
 * 默认实现是 {@link DefaultFilterInvocationSecurityMetadataSource} <br>
 */
@Service
@Slf4j
public class LoginSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<RequestMatcher, Collection<ConfigAttribute>> permissionMap;

    private SysRoleMapper sysRoleMapper;
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    LoginSecurityMetadataSource(SysRoleMapper sysRoleMapper, SysPermissionMapper sysPermissionMapper) {
        this.sysPermissionMapper = sysPermissionMapper;
        this.sysRoleMapper = sysRoleMapper;
    }

    /**
     * 在Web服务器启动时，缓存系统中的所有权限映射。<br>
     * 被{@link PostConstruct}修饰的方法会在服务器加载Servlet的时候运行(构造器之后,init()之前) <br/>
     */
    @PostConstruct
    private void loadResourceDefine() {
        permissionMap = new LinkedHashMap<>();

        // 角色的权限
        List<SysPermissionDO> permissionList = sysPermissionMapper.queryRolePermission();
        for (SysPermissionDO permission : permissionList) {
            String url = permission.getUrl();
            String method = permission.getMethod();
            String[] roles = permission.getRoleList().split(",");
            log.info("{} - {}", url, method);
            AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, method);

            Collection<ConfigAttribute> attributes = new ArrayList<>();
            for (String role : roles) {
                attributes.add(new SecurityConfig(role));
            }
            // 给10001增加所有接口的权限
            /*attributes.add(new SecurityConfig("10001"));*/
            permissionMap.put(requestMatcher, attributes);
        }

        // 公共的权限 & 系统接口的权限
        List<SysPermissionDO> publicList = sysPermissionMapper.queryPublicPermission();
        for (SysPermissionDO permission : publicList) {
            String url = permission.getUrl();
            String method = permission.getMethod();
            AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, "*".equals(method) ? null : method);
            Collection<ConfigAttribute> attributes = new ArrayList<>();
            List<String> roles = sysRoleMapper.queryAllRoleNo();
            for (String role : roles) {
                attributes.add(new SecurityConfig(role));
            }
            attributes.add(new SecurityConfig("ANONYMOUS"));
            permissionMap.put(requestMatcher, attributes);
        }

        log.info("[全局权限映射集合]: {}", permissionMap.toString());
    }

    /**
     * 授权时会被调用，根据URL找到相关权限配置
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        log.info("[根据URL找到相关权限配置]: {}\n {}", object, permissionMap);

        if (permissionMap == null) {
            loadResourceDefine();
        }
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : permissionMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                log.info("[找到的Key]: {}", entry.getKey());
                log.info("[找到的Value]: {}", entry.getValue());
                if (entry.getValue().size() > 0) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<ConfigAttribute>();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
