package com.github.kurlymarketclone.service.auth;

public interface OAuth2UserInfo {

  String getProvider();
  String getProviderId();
  String getEmail();
  String getName();

}
