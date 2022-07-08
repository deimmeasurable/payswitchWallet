package com.example.paytwitch.data.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequest {
    private String newFirstName;
    private String newLastName;
    private String email;
    private List<String> listOfCountry= new ArrayList<>();
    private String newPassword;
}
