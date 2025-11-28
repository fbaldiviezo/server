package com.proyecto.backend_2.features.dicta;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.requests.PersonAsignedRequest;
import com.proyecto.backend_2.dtos.requests.UpdateAsignedPerson;
import com.proyecto.backend_2.dtos.responses.MapaDataDto;
import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.general.GeneralRepository;
import com.proyecto.backend_2.features.mapa.MapaModel;
import com.proyecto.backend_2.features.mapa.MapaRepository;
import com.proyecto.backend_2.features.personals.PersonalModel;
import com.proyecto.backend_2.features.personals.PersonalRepository;
import com.proyecto.backend_2.features.users.UserModel;
import com.proyecto.backend_2.features.users.UserRepository;
import com.proyecto.backend_2.ids.DictaId;
import com.proyecto.backend_2.ids.MapaId;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DictaService {
        private final DictaRepository repository;
        private final PersonalRepository personalRepository;
        private final UserRepository userRepository;
        private final MapaRepository mapaRepository;
        private final GeneralRepository generalRepository;
        private final CustomResponseBuilder customResponseBuilder;

        public List<MapaDataDto> getList() {
                return repository.getList();
        }

        public List<MapaDataDto> getFilteredList(String nombre) {
                if (nombre.equals("todos")) {
                        return repository.getList();
                }
                return repository.getFilteredList(nombre);
        }

        @Transactional
        public ResponseEntity<ApiResponse> save(PersonAsignedRequest dicta) {
                PersonalModel personal = personalRepository.findById(dicta.codp())
                                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
                if (!userRepository.getCodp(dicta.codp())) {
                        throw new ResourceNotFoundException("El usuario no tiene login");
                }
                if (!personalRepository.isDocente(dicta.codp())) {
                        throw new ResourceNotFoundException("El usuario no es docente");
                }
                UserModel user = userRepository.findById(dicta.login())
                                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
                MapaId mapaId = new MapaId(dicta.codmat(), dicta.codpar(), dicta.gestion());
                MapaModel mapa = mapaRepository.findById(mapaId)
                                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
                Integer gestion = generalRepository.getGestion(dicta.loginA());
                DictaId dictaId = new DictaId(
                                dicta.codp(),
                                dicta.codmat(),
                                dicta.codpar(),
                                gestion);
                if (repository.existsByIdCodpAndIdCodmatAndIdGestion(dicta.codp(), dicta.codmat(), gestion)) {
                        throw new ResourceAlreadyExistsException("Ya existe este recurso");
                }
                DictaModel model = DictaModel.builder()
                                .id(dictaId)
                                .usuario(user)
                                .persona(personal)
                                .mapa(mapa)
                                .build();
                repository.save(model);
                return customResponseBuilder.buildResponse("Guardado con exito", model);
        }

        @Transactional
        public ResponseEntity<ApiResponse> delete(DictaId dicta) {
                DictaId dictaId = new DictaId(
                                dicta.getCodp(),
                                dicta.getCodmat(),
                                dicta.getCodpar(),
                                dicta.getGestion());
                DictaModel model = repository.findById(dictaId)
                                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
                repository.delete(model);
                return customResponseBuilder.buildResponse("Eliminado con exito", null);
        }

        @Transactional
        public ResponseEntity<ApiResponse> updateFull(UpdateAsignedPerson req) {
                DictaId oldId = new DictaId(
                                req.oldCodp(),
                                req.oldCodmat(),
                                req.oldCodpar(),
                                req.oldGestion());
                // 1. Buscar el antiguo
                DictaModel oldModel = repository.findById(oldId)
                                .orElseThrow(() -> new ResourceNotFoundException("El recusrso original no existe"));
                // 2. Validar nuevo usuario
                PersonalModel personal = personalRepository.findById(req.codp())
                                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
                if (!userRepository.getCodp(req.codp())) {
                        throw new ResourceNotFoundException("El usuario no tiene login");
                }
                if (!personalRepository.isDocente(req.codp())) {
                        throw new ResourceNotFoundException("El usuario no es docente");
                }
                UserModel user = userRepository.findById(req.login())
                                .orElseThrow(() -> new ResourceNotFoundException("El login no existe"));
                // 3. Validar nuevo mapa
                MapaId mapaId = new MapaId(req.codmat(), req.codpar(), req.gestion());
                MapaModel mapa = mapaRepository.findById(mapaId)
                                .orElseThrow(() -> new ResourceNotFoundException("El mapa no existe"));
                // 4. Eliminar registro antiguo

                // 5. Crear nuevo registro
                DictaId newId = new DictaId(
                                req.codp(),
                                req.codmat(),
                                req.codpar(),
                                req.oldGestion());
                if (repository.existsById(newId)) {
                        throw new ResourceAlreadyExistsException("Ya existe este recurso");
                }
                repository.delete(oldModel);
                DictaModel newModel = DictaModel.builder()
                                .id(newId)
                                .usuario(user)
                                .persona(personal)
                                .mapa(mapa)
                                .build();
                repository.save(newModel);
                return customResponseBuilder.buildResponse("Modificado con éxito", newModel);
        }

}
