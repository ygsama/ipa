package g.ygsama.ipa.config;


import g.ygsama.ipa.realm.CustomRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    //注入自定义的realm，告诉shiro如何获取用户信息来做登录或权限控制
    @Bean
    public Realm realm() {
        return new CustomRealm();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        creator.setUsePrefix(true);
        return creator;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {

        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();

        //哪些请求可以匿名访问
        chain.addPathDefinition("/user", "anon");
        chain.addPathDefinition("/login", "anon");
        chain.addPathDefinition("/index", "anon");


        //除了以上的请求外，其它请求都需要登录
        chain.addPathDefinition("/**", "authc");

        // 这另一种配置方式。但是还是用上面那种吧，容易理解一点。
        // chainDefinition.addPathDefinition("/**", "authcBasic[permissive]");
        return chain;
    }


}
