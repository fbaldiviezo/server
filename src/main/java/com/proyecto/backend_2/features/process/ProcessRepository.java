package com.proyecto.backend_2.features.process;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.backend_2.dtos.responses.ProcessByMenuDto;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessModel, Integer> {
    @Query(value = "select * from procesos", nativeQuery = true)
    List<ProcessByMenuDto> findAllProcess();

    @Query(value = "SELECT p.codp, p.nombre, p.enlace, p.ayuda, p.estado, TRUE AS relacionMenu FROM procesos p INNER JOIN mepro m ON p.codp = m.codp WHERE m.codm = :codm;", nativeQuery = true)
    List<ProcessByMenuDto> getAsignedProcess(@Param("codm") Integer codm);

    @Query(value = "SELECT p.codp, p.nombre, p.enlace, p.ayuda, p.estado, FALSE AS relacionMenu FROM procesos p WHERE p.codp NOT IN ( SELECT codp FROM mepro WHERE codm = :codm );", nativeQuery = true)
    List<ProcessByMenuDto> getUnsignedProcess(@Param("codm") Integer codm);

    @Query(value = "SELECT p.codp, p.nombre, p.enlace, p.ayuda, p.estado,\r\n" + //
            "    CASE\r\n" + //
            "        WHEN m.codp IS NULL THEN FALSE\r\n" + //
            "        ELSE TRUE\r\n" + //
            "    END AS relacionMenu\r\n" + //
            "FROM\r\n" + //
            "    procesos p\r\n" + //
            "LEFT JOIN\r\n" + //
            "    mepro m ON p.codp = m.codp AND m.codm = :codm\r\n" + //
            "WHERE\r\n" + //
            "    p.codp = p.codp", nativeQuery = true)
    List<ProcessByMenuDto> getProcessByMenu(@Param("codm") Integer codm);
}
