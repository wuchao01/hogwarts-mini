package com.example.demo.controller;

import com.example.demo.common.PageTableRequest;
import com.example.demo.common.TokenDb;
import com.example.demo.common.UserBaseStr;
import com.example.demo.dto.*;
import com.example.demo.dto.jenkins.AddHogwartsTestJenkinsDto;
import com.example.demo.dto.jenkins.QueryHogwartsTestJenkinsListDto;
import com.example.demo.entity.HogwartsTestJenkins;
import com.example.demo.service.HogwartsTestJenkinsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping
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

    @ApiOperation("列表查询")
    @PostMapping("/list")
    public ResultDto<HogwartsTestJenkins> List(HttpServletRequest request, PageTableRequest<QueryHogwartsTestJenkinsListDto> pageTableRequest){
        if (Objects.isNull(pageTableRequest)){
            return ResultDto.fail("分页查询参数不能为空!");
        }

        TokenDto tokenDto = tokenDb.getUserInfo(request.getHeader(UserBaseStr.LOGIN_TOKEN));
        QueryHogwartsTestJenkinsListDto queryHogwartsTestJenkinsListDto = pageTableRequest.getParams();
        if (Objects.isNull(queryHogwartsTestJenkinsListDto)){
            queryHogwartsTestJenkinsListDto.setCreateUserId(tokenDto.getUserId());
        }
        pageTableRequest.setParams(queryHogwartsTestJenkinsListDto);
        return hogwartsTestJenkinsService.list(pageTableRequest);
    }

}
