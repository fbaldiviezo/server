package com.proyecto.backend_2.features.modalities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalityRepository extends JpaRepository<ModalityModel, Integer> {
    @Modifying
    @Query(value = "update modalidad set estado = :state where codm = :id;", nativeQuery = true)
    void changeState(@Param("id") Integer id, @Param("state") Integer state);

    @Query(value = "select * from modalidad where estado = :state;", nativeQuery = true)
    List<ModalityModel> getByState(@Param("state") Integer state);

    @Query(value = "SELECT EXISTS (\n" + //
            "    SELECT 1\n" + //
            "    FROM modalidad\n" + //
            "    WHERE nombre = :name\n" + //
            ") AS nombre_existe;", nativeQuery = true)
    public Boolean findName(@Param("name") String name);
}
