package com.nexamed.api.especialidade;

import jakarta.persistence.*;

@Entity
@Table(name = "especialidades")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private int duracaoMinutos;

    protected Especialidade() {
        // exigido pelo JPA
    }

    public Especialidade(String nome, int duracaoMinutos) {
        if (duracaoMinutos <= 0) {
            throw new IllegalArgumentException("A duração da consulta deve ser maior que zero");
        }
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public int getDuracaoMinutos() { return duracaoMinutos; }
}