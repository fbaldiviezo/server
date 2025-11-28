package com.proyecto.backend_2.features.progra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.backend_2.dtos.responses.MapaDataDto;
import com.proyecto.backend_2.ids.PrograId;

@Repository
public interface PrograRepository extends JpaRepository<PrograModel, PrograId> {
        @Query(value = "select n.nombre as nivel, m.nombre as materia,  p.nombre || ' ' || p.ap || ' ' || p.am as nombre, par.nombre as paralelo,\n"
                        + //
                        "p.codp, u.login, m.codmat, par.codpar, d.gestion\n" + //
                        "from progra d\n" + //
                        "join materias m on d.codmat = m.codmat\n" + //
                        "join niveles n on m.codn = n.codn\n" + //
                        "join personal p on d.codp = p.codp\n" + //
                        "join usuarios u on p.codp = u.codp\n" + //
                        "join paralelos par on d.codpar = par.codpar", nativeQuery = true)
        public List<MapaDataDto> getList();

        @Query(value = "select n.nombre as nivel, m.nombre as materia,  p.nombre || ' ' || p.ap || ' ' || p.am as nombre, par.nombre as paralelo,\n"
                        + //
                        "p.codp, u.login, m.codmat, par.codpar, d.gestion\n" + //
                        "from progra d\n" + //
                        "join materias m on d.codmat = m.codmat\n" + //
                        "join niveles n on m.codn = n.codn and n.nombre = :nombre\n" + //
                        "join personal p on d.codp = p.codp\n" + //
                        "join usuarios u on p.codp = u.codp\n" + //
                        "join paralelos par on d.codpar = par.codpar", nativeQuery = true)
        public List<MapaDataDto> getFilteredList(@Param("nombre") String nombre);

        public boolean existsByIdCodpAndIdCodmatAndIdCodparAndIdGestion(Integer codp, String codmat, Integer codpar,
                        Integer gestion);

        public List<PrograModel> findByUsuarioLogin(String login);
}
