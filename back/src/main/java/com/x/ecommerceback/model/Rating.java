package com.x.ecommerceback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "product_id",nullable = false)
    @JsonBackReference
    private Product product;

    @Column(name = "rating")
    private double rating;

    private LocalDateTime createdAt;


}
