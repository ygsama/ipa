package io.github.ygsama.oauth2server.web.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.ygsama.oauth2server.dto.BaseDTO;
import io.github.ygsama.oauth2server.dto.UserDTO;
import io.github.ygsama.oauth2server.utils.ResultEnum;
import io.github.ygsama.oauth2server.web.CallbackResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * 回调接口
 * TODO: 重写spring security 登录接口
 *
 * @author 杨光
 */
@Slf4j
@RestController
public class CallbackResourceImpl implements CallbackResource {
	/**
	 * 未测试
	 */
	@Override
	public String getToken(String code) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		log.info("获取到的code={},准备获取token ", code);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("code", code);
		params.add("client_id", "my-client-1");
		params.add("redirect_uri", "http://localhost:8088/re");
		params.add("scopes", "read");

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8088/oauth/token", requestEntity, String.class);
		String token = response.getBody();
		log.info("token => {}", token);
		System.out.println("入参：" + JSONObject.toJSONString(params));

		return token;
	}

	@Override
	public Object callBackLogin(UserDTO user) {
		log.info("[登录用户]: ", user);
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.add("Authorization", "Basic bXktY2xpZW50LTE6MTIzNDU2Nzg=");
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

			params.add("grant_type", "password");
			params.add("username", user.getUsername());
			params.add("password", user.getPassword());
			params.add("client_id", "my-client-1");
			params.add("scopes", "all");
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
			ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", requestEntity, String.class);
			return JSON.parse(response.getBody());
		} catch (Exception e) {
			return new BaseDTO(ResultEnum.FAIL);
		}
	}

	@Override
	public Object callBackRefreshToken(String refreshToken) {
		log.info("[刷新token]: {}", refreshToken);
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.add("Authorization", "Basic bXktY2xpZW50LTE6MTIzNDU2Nzg=");
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

			params.add("grant_type", "refresh_token");
			params.add("refresh_token", JSONObject.parseObject(refreshToken).getString("refreshToken"));
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
			ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", requestEntity, String.class);
			return JSON.parse(response.getBody());
		} catch (Exception e) {
			return new BaseDTO(ResultEnum.FAIL);
		}
	}


	//    @RequestMapping("/token")
//    public String getToken(@RequestParam String code){
//        RestTemplate restTemplate=new RestTemplate();
//        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        String secret = "123qwe";
//        log.info("获取到的code={},准备获取token ",code);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
//        params.add("grant_type","authorization_code");
//        params.add("code",code);
//        params.add("client_id","my-client-1");
////        params.add("client_secret",secret);
//        params.add("redirect_uri","http://localhost:8088/re");
//        params.add("scopes","read");
//
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8088/oauth/token", requestEntity, String.class);
//        String token = response.getBody();
//        log.info("token => {}",token);
//        System.out.println("入参："+ JSONObject.toJSONString(params));
//
//        return token;
//    }
}
