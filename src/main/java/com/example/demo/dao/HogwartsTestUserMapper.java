package com.example.demo.dao;

import com.example.demo.common.MysqlExtensionMapper;
import com.example.demo.entity.HogwartsTestUser;
import org.springframework.stereotype.Repository;

@Repository
public interface HogwartsTestUserMapper extends MysqlExtensionMapper<HogwartsTestUser> {
}