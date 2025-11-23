package com.proyecto.backend_2.features.itemat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.backend_2.ids.ItematId;

public interface ItematRepository extends JpaRepository<ItematModel, ItematId> {
    @Query(value = "update itemat set estado = :state where codmat = :codmat and codi = :codi;", nativeQuery = true)
    public void changeState(@Param("codmat") String codmat, @Param("codi") Integer codi,
            @Param("state") Integer state);

    @Query(value = "SELECT\n" + //
            "    SUM(ponderacion) AS suma\n" + //
            "FROM\n" + //
            "    itemat  \n" + //
            "WHERE \n" + //
            "\tcodmat = :codmat", nativeQuery = true)
    public Integer calcPonderacion(@Param("codmat") String codmat);

    @Query(value = "SELECT COUNT(*) FROM itemat WHERE codmat = :codmat AND codi = :codi", nativeQuery = true)
    public Integer existsItematBySubjectAndItem(@Param("codmat") String codmat, @Param("codi") Integer codi);
}
