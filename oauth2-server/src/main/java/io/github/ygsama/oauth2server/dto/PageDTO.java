package io.github.ygsama.oauth2server.dto;

import io.github.ygsama.oauth2server.utils.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 杨光
 */
@Getter
@Setter
@ToString
@ApiModel(value = "分页数据传输对象", description = "分页数据传输对象")
public class PageDTO<E> extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "总条数")
	private Integer totalRow;

	@ApiModelProperty(value = "总页数")
	private Integer totalPage;

	@ApiModelProperty(value = "当前页")
	private Integer curPage;

	@ApiModelProperty(value = "页面条数")
	private Integer pageSize;

	@ApiModelProperty(value = "页面数据集合")
	private List<E> retList;

	@ApiModelProperty(value = "机构等级")
	private String orgGradeNo;

	public PageDTO() {
		super();
	}

	public PageDTO(ResultEnum re) {
		super(re);
	}

	public PageDTO(String retCode, String retMsg, int pageSize) {
		super(retCode, retMsg);
		this.pageSize = pageSize;
	}

}
