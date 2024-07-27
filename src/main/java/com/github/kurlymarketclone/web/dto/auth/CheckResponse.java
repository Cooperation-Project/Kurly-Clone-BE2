package com.github.kurlymarketclone.web.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CheckResponse {
    private String message;
    private Boolean check;
}
