package com.github.kurlymarketclone.service.mypage;

import com.github.kurlymarketclone.repository.user.User;
import com.github.kurlymarketclone.repository.user.UserRepository;
import com.github.kurlymarketclone.repository.userDetail.CustomUserDetails;
import com.github.kurlymarketclone.service.exception.NotFoundException;
import com.github.kurlymarketclone.web.dto.mypage.UserDeliveryResponse;
import com.github.kurlymarketclone.web.dto.mypage.UserInfo;
import com.github.kurlymarketclone.web.dto.mypage.UserInfoResponse;
import com.github.kurlymarketclone.web.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

  private final UserRepository userRepository;

  public ResponseDto getUserInfo(CustomUserDetails customUserDetails) {
    Integer userId =  customUserDetails.getUserId();
    User user = userRepository.findById(userId)
        .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다."));

    return new ResponseDto(HttpStatus.OK.value(), "정보 조회 성공", user);
  }

  public ResponseDto modUserInfo(CustomUserDetails customUserDetails, UserInfo userInfo) {
    Integer userId =  customUserDetails.getUserId();
    User user = userRepository.findById(userId)
        .orElseThrow(()-> new NotFoundException("유저를 찾을 수 없습니다."));

    try {

      user.setEmail(userInfo.getEmail());
      user.setPhoneNumber(userInfo.getPhoneNumber());
      userRepository.save(user);

      return new ResponseDto(HttpStatus.OK.value(), "유저 정보가 성공적으로 변경되었습니다.");

    }catch (Exception e) {
      return new ResponseDto(HttpStatus.BAD_REQUEST.value(),"유저 정보 수정에 실패했습니다.");
    }

  }

}
