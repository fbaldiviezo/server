package com.proyecto.backend_2.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomResponseBuilder {
    public ResponseEntity<ApiResponse> buildResponse(String message, Object data) {
        return new ApiResponse.ApiResponseBuilder(message).withData(data).build();
    }

    public ResponseEntity<ApiResponse> buildResponse(String message, Object data, Object data2) {
        return new ApiResponse.ApiResponseBuilder(message).withData(data).withData2(data2).build();
    }
}
