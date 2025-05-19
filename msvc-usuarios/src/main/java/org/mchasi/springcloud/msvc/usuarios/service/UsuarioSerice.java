package org.mchasi.springcloud.msvc.usuarios.service;

import org.mchasi.springcloud.msvc.usuarios.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioSerice {
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Optional<Usuario> porEmail(String email);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    boolean existePorEmail(String email);

    List<Usuario> listarPorIds(Iterable<Long> ids);

}
