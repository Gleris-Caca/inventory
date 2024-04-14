package com.lufthansatest.inventory.exceptions;

import com.lufthansatest.inventory.model.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice //allows the class to handle exceptions thrown by any controller
@Slf4j
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity<BaseResponse> handeGeneralException(GeneralException e){
        log.info("General Exception");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessages(List.of("Bad Request"));
        return ResponseEntity.status(403).body(baseResponse);
    }
}