package com.team2.grabtablecustomer.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "membership")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Membership {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId;

    private String name;
}
