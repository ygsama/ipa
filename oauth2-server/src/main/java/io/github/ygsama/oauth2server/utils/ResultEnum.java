package io.github.ygsama.oauth2server.utils;

public enum ResultEnum {

	/**
	 * 成功
	 */
	SUCCEED("00", "成功"),

	/**
	 * 失败
	 */
	FAIL("FF", "失败"),

	/**
	 * 缺少参数
	 */
	PARAM_LACK("E1", "缺少参数"),

	/**
	 * 参数错误
	 */
	PARAM_ERROR("E2", "参数错误");
	
	private String code;
	private String tip;

	ResultEnum(String code, String tip) {
		this.code = code;
		this.tip = tip;
	}

	public String getCode() {
		return this.code;
	}

	public String getTip() {
		return this.tip;
	}
	
	public static String getTipByCode(String code) {
		for (ResultEnum temp : ResultEnum.values()) {
			if (temp.getCode().equals(code)) {
				return temp.getTip();
			}
		}
		return "";
	}

}
