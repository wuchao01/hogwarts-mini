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
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private HogwartsTestUserMapper hogwartsTestUserMapper;

    //实现jenkins新增方法
    @Override
    //声明式事务，失败就回滚
    @Transactional(rollbackFor = Exception.class)
    public ResultDto<HogwartsTestJenkins> save(TokenDto tokenDto,HogwartsTestJenkins hogwartsTestJenkins) {
        hogwartsTestJenkins.setCreateUserId(tokenDto.getUserId());
        hogwartsTestJenkins.setCreateTime(new Date());
        hogwartsTestJenkins.setUpdateTime(new Date());
        hogwartsTestJenkinsMapper.insertUseGeneratedKeys(hogwartsTestJenkins);
        Integer defaultJenkinsFlag = hogwartsTestJenkins.getDefaultJenkinsFlag();
        //判断jenkinsID是否为默认服务器，如果是就设置为默认
        if (Objects.nonNull(defaultJenkinsFlag) && defaultJenkinsFlag == 1){
            HogwartsTestUser queryHogwartsTestUser = new HogwartsTestUser();
            //从tokenDto中获取用户ID
            queryHogwartsTestUser.setId(tokenDto.getUserId());
            //获取用户信息
            HogwartsTestUser resultHogwartsTestUser = hogwartsTestUserMapper.selectOne(queryHogwartsTestUser);
            resultHogwartsTestUser.setDefaultJenkinsId(hogwartsTestJenkins.getId());
            hogwartsTestUserMapper.updateByPrimaryKeySelective(resultHogwartsTestUser);
            //更新请求头token中的信息
            tokenDto.setDefaultJenkinsId(hogwartsTestJenkins.getId());
            tokenDb.addUserInfo(tokenDto.getToken(),tokenDto);
        }
        return ResultDto.success("成功",hogwartsTestJenkins);
    }

}
