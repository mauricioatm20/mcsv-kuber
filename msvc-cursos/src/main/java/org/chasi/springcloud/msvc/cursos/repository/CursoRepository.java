package org.chasi.springcloud.msvc.cursos.repository;

import org.chasi.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Modifying
    @Query("delete from CursoUsuario c where c.usuarioId = ?1")
    void eliminarCursoUsuarioById(Long id);
}
