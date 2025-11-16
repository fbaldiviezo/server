package com.proyecto.backend_2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsBySubjectDto {
    private Integer codi;
    private String nombre;
    private Integer estado;
    private Integer gestion;
    private Integer ponderacion;
}
