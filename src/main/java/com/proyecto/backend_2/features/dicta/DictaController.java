package com.proyecto.backend_2.features.dicta;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.requests.DictaRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/asign/dicta")
@RequiredArgsConstructor
public class DictaController {
    private final DictaService service;

    @PostMapping
    public DictaModel saveDicta(@RequestBody DictaRequest dicta) {
        return service.save(dicta);
    }

    @DeleteMapping("/{coddic}")
    public void deleteDicta(@PathVariable Integer coddic) {
        service.delete(coddic);
    }
}
