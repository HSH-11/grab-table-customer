package com.team2.grabtablecustomer.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private String email;
    private String level; // 사용자 등급
}
