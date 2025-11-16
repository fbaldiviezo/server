package com.proyecto.backend_2.features.levels;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LevelRepository extends JpaRepository<LevelModel, Integer> {
    @Modifying
    @Query(value = "update niveles set estado = :state where codn = :id;", nativeQuery = true)
    void changeState(@Param("id") Integer id, @Param("state") Integer state);

    @Query(value = "select * from niveles where estado = :state;", nativeQuery = true)
    List<LevelModel> getByState(@Param("state") Integer state);

    @Query(value = "SELECT EXISTS (\n" + //
            "    SELECT 1\n" + //
            "    FROM niveles\n" + //
            "    WHERE nombre = :name\n" + //
            ") AS nombre_existe;", nativeQuery = true)
    public Boolean findName(@Param("name") String name);
}
