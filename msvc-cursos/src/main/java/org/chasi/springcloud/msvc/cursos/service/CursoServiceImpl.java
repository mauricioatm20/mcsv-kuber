package org.chasi.springcloud.msvc.cursos.service;

import jakarta.transaction.Transactional;
import org.chasi.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.chasi.springcloud.msvc.cursos.models.Usuario;
import org.chasi.springcloud.msvc.cursos.models.entity.Curso;
import org.chasi.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.chasi.springcloud.msvc.cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest cliente;

    @Override
    public List<Curso> listar() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }


    @Override
    @Transactional
    public Optional<Curso> porIdConUsuarios(Long id) {
       Optional<Curso> c = cursoRepository.findById(id);
       if(c.isPresent()){
           Curso curso = c.get();
           if (!curso.getCursoUsuarios().isEmpty()){
               List<Long> ids = curso.getCursoUsuarios().stream().map(cu->cu.getUsuarioId()).collect(Collectors.toList());
               List<Usuario> usuarios = cliente.obtenerAlumnosPorCurso(ids);
                curso.setUsuarios(usuarios);
           }
           return Optional.of(curso);
       }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioById(Long id) {
        cursoRepository.eliminarCursoUsuarioById(id);
    }


    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);

        if(curso.isPresent()){
            Usuario usuarioMsvc = cliente.detalle(usuario.getId());
            Curso curso1 = curso.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso1.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso1);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        // Se crea el usuario en el microservicio de usuarios
        if(curso.isPresent()){
            Usuario usuarioNuevo = cliente.crear(usuario);
            Curso curso1 = curso.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevo.getId());

            curso1.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso1);
            return Optional.of(usuarioNuevo);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);

        if(curso.isPresent()){
            Usuario usuarioMsvc = cliente.detalle(usuario.getId());

            Curso curso1 = curso.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso1.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso1);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }
}
