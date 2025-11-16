package com.proyecto.backend_2.features.modalities;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/modality")
@RequiredArgsConstructor
public class ModalityController {
    private final ModalityService service;

    @GetMapping
    public List<ModalityModel> getAllModalities() {
        return service.get();
    }

    @GetMapping("/filter/{state}")
    public List<ModalityModel> getModalitiesByState(@PathVariable Integer state) {
        return service.getByState(state);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveModality(@RequestBody ModalityModel modality) {
        return service.post(modality);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateModality(@PathVariable Integer id, @RequestBody ModalityModel modality) {
        return service.put(id, modality);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable Integer id, @PathVariable Integer state) {
        return service.changeState(id, state);
    }
}
