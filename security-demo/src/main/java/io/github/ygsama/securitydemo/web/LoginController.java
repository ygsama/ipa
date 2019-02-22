package io.github.ygsama.securitydemo.web;

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


        return new JSONObject().fluentPut("msg","success!");

    }
}
