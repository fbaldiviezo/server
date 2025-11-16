package com.proyecto.backend_2.features.subjects;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.features.levels.LevelRepository;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;
import com.proyecto.backend_2.dtos.requests.SubjectRequest;
import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.levels.LevelModel;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository repository;
    private final LevelRepository levelRepository;
    private final CustomResponseBuilder customResponseBuilder;

    public List<SubjectModel> get() {
        return repository.findAll();
    }

    public List<SubjectModel> getByState(Integer state) {
        if (state == 2) {
            return repository.findAll();
        }
        return repository.getByState(state);
    }

    public ResponseEntity<ApiResponse> post(SubjectRequest post) {
        String capitalizado = post.nombre().substring(0, 1).toUpperCase() + post.nombre().substring(1);
        if (repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        if (repository.existsById(post.codmat())) {
            throw new ResourceAlreadyExistsException("El recurso ya existe");
        }
        LevelModel nivel = levelRepository.findById(post.codn())
                .orElseThrow(() -> new EntityNotFoundException("Nivel no encontrado"));
        SubjectModel model = SubjectModel.builder()
                .codmat(post.codmat())
                .nombre(post.nombre())
                .estado(1)
                .nivel(nivel)
                .build();
        repository.save(model);
        return customResponseBuilder.buildResponse("Guardado con exito", model);
    }

    public ResponseEntity<ApiResponse> put(String id, SubjectRequest put) {
        SubjectModel existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
        String capitalizado = put.nombre().substring(0, 1).toUpperCase() + put.nombre().substring(1);
        if (!existing.getNombre().equalsIgnoreCase(capitalizado) &&
                repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        LevelModel nivel = levelRepository.findById(put.codn())
                .orElseThrow(() -> new EntityNotFoundException("Nivel no encontrado"));
        existing.setNombre(capitalizado);
        existing.setNivel(nivel);
        repository.save(existing);
        return customResponseBuilder.buildResponse("Modificado correctamente", existing);
    }

    @Transactional
    public ResponseEntity<ApiResponse> changeState(String id, Integer state) {
        repository.changeState(id, state);
        return customResponseBuilder.buildResponse("Modificado con exito", state);
    }
}
