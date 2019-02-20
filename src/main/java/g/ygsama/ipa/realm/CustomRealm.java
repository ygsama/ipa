package g.ygsama.ipa.realm;



import g.ygsama.ipa.entity.User;
import g.ygsama.ipa.service.PermService;
import g.ygsama.ipa.service.RoleService;
import g.ygsama.ipa.service.UserService;
import g.ygsama.ipa.web.LoginController;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 这个类是参照JDBCRealm写的，主要是：查询用户信息，查询用户的角色和权限，校验密码。等逻辑的实现
 */
public class CustomRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(CustomRealm.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermService permService;

    //告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
    {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        hashMatcher.setStoredCredentialsHexEncoded(false);
        hashMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(hashMatcher);
    }


    /**
     * 授权：
     *      定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
     * @param principals
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("[执行授权]","...");
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        User user = (User) getAvailablePrincipal(principals);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        log.info("[获取角色信息]: "+user.getRoles());
        log.info("[获取权限信息]: "+user.getPerms());
        info.setRoles(user.getRoles());
        info.setStringPermissions(user.getPerms());
        return info;
    }

    /**
     * 认证：
     *  1、检查提交的进行认证的令牌信息 
     *  2、根据令牌信息从数据源(通常为数据库)中获取用户信息 
     *  3、对用户信息进行匹配验证。
     * @param token
     * @return 封装了用户信息的AuthenticationInfo对象
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        log.info("[执行认证]","...");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        User userDB = userService.findUserByName(username);
        if (userDB == null) {
            throw new UnknownAccountException("No account found for admin [" + username + "]");
        }

        // 查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
        // SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        Set<String> roles = roleService.getRolesByUserId(userDB.getUid());
        Set<String> perms = permService.getPermsByUserId(userDB.getUid());
        userDB.getRoles().addAll(roles);
        userDB.getPerms().addAll(perms);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDB, userDB.getPwd(), getName());
        if (userDB.getSalt() != null) {
            info.setCredentialsSalt(ByteSource.Util.bytes(userDB.getSalt()));
        }

        return info;

    }

}
