package com.nexamed.api.seguranca;

import com.nexamed.api.usuario.Perfil;
import com.nexamed.api.usuario.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private static final String SEGREDO = "segredo-de-teste-com-mais-de-32-caracteres-123";

    private JwtService jwtService;
    private UsuarioDetailsService detailsService;
    private JwtAuthenticationFilter filtro;

    @BeforeEach
    void preparar() {
        SecurityContextHolder.clearContext();
        jwtService = new JwtService(SEGREDO, 60);
        detailsService = mock(UsuarioDetailsService.class);
        filtro = new JwtAuthenticationFilter(jwtService, detailsService);
    }

    @AfterEach
    void limpar() {
        SecurityContextHolder.clearContext();
    }

    private String tokenDaAna() {
        return jwtService.gerarToken(new Usuario("Ana Teste", "ana@teste.com", "hash", Perfil.PACIENTE));
    }

    private MockHttpServletRequest requisicaoComCabecalho(String valor) {
        MockHttpServletRequest requisicao = new MockHttpServletRequest();
        requisicao.addHeader("Authorization", valor);
        return requisicao;
    }

    @Test
    void tokenValidoAutenticaOUsuario() throws Exception {
        UserDetails detalhes = User.withUsername("ana@teste.com").password("hash").roles("PACIENTE").build();
        when(detailsService.loadUserByUsername("ana@teste.com")).thenReturn(detalhes);

        filtro.doFilter(requisicaoComCabecalho("Bearer " + tokenDaAna()),
                new MockHttpServletResponse(), new MockFilterChain());

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("ana@teste.com", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void tokenAdulteradoNaoAutentica() throws Exception {
        filtro.doFilter(requisicaoComCabecalho("Bearer " + tokenDaAna() + "x"),
                new MockHttpServletResponse(), new MockFilterChain());

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verifyNoInteractions(detailsService);
    }

    @Test
    void semCabecalhoNaoAutentica() throws Exception {
        filtro.doFilter(new MockHttpServletRequest(),
                new MockHttpServletResponse(), new MockFilterChain());

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}