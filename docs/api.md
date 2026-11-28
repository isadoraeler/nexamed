# Desenho da API (rascunho)

| Método | Rota | Quem acessa | Sucesso | Erros |
|---|---|---|---|---|
| POST | /pacientes | público | 201 | 400, 409 |
| POST | /login | público | 200 | 400, 401 |
| GET | /especialidades | logado | 200 | 401 |
| POST | /medicos | ADMIN | 201 | 400, 403 |
| POST | /horarios-atendimento | ADMIN | 201 | 400, 403, 409 |
| GET | /medicos/{id}/horarios-disponiveis?data= | logado | 200 | 401, 404 |
| POST | /consultas | PACIENTE | 201 | 400, 403, 409 |
| GET | /consultas | logado (só as suas) | 200 | 401 |
| PATCH | /consultas/{id}/cancelar | PACIENTE | 200 | 403, 409 |
| PATCH | /consultas/{id}/concluir | MEDICO | 200 | 403, 404 |
| GET | /me | logado | 200 | 401 |


## Legenda dos status
- 200 OK, 201 criado
- 400 dados inválidos, 401 sem login, 403 sem permissão
- 404 não encontrado, 409 conflito (ex.: horário já ocupado)


## Autenticação

### POST /login
Rota pública. Autentica o usuário com e-mail e senha e devolve um token JWT.

**Requisição**
```json
{
  "email": "ana@teste.com",
  "senha": "Senha1234"
}
```

**Resposta 200**
```json
{
  "token": "<token JWT>",
  "tipo": "Bearer",
  "expiraEmMinutos": 60
}
```

**Erros**

| Status | Quando | Mensagem |
|---|---|---|
| 400 | E-mail inválido ou campo em branco | Lista de erros por campo |
| 401 | E-mail inexistente ou senha errada | `Credenciais inválidas` (a mesma nos dois casos) |

### Como usar o token
Envie o token no cabeçalho de cada requisição às rotas protegidas:

```
Authorization: Bearer <token>
```

Exemplo no PowerShell:
```powershell
$resposta = Invoke-RestMethod -Method Post -Uri http://localhost:8080/login `
  -ContentType "application/json" `
  -Body '{"email":"ana@teste.com","senha":"Senha1234"}'

$cabecalho = @{ Authorization = "Bearer $($resposta.token)" }
```

### Detalhes de segurança
- O token expira em 60 minutos; depois disso, é preciso fazer login de novo.
- O token carrega apenas o e-mail e o perfil. Nunca há senha ou CPF dentro dele.
- A resposta 401 é a mesma para e-mail inexistente e para senha errada, para não revelar quais e-mails estão cadastrados.
- A validação do token nas demais rotas será documentada junto com o filtro JWT.

## Cadastro de pacientes

### POST /pacientes
Rota pública. Cadastra um novo paciente (usuário com perfil PACIENTE). A senha é salva apenas como hash BCrypt, e o aceite do termo LGPD é obrigatório.

**Requisição**
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

**Regras de validação**
- `nome`, `email`, `telefone` e `dataNascimento` são obrigatórios; o e-mail deve ser válido
- `senha`: entre 8 e 72 caracteres
- `cpf`: 11 dígitos, somente números
- `dataNascimento`: data no passado
- `consentimentoLgpd`: precisa ser `true`

**Resposta 201**
```json
{
  "id": 1,
  "nome": "Ana Teste",
  "email": "ana@teste.com",
  "cpf": "***.654.321-**"
}
```
O CPF é devolvido mascarado, e a senha nunca é devolvida.

**Erros**

| Status | Quando | Mensagem |
|---|---|---|
| 400 | Dados inválidos ou termo LGPD não aceito | `Dados inválidos`, com a lista de erros por campo |
| 409 | E-mail ou CPF já cadastrado | `Já existe um cadastro com este e-mail` ou `Já existe um cadastro com este CPF` |


### GET /me
Rota protegida (qualquer usuário logado). Devolve os dados de quem está autenticado.

**Requisição**
```
GET /me
Authorization: Bearer <token>
```

**Resposta 200**
```json
{
  "nome": "Ana Teste",
  "email": "ana@teste.com",
  "perfil": "PACIENTE"
}
```

**Erros**

| Status | Quando |
|---|---|
| 401 | Token ausente, inválido, adulterado ou expirado |

### Como a API valida o token
- Um filtro lê o cabeçalho `Authorization: Bearer <token>` em toda requisição
- Confere a assinatura e a validade do token e busca o usuário no banco
- O perfil usado nas permissões vem do banco, não do token
- Sem token válido, as rotas protegidas respondem 401; apenas `POST /pacientes` e `POST /login` são públicas