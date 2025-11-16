package com.proyecto.backend_2.features.levels;

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
@RequestMapping("/api/levels")
@RequiredArgsConstructor
public class LevelController {
    private final LevelService service;

    @GetMapping
    public List<LevelModel> getAllLevels() {
        return service.get();
    }

    @GetMapping("/filter/{state}")
    public List<LevelModel> getLevelsByState(@PathVariable Integer state) {
        return service.getByState(state);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveLevel(@RequestBody LevelModel level) {
        return service.post(level);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateLevel(@PathVariable Integer id, @RequestBody LevelModel level) {
        return service.put(id, level);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable Integer id, @PathVariable Integer state) {
        return service.changeState(id, state);
    }
}
