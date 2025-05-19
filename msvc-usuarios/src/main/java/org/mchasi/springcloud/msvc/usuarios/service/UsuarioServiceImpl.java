package org.mchasi.springcloud.msvc.usuarios.service;

import jakarta.transaction.Transactional;
import org.mchasi.springcloud.msvc.usuarios.client.CursoClienteRest;
import org.mchasi.springcloud.msvc.usuarios.entity.Usuario;
import org.mchasi.springcloud.msvc.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioSerice{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoClienteRest cliente;

    @Override
    @Transactional
    public List<Usuario> listar() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Usuario> porId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
        cliente.eliminarCursoUsuario(id);
    }


    @Override
    @Transactional
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) usuarioRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public Optional<Usuario> porEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

}
