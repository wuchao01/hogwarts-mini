package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.Token;
import com.example.demo.common.TokenDb;
import com.example.demo.common.UserBaseStr;
import com.example.demo.dto.*;
import com.example.demo.dto.jenkins.AddHogwartsTestJenkinsDto;
import com.example.demo.entity.HogwartsTestJenkins;
import com.example.demo.service.HogwartsTestJenkinsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@Api(tags = "霍格沃兹测试学院-Jenkins管理")
@RestController
@RequestMapping("hogwartsJenkins")
@Slf4j
public class HogwartsTestJenkinsController {
    @Autowired
    private HogwartsTestJenkinsService hogwartsTestJenkinsService;
    @Autowired
    private TokenDb tokenDb;

    @ApiOperation("添加Jenkins")
    @PostMapping("save")
    public ResultDto<HogwartsTestJenkins> save(HttpServletRequest request,@RequestBody AddHogwartsTestJenkinsDto addHogwartsTestJenkinsDto){
        //获取请求头中的token
        String tokenStr = request.getHeader(UserBaseStr.LOGIN_TOKEN);
        TokenDto tokenDto = tokenDb.getUserInfo(tokenStr);
        String commandRunCaseSuffix = addHogwartsTestJenkinsDto.getCommandRunCaseSuffix();
        if (Objects.nonNull(commandRunCaseSuffix) && commandRunCaseSuffix.startsWith(".")){
            commandRunCaseSuffix = commandRunCaseSuffix.replace(".","");
        }
        addHogwartsTestJenkinsDto.setCommandRunCaseSuffix(commandRunCaseSuffix);
        HogwartsTestJenkins hogwartsTestJenkins = new HogwartsTestJenkins();
        BeanUtils.copyProperties(addHogwartsTestJenkinsDto,hogwartsTestJenkins);
        hogwartsTestJenkinsService.save(tokenDto,hogwartsTestJenkins);
        return ResultDto.success("成功",hogwartsTestJenkins);

    }

}
