package com.proyecto.backend_2.features.levels;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LevelService {
    private final LevelRepository repository;
    private final CustomResponseBuilder customResponseBuilder;

    public List<LevelModel> get() {
        return repository.findAll();
    }

    public List<LevelModel> getByState(Integer state) {
        if (state == 2) {
            return repository.findAll();
        }
        return repository.getByState(state);
    }

    public ResponseEntity<ApiResponse> post(LevelModel post) {
        String capitalizado = post.getNombre().substring(0, 1).toUpperCase() + post.getNombre().substring(1);
        if (repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        LevelModel model = LevelModel.builder()
                .nombre(post.getNombre())
                .estado(1)
                .build();
        repository.save(post);
        return customResponseBuilder.buildResponse("Guardado con exito", model);
    }

    public ResponseEntity<ApiResponse> put(Integer id, LevelModel put) {
        LevelModel existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
        String capitalizado = put.getNombre().substring(0, 1).toUpperCase() + put.getNombre().substring(1);
        if (!existing.getNombre().equalsIgnoreCase(capitalizado) &&
                repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        put.setCodn(id);
        LevelModel modify = repository.save(put);
        return customResponseBuilder.buildResponse("Modificado correctamente", modify);
    }

    @Transactional
    public ResponseEntity<ApiResponse> changeState(Integer id, Integer state) {
        repository.changeState(id, state);
        return customResponseBuilder.buildResponse("Modificado correctamente", state);
    }
}
