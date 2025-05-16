package com.team2.grabtablecustomer.domain.user.dto;

import com.team2.grabtablecustomer.domain.user.entity.Membership;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String name;
    private String email;
    private String password;
    private Date createdAt;

    private List<Membership> memberships;

    private String selectedMembership;
}
