package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.ResultDto;
import com.example.demo.common.ServiceException;
import com.example.demo.dto.AddHogwartsTestUserDto;
import com.example.demo.dto.JenkinsBuildDto;
import com.example.demo.dto.UpdateHogwartsTestUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.HogwartsTestUser;
import com.example.demo.service.HogwartsTestUserService;
import com.example.demo.util.JenkinsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Api(tags = "霍格沃兹测试学院-测试任务管理")
@RestController
@RequestMapping("hogwartsUser")
@Slf4j
public class HogwartsTestUserController {
    @Autowired
    private HogwartsTestUserService hogwartsTestUserService;

    @Value("${hogwarts.key}")
    private String hogwartsKey;

    @ApiOperation("登录接口")
    //@RequestMapping(value = "login", method = RequestMethod.POST)
    @PostMapping("login")
    public ResultDto<UserDto> login(@RequestBody UserDto userDto){

        String result = hogwartsTestUserService.login(userDto);

        if(userDto.getUserName().contains("error2")){
            throw new NullPointerException();
        }
        if(userDto.getUserName().contains("error")){
            ServiceException.throwEx("用户名中含有error");
        }

        return ResultDto.success("成功 " + result + " hogwartsKey1= "+ hogwartsKey,userDto);
    }

    @ApiOperation("注册接口")
    @PostMapping("register")
    public ResultDto<HogwartsTestUser> register(@RequestBody AddHogwartsTestUserDto addHogwartsTestUserDto){
        HogwartsTestUser hogwartsTestUser = new HogwartsTestUser();
        BeanUtils.copyProperties(addHogwartsTestUserDto,hogwartsTestUser);
        if (StringUtils.isEmpty(hogwartsTestUser.getUserName())){
            return ResultDto.fail("用户名不能为空!");
        }
        if (StringUtils.isEmpty(hogwartsTestUser.getPassword())){
            return ResultDto.fail("密码不能为空!");
        }
        log.info("用户注册，请求入参：" + JSONObject.toJSONString(hogwartsTestUser));
        return hogwartsTestUserService.save(hogwartsTestUser);
    }

    @ApiOperation("修改接口")
    @PutMapping("update")
    public ResultDto<HogwartsTestUser> update(@RequestBody UpdateHogwartsTestUserDto updateHogwartsTestUserDto){
        HogwartsTestUser hogwartsTestUser = new HogwartsTestUser();
        BeanUtils.copyProperties(updateHogwartsTestUserDto,hogwartsTestUser);
        if (StringUtils.isEmpty(hogwartsTestUser.getUserName())){
            return ResultDto.fail("用户名不能为空!");
        }
        if (StringUtils.isEmpty(hogwartsTestUser.getPassword())){
            return ResultDto.fail("密码不能为空!");
        }
        log.info("用户修改，请求入参：" + JSONObject.toJSONString(hogwartsTestUser));
        return hogwartsTestUserService.update(hogwartsTestUser);
    }

    @ApiOperation("删除接口")
    @DeleteMapping("{userId}")
    public ResultDto delete(@RequestBody Integer userId){
        log.info("根据用户Id删除用户信息，请求入参：" + userId);
        return hogwartsTestUserService.delete(userId);
    }

    @RequestMapping(value = "byId/{userId}/{Id}",method = RequestMethod.GET)
    public String getById(@PathVariable Long userId,@PathVariable Long Id){
        log.info("userId：" + userId);
        log.info("Id：" + Id);
        return "成功" + userId + ",Id=" + Id;
    }

//    @RequestMapping(value = "byId",method = RequestMethod.GET)
    @GetMapping("byId")
    public String getById2(@RequestParam(value = "userId",required = false) String userId,@RequestParam String Id){
        log.info(userId);
        log.info(Id);
        return "成功" + userId + ",Id=" + Id;
    }

    @ApiOperation("根据用户Id或userName模糊查询")
    @GetMapping("getByName")
    public ResultDto<List<HogwartsTestUser>> getByName(@RequestParam(value = "userId",required = false) Integer userId, @RequestParam(value = "userName",required = false) String userName){
        HogwartsTestUser hogwartsTestUser = new HogwartsTestUser();
        hogwartsTestUser.setUserName(userName);
        hogwartsTestUser.setId(userId);
        log.info("根据用户Id或userName模糊查询，请求入参" + JSONObject.toJSONString(hogwartsTestUser));
        return hogwartsTestUserService.getByName(hogwartsTestUser);
    }

    @ApiOperation("调用Jenkins构建job")
    @PutMapping("build")
    public ResultDto build(@RequestBody JenkinsBuildDto jenkinsBuildDto) throws IOException, URISyntaxException {
        log.info("调用Jenkins构建job，请求入参" + JSONObject.toJSONString(jenkinsBuildDto));
        JenkinsUtil.build(jenkinsBuildDto.getJobName(),jenkinsBuildDto.getUserName(),jenkinsBuildDto.getRemark(),
                jenkinsBuildDto.getCommand());
        return ResultDto.success("成功");
    }
}
