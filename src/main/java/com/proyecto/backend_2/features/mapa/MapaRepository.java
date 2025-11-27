package com.proyecto.backend_2.features.mapa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.backend_2.dtos.responses.LevelSubjectParallelDto;
import com.proyecto.backend_2.ids.MapaId;

public interface MapaRepository extends JpaRepository<MapaModel, MapaId> {
    @Modifying
    @Query(value = "update mapa set estado = :state where codmat = :codmat and codpar = :codpar and gestion = :gestion;", nativeQuery = true)
    public void changeState(@Param("codmat") String codmat, @Param("codpar") Integer codpar,
            @Param("gestion") Integer gestion,
            @Param("state") Integer state);

    @Query(value = "SELECT COUNT(*) FROM mapa WHERE codmat = :codmat AND codpar = :codpar", nativeQuery = true)
    public Integer existsMapaBySubjectAndParallel(@Param("codmat") String codmat, @Param("codpar") Integer codpar);

    @Query(value = "SELECT COUNT(*) FROM mapa WHERE codmat = :codmat AND codpar = :codpar AND estado = 1", nativeQuery = true)
    public Integer countActiveBySubjectAndParallel(@Param("codmat") String codmat, @Param("codpar") Integer codpar);

    @Query(value = "SELECT COUNT(*) FROM mapa WHERE codmat = :codmat AND codpar = :codpar AND estado = 0", nativeQuery = true)
    public Integer countIncativeBySubjectAndParallel(@Param("codmat") String codmat, @Param("codpar") Integer codpar);

    @Modifying
    @Query(value = "UPDATE mapa SET estado = 1 WHERE codmat = :codmat AND codpar = :codpar AND estado = 0", nativeQuery = true)
    public void reactivateMapa(@Param("codmat") String codmat, @Param("codpar") Integer codpar);

    @Query(value = "SELECT * FROM mapa WHERE codmat = :codmat AND codpar = :codpar LIMIT 1", nativeQuery = true)
    public MapaModel findAnySubjectAndParallel(@Param("codmat") String codmat, @Param("codpar") Integer codpar);

    @Query(value = "SELECT n.nombre || ' - ' || m.nombre || ' - ' || p.nombre as lista, m.codmat, p.codpar, map.gestion, map.estado\n"
            + //
            "FROM mapa map\n" + //
            "JOIN \n" + //
            "    materias m ON map.codmat = m.codmat\n" + //
            "JOIN \n" + //
            "    niveles n ON m.codn = n.codn\n" + //
            "JOIN \n" + //
            "    paralelos p ON map.codpar = p.codpar\n" + //
            "WHERE \n" + //
            "    m.estado = 1 AND n.estado = 1 AND  p.estado = 1;", nativeQuery = true)
    public List<LevelSubjectParallelDto> getLevelSubjectParallel();
}
