package com.btb.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("校验进度实体类")
@Data
public class ValidationProgress {
    @ApiModelProperty("校验表单总数")
    private Integer total;
    @ApiModelProperty("校验的当前数量")
    private Integer current;
    @ApiModelProperty("校验百分比")
    private Integer percentage;
}
