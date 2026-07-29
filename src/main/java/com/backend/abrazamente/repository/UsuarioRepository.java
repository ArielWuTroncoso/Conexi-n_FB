package com.backend.abrazamente.repository;

import com.backend.abrazamente.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.usuarioRoles ur LEFT JOIN FETCH ur.rol WHERE LOWER(u.email) = LOWER(:email)")
    Optional<Usuario> findByEmailWithRoles(@Param("email") String email);

    Optional<Usuario> findByEmailIgnoreCase(String email);

    Optional<Usuario> findByRun(String run);

    Optional<Usuario> findByTelefono(String telefono);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByRun(String run);

    List<Usuario> findByCiudadIgnoreCase(String ciudad);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nombres) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(u.apellidos) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Usuario> buscarByNombre(@Param("nombre") String nombre);
}
