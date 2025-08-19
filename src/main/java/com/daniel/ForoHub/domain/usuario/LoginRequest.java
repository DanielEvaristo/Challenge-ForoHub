package com.daniel.ForoHub.domain.usuario;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String contrasena
) {}
