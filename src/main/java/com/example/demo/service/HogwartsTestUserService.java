package com.example.demo.service;

import com.example.demo.dto.ResultDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.HogwartsTestUser;

import java.util.List;

public interface HogwartsTestUserService {
    ResultDto login(UserDto userDto);

    /**
     * 保存
     * @param hogwartsTestUser
     * @return
     */
    ResultDto<HogwartsTestUser> save(HogwartsTestUser hogwartsTestUser);

    /**
     * 修改
     * @param hogwartsTestUser
     * @return
     */
    ResultDto<HogwartsTestUser> update(HogwartsTestUser hogwartsTestUser);

    /**
     * 根据用户名或ID模糊查询
     * @param hogwartsTestUser
     * @return
     */
    ResultDto<List<HogwartsTestUser>> getByName(HogwartsTestUser hogwartsTestUser);

    /**
     * 根据用户ID删除
     * @param userId
     * @return
     */
    ResultDto delete(Integer userId);

}
