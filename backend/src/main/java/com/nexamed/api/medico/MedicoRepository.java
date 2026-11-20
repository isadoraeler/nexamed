package com.nexamed.api.medico;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByEspecialidadeId(Long especialidadeId);
    boolean existsByCrm(String crm);
}