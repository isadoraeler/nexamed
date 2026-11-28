package com.nexamed.api.seguranca;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIXO = "Bearer ";

    private final JwtService jwtService;
    private final UsuarioDetailsService usuarioDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UsuarioDetailsService usuarioDetailsService) {
        this.jwtService = jwtService;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String cabecalho = request.getHeader("Authorization");

        if (cabecalho != null && cabecalho.startsWith(PREFIXO)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = cabecalho.substring(PREFIXO.length());
            jwtService.extrairEmail(token).ifPresent(email -> autenticar(email, request));
        }

        filterChain.doFilter(request, response);
    }

    private void autenticar(String email, HttpServletRequest request) {
        try {
            UserDetails detalhes = usuarioDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken autenticacao =
                    new UsernamePasswordAuthenticationToken(detalhes, null, detalhes.getAuthorities());
            autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        } catch (UsernameNotFoundException e) {
            // o usuário do token não existe mais: segue sem autenticar
        }
    }
}