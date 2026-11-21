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
- PostgreSQL (H2 em desenvolvimento)
- HTML, CSS e JavaScript
- JUnit 5 + Mockito
- Docker + GitHub Actions

## Funcionalidades (MVP)
- [x] Cadastro de paciente com senha em hash (BCrypt), validação e aceite LGPD
- [x] CPF mascarado nas respostas da API
- [ ] Login com JWT
- [ ] Controle de acesso por perfil
- [ ] Agendamento sem conflito de horários
- [ ] Cancelamento com regra de antecedência
- [ ] Log de auditoria
- [ ] Front-end e deploy

## Como rodar

### Backend
Requisitos: Java 21 ou superior.

```bash
cd backend
./mvnw spring-boot:run
```
No Windows, use `.\mvnw.cmd spring-boot:run`. A API sobe em `http://localhost:8080`
com banco H2 em memória (os dados somem ao reiniciar).

### Exemplo: cadastro de paciente
`POST /pacientes`
```json
{
  "nome": "Ana Teste",
  "email": "ana@teste.com",
  "senha": "Senha1234",
  "cpf": "98765432100",
  "telefone": "27999990000",
  "dataNascimento": "1998-03-10",
  "consentimentoLgpd": true
}
```
Respostas: `201` (criado), `400` (dados inválidos) e `409` (e-mail ou CPF já cadastrado).

## Documentação
- Desenho da API: [`docs/api.md`](docs/api.md)
- Segurança: em construção (`docs/seguranca.md`)

## Status
🚧 Em desenvolvimento até 31/12/2026