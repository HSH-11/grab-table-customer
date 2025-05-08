package com.team2.grabtablecustomer.domain.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResultDto {

    private String result;
    private UserDto userDto;            // 단건
    private List<UserDto> userList;     // 복수건
}
