package com.example.demo.service;

import com.example.demo.dto.ResultDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.HogwartsTestJenkins;
import com.example.demo.entity.HogwartsTestUser;

import java.util.List;

public interface HogwartsTestJenkinsService {

    /**
     * 新增Jenkins信息
     * @param hogwartsTestJenkins
     * @return
     */
    ResultDto<HogwartsTestJenkins> save(TokenDto tokenDto,HogwartsTestJenkins hogwartsTestJenkins);


}
