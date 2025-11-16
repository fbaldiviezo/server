package com.proyecto.backend_2.features.process;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.responses.ProcessByMenuDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessService {
    private final ProcessRepository repository;

    public List<ProcessModel> get() {
        return repository.findAll();
    }

    public List<ProcessByMenuDto> filterProcess(Integer state, Integer codm) {
        if (state == 2) {
            return repository.getAsignedProcess(codm);
        }
        if (state == 3) {
            return repository.getUnsignedProcess(codm);
        }
        return repository.getProcessByMenu(codm);
    }

    public List<ProcessByMenuDto> filterProcessBySelectedMenu(Integer codm) {
        return repository.getProcessByMenu(codm);
    }
}
