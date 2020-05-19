package io.github.ygsama.dubbo.demo.service;

import io.github.ygsama.dubbo.demo.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author yangguang0711
 */
@Service
@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public String getUsername() {
        log.info("getUsername");
        return "zhangsan";
    }
}
