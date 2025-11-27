package com.proyecto.backend_2.features.mapa;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.requests.MapaRequest;
import com.proyecto.backend_2.dtos.responses.LevelSubjectParallelDto;
import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.features.general.GeneralRepository;
import com.proyecto.backend_2.features.parallels.ParallelModel;
import com.proyecto.backend_2.features.parallels.ParallelRepository;
import com.proyecto.backend_2.features.subjects.SubjectModel;
import com.proyecto.backend_2.features.subjects.SubjectRepository;
import com.proyecto.backend_2.ids.MapaId;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MapaService {
        private final MapaRepository repository;
        private final ParallelRepository parallelRepository;
        private final SubjectRepository subjectRepository;
        private final GeneralRepository generalRepository;
        private final CustomResponseBuilder customResponseBuilder;

        @Transactional
        public ResponseEntity<ApiResponse> save(MapaRequest mapa) {
                ParallelModel paralelo = parallelRepository.findById(mapa.codpar())
                                .orElseThrow(() -> new EntityNotFoundException("No existe el paralelo"));
                SubjectModel materia = subjectRepository.findById(mapa.codmat())
                                .orElseThrow(() -> new EntityNotFoundException("No existe la materia"));

                Integer activeCount = repository.countActiveBySubjectAndParallel(mapa.codmat(), mapa.codpar());
                if (activeCount != null && activeCount > 0) {
                        throw new ResourceAlreadyExistsException("El paralelo ya existe para esta materia");
                }

                Integer inactivaCount = repository.countIncativeBySubjectAndParallel(mapa.codmat(), mapa.codpar());
                if (inactivaCount != null && inactivaCount > 0) {
                        repository.reactivateMapa(mapa.codmat(), mapa.codpar());
                        MapaModel reactivated = repository.findAnySubjectAndParallel(mapa.codmat(), mapa.codpar());
                        return customResponseBuilder.buildResponse("Guardado con exito", reactivated);
                }

                Integer gestion = generalRepository.getGestion(mapa.login());
                MapaId id = new MapaId(mapa.codmat(), mapa.codpar(), gestion);
                MapaModel model = MapaModel.builder()
                                .id(id)
                                .estado(1)
                                .mapaMateria(materia)
                                .mapaParalelo(paralelo)
                                .build();
                repository.save(model);
                return customResponseBuilder.buildResponse("Guardado con exito", model);
        }

        @Transactional
        public ResponseEntity<ApiResponse> changeState(String codmat, Integer codpar, Integer state, String login) {
                Integer gestion = generalRepository.getGestion(login);
                repository.changeState(codmat, codpar, gestion, state);
                return customResponseBuilder.buildResponse("Modificado correctamente", state);
        }

        public List<LevelSubjectParallelDto> getList() {
                return repository.getLevelSubjectParallel();
        }
}
