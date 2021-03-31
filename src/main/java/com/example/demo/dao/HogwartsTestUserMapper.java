package com.example.demo.dao;

import com.example.demo.common.MysqlExtensionMapper;
import com.example.demo.entity.HogwartsTestUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HogwartsTestUserMapper extends MysqlExtensionMapper<HogwartsTestUser> {
//    HogwartsTestUser selectHogwartsTestUser(@Param("id") Integer id);
        List<HogwartsTestUser> getByName(@Param("userId") Integer userId, @Param("userName") String userName);

}