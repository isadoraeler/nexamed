package com.nexamed.api.paciente;

import com.nexamed.api.erro.ConflitoException;
import com.nexamed.api.usuario.Perfil;
import com.nexamed.api.usuario.Usuario;
import com.nexamed.api.usuario.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteService {

    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;

    public PacienteService(UsuarioRepository usuarioRepository,
                           PacienteRepository pacienteRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Paciente cadastrar(CadastroPacienteRequest dados) {
        if (usuarioRepository.existsByEmail(dados.email())) {
            throw new ConflitoException("Já existe um cadastro com este e-mail");
        }
        if (pacienteRepository.existsByCpf(dados.cpf())) {
            throw new ConflitoException("Já existe um cadastro com este CPF");
        }

        Usuario usuario = new Usuario(
                dados.nome(),
                dados.email(),
                passwordEncoder.encode(dados.senha()),
                Perfil.PACIENTE);
        usuarioRepository.save(usuario);

        Paciente paciente = new Paciente(
                usuario,
                dados.cpf(),
                dados.telefone(),
                dados.dataNascimento(),
                dados.consentimentoLgpd());
        return pacienteRepository.save(paciente);
    }
}