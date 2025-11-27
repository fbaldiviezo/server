package com.proyecto.backend_2.dtos.requests;

public record UpdateAsignedPerson(
                Integer oldCodp, String oldCodmat, Integer oldCodpar, Integer oldGestion,
                Integer codp, String login, String codmat, Integer codpar, Integer gestion) {
}
