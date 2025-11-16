package com.proyecto.backend_2.features.mapa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.backend_2.ids.MapaId;

public interface MapaRepository extends JpaRepository<MapaModel, MapaId> {
    @Query(value = "update mapa set estado = :state where codmat = :codmat and codpar = :codpar;", nativeQuery = true)
    public void changeState(@Param("codmat") String codmat, @Param("codpar") Integer codpar,
            @Param("state") Integer state);
}
