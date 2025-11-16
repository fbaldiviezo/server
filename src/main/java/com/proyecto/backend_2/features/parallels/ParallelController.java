package com.proyecto.backend_2.features.parallels;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.responses.ParallelsBySubjectDto;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parallels")
@RequiredArgsConstructor
public class ParallelController {
    private final ParallelService service;

    @GetMapping
    public List<ParallelModel> getAllParallels() {
        return service.get();
    }

    @GetMapping("/filter/{state}")
    public List<ParallelModel> getParallelsByState(@PathVariable Integer state) {
        return service.getByState(state);
    }

    @GetMapping("/filter/subject/{codmat}")
    public List<ParallelsBySubjectDto> getParallelsBySubject(@PathVariable String codmat) {
        return service.getParallelsBySubject(codmat);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveParallel(@RequestBody ParallelModel parallel) {
        return service.post(parallel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateParallel(@PathVariable Integer id, @RequestBody ParallelModel parallel) {
        return service.put(id, parallel);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable Integer id, @PathVariable Integer state) {
        return service.changeState(id, state);
    }
}
