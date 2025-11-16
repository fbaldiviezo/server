package com.proyecto.backend_2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParallelsBySubjectDto {
    private Integer codpar;
    private String nombre;
    private Integer estado;
    private Integer gestion;
}
