package com.proyecto.backend_2.features.rolme;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.menus.MenuModel;
import com.proyecto.backend_2.features.menus.MenuRepository;
import com.proyecto.backend_2.features.roles.RolModel;
import com.proyecto.backend_2.features.roles.RolRepository;
import com.proyecto.backend_2.ids.RolmeId;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolmeService {
    private final RolmeRepository repository;
    private final MenuRepository menuRepository;
    private final RolRepository rolRepository;
    private final CustomResponseBuilder customResponseBuilder;

    @Transactional
    public ResponseEntity<ApiResponse> save(RolmeId rolme) {
        RolModel rol = rolRepository.findById(rolme.getCodr())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el elemento"));
        MenuModel menu = menuRepository.findById(rolme.getCodm())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el elemento"));
        RolmeId id = new RolmeId(rol.getCodr(), menu.getCodm());
        RolmeModel saved = repository.save(new RolmeModel(id, rol, menu));
        return customResponseBuilder.buildResponse("Agregado correctamente", saved);
    }

    @Transactional
    public ResponseEntity<ApiResponse> delete(Integer codr, Integer codm) {
        RolmeId id = new RolmeId(codr, codm);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return customResponseBuilder.buildResponse("Eliminado correctamente", id);
        } else {
            throw new ResourceNotFoundException("No existe el elemento");
        }
    }
}
