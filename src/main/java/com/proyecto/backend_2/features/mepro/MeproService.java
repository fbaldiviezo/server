package com.proyecto.backend_2.features.mepro;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.menus.MenuModel;
import com.proyecto.backend_2.features.menus.MenuRepository;
import com.proyecto.backend_2.features.process.ProcessModel;
import com.proyecto.backend_2.features.process.ProcessRepository;
import com.proyecto.backend_2.ids.MeproId;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeproService {
    private final MeproRepository repository;
    private final MenuRepository menuRepository;
    private final ProcessRepository processRepository;
    private final CustomResponseBuilder customResponseBuilder;

    @Transactional
    public ResponseEntity<ApiResponse> save(MeproId mepro) {
        MenuModel menu = menuRepository.findById(mepro.getCodm())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el elemento"));
        ProcessModel process = processRepository.findById(mepro.getCodp())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el elemento"));
        MeproId id = new MeproId(menu.getCodm(), process.getCodp());
        MeproModel saved = repository.save(new MeproModel(id, menu, process));
        return customResponseBuilder.buildResponse("Agregado correctamente", saved);
    }

    @Transactional
    public ResponseEntity<ApiResponse> delete(Integer codm, Integer codp) {
        MeproId id = new MeproId(codm, codp);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return customResponseBuilder.buildResponse("Eliminado correctamente", id);
        } else {
            throw new ResourceNotFoundException("No existe el elemento");
        }
    }
}
