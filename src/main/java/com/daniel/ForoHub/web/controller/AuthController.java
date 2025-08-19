package com.daniel.ForoHub.web.controller;

import com.daniel.ForoHub.domain.usuario.*;
import com.daniel.ForoHub.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final TokenService tokens;

    public AuthController(AuthenticationManager authManager, UsuarioRepository usuarios,
                          PasswordEncoder encoder, TokenService tokens) {
        this.authManager = authManager;
        this.usuarios = usuarios;
        this.encoder = encoder;
        this.tokens = tokens;
    }

    @PostMapping("/register")
    public ResponseEntity<RespuestaUsuario> register(@Valid @RequestBody RegistroUsuario dto) {
        if (usuarios.existsByEmail(dto.email())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Usuario u = Usuario.builder()
                .nombre(dto.nombre())
                .email(dto.email())
                .contrasena(encoder.encode(dto.contrasena()))
                .rol(dto.rol().toUpperCase())
                .build();
        u = usuarios.save(u);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespuestaUsuario(u.getId(), u.getNombre(), u.getEmail(), u.getRol()));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest dto) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.contrasena()));
        var usuario = usuarios.findByEmail(dto.email()).orElseThrow();
        String token = tokens.generar(usuario.getEmail(), usuario.getRol());
        return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
    }
}
