package com.proyecto.backend_2.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private final Integer status;
    private final String message;
}
