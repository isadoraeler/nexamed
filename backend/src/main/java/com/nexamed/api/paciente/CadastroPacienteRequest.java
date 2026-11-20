package com.nexamed.api.paciente;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CadastroPacienteRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "E-mail é obrigatório") @Email(message = "E-mail inválido") String email,
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres") String senha,
        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve ter 11 dígitos, só números") String cpf,
        @NotBlank(message = "Telefone é obrigatório") String telefone,
        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve estar no passado") LocalDate dataNascimento,
        @AssertTrue(message = "É obrigatório aceitar o termo LGPD") boolean consentimentoLgpd
) {}