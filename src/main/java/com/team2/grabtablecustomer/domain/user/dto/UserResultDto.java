package com.team2.grabtablecustomer.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResultDto {

    private String result;
    private UserDto userDto;            // 단건
    private List<UserDto> userList;     // 복수건
}
