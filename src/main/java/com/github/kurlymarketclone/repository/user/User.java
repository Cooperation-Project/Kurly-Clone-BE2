package com.github.kurlymarketclone.repository.user;

import com.github.kurlymarketclone.web.dto.auth.SignRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="userId")
@ToString
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "my_id",nullable = false,length = 55)
    private String myId;
    @Column(name = "password",nullable = false,length = 255)
    private String password;
    @Column(name = "name",nullable = false,length = 45)
    private String name;
    @Column(name = "email",nullable = false,length = 100)
    private String email;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public User(SignRequest signRequest){
        this.myId=signRequest.getId();
        this.name=signRequest.getName();
        this.email=signRequest.getEmail();
        this.phoneNumber=signRequest.getPhoneNumber();
        this.birthday=signRequest.getBirthday();
        this.createdAt=LocalDateTime.now();
    }
}
