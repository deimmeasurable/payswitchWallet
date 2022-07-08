package com.example.paytwitch.services;

import com.example.paytwitch.data.dtos.request.UserRequest;
import com.example.paytwitch.data.dtos.request.UserUpdateRequest;
import com.example.paytwitch.data.dtos.response.UserResponse;
import com.example.paytwitch.data.dtos.response.UserUpdateResponse;
import com.example.paytwitch.data.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

   UserResponse createUser(UserRequest form);

   User findUserByEmail(String email);


   UserUpdateResponse userCanUpdateDetails(UserUpdateRequest userUpdate);
}
