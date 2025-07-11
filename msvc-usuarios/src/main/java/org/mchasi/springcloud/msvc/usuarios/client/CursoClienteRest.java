package org.mchasi.springcloud.msvc.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "host.docker.internal:8002")
public interface CursoClienteRest {

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    void eliminarCursoUsuario(@PathVariable Long id);

}
