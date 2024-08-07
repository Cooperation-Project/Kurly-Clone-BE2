package com.github.kurlymarketclone.web.controller;

import com.github.kurlymarketclone.repository.userDetail.CustomUserDetails;
import com.github.kurlymarketclone.service.mypage.MyPageService;
import com.github.kurlymarketclone.web.dto.mypage.UserInfo;
import com.github.kurlymarketclone.web.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mypage")
@RequiredArgsConstructor
public class MyPageController {

  private final MyPageService myPageService;

  @GetMapping(value = "/info")
  public ResponseDto getInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
    return myPageService.getUserInfo(customUserDetails);
  }

  @PutMapping(value = "/info")
  public ResponseDto modUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserInfo userInfo) {
    return myPageService.modUserInfo(customUserDetails, userInfo);
  }

}
