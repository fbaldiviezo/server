package com.proyecto.backend_2.dtos.requests;

import com.proyecto.backend_2.features.personals.PersonalModel;

public record PersonalRequest(PersonalModel persona, String cedula) {
}
