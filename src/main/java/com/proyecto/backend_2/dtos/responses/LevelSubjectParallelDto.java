package com.proyecto.backend_2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LevelSubjectParallelDto {
    private String lista;
    private String codmat;
    private Integer codpar;
    private Integer gestion;
    private Integer estado;
}
