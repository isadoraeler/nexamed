package com.nexamed.api.especialidade;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    boolean existsByNome(String nome);
}