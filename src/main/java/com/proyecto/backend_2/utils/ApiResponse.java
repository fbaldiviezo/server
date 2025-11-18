package com.proyecto.backend_2.utils;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "message", "data", "data2" })
public class ApiResponse<T> {
    private final String message;
    private final T data;
    private final T data2;

    private ApiResponse(ApiResponseBuilder builder) {
        this.message = builder.message;
        this.data = (T) builder.data;
        this.data2 = (T) builder.data2;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public T getData2() {
        return data2;
    }

    public static class ApiResponseBuilder<T> {
        private final String message;
        private T data;
        private T data2;

        public ApiResponseBuilder(String message) {
            this.message = message;
        }

        public ApiResponseBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> withData2(T data2) {
            this.data2 = data2;
            return this;
        }

        public ResponseEntity<ApiResponse<T>> build() {
            ApiResponse<T> apiResponse = new ApiResponse<>(this);
            return ResponseEntity.ok().body(apiResponse);
        }
    }

}
