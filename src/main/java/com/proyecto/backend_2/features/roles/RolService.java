package com.proyecto.backend_2.features.roles;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.responses.RolDto;
import com.proyecto.backend_2.dtos.responses.RolesByUsersDto;
import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.users.services.UsuariosMenuService;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {
    private final RolRepository repository;
    private final UsuariosMenuService usuariosMenuService;
    private final CustomResponseBuilder customResponseBuilder;

    public List<RolModel> get() {
        return repository.findAll();
    }

    public List<RolModel> getByState(Integer estado) {
        if (estado == 2) {
            return repository.findAll();
        }
        return repository.getByState(estado);
    }

    public ResponseEntity<ApiResponse> post(RolModel post) {
        String capitalizado = post.getNombre().substring(0, 1).toUpperCase() + post.getNombre().substring(1);
        if (repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        RolModel model = RolModel.builder()
                .nombre(post.getNombre())
                .estado(1)
                .build();
        repository.save(model);
        return customResponseBuilder.buildResponse("Guardado con exito", model);
    }

    public ResponseEntity<ApiResponse> put(Integer id, String login, RolModel put) {
        RolModel existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
        String capitalizado = put.getNombre().substring(0, 1).toUpperCase() + put.getNombre().substring(1);
        if (!existing.getNombre().equalsIgnoreCase(capitalizado) &&
                repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        put.setCodr(id);
        RolModel modify = repository.save(put);
        List<RolDto> roles = usuariosMenuService.obtenerRolesMenusProcesos(login);
        return customResponseBuilder.buildResponse("Modificado correctamente", modify, roles);
    }

    @Transactional
    public ResponseEntity<ApiResponse> changeState(Integer id, Integer state) {
        repository.changeState(id, state);
        return customResponseBuilder.buildResponse("Modificado correctamente", state);
    }

    // obtener roles segun el usuario
    public List<RolesByUsersDto> filterRoles(Integer state, String login) {
        if (state == 2) {
            return repository.getAsignedRoles(login);
        }
        if (state == 3) {
            return repository.getUnsignedRoles(login);
        }
        return repository.getRolesByUser(login);
    }

    public List<RolesByUsersDto> filterRolesByUsers(String login) {
        return repository.getRolesByUser(login);
    }
}
