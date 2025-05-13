package com.team2.grabtablecustomer.domain.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    private Long storeId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id")
//    private Owner owner;


    private String name;
    private String location;
    private String type;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;
    private String imageContentType;

}
