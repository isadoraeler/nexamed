package com.nexamed.api.paciente;

import com.nexamed.api.usuario.Usuario;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private boolean consentimentoLgpd;

    @Column(nullable = false, updatable = false)
    private LocalDateTime consentimentoEm;

    protected Paciente() {
        // exigido pelo JPA
    }

    public Paciente(Usuario usuario, String cpf, String telefone,
                    LocalDate dataNascimento, boolean consentimentoLgpd) {
        if (!consentimentoLgpd) {
            throw new IllegalArgumentException("O aceite do termo LGPD é obrigatório");
        }
        this.usuario = usuario;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.consentimentoLgpd = true;
        this.consentimentoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public boolean isConsentimentoLgpd() { return consentimentoLgpd; }
    public LocalDateTime getConsentimentoEm() { return consentimentoEm; }
}