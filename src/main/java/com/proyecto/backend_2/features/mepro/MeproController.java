package com.proyecto.backend_2.features.mepro;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.ids.MeproId;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/asign/mepro")
@RequiredArgsConstructor
public class MeproController {
    private final MeproService service;

    @PostMapping
    public ResponseEntity<ApiResponse> saveData(@RequestBody MeproId mepro) {
        return service.save(mepro);
    }

    @DeleteMapping("/{codm}/{codp}")
    public ResponseEntity<ApiResponse> deleteData(@PathVariable Integer codm, @PathVariable Integer codp) {
        return service.delete(codm, codp);
    }
}
