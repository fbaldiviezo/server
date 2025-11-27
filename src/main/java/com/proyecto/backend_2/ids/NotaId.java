package com.proyecto.backend_2.ids;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaId implements Serializable {
    private Integer codp;
    private String login;
    private String codmat;
    private Integer codpar;
    private Integer gestion;
}
