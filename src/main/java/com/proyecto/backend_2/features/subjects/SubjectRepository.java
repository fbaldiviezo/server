package com.proyecto.backend_2.features.subjects;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubjectRepository extends JpaRepository<SubjectModel, String> {
    @Modifying
    @Query(value = "update materias set estado = :state where codmat = :id;", nativeQuery = true)
    void changeState(@Param("id") String id, @Param("state") Integer state);

    @Query(value = "select * from materias where estado = :state;", nativeQuery = true)
    List<SubjectModel> getByState(@Param("state") Integer state);

    @Query(value = "SELECT EXISTS (\n" + //
            "    SELECT 1\n" + //
            "    FROM materias\n" + //
            "    WHERE nombre = :name\n" + //
            ") AS nombre_existe;", nativeQuery = true)
    public Boolean findName(@Param("name") String name);
}
