package com.daniel.ForoHub.domain.usuario;

import jakarta.validation.constraints.*;

public record RegistroUsuario(
        @NotBlank @Size(max=100) String nombre,
        @NotBlank @Email @Size(max=150) String email,
        @NotBlank @Size(max=100) String contrasena,
        @NotBlank String rol // "USER" o "ADMIN"
) {}
