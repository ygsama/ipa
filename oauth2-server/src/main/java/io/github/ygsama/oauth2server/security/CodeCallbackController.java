package io.github.ygsama.oauth2server.security;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
public class CodeCallbackController {
    private static final Logger log= LoggerFactory.getLogger(CodeCallbackController.class);

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @RequestMapping("/re")
    public String getToken(@RequestParam String code){
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String secret = "123qwe";
        log.info("获取到的code={},准备获取token ",code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("code",code);
        params.add("client_id","client");
        params.add("client_secret",secret);
        params.add("redirect_uri","http://localhost:8088/re");
        params.add("scopes","select");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8088/oauth/token", requestEntity, String.class);
        String token = response.getBody();
        log.info("token => {}",token);
        System.out.println("入参："+ JSONObject.toJSONString(params));

        return token;
    }

    @PostMapping(value = "/user/info", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object info(HttpServletRequest request) {
        System.out.println(request.getHeader("Authorization"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();




		return authentication;
    }

}
