package com.proyecto.backend_2.features.dmodalities;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.requests.DmodalityRequest;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dmodality")
@RequiredArgsConstructor
public class DmodalityController {
    private final DmodalityService service;

    @GetMapping
    public List<DmodalityModel> getAllDmodalities() {
        return service.get();
    }

    @GetMapping("/filter/{state}")
    public List<DmodalityModel> getDmodalitiesByState(@PathVariable Integer state) {
        return service.getByState(state);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveDmodality(@RequestBody DmodalityRequest dmodality) {
        return service.post(dmodality);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDmodality(@PathVariable String id,
            @RequestBody DmodalityRequest dmodality) {
        return service.put(id, dmodality);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable String id, @PathVariable Integer state) {
        return service.changeState(id, state);
    }
}
