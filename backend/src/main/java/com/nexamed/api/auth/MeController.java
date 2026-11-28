package com.nexamed.api.auth;

import com.nexamed.api.usuario.Usuario;
import com.nexamed.api.usuario.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

    private final UsuarioRepository usuarioRepository;

    public MeController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication autenticacao) {
        Usuario usuario = usuarioRepository.findByEmail(autenticacao.getName()).orElseThrow();
        return ResponseEntity.ok(
                new MeResponse(usuario.getNome(), usuario.getEmail(), usuario.getPerfil().name()));
    }
}