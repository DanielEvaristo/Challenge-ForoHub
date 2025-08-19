package com.daniel.ForoHub.domain.topico;

import jakarta.validation.constraints.*;

public record RegistroTopico(
        @NotBlank @Size(max=200) String titulo,
        @NotBlank String mensaje,
        @NotNull Long autorId,
        @NotNull Long cursoId
) {}
