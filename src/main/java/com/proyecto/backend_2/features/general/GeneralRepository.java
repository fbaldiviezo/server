package com.proyecto.backend_2.features.general;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.backend_2.ids.GeneralId;

public interface GeneralRepository extends JpaRepository<GeneralModel, GeneralId> {
    @Query(value = "select gestion from general where login = :login", nativeQuery = true)
    Integer getGestion(@Param("login") String login);
}
