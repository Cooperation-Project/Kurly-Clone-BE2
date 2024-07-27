package com.github.kurlymarketclone.service.auth;

import com.github.kurlymarketclone.config.security.JwtTokenProvider;
import com.github.kurlymarketclone.config.security.PasswordEncoderConfig;
import com.github.kurlymarketclone.repository.user.User;
import com.github.kurlymarketclone.repository.user.UserRepository;
import com.github.kurlymarketclone.service.exception.NotFoundException;
import com.github.kurlymarketclone.web.dto.auth.*;
import com.github.kurlymarketclone.web.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    @Transactional
    public ResponseDto signUpResult(SignRequest signRequest) {
        if (userRepository.existsByMyId(signRequest.getId())){
            return new ResponseDto(HttpStatus.CONFLICT.value(),"아이디"+ signRequest.getId()+"은 이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByEmail(signRequest.getEmail())){
            return new ResponseDto(HttpStatus.CONFLICT.value(),"이메일 : "+signRequest.getEmail()+"은 이미 존재하는 이메일입니다.");
        }
        if (!signRequest.getPassword().equals(signRequest.getPasswordCheck())){
            return new ResponseDto(HttpStatus.BAD_REQUEST.value(),"비밀번호가 비밀번호 확인 창과 다릅니다.");
        }

        User user =new User(signRequest);
        user.setPassword(passwordEncoder.encode(signRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return new ResponseDto(HttpStatus.OK.value(),signRequest.getName()+ "님 회원 가입에 성공하셨습니다.");
    }

    public String login(Login loginRequest) {
        String myId = loginRequest.getId();
        String password = loginRequest.getPassword();

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(myId,password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user =userRepository.findByMyId(myId)
                    .orElseThrow(() -> new NotFoundException("해당 아이디를 찾을 수 없습니다."));


            return jwtTokenProvider.createToken(myId);
        }catch (NotFoundException e){
            throw e;
        }catch(Exception e){
            e.printStackTrace();
            throw new NotFoundException("이메일 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.");
        }

    }

    public ResponseDto emailCheckResult(EmailCheck emailCheck) {
        String email=emailCheck.getEmail().toLowerCase();
        if (userRepository.existsByEmail(email)){
            CheckResponse checkResponse = new CheckResponse(emailCheck.getEmail()+"(는)은 이미 존재하는 이메일입니다. 다른 이메일을 이용해주세요.",true);
            return new ResponseDto(HttpStatus.CONFLICT.value(), "중복 여부 확인",checkResponse);
        }else {
            CheckResponse checkResponse = new CheckResponse(emailCheck.getEmail()+"(는)은 사용하실 수 있는 이메일입니다.",false);
            return new ResponseDto(HttpStatus.OK.value(), "중복 여부 확인",checkResponse);
        }
    }

    public ResponseDto idCheckResult(IdCheck idCheck) {
        String myId=idCheck.getId().toLowerCase();
        if (userRepository.existsByMyId(myId)){
            CheckResponse checkResponse = new CheckResponse(idCheck.getId()+"(는)은 이미 존재하는 아이디입니다. 다른 이메일을 이용해주세요.",true);
            return new ResponseDto(HttpStatus.CONFLICT.value(), "중복 여부 확인",checkResponse);
        }else {
            CheckResponse checkResponse = new CheckResponse(idCheck.getId()+"(는)은 사용하실 수 있는 아이디입니다.",false);
            return new ResponseDto(HttpStatus.OK.value(), "중복 여부 확인",checkResponse);
        }
    }

    }

