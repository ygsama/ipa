package io.github.ygsama.oauth2server.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/src")
public class TestController {

    @GetMapping("/test")
    public JSONObject test(){

        return new JSONObject().fluentPut("msg","test success!");
    }

    @GetMapping("/order")
    public JSONObject test1(){

        return new JSONObject().fluentPut("msg","order success!");
    }

    @GetMapping("/product")
    public JSONObject test2(){

        return new JSONObject().fluentPut("msg","product success!");
    }

    @GetMapping("/message")
    public JSONObject test3(){

        return new JSONObject().fluentPut("msg","product success!");
    }
}
