package com.github.kurlymarketclone.service.auth;

import com.github.kurlymarketclone.repository.user.User;
import com.github.kurlymarketclone.repository.user.UserRepository;
import com.github.kurlymarketclone.repository.userDetail.CustomUserDetails;
import com.github.kurlymarketclone.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Primary
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String myId) throws UsernameNotFoundException {
        User user=userRepository.findByMyId(myId).orElseThrow(()-> new NotFoundException("해당 유저를 찾을 수 없습니다."));


        return CustomUserDetails.builder()
                .myId(user.getMyId())
                .userId(user.getUserId())
                .password(user.getPassword())
                .build();
    }
}
