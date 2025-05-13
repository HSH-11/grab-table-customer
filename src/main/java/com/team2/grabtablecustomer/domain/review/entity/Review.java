package com.team2.grabtablecustomer.domain.review.entity;

import com.team2.grabtablecustomer.domain.menu.entity.Menu;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    Store store;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    Menu menu;

    // todo : 예약 연결
//    @OneToOne
//    Reservation reservation;

    String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    private String imageContentType;

}
