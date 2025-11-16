package com.proyecto.backend_2.features.dmodalities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DmodalityRepository extends JpaRepository<DmodalityModel, String> {
    @Modifying
    @Query(value = "update dmodalidad set estado = :state where coddm = :id;", nativeQuery = true)
    void changeState(@Param("id") String id, @Param("state") Integer state);

    @Query(value = "select * from dmodalidad where estado = :state;", nativeQuery = true)
    List<DmodalityModel> getByState(@Param("state") Integer state);

    @Query(value = "SELECT EXISTS (\n" + //
            "    SELECT 1\n" + //
            "    FROM dmodalidad\n" + //
            "    WHERE nombre = :name\n" + //
            ") AS nombre_existe;", nativeQuery = true)
    public Boolean findName(@Param("name") String name);
}
