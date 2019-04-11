package io.github.ygsama.oauth2server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@MapperScan(basePackages = "io.github.ygsama.oauth2server.repository")
@SpringBootApplication
@EnableSwagger2
public class Oauth2ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }
}

