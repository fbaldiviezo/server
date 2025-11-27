package com.proyecto.backend_2.features.notas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.backend_2.ids.NotaId;

public interface NotasRepository extends JpaRepository<NotasModel, NotaId> {
    @Query(value = "select nota from notas where codp = :codp and login = :login and codmat = :codmat and codpar = :codpar and gestion = :gestion", nativeQuery = true)
    public Integer getNota(@Param("codp") Integer codp, @Param("login") String login, @Param("codmat") String codmat,
            @Param("codpar") Integer codpar, @Param("gestion") Integer gestion);
}
