package com.proyecto.backend_2.features.menus;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.responses.MenuDto;
import com.proyecto.backend_2.dtos.responses.MenusByRoleDto;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService service;

    @GetMapping
    public List<MenuModel> getAllMenus() {
        return service.get();
    }

    @GetMapping("/filter/{state}")
    public List<MenuModel> getMenusByState(@PathVariable Integer state) {
        return service.getByState(state);
    }

    @GetMapping("/{codr}")
    public List<MenuDto> getMenusRol(@PathVariable Integer codr) {
        return service.getMenusRol(codr);
    }

    // extrae los menus segun el rol de usario
    @GetMapping("/filter/{state}/{codr}")
    public List<MenusByRoleDto> getFilteredMenus(@PathVariable Integer state, @PathVariable Integer codr) {
        return service.filterMenus(state, codr);
    }

    @GetMapping("/filter/role/{codr}")
    public List<MenusByRoleDto> getMenusByRole(@PathVariable Integer codr) {
        return service.filterMenusByRoles(codr);
    }
    // extrae los menus segun el rol de usario

    @PostMapping
    public ResponseEntity<ApiResponse> saveMenu(@RequestBody MenuModel menu) {
        return service.post(menu);
    }

    @PutMapping("/{id}/usr/{login}")
    public ResponseEntity<ApiResponse> updateMenu(@PathVariable Integer id, @PathVariable String login,
            @RequestBody MenuModel menu) {
        return service.put(id, login, menu);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable Integer id, @PathVariable Integer state) {
        return service.changeState(id, state);
    }
}
