package com.github.kurlymarketclone.web.dto.mypage;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class UserDeliveryResponse {
    private List<UserDelivery> addressList;

    public UserDeliveryResponse(List<UserDelivery> userDeliveryList){
        this.addressList = userDeliveryList;

    }
}
