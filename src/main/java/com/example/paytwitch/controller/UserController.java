package com.example.paytwitch.controller;

import com.example.paytwitch.data.dtos.request.UserRequest;
import com.example.paytwitch.data.dtos.request.UserUpdateRequest;
import com.example.paytwitch.data.dtos.response.ApiResponse;
import com.example.paytwitch.data.dtos.response.UserResponse;
import com.example.paytwitch.data.dtos.response.UserUpdateResponse;
import com.example.paytwitch.exception.UserAlreadyExistsException;
import com.example.paytwitch.exception.UserNotFoundException;
import com.example.paytwitch.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payTwitch")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
        @PostMapping("/user/Register")
    public ResponseEntity<?>createUser(@RequestParam UserRequest userRequest)throws UserAlreadyExistsException{
        try{
            UserResponse userResponse= userService.createUser(userRequest);
            ApiResponse response =ApiResponse.builder()
                    .payload(userResponse)
                    .isSuccessful(true)
                    .statusCode(200)
                    .message("user created successfully")
                    .build();
            return  new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (UserAlreadyExistsException e){
            ApiResponse response = ApiResponse.builder()
                    .message(e.getMessage())
                    .isSuccessful(false)
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        }
       @PutMapping("/update/{email}/{password}")
    public ResponseEntity<?> userCanUpdateDetails(@RequestParam UserUpdateRequest userUpdateRequest)throws UserNotFoundException{
        try{
            UserUpdateResponse userResponse = userService.userCanUpdateDetails(userUpdateRequest);
            ApiResponse response = ApiResponse.builder()
                    .payload(userResponse)
                    .isSuccessful(true)
                    .statusCode(200)
                    .message("info update successfully")
                    .build();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(UserNotFoundException e){
            ApiResponse response =ApiResponse.builder()
                    .message(e.getMessage())
                    .isSuccessful(false)
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
       }



}
