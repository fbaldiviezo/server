package com.proyecto.backend_2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessByMenuDto {
    Integer codp;
    String nombre;
    String enlace;
    String ayuda;
    Integer estado;
    Boolean relacion;
}
