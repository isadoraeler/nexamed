package com.nexamed.api.seguranca;

import com.nexamed.api.usuario.Perfil;
import com.nexamed.api.usuario.Usuario;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private static final String SEGREDO = "segredo-de-teste-com-mais-de-32-caracteres-123";

    private Usuario usuario() {
        return new Usuario("Ana Teste", "ana@teste.com", "hash", Perfil.PACIENTE);
    }

    @Test
    void geraTokenValidoEExtraiOEmail() {
        JwtService service = new JwtService(SEGREDO, 60);
        String token = service.gerarToken(usuario());

        assertTrue(service.tokenValido(token));
        assertEquals(Optional.of("ana@teste.com"), service.extrairEmail(token));
    }

    @Test
    void rejeitaTokenAdulterado() {
        JwtService service = new JwtService(SEGREDO, 60);
        String token = service.gerarToken(usuario());

        assertFalse(service.tokenValido(token + "x"));
    }

    @Test
    void rejeitaTokenExpirado() {
        JwtService service = new JwtService(SEGREDO, -1);
        String token = service.gerarToken(usuario());

        assertFalse(service.tokenValido(token));
    }

    @Test
    void rejeitaSegredoCurto() {
        assertThrows(IllegalStateException.class, () -> new JwtService("curto", 60));
    }
}