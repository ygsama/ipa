package io.github.ygsama.dubbo.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ygsama
 */
@SpringBootApplication
@DubboComponentScan(basePackages = "io.github.ygsama")
@Slf4j
public class Test1Application {
    public static void main(String[] args) {
        SpringApplication.run(Test1Application.class, args);
    }
}