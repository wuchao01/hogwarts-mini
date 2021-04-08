package com.example.demo.dto;

import lombok.Data;

@Data
public class TokenDto {
    private Integer userId;
    private Integer userName;
    private Integer token;
    private Integer defaultJenkinsId;

}
