package com.proyecto.backend_2.features.items;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.backend_2.dtos.responses.ItemsBySubjectDto;

public interface ItemRepository extends JpaRepository<ItemModel, Integer> {
        // traer items segun la materia seleccionada
        @Query(value = "SELECT i.codi, i.nombre, it.estado, it.gestion, it.ponderacion\n" + //
                        "FROM\n" + //
                        "\titems i\n" + //
                        "INNER JOIN\n" + //
                        "    itemat it ON i.codi = it.codi  \n" + //
                        "WHERE\n" + //
                        "    it.codmat = :codmat;", nativeQuery = true)
        public List<ItemsBySubjectDto> getItemsBySubject(@Param("codmat") String codmat);

        @Query(value = "select * from items where estado = :state;", nativeQuery = true)
        List<ItemModel> getByState(@Param("state") Integer state);

        @Query(value = "SELECT EXISTS (\n" + //
                        "    SELECT 1\n" + //
                        "    FROM items\n" + //
                        "    WHERE nombre = :name\n" + //
                        ") AS nombre_existe;", nativeQuery = true)
        public Boolean findName(@Param("name") String name);

        @Modifying
        @Query(value = "update items set estado = :state where codi = :id;", nativeQuery = true)
        void changeState(@Param("id") Integer id, @Param("state") Integer state);
}
