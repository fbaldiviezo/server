package com.proyecto.backend_2.features.mapa;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.requests.MapaRequest;
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
        private final CustomResponseBuilder customResponseBuilder;

        @Transactional
        public ResponseEntity<ApiResponse> save(MapaRequest mapa) {
                ParallelModel paralelo = parallelRepository.findById(mapa.codpar())
                                .orElseThrow(() -> new EntityNotFoundException("No existe el paralelo"));
                SubjectModel materia = subjectRepository.findById(mapa.codmat())
                                .orElseThrow(() -> new EntityNotFoundException("No existe la materia"));
                Integer currentYear = LocalDate.now().getYear();
                MapaRequest newMapa = new MapaRequest(
                                mapa.codmat(),
                                mapa.codpar(),
                                currentYear);

                MapaId id = new MapaId(newMapa.codmat(), newMapa.codpar(), newMapa.gestion());
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
        public ResponseEntity<ApiResponse> changeState(String codmat, Integer codpar, Integer state) {
                repository.changeState(codmat, codpar, state);
                return customResponseBuilder.buildResponse("Modificado correctamente", state);
        }
}
