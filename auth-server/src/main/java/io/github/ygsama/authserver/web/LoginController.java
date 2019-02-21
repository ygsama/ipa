package io.github.ygsama.authserver.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/sys")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public JSONObject login(@RequestBody String body){

        log.info("[user login] body: {}",body);

        JSONObject json = JSON.parseObject(body);
        String uname = json.getString("uname");
        String pwd = json.getString("pwd");

        if (StringUtils.isEmpty(uname)){
            return new JSONObject().fluentPut("msg","用户名不能为空");
        }
        if (StringUtils.isEmpty(pwd)){
            return new JSONObject().fluentPut("msg","密码不能为空");
        }


        return null;
//        Subject currentUser = SecurityUtils.getSubject();
//        try {
//            //登录
//            currentUser.login( new UsernamePasswordToken(uname, pwd) );
//            //从session取出用户信息
//            User user = (User) currentUser.getPrincipal();
//
//            if (user==null) throw new AuthenticationException();
//            //返回登录用户的信息给前台，含用户的所有角色和权限
//            return new JSONObject()
//                    .fluentPut("uid",user.getUid())
//                    .fluentPut("nick",user.getNick())
//                    .fluentPut("roles",user.getRoles())
//                    .fluentPut("perms",user.getPerms());
//
//        } catch ( UnknownAccountException uae ) {
//            log.warn("用户帐号不正确");
//            return new JSONObject().fluentPut("msg","用户帐号或密码不正确");
//
//        } catch ( IncorrectCredentialsException ice ) {
//            log.warn("用户密码不正确");
//            return new JSONObject().fluentPut("msg","用户帐号或密码不正确");
//
//        } catch ( LockedAccountException lae ) {
//            log.warn("用户帐号被锁定");
//            return new JSONObject().fluentPut("msg","用户帐号被锁定不可用");
//
//        } catch ( AuthenticationException ae ) {
//            log.warn("登录出错");
//            return new JSONObject().fluentPut("msg",ae.getMessage());
//        }

    }
}
