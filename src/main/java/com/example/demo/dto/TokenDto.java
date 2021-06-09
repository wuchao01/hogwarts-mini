package com.example.demo.dto;

import lombok.Data;

@Data
public class TokenDto {
    private Integer userId;
    private String userName;
    private String token;
    private Integer defaultJenkinsId;

}
