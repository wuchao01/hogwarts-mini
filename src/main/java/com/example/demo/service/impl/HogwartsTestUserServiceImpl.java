package com.example.demo.service.impl;

import com.example.demo.common.UserBaseStr;
import com.example.demo.dao.HogwartsTestUserMapper;
import com.example.demo.dto.ResultDto;
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
    private HogwartsTestUserMapper hogwartsTestUserMapper;

    @Override
    public String login(UserDto userDto){
        System.out.println(userDto.getUserName());
        System.out.println(userDto.getPwd());
        return userDto.getUserName() + "-" + userDto.getPwd();
    }

    //实现注册方法
    @Override
    public ResultDto<HogwartsTestUser> save(HogwartsTestUser hogwartsTestUser) {
        String userName = hogwartsTestUser.getUserName();
        String password= hogwartsTestUser.getPassword();

        // TODO: 2021/4/25 这里暂时还没弄明白为啥要重新new一个HogwartsTestUser，我理解应该可以直接用hogwartsTestUser类的setUserName
        HogwartsTestUser queryHogwartsTestUser = new HogwartsTestUser();
        queryHogwartsTestUser.setUserName(userName);
        //通过hogwartsTestUserMapper.select查询数据库列表数据是否存在已注册过的userName
        List<HogwartsTestUser> resultHogwartsTestUser = hogwartsTestUserMapper.select(queryHogwartsTestUser);
        //判断如果数据库查询resultHogwartsTestUser数据不为空且大于0条即已注册
        if (Objects.nonNull(resultHogwartsTestUser) && resultHogwartsTestUser.size() > 0){
            return ResultDto.fail("用户名已存在");
        }

        //密码加密
        String newPwd = DigestUtils.md5DigestAsHex((UserBaseStr.md5Hex_sign + userName + password).getBytes());
        //使用md5加密后的密码存储数据
        hogwartsTestUser.setPassword(newPwd);
        hogwartsTestUser.setCreateTime(new Date());
        //如果数据库不为空字段传空值，系统会报错空指针，ResultDto.fail方法会拦截并返回失败提示
        hogwartsTestUser.setUpdateTime(new Date());
        hogwartsTestUserMapper.insertUseGeneratedKeys(hogwartsTestUser);

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
