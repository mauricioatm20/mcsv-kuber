package org.chasi.springcloud.msvc.cursos.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Usuario {

    private Long id;

    private String nombre;

    private String email;

    private String password;
}
