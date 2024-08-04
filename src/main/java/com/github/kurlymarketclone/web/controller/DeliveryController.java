package com.github.kurlymarketclone.web.controller;

import com.github.kurlymarketclone.repository.userDetail.CustomUserDetails;
import com.github.kurlymarketclone.service.mypage.DeliveryService;
import com.github.kurlymarketclone.web.dto.mypage.UserDelivery;
import com.github.kurlymarketclone.web.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;
    @GetMapping(value = "/address")
    public ResponseDto getAddress(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return deliveryService.getAddressResult(customUserDetails);
    }
    @PostMapping(value = "/address")
    public ResponseDto addAddress(@AuthenticationPrincipal CustomUserDetails customUserDetails
            , @RequestBody UserDelivery userDelivery){
        return deliveryService.addAddressResult(customUserDetails,userDelivery);

    }
}
