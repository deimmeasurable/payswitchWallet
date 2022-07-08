package com.example.paytwitch.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private Object payload;
    private boolean isSuccessful;
    private int statusCode;
    private String message;
}
