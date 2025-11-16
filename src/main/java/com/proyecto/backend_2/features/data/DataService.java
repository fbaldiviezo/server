package com.proyecto.backend_2.features.data;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.features.data.repositories.DataRepository;
import com.proyecto.backend_2.features.data.repositories.DataRepository2;
import com.proyecto.backend_2.features.personals.PersonalModel;
import com.proyecto.backend_2.features.personals.PersonalRepository;
import com.proyecto.backend_2.ids.DataId;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataService {
    private final DataRepository repository;
    private final DataRepository2 repository2;
    private final PersonalRepository personalRepository;
    private final CustomResponseBuilder customResponseBuilder;

    @Transactional
    public ResponseEntity<ApiResponse> save(DataId data) {
        PersonalModel p = personalRepository.findById(data.getCodp())
                .orElseThrow(
                        () -> new EntityNotFoundException("No se encontró el personal con codp: "));
        DataId id = new DataId(p.getCodp(), data.getCedula());
        if (repository.existsById(id)) {
            throw new ResourceAlreadyExistsException("El recurso ya existe");
        }
        repository.save(new DataModel(id, p));
        return customResponseBuilder.buildResponse("successfull", data);
    }

    @Transactional
    public ResponseEntity<ApiResponse> update(Integer codp, String oldCedula, String newCedula) {
        DataId id = new DataId(codp, oldCedula);
        if (repository.existsById(id)) {
            repository2.updateCedula(codp, newCedula);
        } else {
            throw new EntityNotFoundException("No existe la clave buscada");
        }
        return customResponseBuilder.buildResponse("successful", id);
    }
}
