package com.example.paytwitch.data.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestLogInDto {
    private String email;
    private String password;
    private String message;

}
