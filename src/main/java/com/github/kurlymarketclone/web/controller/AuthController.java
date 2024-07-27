package com.github.kurlymarketclone.web.controller;

import com.github.kurlymarketclone.repository.userDetail.CustomUserDetails;
import com.github.kurlymarketclone.service.auth.AuthService;
import com.github.kurlymarketclone.web.dto.auth.EmailCheck;
import com.github.kurlymarketclone.web.dto.auth.IdCheck;
import com.github.kurlymarketclone.web.dto.auth.Login;
import com.github.kurlymarketclone.web.dto.auth.SignRequest;
import com.github.kurlymarketclone.web.dto.response.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    @PostMapping(value = "/sign-up")
    public ResponseDto signUp(@RequestBody SignRequest signRequest){
        return authService.signUpResult(signRequest);
    }
    @PostMapping(value = "/login")
    public ResponseDto login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse){
        String token = authService.login(loginRequest);
        httpServletResponse.setHeader("Token", token);
        return new ResponseDto(HttpStatus.OK.value(),"Login Success");
    }
    @GetMapping(value = "/email")
    public ResponseDto emailCheck(@RequestBody EmailCheck emailCheck){
        return authService.emailCheckResult(emailCheck);
    }

    @GetMapping(value = "/id")
    public ResponseDto IdCheck(@RequestBody IdCheck idCheck){
        return authService.idCheckResult(idCheck);
    }




}
