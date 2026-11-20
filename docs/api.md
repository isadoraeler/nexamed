# Desenho da API (rascunho)

| Método | Rota | Quem acessa | Sucesso | Erros |
|---|---|---|---|---|
| POST | /pacientes | público | 201 | 400, 409 |
| POST | /login | público | 200 | 401 |
| GET | /especialidades | logado | 200 | 401 |
| POST | /medicos | ADMIN | 201 | 400, 403 |
| POST | /horarios-atendimento | ADMIN | 201 | 400, 403, 409 |
| GET | /medicos/{id}/horarios-disponiveis?data= | logado | 200 | 401, 404 |
| POST | /consultas | PACIENTE | 201 | 400, 403, 409 |
| GET | /consultas | logado (só as suas) | 200 | 401 |
| PATCH | /consultas/{id}/cancelar | PACIENTE | 200 | 403, 409 |
| PATCH | /consultas/{id}/concluir | MEDICO | 200 | 403, 404 |


## Legenda dos status
- 200 OK, 201 criado
- 400 dados inválidos, 401 sem login, 403 sem permissão
- 404 não encontrado, 409 conflito (ex.: horário já ocupado)