package io.github.ygsama.oauth2server.utils;

import java.text.DecimalFormat;

public final class NumberUtil {

	static String decimal2(double arg0) {
		return new DecimalFormat("#,##0.00").format(arg0);
	}

	/**
	 * 数字小数化（最多保留两位小数）
	 * @param arg0
	 *  - 双精度数
	 * @return 返回小数化之后的字符串
	 */

	public static String decimal(double arg0) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		return df.format(arg0);
	}
}
