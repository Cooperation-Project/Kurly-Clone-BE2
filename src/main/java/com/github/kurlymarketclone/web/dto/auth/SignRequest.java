package com.github.kurlymarketclone.web.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignRequest {

    private String id;
    private String password;
    private String passwordCheck;
    private String name;
    private String email;
    private Integer phoneNumber;
    private String address;
    private LocalDate birthday;
}
