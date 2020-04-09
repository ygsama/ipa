package com.jojomock.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yangguang0711
 * @since CROV_LV_2020.01
 */
@SpringBootApplication
@EnableSwagger2
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class);
    }

//    @PostConstruct
//    void init(){
//        log.info("-------------------------------------------");
//        ServiceBeanDefinitionRegistry.simpleNameList.stream().forEach(item -> ServiceBeanDefinitionRegistry.registerController(item));
//    }

}
