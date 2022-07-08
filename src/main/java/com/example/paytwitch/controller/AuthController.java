package com.example.paytwitch.controller;

//import com.example.paytwitch.exception.UserNotFoundException;
import com.example.paytwitch.exception.WalletException;
import com.example.paytwitch.security.jwt.TokenServices;
import com.example.paytwitch.services.LoginService;
import com.example.paytwitch.services.UserService;
import com.example.paytwitch.data.dtos.request.UserRequestLogInDto;
//import com.example.paytwitch.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/v1/emailApp/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordConfig passwordConfig;


    @PostMapping("/login")
    public Response login(@RequestBody UserRequestLogInDto loginRequest) {
        String token;
        try {

            token = loginService.login(loginRequest).getAccessToken();
        } catch (WalletException e) {
            return new Response(HttpStatus.BAD_REQUEST,false,e.getMessage());
        }
        return new Response(HttpStatus.OK,true,token);

    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }));
        return errors;
    }
}
