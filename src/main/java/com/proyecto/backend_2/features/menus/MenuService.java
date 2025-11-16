package com.proyecto.backend_2.features.menus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.responses.MenuDto;
import com.proyecto.backend_2.dtos.responses.MenusByRoleDto;
import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository repository;
    private final CustomResponseBuilder customResponseBuilder;

    public List<MenuModel> get() {
        return repository.findAll();
    }

    public List<MenuModel> getByState(Integer estado) {
        if (estado == 2) {
            return repository.findAll();
        }
        return repository.getByState(estado);
    }

    public ResponseEntity<ApiResponse> post(MenuModel post) {
        String capitalizado = post.getNombre().substring(0, 1).toUpperCase() + post.getNombre().substring(1);
        if (repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        MenuModel model = MenuModel.builder()
                .nombre(post.getNombre())
                .estado(1)
                .build();
        repository.save(model);
        return customResponseBuilder.buildResponse("Guardado con exito", model);
    }

    public ResponseEntity<ApiResponse> put(Integer id, MenuModel put) {
        MenuModel existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
        String capitalizado = put.getNombre().substring(0, 1).toUpperCase() + put.getNombre().substring(1);
        if (!existing.getNombre().equalsIgnoreCase(capitalizado) &&
                repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre usado ya existe");
        }
        put.setCodm(id);
        MenuModel modify = repository.save(put);
        return customResponseBuilder.buildResponse("Modificado correctamente", modify);
    }

    @Transactional
    public ResponseEntity<ApiResponse> changeState(Integer id, Integer state) {
        repository.changeState(id, state);
        return customResponseBuilder.buildResponse("Modificado correctamente", state);
    }

    public List<MenuDto> getMenusRol(Integer codr) {
        List<MenuModel> menus = repository.getMenusRol(codr);
        List<MenuDto> menuRol = new ArrayList<>();
        for (MenuModel x : menus) {
            menuRol.add(new MenuDto(x.getCodm(), x.getNombre(), repository.getProcesosMenu(x.getCodm())));
        }
        return menuRol;
    }

    // obtener menus segun los roles
    public List<MenusByRoleDto> filterMenus(Integer state, Integer codr) {
        if (state == 2) {
            return repository.getAsignedMenus(codr);
        }
        if (state == 3) {
            return repository.getUnsignedMenus(codr);
        }
        return repository.getMenusByRoles(codr);
    }

    public List<MenusByRoleDto> filterMenusByRoles(Integer codr) {
        return repository.getMenusByRoles(codr);
    }
}
