package com.example.demo.common;

import com.example.demo.dto.TokenDto;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Restful方式登陆token
 */
public class TokenDb {
    //key就是token字符串
    private Map<String, TokenDto> tokenMap = new HashMap<>();

    //返回在线人数
    public int getTokenSize(){
        return tokenMap.size();
    }

    public TokenDto getTokenDto(String token){
        if (StringUtils.isEmpty(token)){
            return new TokenDto();
        }
        return tokenMap.get(token);
    }

    //也可以实现成登录用户互踢，2种方式，1是id前后缀，2是id-token=map的key-value
    public TokenDto addToken(String token,TokenDto tokenDto){
        if (Objects.isNull(tokenDto)){
            return tokenDto;
        }
        return tokenMap.put(token,tokenDto);
    }

    //移除token
    public TokenDto removeToken(String token){
        if (Objects.isNull(token)){
            return null;
        }
        return tokenMap.remove(token);
    }

    public boolean isLogin(String token){
        return tokenMap.get(token)!=null;
    }
}
