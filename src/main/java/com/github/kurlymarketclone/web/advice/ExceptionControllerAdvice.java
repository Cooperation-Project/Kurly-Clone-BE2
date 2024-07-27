package com.github.kurlymarketclone.web.advice;

import com.github.kurlymarketclone.service.exception.NotFoundException;
import com.github.kurlymarketclone.web.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto> handleNotFoundException(NotFoundException nfe){
        log.error("Client 요청이후 DB 검색 중 에러로 다음처럼 출력합니다. " + nfe.getMessage());
        ResponseDto responseDto = new ResponseDto(HttpStatus.NOT_FOUND.value(), nfe.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDto> handleAccessDeniedException(AccessDeniedException ade){
        log.error("Client 요청에 문제가 있어 다음처럼 출력합니다. " + ade.getMessage());
        ResponseDto responseDto = new ResponseDto(HttpStatus.FORBIDDEN.value(), ade.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
    }


}
