package com.example.demo.dto;

import com.example.demo.entity.BaseEntityNew;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户修改类", description = "修改类")
@Data
public class UpdateHogwartsTestUserDto extends BaseEntityNew {

    /**
     * 用户Id
     */
    @ApiModelProperty(value = "用户Id",example = "123",required = true)
    private Integer Id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",example = "hogwarts",required = true)
    private String userName;

    /**
     * 密码，只有带下划线的才会生成 @Column注解
     */
    @ApiModelProperty(value = "密码",example = "123456",required = true)
    private String password;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱",example = "hogwarts@qq.com",required = false)
    private String email;

    /**
     * 自动生成用例job名称，不为空时表示已经创建job
     */
    @ApiModelProperty(value = "用例名称",example = "注册用例")
    private String autoCreateCaseJobName;

    /**
     * 执行测试job名称，不为空时表示已经创建job
     */
    @ApiModelProperty(value = "job名称",example = "测试job")
    private String startTestJobName;

    /**
     * 默认jenkins服务器Id
     */
    @ApiModelProperty(value = "jenkins服务器Id",example = "123")
    private Integer defaultJenkinsId;
}