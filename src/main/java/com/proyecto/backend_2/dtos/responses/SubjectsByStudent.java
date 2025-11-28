package com.proyecto.backend_2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectsByStudent {
    private String codmat;
    private String nombreMateria;
    private java.util.List<ItemDto> items;
}
