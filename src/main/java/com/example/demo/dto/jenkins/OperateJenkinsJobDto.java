package com.example.demo.dto.jenkins;

import com.example.demo.dto.TokenDto;
import com.example.demo.entity.HogwartsTestJenkins;
import com.example.demo.entity.HogwartsTestUser;

import java.util.Map;

public class OperateJenkinsJobDto {
    private TokenDto tokenDto;

    private HogwartsTestJenkins hogwartsTestJenkins;

    private HogwartsTestUser hogwartsTestUser;

    //构建参数
    private Map<String, String> params;
}
