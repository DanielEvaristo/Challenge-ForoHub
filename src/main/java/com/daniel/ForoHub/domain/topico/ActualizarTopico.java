package com.daniel.ForoHub.domain.topico;

import jakarta.validation.constraints.*;

public record ActualizarTopico(
        @NotBlank @Size(max=200) String titulo,
        @NotBlank String mensaje,
        StatusTopico status
) {}
