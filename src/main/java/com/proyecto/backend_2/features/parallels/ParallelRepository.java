package com.proyecto.backend_2.features.parallels;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.backend_2.dtos.responses.ParallelsBySubjectDto;

public interface ParallelRepository extends JpaRepository<ParallelModel, Integer> {
        @Modifying
        @Query(value = "update paralelos set estado = :state where codpar = :id;", nativeQuery = true)
        void changeState(@Param("id") Integer id, @Param("state") Integer state);

        @Query(value = "select * from paralelos where estado = :state;", nativeQuery = true)
        List<ParallelModel> getByState(@Param("state") Integer state);

        // extrae paralelos segun la materia
        @Query(value = "SELECT p.codpar, p.nombre, m.estado, m.gestion\n" + //
                        "FROM\n" + //
                        "    paralelos p                    \n" + //
                        "INNER JOIN\n" + //
                        "    mapa m ON p.codpar = m.codpar  \n" + //
                        "WHERE\n" + //
                        "    m.codmat = :codmat;", nativeQuery = true)

        public List<ParallelsBySubjectDto> getParallelsBySubject(@Param("codmat") String codmat);

        @Query(value = "SELECT EXISTS (\n" + //
                        "    SELECT 1\n" + //
                        "    FROM paralelos\n" + //
                        "    WHERE nombre = :name\n" + //
                        ") AS nombre_existe;", nativeQuery = true)
        public Boolean findName(@Param("name") String name);
}
