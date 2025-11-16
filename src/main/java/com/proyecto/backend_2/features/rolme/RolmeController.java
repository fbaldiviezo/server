package com.proyecto.backend_2.features.rolme;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.ids.RolmeId;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/asign/rolme")
@RequiredArgsConstructor
public class RolmeController {
    private final RolmeService service;

    @PostMapping
    public ResponseEntity<ApiResponse> saveData(@RequestBody RolmeId rolme) {
        return service.save(rolme);
    }

    @DeleteMapping("/{codr}/{codm}")
    public ResponseEntity<ApiResponse> deleteData(@PathVariable Integer codr, @PathVariable Integer codm) {
        return service.delete(codr, codm);
    }
}
