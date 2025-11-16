package com.proyecto.backend_2.features.dicta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictaRepository extends JpaRepository<DictaModel, Integer> {

}
