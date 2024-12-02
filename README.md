# Documentação do Projeto - API de Pagamentos

## Iniciado Projeto

Este projeto é uma API de gerenciamento de pagamentos utilizando arquitetura limpa e padrão State para validação e transição de estados.

## Tecnologias Utilizadas

- **Spring Boot** para desenvolvimento da API.
- **Banco de Dados H2** em memória para facilitar os testes.
- **Swagger** para documentação interativa.
- **Clean Architecture** para maior organização e separação de responsabilidades.
- **Padrão State** para manipulação e validação do ciclo de vida de pagamentos.

## Benefícios do Padrão State

O uso do padrão State permite:

1. **Organização clara** de regras de transição de estados.
2. **Validação centralizada**: cada estado sabe como manipular suas próprias transições.
3. **Flexibilidade para mudanças**: é fácil adicionar novos estados ou modificar a lógica de um estado existente.
4. **Maior manutenção**: o código fica modular e fácil de entender.

# Como acessar a API e o banco de dados

## Acessar a API pelo Swagger

Link do Swagger:
```
http://localhost:8080/swagger-ui/index.html
```

## Acessar banco de dados em memória H2

- URL do console:
```
http://localhost:8080/h2-console/
```
- Configurações:
  - **JDBC URL**: `jdbc:h2:mem:test`
  - **Username**: `admin`
  - **Password**: `admin`

![Banco H2](image.png)

# Exemplo de Cadastro de Pagamento

### Endpoint de Criação
- **POST** `/pagamentos`

#### Exemplo de Requisição
```json
{
  "codigoDebito": 123456,
  "identificadorPagador": "123.456.789-00",
  "metodoPagamento": "BOLETO",
  "valor": 250.50
}
```

#### Exemplo de Resposta
```json
{
  "id": 4,
  "codigoDebito": 123456,
  "identificadorPagador": "123.456.789-00",
  "metodoPagamento": "BOLETO",
  "numeroCartao": null,
  "valor": 250.5,
  "status": "PENDENTE_PROCESSAMENTO"
}
```

![Pagamento Criado](image-1.png)

# Testes de Atualização de Pagamento

## 1. Quando o pagamento está **PENDENTE_PROCESSAMENTO**

### Cenário esperado:
- Pode ser alterado para **PROCESSADO_SUCESSO** ou **PROCESSADO_FALHA**.

### Exemplo de Requisição
- **PUT** `/pagamentos/{id}`

```json
{
  "codigoDebito": 123456,
  "identificadorPagador": "123.456.789-00",
  "metodoPagamento": "BOLETO",
  "valor": 250.50,
  "status": "PROCESSADO_SUCESSO"
}
```

### Resposta esperada:
- **Código HTTP**: `200 OK`
- O status é atualizado para **PROCESSADO_SUCESSO**.

## 2. Quando o pagamento está **PROCESSADO_SUCESSO**

### Cenário esperado:
- **Não pode** ter o status alterado.

### Exemplo de Requisição
- **PUT** `/pagamentos/{id}`

```json
{
  "codigoDebito": 123456,
  "identificadorPagador": "123.456.789-00",
  "metodoPagamento": "BOLETO",
  "valor": 250.50,
  "status": "PROCESSADO_FALHA"
}
```

### Resposta esperada:
- **Código HTTP**: `400 Bad Request` ou `409 Conflict`.
- **Mensagem**: "Pagamentos processados com sucesso não podem ser alterados."

## 3. Quando o pagamento está **PROCESSADO_FALHA**

### Cenário esperado:
- Pode ser alterado **apenas** para **PENDENTE_PROCESSAMENTO**.

### Exemplo de Requisição
- **PUT** `/pagamentos/{id}`

```json
{
  "codigoDebito": 123456,
  "identificadorPagador": "123.456.789-00",
  "metodoPagamento": "BOLETO",
  "valor": 250.50,
  "status": "PENDENTE_PROCESSAMENTO"
}
```

### Resposta esperada:
- **Código HTTP**: `200 OK`
- O status é atualizado para **PENDENTE_PROCESSAMENTO**.

# Validação dos Testes

1. Inicialize o pagamento com o status **PENDENTE_PROCESSAMENTO**.
2. Crie um novo pagamento com o status inicial **PENDENTE_PROCESSAMENTO**.
3. Altere para **PROCESSADO_SUCESSO** ou **PROCESSADO_FALHA** e valide.
4. Tente alterar o status para cenários inválidos e valide se a API retorna erro.
5. Realize alterações permitidas entre **PROCESSADO_FALHA** e **PENDENTE_PROCESSAMENTO**.

# Endpoints Disponíveis

### Pagamento Controller

| Método | Endpoint                                   | Descrição                                           |
|--------|-------------------------------------------|---------------------------------------------------|
| GET    | `/pagamentos/{id}`                        | Obter informações de um pagamento.               |
| PUT    | `/pagamentos/{id}`                        | Atualizar um pagamento.                          |
| DELETE | `/pagamentos/{id}`                        | Excluir pagamento logicamente.                  |
| GET    | `/pagamentos`                             | Listar todos os pagamentos.                     |
| POST   | `/pagamentos`                             | Registrar um novo pagamento.                    |
| GET    | `/pagamentos/filtro/status/{status}`      | Filtrar pagamentos por status.                  |
| GET    | `/pagamentos/filtro/cpfOuCnpj/{cpfOuCnpj}`| Filtrar pagamentos por CPF ou CNPJ.             |
| GET    | `/pagamentos/filtro/codigoDebito/{codigoDebito}` | Filtrar pagamentos por código de débito. |

# Conclusão

Este projeto demonstrou a utilização de boas práticas no desenvolvimento de APIs RESTful, com um foco especial em arquitetura limpa e padrões como o State, garantindo flexibilidade, organização e robustez.

