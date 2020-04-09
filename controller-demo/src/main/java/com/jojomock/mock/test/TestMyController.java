package com.jojomock.mock.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@MyController
@RestController
public interface TestMyController {

    @GetMapping("/my/hello")
    @MyControllerMethod(beanType = TestMyService.class, methodName = "hello")
    String hello();
}
