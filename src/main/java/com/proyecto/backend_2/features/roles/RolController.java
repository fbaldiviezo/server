package com.proyecto.backend_2.features.roles;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.responses.RolesByUsersDto;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {
    private final RolService service;

    @GetMapping
    public List<RolModel> getAllRoles() {
        return service.get();
    }

    @GetMapping("/filter/{state}")
    public List<RolModel> getRolesByState(@PathVariable Integer state) {
        return service.getByState(state);
    }

    // extrae los roles segun el usuario seleccionado
    @GetMapping("/filter/{state}/{login}")
    public List<RolesByUsersDto> getFilteredRoles(@PathVariable Integer state, @PathVariable String login) {
        return service.filterRoles(state, login);
    }

    @GetMapping("/filter/user/{login}")
    public List<RolesByUsersDto> getRolesByUser(@PathVariable String login) {
        return service.filterRolesByUsers(login);
    }
    // extrae los roles segun el usuario seleccionado

    @PostMapping
    public ResponseEntity<ApiResponse> saveRole(@RequestBody RolModel role) {
        return service.post(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRole(@PathVariable Integer id, @RequestBody RolModel rol) {
        return service.put(id, rol);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable Integer id, @PathVariable Integer state) {
        return service.changeState(id, state);
    }
}
