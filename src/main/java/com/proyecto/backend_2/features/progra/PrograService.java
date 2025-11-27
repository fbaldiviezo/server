package com.proyecto.backend_2.features.progra;

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
import com.proyecto.backend_2.features.notas.NotasModel;
import com.proyecto.backend_2.features.notas.NotasRepository;
import com.proyecto.backend_2.features.personals.PersonalModel;
import com.proyecto.backend_2.features.personals.PersonalRepository;
import com.proyecto.backend_2.features.users.UserModel;
import com.proyecto.backend_2.features.users.UserRepository;
import com.proyecto.backend_2.ids.MapaId;
import com.proyecto.backend_2.ids.NotaId;
import com.proyecto.backend_2.ids.PrograId;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrograService {
        private final PrograRepository repository;
        private final PersonalRepository personalRepository;
        private final UserRepository userRepository;
        private final MapaRepository mapaRepository;
        private final GeneralRepository generalRepository;
        private final NotasRepository notasRepository;
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
        public ResponseEntity<ApiResponse> save(PersonAsignedRequest progra) {
                PersonalModel personal = personalRepository.findById(progra.codp())
                                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
                if (!userRepository.getCodp(progra.codp())) {
                        throw new ResourceNotFoundException("El usuario no tiene login");
                }
                if (!personalRepository.isEstudiante(progra.codp())) {
                        throw new ResourceNotFoundException("El usuario no es estudiante");
                }
                UserModel user = userRepository.findById(progra.login())
                                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
                MapaId mapaId = new MapaId(progra.codmat(), progra.codpar(), progra.gestion());
                MapaModel mapa = mapaRepository.findById(mapaId)
                                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
                Integer gestion = generalRepository.getGestion(progra.loginA());
                PrograId prograId = new PrograId(
                                progra.codp(),
                                progra.codmat(),
                                progra.codpar(),
                                gestion);
                if (repository.existsById(prograId)) {
                        throw new ResourceAlreadyExistsException("Ya existe este recurso");
                }
                PrograModel model = PrograModel.builder()
                                .id(prograId)
                                .usuario(user)
                                .persona(personal)
                                .mapa(mapa)
                                .build();
                repository.save(model);

                NotaId notaId = new NotaId(progra.codp(), progra.login(), progra.codmat(), progra.codpar(), gestion);
                NotasModel notasModel = NotasModel.builder()
                                .id(notaId)
                                .progra(model)
                                .nota(0)
                                .build();
                notasRepository.save(notasModel);

                return customResponseBuilder.buildResponse("Guardado con exito", model);
        }

        @Transactional
        public ResponseEntity<ApiResponse> delete(PrograId progra) {
                PrograId prograId = new PrograId(
                                progra.getCodp(),
                                progra.getCodmat(),
                                progra.getCodpar(),
                                progra.getGestion());
                PrograModel model = repository.findById(prograId)
                                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));

                String login = userRepository.getLogin(progra.getCodp());
                NotaId notaId = new NotaId(progra.getCodp(), login, progra.getCodmat(), progra.getCodpar(),
                                progra.getGestion());
                NotasModel notasModel = notasRepository.findById(notaId)
                                .orElseThrow(() -> new ResourceNotFoundException("Error interno del servidor"));
                notasRepository.delete(notasModel);

                repository.delete(model);

                return customResponseBuilder.buildResponse("Eliminado con exito", model);
        }

        @Transactional
        public ResponseEntity<ApiResponse> updateFull(UpdateAsignedPerson updateAsignedPerson) {
                PrograId oldId = new PrograId(
                                updateAsignedPerson.oldCodp(),
                                updateAsignedPerson.oldCodmat(),
                                updateAsignedPerson.oldCodpar(),
                                updateAsignedPerson.oldGestion());
                PrograModel oldModel = repository.findById(oldId)
                                .orElseThrow(() -> new ResourceNotFoundException("El recurso original no existe"));
                PersonalModel personal = personalRepository.findById(updateAsignedPerson.codp())
                                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
                if (!userRepository.getCodp(updateAsignedPerson.codp())) {
                        throw new ResourceNotFoundException("El usuario no tiene login");
                }
                if (!personalRepository.isEstudiante(updateAsignedPerson.codp())) {
                        throw new ResourceNotFoundException("El usuario no es estudiante");
                }

                // elije y elimina el antiguo nota
                String oldLogin = userRepository.getLogin(updateAsignedPerson.oldCodp());
                NotaId oldNotaId = new NotaId(updateAsignedPerson.oldCodp(), oldLogin, updateAsignedPerson.oldCodmat(),
                                updateAsignedPerson.oldCodpar(), updateAsignedPerson.oldGestion());
                NotasModel oldNotasModel = notasRepository.findById(oldNotaId)
                                .orElseThrow(() -> new ResourceNotFoundException("El nota no existe"));
                Integer nota = notasRepository.getNota(updateAsignedPerson.oldCodp(), oldLogin,
                                updateAsignedPerson.oldCodmat(),
                                updateAsignedPerson.oldCodpar(), updateAsignedPerson.oldGestion());
                notasRepository.delete(oldNotasModel);
                // elije y elimina el antiguo nota

                UserModel user = userRepository.findById(updateAsignedPerson.login())
                                .orElseThrow(() -> new ResourceNotFoundException("El login no existe"));
                MapaId mapaId = new MapaId(updateAsignedPerson.codmat(), updateAsignedPerson.codpar(),
                                updateAsignedPerson.gestion());
                MapaModel mapa = mapaRepository.findById(mapaId)
                                .orElseThrow(() -> new ResourceNotFoundException("El mapa no existe"));
                repository.delete(oldModel);
                PrograId newId = new PrograId(updateAsignedPerson.codp(),
                                updateAsignedPerson.codmat(),
                                updateAsignedPerson.codpar(),
                                updateAsignedPerson.oldGestion());
                PrograModel newModel = PrograModel.builder()
                                .id(newId)
                                .usuario(user)
                                .persona(personal)
                                .mapa(mapa)
                                .build();
                repository.save(newModel);

                // Guarda el nuevo nota
                NotaId newNotaId = new NotaId(updateAsignedPerson.codp(), updateAsignedPerson.login(),
                                updateAsignedPerson.codmat(), updateAsignedPerson.codpar(),
                                updateAsignedPerson.oldGestion());
                NotasModel newNotasModel = NotasModel.builder()
                                .id(newNotaId)
                                .progra(newModel)
                                .nota(nota)
                                .build();
                notasRepository.save(newNotasModel);
                // Guarda el nuevo nota
                return customResponseBuilder.buildResponse("Modificado con éxito", newModel);
        }
}
