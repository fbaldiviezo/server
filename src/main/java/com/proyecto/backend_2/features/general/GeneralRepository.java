package com.proyecto.backend_2.features.general;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.backend_2.ids.GeneralId;

public interface GeneralRepository extends JpaRepository<GeneralModel, GeneralId> {

}
