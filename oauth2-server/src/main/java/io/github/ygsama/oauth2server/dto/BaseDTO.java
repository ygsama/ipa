package io.github.ygsama.oauth2server.dto;

import io.github.ygsama.oauth2server.utils.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 杨光
 */
@Data
@ApiModel(value = "响应数据传输对象", description = "响应数据传输对象")
public class BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "响应信息编码")
	private String retCode;

	@ApiModelProperty(value = "响应信息")
	private String retMsg;

	public BaseDTO() {
	}

	public BaseDTO(ResultEnum e) {
		this.retCode = e.getCode();
		this.retMsg = e.getTip();
	}

	public BaseDTO(String retCode, String retMsg) {
		this.retCode = retCode;
		this.retMsg = retMsg;
	}

	public void setResult(ResultEnum e) {
		this.retCode = e.getCode();
		this.retMsg = e.getTip();
	}

}
