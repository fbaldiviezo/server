package com.proyecto.backend_2.features.items;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.responses.ItemsBySubjectDto;
import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository repository;
    private final CustomResponseBuilder customResponseBuilder;

    public List<ItemsBySubjectDto> getItemsBySubject(String codmat) {
        return repository.getItemsBySubject(codmat);
    }

    public List<ItemModel> getByState(Integer state) {
        return repository.getByState(state);
    }

    public List<ItemModel> getItems() {
        return repository.findAll();
    }

    public ResponseEntity<ApiResponse> post(ItemModel post) {
        String capitalizado = post.getNombre().substring(0, 1).toUpperCase() + post.getNombre().substring(1);
        if (repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        ItemModel model = ItemModel.builder()
                .nombre(post.getNombre())
                .estado(1)
                .build();
        repository.save(model);
        return customResponseBuilder.buildResponse("Guardado con exito", model);
    }

    public ResponseEntity<ApiResponse> put(Integer id, ItemModel put) {
        ItemModel existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
        String capitalizado = put.getNombre().substring(0, 1).toUpperCase() + put.getNombre().substring(1);
        if (!existing.getNombre().equalsIgnoreCase(capitalizado) &&
                repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        put.setCodi(id);
        ItemModel modify = repository.save(put);
        return customResponseBuilder.buildResponse("Modificado correctamente", modify);
    }

    @Transactional
    public ResponseEntity<ApiResponse> changeState(Integer id, Integer state) {
        repository.changeState(id, state);
        return customResponseBuilder.buildResponse("Modificado correctamente", state);
    }
}
