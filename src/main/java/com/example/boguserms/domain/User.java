package com.example.boguserms.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy ="org.hibernate.id.UUIDGenerator")
    private UUID userId;
    @Column(name = "login")
    @NonNull
    private String login;
    @Column(name = "password")
    @NonNull
    private String password;
    @Column(name = "email")
    @NonNull
    private String email;
    @Column(name = "firstName")
    @NonNull
    private String firstName;
    @Column(name = "lastName")
    @NonNull
    private String lastName;
}
