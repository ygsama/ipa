package io.github.ygsama.authserver.web;


import io.github.ygsama.authserver.entity.User;
import io.github.ygsama.authserver.service.UserService;
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

    @GetMapping()
    public List getUserList() {
        log.info("[查询用户]: ",new Object());
        return  userService.getAllUsers();
    }

    @PostMapping()
    public String addUser(@RequestBody User user) {
        log.info("[添加用户]: ",user);
//        userService.create(user.getId(),user.getName(),user.getAge());
        return "success";
    }

    @DeleteMapping()
    public String delUser() {
        log.info("[删除用户]","");

        return "success";
    }

}