package com.example.boguserms.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
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
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;
}
