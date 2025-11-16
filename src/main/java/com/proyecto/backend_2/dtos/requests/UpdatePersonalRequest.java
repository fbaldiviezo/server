package com.proyecto.backend_2.dtos.requests;

import java.time.LocalDate;

public record UpdatePersonalRequest(String oldCedula, String newCedula, Integer codp, String nombre,
        String ap, String am, Integer estado, LocalDate fnac, String ecivil,
        String genero, String direc, String telf, String tipo, String foto) {
}
