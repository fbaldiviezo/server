package com.proyecto.backend_2.features.itemat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.backend_2.ids.ItematId;

public interface ItematRepository extends JpaRepository<ItematModel, ItematId> {
        @Modifying
        @Query(value = "update itemat set estado = :state where codmat = :codmat and codi = :codi and gestion = :gestion;", nativeQuery = true)
        public void changeState(@Param("codmat") String codmat, @Param("codi") Integer codi,
                        @Param("gestion") Integer gestion,
                        @Param("state") Integer state);

        @Query(value = "SELECT\n" + //
                        "    SUM(ponderacion) AS suma\n" + //
                        "FROM\n" + //
                        "    itemat  \n" + //
                        "WHERE \n" + //
                        "\tcodmat = :codmat AND estado = 1", nativeQuery = true)
        public Integer calcPonderacion(@Param("codmat") String codmat);

        @Query(value = "SELECT COUNT(*) FROM itemat WHERE codmat = :codmat AND codi = :codi", nativeQuery = true)
        public Integer existsItematBySubjectAndItem(@Param("codmat") String codmat, @Param("codi") Integer codi);

        @Query(value = "SELECT COUNT(*) FROM itemat WHERE codmat = :codmat AND codi = :codi AND estado = 1", nativeQuery = true)
        public Integer countActiveBySubjectAndItem(@Param("codmat") String codmat, @Param("codi") Integer codi);

        @Query(value = "SELECT COUNT(*) FROM itemat WHERE codmat = :codmat AND codi = :codi AND estado = 0", nativeQuery = true)
        public Integer countInactiveBySubjectAndItem(@Param("codmat") String codmat, @Param("codi") Integer codi);

        @Modifying
        @Query(value = "UPDATE itemat SET estado = 1, ponderacion = :ponderacion WHERE codmat = :codmat AND codi = :codi AND estado = 0", nativeQuery = true)
        public void reactivateItemat(@Param("codmat") String codmat, @Param("codi") Integer codi,
                        @Param("ponderacion") Integer ponderacion);

        @Query(value = "SELECT * FROM itemat WHERE codmat = :codmat AND codi = :codi LIMIT 1", nativeQuery = true)
        public ItematModel findAnyBySubjectAndItem(@Param("codmat") String codmat, @Param("codi") Integer codi);

        @Query(value = "SELECT codi FROM itemat WHERE codmat = :codmat AND estado = 1", nativeQuery = true)
        public Integer getCodi(@Param("codmat") String codmat);
}
