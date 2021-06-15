package com.example.demo.service.impl;

import com.example.demo.common.Token;
import com.example.demo.common.TokenDb;
import com.example.demo.common.UserBaseStr;
import com.example.demo.dao.HogwartsTestJenkinsMapper;
import com.example.demo.dao.HogwartsTestUserMapper;
import com.example.demo.dto.ResultDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.HogwartsTestJenkins;
import com.example.demo.entity.HogwartsTestUser;
import com.example.demo.service.HogwartsTestJenkinsService;
import com.example.demo.service.HogwartsTestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class HogwartsTestJenkinsServiceImpl implements HogwartsTestJenkinsService {
    @Autowired
    private TokenDb tokenDb;

    @Autowired
    private HogwartsTestJenkinsMapper hogwartsTestJenkinsMapper;

    //实现jenkins新增方法
    @Override
    public ResultDto<HogwartsTestJenkins> save(TokenDto tokenDto,HogwartsTestJenkins hogwartsTestJenkins) {

        return ResultDto.success("成功",hogwartsTestJenkins);
    }

}
