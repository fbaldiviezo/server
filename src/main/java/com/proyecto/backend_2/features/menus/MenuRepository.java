package com.proyecto.backend_2.features.menus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.backend_2.dtos.responses.MenusByRoleDto;
import com.proyecto.backend_2.dtos.responses.ProcessDto;

public interface MenuRepository extends JpaRepository<MenuModel, Integer> {
        @Modifying
        @Query(value = "update menus set estado = :state where codm = :id;", nativeQuery = true)
        void changeState(@Param("id") Integer id, @Param("state") Integer state);

        // Query para obtener todos los menus relacionados a un rol
        @Query(value = "select m.* from menus m join rolme rm on rm.codm = m.codm where rm.codr = :xcodr", nativeQuery = true)
        List<MenuModel> getMenusRol(@Param("xcodr") Integer xcodr);

        // Query para obtener todos lo procesos relacionados a ese menu
        @Query(value = "select p.nombre as nombre,p.ayuda as ayuda, p.enlace as enlace from procesos p"
                        + " join mepro mp on mp.codp = p.codp where mp.codm = :xcodm", nativeQuery = true)
        List<ProcessDto> getProcesosMenu(@Param("xcodm") Integer xcodm);

        @Query(value = "select * from menus where estado = :state;", nativeQuery = true)
        List<MenuModel> getByState(@Param("state") Integer state);

        // traer los menus respecto a los roles
        @Query(value = "SELECT m.codm, m.nombre, m.estado, TRUE AS relacionMenu FROM menus m INNER JOIN rolme r ON m.codm = r.codm WHERE r.codr = :codr;", nativeQuery = true)
        List<MenusByRoleDto> getAsignedMenus(@Param("codr") Integer codr);

        @Query(value = "SELECT m.codm, m.nombre, m.estado, False as relacionMenu FROM menus m WHERE m.codm NOT IN ( SELECT codm FROM rolme WHERE codr = :codr );", nativeQuery = true)
        List<MenusByRoleDto> getUnsignedMenus(@Param("codr") Integer codr);

        @Query(value = "SELECT m.codm, m.nombre, m.estado,\n" + //
                        "    CASE\n" + //
                        "        WHEN r.codm IS NULL THEN FALSE\n" + //
                        "        ELSE TRUE\n" + //
                        "    END AS no_relacionado_con_menu\n" + //
                        "FROM\n" + //
                        "    menus m\n" + //
                        "LEFT JOIN\n" + //
                        "    rolme r ON m.codm = r.codm AND r.codr = :codr\n" + //
                        "WHERE\n" + //
                        "    m.codm = m.codm", nativeQuery = true)
        List<MenusByRoleDto> getMenusByRoles(@Param("codr") Integer codr);

        @Query(value = "SELECT EXISTS (\n" + //
                        "    SELECT 1\n" + //
                        "    FROM menus\n" + //
                        "    WHERE nombre = :name\n" + //
                        ") AS nombre_existe;", nativeQuery = true)
        public Boolean findName(@Param("name") String name);
}
