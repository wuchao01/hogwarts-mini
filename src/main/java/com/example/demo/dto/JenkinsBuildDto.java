package com.example.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "JenkinsBuild类", description = "请求类")
@Data
public class JenkinsBuildDto {
    @ApiModelProperty(value = "JenkinsJob名称",example = "test02",required = true)
    private String jobName;
    @ApiModelProperty(value = "用户名",example = "hogwarts",required = false)
    private String userName;
    @ApiModelProperty(value = "备注",example = "霍格沃兹演示",required = false)
    private String remark;
    @ApiModelProperty(value = "测试命令",example = "pwd",required = false)
    private String command;

}
