package com.proyecto.backend_2.features.mapa;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.requests.MapaRequest;
import com.proyecto.backend_2.dtos.responses.LevelSubjectParallelDto;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/asign/mapa")
@RequiredArgsConstructor
public class MapaController {
    private final MapaService service;

    @PostMapping
    public ResponseEntity<ApiResponse> saveMapa(@RequestBody MapaRequest mapa) {
        return service.save(mapa);
    }

    @GetMapping("/list")
    public List<LevelSubjectParallelDto> getList() {
        return service.getList();
    }

    @PutMapping("/{codmat}/{codpar}/{estado}/{login}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable String codmat, @PathVariable Integer codpar,
            @PathVariable Integer estado, @PathVariable String login) {
        return service.changeState(codmat, codpar, estado, login);
    }
}
