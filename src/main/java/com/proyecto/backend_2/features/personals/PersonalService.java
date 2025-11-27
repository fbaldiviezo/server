package com.proyecto.backend_2.features.personals;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.requests.PersonalRequest;
import com.proyecto.backend_2.dtos.requests.UpdatePersonalRequest;
import com.proyecto.backend_2.dtos.responses.PersonalLoginDto;
import com.proyecto.backend_2.dtos.responses.PersonalInfoUserDto;
import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.features.data.DataModel;
import com.proyecto.backend_2.features.data.repositories.DataRepository;
import com.proyecto.backend_2.features.data.repositories.DataRepository2;
import com.proyecto.backend_2.ids.DataId;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonalService {
    private final PersonalRepository repository;
    private final DataRepository dataRepository;
    private final DataRepository2 dataRepository2;
    private final CustomResponseBuilder customResponseBuilder;

    public List<PersonalModel> get() {
        return repository.findAll();
    }

    public List<PersonalModel> getPersonalByFilter(String tipo, Integer estado) {
        boolean tipoTodos = (tipo == null || tipo.equalsIgnoreCase("T"));
        boolean estadoTodos = (estado == null || estado == 2);

        if (tipoTodos && estadoTodos) {
            return repository.findAll();
        }
        if (!tipoTodos && estadoTodos) {
            return repository.getByType(tipo);
        }
        if (tipoTodos && !estadoTodos) {
            return repository.getByState(estado);
        }
        return repository.getByFilter(tipo, estado);
    }

    public ResponseEntity<ApiResponse> post(PersonalRequest post) {
        String capitalizado = post.persona().getNombre().substring(0, 1).toUpperCase()
                + post.persona().getNombre().substring(1);
        if (repository.findName(capitalizado)) {
            throw new ResourceAlreadyExistsException("El nombre del recurso ya existe");
        }
        PersonalModel p = repository.save(post.persona());
        if (post.cedula() != null) {
            DataId id = new DataId(p.getCodp(), post.cedula());
            dataRepository.save(new DataModel(id, p));
        }
        return customResponseBuilder.buildResponse("Guardado con exito", p);
    }

    @Transactional
    public ResponseEntity<ApiResponse> put(UpdatePersonalRequest put, int id) {
        if (put.newCedula() == null || put.newCedula().trim().isEmpty()) {
            throw new IllegalArgumentException("La nueva cédula ('newcedula') no puede ser nula o vacía.");
        }
        PersonalModel p = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("No se encontró el registro de Personal con codp: " + id));
        p.setNombre(put.nombre());
        p.setAp(put.ap());
        p.setAm(put.am());
        p.setEstado(put.estado());
        p.setFnac(put.fnac());
        p.setEcivil(put.ecivil());
        p.setGenero(put.genero());
        p.setDirec(put.direc());
        p.setTelf(put.telf());
        p.setTipo(put.tipo());
        p.setFoto(put.foto());
        PersonalModel modify = repository.save(p);
        dataRepository2.updateCedula(id, put.newCedula());
        return customResponseBuilder.buildResponse("Modificado correctamente", modify);
    }

    @Transactional
    public ResponseEntity<ApiResponse> changeState(Integer id, Integer state) {
        repository.changeState(id, state);
        return customResponseBuilder.buildResponse("Modificado correctamente", state);
    }

    public List<PersonalInfoUserDto> getPersonalInfoUser() {
        return repository.getPersonalInfo();
    }

    public List<PersonalLoginDto> getDocentes() {
        return repository.getDocenteAndLogin();
    }

    public List<PersonalLoginDto> getEstudiantes() {
        return repository.getEstudianteAndLogin();
    }
}
