package com.proyecto.backend_2.features.dmodalities;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.requests.DmodalityRequest;
import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.modalities.ModalityModel;
import com.proyecto.backend_2.features.modalities.ModalityRepository;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DmodalityService {
    private final DmodalityRepository repository;
    private final ModalityRepository modalityRepository;
    private final CustomResponseBuilder customResponseBuilder;

    public List<DmodalityModel> get() {
        return repository.findAll();
    }

    public List<DmodalityModel> getByState(Integer state) {
        if (state == 2) {
            return repository.findAll();
        }
        return repository.getByState(state);
    }

    public ResponseEntity<ApiResponse> post(DmodalityRequest post) {
        String capitalizado = post.nombre().substring(0, 1).toUpperCase() + post.nombre().substring(1);
        if (repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        if (repository.existsById(post.coddm())) {
            throw new ResourceAlreadyExistsException("El recurso ya existe");
        }
        ModalityModel modalidad = modalityRepository.findById(post.codm())
                .orElseThrow(() -> new EntityNotFoundException("Modalidad no encontrada"));
        DmodalityModel model = DmodalityModel.builder()
                .coddm(post.coddm())
                .nombre(post.nombre())
                .estado(1)
                .modalidad(modalidad)
                .build();
        repository.save(model);
        return customResponseBuilder.buildResponse("Guardado con exito", model);
    }

    public ResponseEntity<ApiResponse> put(String id, DmodalityRequest put) {
        DmodalityModel existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
        String capitalizado = put.nombre().substring(0, 1).toUpperCase() + put.nombre().substring(1);
        if (!existing.getNombre().equalsIgnoreCase(capitalizado) &&
                repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        ModalityModel modalidad = modalityRepository.findById(put.codm())
                .orElseThrow(() -> new EntityNotFoundException("Nivel no encontrado"));
        existing.setNombre(capitalizado);
        existing.setModalidad(modalidad);
        repository.save(existing);
        return customResponseBuilder.buildResponse("Modificado correctamente", existing);
    }

    @Transactional
    public ResponseEntity<ApiResponse> changeState(String id, Integer state) {
        repository.changeState(id, state);
        return customResponseBuilder.buildResponse("Modificado correctamente", state);
    }
}
