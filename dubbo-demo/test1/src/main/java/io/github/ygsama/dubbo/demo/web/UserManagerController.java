package io.github.ygsama.dubbo.demo.web;

import io.github.ygsama.dubbo.demo.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangguang0711
 */
@RestController
@Slf4j
public class UserManagerController {

    @Reference
    private UserService userService;

    @GetMapping("/user")
    String getUser() {
        log.info("getUser");
        return userService.getUsername();
    }

}
