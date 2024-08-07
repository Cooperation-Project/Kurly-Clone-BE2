package com.github.kurlymarketclone.web.dto.mypage;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.kurlymarketclone.repository.delivey.Delivery;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class UserDelivery {
    private String address;


    public UserDelivery(Delivery delivery){
        this.address=delivery.getDelivery();

    }
}
