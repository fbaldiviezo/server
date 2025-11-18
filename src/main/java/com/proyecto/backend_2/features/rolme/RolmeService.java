package com.proyecto.backend_2.features.rolme;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.responses.RolDto;
import com.proyecto.backend_2.dtos.requests.RolmeRequest;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.menus.MenuModel;
import com.proyecto.backend_2.features.menus.MenuRepository;
import com.proyecto.backend_2.features.roles.RolModel;
import com.proyecto.backend_2.features.roles.RolRepository;
import com.proyecto.backend_2.features.users.services.UsuariosMenuService;
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
    private final UsuariosMenuService usuariosMenuService;

    @Transactional
    public ResponseEntity<ApiResponse> save(RolmeRequest rolme) {
        RolModel rol = rolRepository.findById(rolme.codr())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el elemento"));
        MenuModel menu = menuRepository.findById(rolme.codm())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el elemento"));
        RolmeId id = new RolmeId(rol.getCodr(), menu.getCodm());
        RolmeModel saved = repository.save(new RolmeModel(id, rol, menu));
        List<RolDto> roles = usuariosMenuService.obtenerRolesMenusProcesos(rolme.login());
        return customResponseBuilder.buildResponse("Agregado correctamente", saved, roles);
    }

    @Transactional
    public ResponseEntity<ApiResponse> delete(Integer codr, Integer codm, String login) {
        RolmeId id = new RolmeId(codr, codm);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            List<RolDto> roles = usuariosMenuService.obtenerRolesMenusProcesos(login);
            return customResponseBuilder.buildResponse("Eliminado correctamente", id, roles);
        } else {
            throw new ResourceNotFoundException("No existe el elemento");
        }
    }
}
