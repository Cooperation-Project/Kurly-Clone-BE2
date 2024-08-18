package com.github.kurlymarketclone.service.auth;

import com.github.kurlymarketclone.repository.user.User;
import com.github.kurlymarketclone.repository.user.UserRepository;
import com.github.kurlymarketclone.repository.userDetail.CustomOauth2UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    log.info("getAttributes : {}",oAuth2User.getAttributes());

    String provider = userRequest.getClientRegistration().getRegistrationId();

    OAuth2UserInfo oAuth2UserInfo = null;

    // 뒤에 진행할 다른 소셜 서비스 로그인을 위해 구분 => 구글
    if(provider.equals("google")){
      log.info("구글 로그인");
      oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());

    }

    assert oAuth2UserInfo != null;
    String providerId = oAuth2UserInfo.getProviderId();
    String email = oAuth2UserInfo.getEmail();
    String loginId = provider + "_" + providerId;
    String name = oAuth2UserInfo.getName();

    User findMember =userRepository.findByMyId(loginId).orElse(null);
    User user;

    if (findMember == null) {
      user = User.builder()
          .myId(loginId)
          .name(name)
          .email(email)
          .provider(provider)
          .providerId(providerId)
          .build();
      userRepository.save(user);
    } else{
      user = findMember;
    }

    return new CustomOauth2UserDetails(user, oAuth2User.getAttributes());
  }

}
