package com.daniel.ForoHub.web.controller;

import com.daniel.ForoHub.domain.topico.ActualizarTopico;
import com.daniel.ForoHub.domain.topico.RegistroTopico;
import com.daniel.ForoHub.domain.topico.RespuestaTopico;
import com.daniel.ForoHub.domain.topico.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService service;

    public TopicoController(TopicoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<RespuestaTopico> crear(@Valid @RequestBody RegistroTopico dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @GetMapping
    public Page<RespuestaTopico> listar(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public RespuestaTopico detalle(@PathVariable Long id) { return service.detalle(id); }

    @PutMapping("/{id}")
    public RespuestaTopico actualizar(@PathVariable Long id, @Valid @RequestBody ActualizarTopico dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
