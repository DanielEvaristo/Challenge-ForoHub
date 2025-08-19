package com.daniel.ForoHub.domain.topico;

import java.time.LocalDateTime;

public record RespuestaTopico(
        Long id, String titulo, String mensaje,
        LocalDateTime fechaCreacion, StatusTopico status,
        Long autorId, Long cursoId
) {}
