package com.proyecto.backend_2.features.usurol;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.ids.UsurolId;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/asign/usurol")
@RequiredArgsConstructor
public class UsurolController {
    private final UsurolService service;

    @PostMapping
    public ResponseEntity<ApiResponse> saveData(@RequestBody UsurolId usurol) {
        return service.save(usurol);
    }

    @DeleteMapping("/{login}/{codr}")
    public ResponseEntity<ApiResponse> deleteData(@PathVariable String login, @PathVariable Integer codr) {
        return service.delete(login, codr);
    }
}
