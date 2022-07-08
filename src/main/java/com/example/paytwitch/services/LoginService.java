package com.example.paytwitch.services;



import com.example.paytwitch.data.dtos.request.UserRequestLogInDto;
import com.example.paytwitch.data.models.AccessToken;
import com.example.paytwitch.exception.UserNotFoundException;
//import com.example.paytwitch.models.AccessToken;

public interface LoginService {
    AccessToken login(UserRequestLogInDto loginRequest) throws UserNotFoundException;
}
