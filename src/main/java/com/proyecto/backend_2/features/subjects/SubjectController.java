package com.proyecto.backend_2.features.subjects;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.requests.SubjectRequest;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService service;

    @GetMapping
    public List<SubjectModel> getAllSubjects() {
        return service.get();
    }

    @GetMapping("/filter/{state}")
    public List<SubjectModel> getSubjectsByState(@PathVariable Integer state) {
        return service.getByState(state);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveSubject(@RequestBody SubjectRequest subject) {
        return service.post(subject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSubject(@PathVariable String id, @RequestBody SubjectRequest subject) {
        return service.put(id, subject);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable String id, @PathVariable Integer state) {
        return service.changeState(id, state);
    }
}
