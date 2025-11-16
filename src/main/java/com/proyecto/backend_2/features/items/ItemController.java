package com.proyecto.backend_2.features.items;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.responses.ItemsBySubjectDto;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService service;

    @GetMapping
    public List<ItemModel> getAllItems() {
        return service.getItems();
    }

    @GetMapping("/filter/subject/{codmat}")
    public List<ItemsBySubjectDto> getItemsBySubject(@PathVariable String codmat) {
        return service.getItemsBySubject(codmat);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveItem(@RequestBody ItemModel item) {
        return service.post(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateItem(@PathVariable Integer id, @RequestBody ItemModel item) {
        return service.put(id, item);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable Integer id, @PathVariable Integer state) {
        return service.changeState(id, state);
    }
}
