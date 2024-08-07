package com.github.kurlymarketclone.web.dto.mypage;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class UserInfoResponse {
  private List<UserInfo> userInfo;

  public UserInfoResponse(List<UserInfo> userInfo) {
    this.userInfo = userInfo;
  }
}
