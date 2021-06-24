package com.example.demo.dto.jenkins;

import com.example.demo.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 该对象类用于分页查询传参，可通过name查询或创建者ID查询，如需新增查询条件直接添加参数即可
 */
@Data
public class QueryHogwartsTestJenkinsListDto extends BaseDto {
    @ApiModelProperty(value="Jenkins名称")
    private String name;

    @ApiModelProperty(value="创建者id(客户端传值无效，以token数据为准)")
    private Integer createUserId;
}
