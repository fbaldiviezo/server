package com.proyecto.backend_2.features.personals;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.requests.PersonalRequest;
import com.proyecto.backend_2.dtos.requests.UpdatePersonalRequest;
import com.proyecto.backend_2.dtos.responses.PersonalInfoUserDto;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/personals")
@RequiredArgsConstructor
public class PersonalController {
    private final PersonalService service;

    @GetMapping
    public List<PersonalModel> getAllPersonal() {
        return service.get();
    }

    @GetMapping("/filter")
    public List<PersonalModel> getPersonalsByFilter(@RequestParam(required = false) String tipo,
            @RequestParam(required = false) Integer estado) {
        return service.getPersonalByFilter(tipo, estado);
    }

    @GetMapping("/info")
    public List<PersonalInfoUserDto> getInfoPersonal() {
        return service.getPersonalInfoUser();
    }

    @PostMapping
    public ResponseEntity<ApiResponse> savePersonal(@RequestBody PersonalRequest request) {
        return service.post(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePersonal(@RequestBody UpdatePersonalRequest request,
            @PathVariable int id) {
        return service.put(request, id);
    }

    @PutMapping("/{id}/{state}")
    public ResponseEntity<ApiResponse> changeState(@PathVariable Integer id, @PathVariable Integer state) {
        return service.changeState(id, state);
    }
}
