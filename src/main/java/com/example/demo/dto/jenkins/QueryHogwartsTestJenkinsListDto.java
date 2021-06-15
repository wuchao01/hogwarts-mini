package com.example.demo.dto.jenkins;

import com.example.demo.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;

public class QueryHogwartsTestJenkinsListDto extends BaseDto {
    @ApiModelProperty(value="Jenkins名称")
    private String name;

    @ApiModelProperty(value="创建者id(客户端传值无效，以token数据为准)")
    private Integer createUserId;
}
