package io.github.ygsama.oauth2server.web;

import io.github.ygsama.oauth2server.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 杨光
 */
@Api(value = "权限模块：系统回调接口", tags = {"权限模块：系统回调接口"})
@RequestMapping("/sys/callback")
public interface CallbackResource {


	@PostMapping("/token")
	@ApiOperation(value = "根据授权码获取令牌", notes = "根据授权码获取令牌")
	String getToken(@RequestParam String code) ;


	@PostMapping("/login")
	@ApiOperation(value = "回调登录", notes = "回调登录")
	Object callBackLogin(@RequestBody UserDTO user) ;

	@PostMapping("/refresh")
	@ApiOperation(value = "回调刷新token", notes = "回调刷新token")
	Object callBackRefreshToken(@RequestBody String refreshToken);
}
