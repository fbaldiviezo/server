package com.proyecto.backend_2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessDto {
    private String nombre;
    private String ayuda;
    private String enlace;
}
