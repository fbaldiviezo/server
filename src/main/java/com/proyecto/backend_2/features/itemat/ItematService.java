package com.proyecto.backend_2.features.itemat;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.requests.ItematRequest;
import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.items.ItemModel;
import com.proyecto.backend_2.features.items.ItemRepository;
import com.proyecto.backend_2.features.subjects.SubjectModel;
import com.proyecto.backend_2.features.subjects.SubjectRepository;
import com.proyecto.backend_2.ids.ItematId;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItematService {
    private final ItematRepository repository;
    private final ItemRepository itemRepository;
    private final SubjectRepository subjectRepository;
    private final CustomResponseBuilder customResponseBuilder;

    @Transactional
    public ResponseEntity<ApiResponse> save(ItematRequest itemat) {
        SubjectModel materia = subjectRepository.findById(itemat.codmat())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
        ItemModel item = itemRepository.findById(itemat.codi())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));

        // Validar si el item ya existe para esta materia (nombre duplicado)
        if (repository.existsItematBySubjectAndItem(itemat.codmat(), itemat.codi()) > 0) {
            throw new ResourceAlreadyExistsException("El item ya existe para esta materia");
        }

        // Validar que la suma de ponderaciones más la que entra no supere 100
        Integer currentSum = repository.calcPonderacion(itemat.codmat());
        if (currentSum != null && (currentSum + itemat.ponderacion()) > 100) {
            throw new ResourceAlreadyExistsException("La suma de ponderacion no debe ser mayor a 100");
        }

        Integer gestion = LocalDate.now().getYear();
        ItematId id = new ItematId(itemat.codmat(), itemat.codi(), gestion);
        ItematModel model = ItematModel.builder()
                .id(id)
                .estado(1)
                .ponderacion(itemat.ponderacion())
                .iteMateria(materia)
                .iteItem(item)
                .build();
        repository.save(model);
        return customResponseBuilder.buildResponse("Guardado con exito", model);
    }

    @Transactional
    public ResponseEntity<ApiResponse> changeState(String codmat, Integer codi, Integer state) {
        repository.changeState(codmat, codi, state);
        return customResponseBuilder.buildResponse("Modificado correctamente", state);
    }
}
