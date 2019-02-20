package g.ygsama.ipa.web;


import g.ygsama.ipa.entity.User;
import g.ygsama.ipa.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequiresPermissions("user:select")
    @GetMapping()
    public List getUserList() {
        log.info("[查询用户]: ",new Object());
        return  userService.getAllUsers();
    }

    @RequiresPermissions("user:alter")
    @PostMapping()
    public String addUser(@RequestBody User user) {
        log.info("[添加用户]: ",user);
//        userService.create(user.getId(),user.getName(),user.getAge());
        return "success";
    }

    @RequiresRoles("manager")
    @DeleteMapping()
    public String delUser() {
        log.info("[删除用户]","");

        return "success";
    }

}