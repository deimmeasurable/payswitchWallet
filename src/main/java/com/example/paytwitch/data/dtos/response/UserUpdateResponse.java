package com.example.paytwitch.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateResponse {
    private String newFirstName;
    private String newLastName;
    private String email;
    private List<String> listOfCountry= new ArrayList<>();
    private String newPassword;

}
