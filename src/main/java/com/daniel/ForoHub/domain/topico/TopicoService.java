package com.daniel.ForoHub.domain.topico;


import com.daniel.ForoHub.domain.curso.Curso;
import com.daniel.ForoHub.domain.curso.CursoRepository;
import com.daniel.ForoHub.domain.usuario.Usuario;
import com.daniel.ForoHub.domain.usuario.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicoService {

    private final TopicoRepository topicos;
    private final UsuarioRepository usuarios;
    private final CursoRepository cursos;

    public TopicoService(TopicoRepository t, UsuarioRepository u, CursoRepository c) {
        this.topicos = t; this.usuarios = u; this.cursos = c;
    }

    @Transactional
    public RespuestaTopico crear(RegistroTopico dto) {
        if (topicos.existsByTituloAndMensajeAndCurso_Id(dto.titulo(), dto.mensaje(), dto.cursoId()))
            throw new IllegalArgumentException("Tópico duplicado");

        Usuario autor = usuarios.findById(dto.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));
        Curso curso = cursos.findById(dto.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        Topico t = Topico.builder()
                .titulo(dto.titulo())
                .mensaje(dto.mensaje())
                .autor(autor)
                .curso(curso)
                .build();

        t = topicos.save(t);
        return map(t);
    }

    @Transactional(readOnly = true)
    public Page<RespuestaTopico> listar(Pageable pageable) {
        return topicos.findAll(pageable).map(this::map);
    }

    @Transactional(readOnly = true)
    public RespuestaTopico detalle(Long id) {
        Topico t = topicos.findById(id).orElseThrow(() -> new IllegalArgumentException("No encontrado"));
        return map(t);
    }

    @Transactional
    public RespuestaTopico actualizar(Long id, ActualizarTopico dto) {
        Topico t = topicos.findById(id).orElseThrow(() -> new IllegalArgumentException("No encontrado"));

        boolean cambiaClave = !t.getTitulo().equals(dto.titulo()) || !t.getMensaje().equals(dto.mensaje());
        if (cambiaClave && topicos.existsByTituloAndMensajeAndCurso_Id(dto.titulo(), dto.mensaje(), t.getCurso().getId()))
            throw new IllegalArgumentException("Tópico duplicado");

        t.setTitulo(dto.titulo());
        t.setMensaje(dto.mensaje());
        if (dto.status() != null) t.setStatus(dto.status());

        return map(t);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!topicos.existsById(id)) throw new IllegalArgumentException("No encontrado");
        topicos.deleteById(id);
    }

    private RespuestaTopico map(Topico t) {
        return new RespuestaTopico(
                t.getId(), t.getTitulo(), t.getMensaje(),
                t.getFechaCreacion(), t.getStatus(),
                t.getAutor().getId(), t.getCurso().getId()
        );
    }
}
