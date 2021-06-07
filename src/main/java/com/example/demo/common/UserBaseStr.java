package com.example.demo.common;

public class UserBaseStr {
    public static final String LOGIN_TOKEN = "token";
    //盐值，定义后最好不要改变，否则改之前注册和改制后注册用户生成的密码会导致不一致
    public static final String md5Hex_sign = "Hogwarts";
}
