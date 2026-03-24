package com.popo.esun.socialmedia.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "cover_image", length = 255)
    private String coverImage;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}