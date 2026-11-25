package com.nexamed.api.seguranca;

import com.nexamed.api.usuario.Perfil;
import com.nexamed.api.usuario.Usuario;
import com.nexamed.api.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioDetailsService service;

    @Test
    void carregaUsuarioExistenteComSenhaEPerfil() {
        Usuario usuario = new Usuario("Ana Teste", "ana@teste.com", "hash", Perfil.PACIENTE);
        when(usuarioRepository.findByEmail("ana@teste.com")).thenReturn(Optional.of(usuario));

        UserDetails detalhes = service.loadUserByUsername("ana@teste.com");

        assertEquals("ana@teste.com", detalhes.getUsername());
        assertEquals("hash", detalhes.getPassword());
        assertTrue(detalhes.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE")));
    }

    @Test
    void lancaExcecaoQuandoOUsuarioNaoExiste() {
        when(usuarioRepository.findByEmail("x@teste.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("x@teste.com"));
    }
}