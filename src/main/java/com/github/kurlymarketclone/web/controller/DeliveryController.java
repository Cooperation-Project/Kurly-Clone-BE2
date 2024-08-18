package com.github.kurlymarketclone.web.controller;

import com.github.kurlymarketclone.repository.userDetail.CustomOauth2UserDetails;
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
    public ResponseDto getAddress(@AuthenticationPrincipal CustomOauth2UserDetails customOauth2UserDetails){
        return deliveryService.getAddressResult(customOauth2UserDetails);
    }
    @PostMapping(value = "/address")
    public ResponseDto addAddress(@AuthenticationPrincipal CustomOauth2UserDetails customOauth2UserDetails
            , @RequestBody UserDelivery userDelivery){
        return deliveryService.addAddressResult(customOauth2UserDetails,userDelivery);

    }
}
