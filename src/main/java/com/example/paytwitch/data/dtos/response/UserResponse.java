package com.example.paytwitch.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private List<String> country= new ArrayList<String>();
    private String password;
}
