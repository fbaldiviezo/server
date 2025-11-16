package com.proyecto.backend_2.features.roles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.backend_2.dtos.responses.RolesByUsersDto;

@Repository
public interface RolRepository extends JpaRepository<RolModel, Integer> {
    @Modifying
    @Query(value = "update roles set estado = :state where codr = :id;", nativeQuery = true)
    void changeState(@Param("id") Integer id, @Param("state") Integer state);

    @Query(value = "select * from roles where estado = :state;", nativeQuery = true)
    List<RolModel> getByState(@Param("state") Integer state);

    // traer roles respecto a un usuario
    @Query(value = "SELECT r.codr, r.nombre, r.estado, TRUE AS relacionRol FROM roles r INNER JOIN usurol u ON r.codr = u.codr WHERE u.login = :login;", nativeQuery = true)
    List<RolesByUsersDto> getAsignedRoles(@Param("login") String login);

    @Query(value = "SELECT r.codr, r.nombre, r.estado, False as relacionRol FROM roles r WHERE r.codr NOT IN ( SELECT codr FROM usurol WHERE login = 'user1' );", nativeQuery = true)
    List<RolesByUsersDto> getUnsignedRoles(@Param("login") String login);

    @Query(value = "SELECT r.codr, r.nombre, r.estado,\n" + //
            "    CASE\n" + //
            "        WHEN u.login IS NULL THEN FALSE\n" + //
            "        ELSE TRUE\n" + //
            "    END AS no_relacionado_con_menu\n" + //
            "FROM\n" + //
            "    roles r\n" + //
            "LEFT JOIN\n" + //
            "    usurol u ON r.codr = u.codr AND u.login = :login\n" + //
            "WHERE\n" + //
            "    r.codr = r.codr", nativeQuery = true)
    List<RolesByUsersDto> getRolesByUser(@Param("login") String login);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM roles WHERE nombre = :name) AS nombre_existente;", nativeQuery = true)
    public Boolean findName(@Param("name") String name);
}
