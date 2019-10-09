package io.github.ygsama.springsecurity.config.authorize;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * FilterInvocationSecurityMetadataSource实现类
 * 实现了动态权限验证
 */
@Service
public class AuthorizeSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    /**
     * 在Web服务器启动时，提取系统中的所有权限。
     * 先找出所有的资源url列表
     * 然后遍历每个资源url，循环执行如下操作
     * 根据匹配关系找到对应的角色列表
     * 将匹配的角色列表封装，存入map
     * 被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
     */
    @PostConstruct
    private void loadResourceDefine() {   //一定要加上@PostConstruct注解
        resourceMap = new HashMap<>();

        Collection<ConfigAttribute> admin = new ArrayList<>();
        admin.add(new SecurityConfig("admin"));

        Collection<ConfigAttribute> user = new ArrayList<>();
        user.add(new SecurityConfig("admin"));
        user.add(new SecurityConfig("user"));
        resourceMap.put("/", null);
        resourceMap.put("/admin", admin);
        resourceMap.put("/user", user);

        System.out.println(resourceMap.toString());

    }

    /**
     * 授权时会被调用，根据URL找到相关权限配置
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        System.out.println(" [根据URL找到相关权限配置] : CustomerInvocationSecurityMetadataSource.getAttributes() ");
        // object 是一个URL，被用户请求的url。
        FilterInvocation filterInvocation = (FilterInvocation) object;
        if (resourceMap == null) {
            loadResourceDefine();
        }
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);
            if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
                System.out.println(" return resourceMap.get(resURL) " + resourceMap.get(resURL));
                return resourceMap.get(resURL);
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
