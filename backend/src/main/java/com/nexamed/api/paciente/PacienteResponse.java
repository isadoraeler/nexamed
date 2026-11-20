package com.nexamed.api.paciente;

public record PacienteResponse(Long id, String nome, String email, String cpf) {

    public static PacienteResponse de(Paciente paciente) {
        String cpf = paciente.getCpf();
        String cpfMascarado = "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
        return new PacienteResponse(
                paciente.getId(),
                paciente.getUsuario().getNome(),
                paciente.getUsuario().getEmail(),
                cpfMascarado);
    }
}