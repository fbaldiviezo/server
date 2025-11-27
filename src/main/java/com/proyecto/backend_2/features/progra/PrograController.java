package com.proyecto.backend_2.features.progra;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.requests.PersonAsignedRequest;
import com.proyecto.backend_2.dtos.requests.UpdateAsignedPerson;
import com.proyecto.backend_2.dtos.responses.MapaDataDto;
import com.proyecto.backend_2.ids.PrograId;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/asign/progra")
@RequiredArgsConstructor
public class PrograController {
    private final PrograService service;

    @GetMapping
    public List<MapaDataDto> getList() {
        return service.getList();
    }

    @GetMapping("/filter/{nivel}")
    public List<MapaDataDto> getFilteredList(@PathVariable String nivel) {
        return service.getFilteredList(nivel);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveProgra(@RequestBody PersonAsignedRequest progra) {
        return service.save(progra);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateProgra(@RequestBody UpdateAsignedPerson progra) {
        return service.updateFull(progra);
    }

    @DeleteMapping("/{codp}/{codmat}/{codpar}/{gestion}")
    public ResponseEntity<ApiResponse> deleteProgra(@PathVariable Integer codp, @PathVariable String codmat,
            @PathVariable Integer codpar,
            @PathVariable Integer gestion) {
        PrograId id = new PrograId(codp, codmat, codpar, gestion);
        return service.delete(id);
    }
}
