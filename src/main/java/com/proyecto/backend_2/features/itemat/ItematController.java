package com.proyecto.backend_2.features.itemat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.requests.ItematRequest;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/asign/itemat")
@RequiredArgsConstructor
public class ItematController {
    private final ItematService service;

    @PostMapping
    public ResponseEntity<ApiResponse> saveItemat(@RequestBody ItematRequest itemat) {
        return service.save(itemat);
    }

    @PutMapping("/{codmat}/{codi}/{state}/{login}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable String codmat, @PathVariable Integer codi,
            @PathVariable Integer state, @PathVariable String login) {
        return service.changeState(codmat, codi, state, login);
    }
}
