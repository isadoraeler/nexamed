# NexaMed 🏥

**Agendamento de consultas com foco em segurança de dados.**

> ⚠️ Projeto acadêmico e de portfólio. Todos os dados são fictícios.

## Sobre
O NexaMed é um sistema web de agendamento de consultas médicas com três perfis
de acesso: paciente, médico e administrador. O projeto aplica regras de negócio
reais, como horários sem conflito e cancelamento com antecedência mínima, e
prioriza segurança: autenticação com JWT, senhas com hash, controle de acesso
por perfil, log de auditoria e boas práticas de LGPD.

## Tecnologias
- Java 21 + Spring Boot, Spring Security, JWT
- PostgreSQL
- HTML, CSS e JavaScript
- JUnit 5 + Mockito
- Docker + GitHub Actions

## Funcionalidades (MVP)
- [ ] Cadastro e login com perfis (paciente, médico e admin)
- [ ] Agendamento sem conflito de horários
- [ ] Cancelamento com regra de antecedência
- [ ] Controle de acesso por perfil
- [ ] Log de auditoria

## Como rodar
Em breve.

## Segurança
Em construção. Ver `docs/seguranca.md`.

## Status
🚧 Em desenvolvimento até 31/12/2026