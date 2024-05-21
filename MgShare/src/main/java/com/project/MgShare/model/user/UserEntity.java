package com.project.MgShare.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    @Column(unique = true)
    private String userEmail;

    private String password;

    private String phoneNumber;

    private String role;
}
