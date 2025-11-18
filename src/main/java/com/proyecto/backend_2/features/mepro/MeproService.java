package com.proyecto.backend_2.features.mepro;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.requests.MeproRequest;
import com.proyecto.backend_2.dtos.responses.RolDto;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.menus.MenuModel;
import com.proyecto.backend_2.features.menus.MenuRepository;
import com.proyecto.backend_2.features.process.ProcessModel;
import com.proyecto.backend_2.features.process.ProcessRepository;
import com.proyecto.backend_2.features.users.services.UsuariosMenuService;
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
    private final UsuariosMenuService usuariosMenuService;

    @Transactional
    public ResponseEntity<ApiResponse> save(MeproRequest mepro) {
        MenuModel menu = menuRepository.findById(mepro.codm())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el elemento"));
        ProcessModel process = processRepository.findById(mepro.codp())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el elemento"));
        MeproId id = new MeproId(menu.getCodm(), process.getCodp());
        MeproModel saved = repository.save(new MeproModel(id, menu, process));
        List<RolDto> roles = usuariosMenuService.obtenerRolesMenusProcesos(mepro.login());
        return customResponseBuilder.buildResponse("Agregado correctamente", saved, roles);
    }

    @Transactional
    public ResponseEntity<ApiResponse> delete(Integer codm, Integer codp, String login) {
        MeproId id = new MeproId(codm, codp);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            List<RolDto> roles = usuariosMenuService.obtenerRolesMenusProcesos(login);
            return customResponseBuilder.buildResponse("Eliminado correctamente", id, roles);
        } else {
            throw new ResourceNotFoundException("No existe el elemento");
        }
    }
}
