package io.github.ygsama.dubbo.demo;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ygsama
 */
@SpringBootApplication
@DubboComponentScan(basePackages = "io.github.ygsama.dubbo.demo")
public class Test2Application {
    public static void main(String[] args) {
        SpringApplication.run(Test2Application.class, args);
    }
}