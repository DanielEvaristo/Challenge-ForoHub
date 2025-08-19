package com.daniel.ForoHub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensajeAndCurso_Id(String titulo, String mensaje, Long cursoId);
}
