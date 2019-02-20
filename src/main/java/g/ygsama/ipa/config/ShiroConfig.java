package g.ygsama.ipa.config;


import g.ygsama.ipa.realm.CustomRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
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
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {

        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();

        //哪些请求可以匿名访问
        chain.addPathDefinition("/users/*", "anon");
        chain.addPathDefinition("/sys/*", "anon");

        //除了以上的请求外，其它请求都需要登录
        chain.addPathDefinition("/**", "authc");

        // 这另一种配置方式。但是还是用上面那种吧，容易理解一点。
        // chainDefinition.addPathDefinition("/**", "authcBasic[permissive]");
        return chain;
    }


}
