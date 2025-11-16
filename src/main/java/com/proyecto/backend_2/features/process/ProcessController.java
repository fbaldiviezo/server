package com.proyecto.backend_2.features.process;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.dtos.responses.ProcessByMenuDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/process")
@RequiredArgsConstructor
public class ProcessController {
    private final ProcessService service;

    @GetMapping
    public List<ProcessModel> getAllProcesses() {
        return service.get();
    }

    @GetMapping("/filter/{state}/{codm}")
    public List<ProcessByMenuDto> getFilteredProcess(@PathVariable Integer state, @PathVariable Integer codm) {
        return service.filterProcess(state, codm);
    }

    @GetMapping("/filter/menu/{codm}")
    public List<ProcessByMenuDto> getProcessByMenu(@PathVariable Integer codm) {
        return service.filterProcessBySelectedMenu(codm);
    }

}
