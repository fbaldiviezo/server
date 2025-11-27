package com.proyecto.backend_2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapaDataDto {
    private String nivel;
    private String materia;
    private String nombre;
    private String paralelo;
    private Integer codp;
    private String login;
    private String codmat;
    private Integer codpar;
    private Integer gestion;
}
