package com.nexamed.api.medico;

import com.nexamed.api.especialidade.Especialidade;
import com.nexamed.api.usuario.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, unique = true)
    private String crm;

    @ManyToOne(optional = false)
    @JoinColumn(name = "especialidade_id", nullable = false)
    private Especialidade especialidade;

    protected Medico() {
        // exigido pelo JPA
    }

    public Medico(Usuario usuario, String crm, Especialidade especialidade) {
        this.usuario = usuario;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public String getCrm() { return crm; }
    public Especialidade getEspecialidade() { return especialidade; }
}