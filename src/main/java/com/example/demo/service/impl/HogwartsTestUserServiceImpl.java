package com.example.demo.service.impl;

import com.example.demo.common.Token;
import com.example.demo.common.TokenDb;
import com.example.demo.common.UserBaseStr;
import com.example.demo.dao.HogwartsTestUserMapper;
import com.example.demo.dto.ResultDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.HogwartsTestUser;
import com.example.demo.service.HogwartsTestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class HogwartsTestUserServiceImpl implements HogwartsTestUserService {
    @Autowired
    private TokenDb tokenDb;

    @Autowired
    private HogwartsTestUserMapper hogwartsTestUserMapper;

    @Override
    public ResultDto<Token> login(UserDto userDto){
        //ResultDto<Token>里的为common.Token类
        System.out.println(userDto.getUserName());
        System.out.println(userDto.getPwd());
        //1、获取用户录入的用户名/密码并用MD5加密
        String newPwd = DigestUtils.md5DigestAsHex((UserBaseStr.md5Hex_sign + userDto.getUserName() + userDto.getPwd()).getBytes());
        //2、根据用户名+新密码查询数据库中是否存在数据
        HogwartsTestUser queryHogwartsTestUser = new HogwartsTestUser();
        queryHogwartsTestUser.setUserName(userDto.getUserName());
        queryHogwartsTestUser.setPassword(newPwd);
        HogwartsTestUser resultHogwartsTestUser = hogwartsTestUserMapper.selectOne(queryHogwartsTestUser);
        //3、若不存在提示：用户不存在或密码错误
        if (Objects.isNull(resultHogwartsTestUser)){
            return ResultDto.fail("用户不存在或密码错误");
        }
        //4、若存在，则创建Token对象，生成token并将相关信息存入TokenDto
        Token token = new Token();
        String tokenStr = DigestUtils.md5DigestAsHex((System.currentTimeMillis() + userDto.getUserName() + userDto.getPwd()).getBytes());
        token.setToken(tokenStr);
        TokenDto tokenDto = new TokenDto();

        tokenDto.setToken(tokenStr);
        tokenDto.setUserName(resultHogwartsTestUser.getUserName());
        tokenDto.setUserId(resultHogwartsTestUser.getId());
        tokenDto.setDefaultJenkinsId(resultHogwartsTestUser.getDefaultJenkinsId());
        // TODO: 2021/6/9 这里的tokenDb后续可以用redis实现，目前教程简单所以使用的是TokenDb类模拟redis
        tokenDb.addUserInfo(tokenStr,tokenDto);

        return ResultDto.success("成功",token);
    }

    //实现注册方法
    @Override
    public ResultDto<HogwartsTestUser> save(HogwartsTestUser hogwartsTestUser) {
        String userName = hogwartsTestUser.getUserName();
        String password= hogwartsTestUser.getPassword();

        // TODO: 2021/4/25 这里重新new一个HogwartsTestUser，和开发咨询可以直接用hogwartsTestUser类的setUserName也可以new一个
        //  对象看场景需要，如果hogwartsTestUser有属性就不需要在重新new一个
        HogwartsTestUser queryHogwartsTestUser = new HogwartsTestUser();
        queryHogwartsTestUser.setUserName(userName);
        //通过hogwartsTestUserMapper.select查询数据库列表数据是否存在已注册过的userName
        List<HogwartsTestUser> resultHogwartsTestUser = hogwartsTestUserMapper.select(queryHogwartsTestUser);
        //判断如果数据库查询resultHogwartsTestUser数据不为空且大于0条即已注册
        if (Objects.nonNull(resultHogwartsTestUser) && resultHogwartsTestUser.size() > 0){
            return ResultDto.fail("用户名已存在");
        }

        //密码加密，因为加密规则是用username+password生成，故每次注册用户名不一样所以加密的密码也不一样
        String newPwd = DigestUtils.md5DigestAsHex((UserBaseStr.md5Hex_sign + userName + password).getBytes());
        //使用md5加密后的密码存储数据
        hogwartsTestUser.setPassword(newPwd);
        hogwartsTestUser.setCreateTime(new Date());
        //如果数据库不为空字段传空值，系统会报错空指针，ResultDto.fail方法会拦截并返回失败提示
        hogwartsTestUser.setUpdateTime(new Date());
        hogwartsTestUserMapper.insertUseGeneratedKeys(hogwartsTestUser);
        //response中去除密码字段
        hogwartsTestUser.setPassword(null);
        return ResultDto.success("成功",hogwartsTestUser);
    }


    @Override
    public ResultDto<HogwartsTestUser> update(HogwartsTestUser hogwartsTestUser) {
        hogwartsTestUser.setCreateTime(new Date());
        //如果数据库不为空字段传空值，系统会报错空指针，ResultDto.fail方法会拦截并返回失败提示
        hogwartsTestUser.setUpdateTime(new Date());
        hogwartsTestUserMapper.updateByPrimaryKeySelective(hogwartsTestUser);

        return ResultDto.success("成功",hogwartsTestUser);
    }

    /**
     * 根据用户id或名称查询
     *
     * @param hogwartsTestUser
     * @return
     */
    @Override
    public ResultDto<List<HogwartsTestUser>> getByName(HogwartsTestUser hogwartsTestUser) {
//        List<HogwartsTestUser> hogwartsTestUserList = hogwartsTestUserMapper.getByName( hogwartsTestUser.getId(),hogwartsTestUser.getUserName());
        List<HogwartsTestUser> hogwartsTestUserList = hogwartsTestUserMapper.select(hogwartsTestUser);
        return ResultDto.success("成功", hogwartsTestUserList);
    }

    /**
     * 根据用户id删除
     * @param userId
     * @return
     */
    @Override
    public ResultDto delete(Integer userId) {
        HogwartsTestUser hogwartsTestUser = new HogwartsTestUser();
        hogwartsTestUser.setId(userId);
        hogwartsTestUserMapper.deleteByPrimaryKey(hogwartsTestUser);
        return ResultDto.success("成功");
    }


}
