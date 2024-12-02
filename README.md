## Iniciado Projeto


## acessar a api pelo swagger 

http://localhost:8080/swagger-ui/index.html

## acessar banco de dado em memória h2
acesar o h2 
http://localhost:8080/h2-console/
jdbc url=jdbc:h2:mem:test
username=admin
password=admin

![alt text](image.png)

pagamento criado 
![alt text](image-1.png)
Exemplo
{
  "codigoDebito": 123456,
  "identificadorPagador": "123.456.789-00",
  "metodoPagamento": "BOLETO",
  "valor": 250.50
}

respsota
{
  "id": 4,
  "codigoDebito": 123456,
  "identificadorPagador": "123.456.789-00",
  "metodoPagamento": "BOLETO",
  "numeroCartao": null,
  "valor": 250.5,
  "status": "PENDENTE_PROCESSAMENTO"
}
testar os 
1. Quando o pagamento está PENDENTE_PROCESSAMENTO
Cenário esperado:
Pode ser alterado para PROCESSADO_SUCESSO ou PROCESSADO_FALHA.
Exemplo de Payload:
Endpoint: PUT /pagamentos/{id}

json
Copiar código
{
  "codigoDebito": 123456,
  "identificadorPagador": "123.456.789-00",
  "metodoPagamento": "BOLETO",
  "valor": 250.50,
  "status": "PROCESSADO_SUCESSO"
}
Resposta esperada:

Código HTTP: 200 OK
O status deve ser atualizado para PROCESSADO_SUCESSO.
2. Quando o pagamento está PROCESSADO_SUCESSO
Cenário esperado:
Não pode ter o status alterado.
Exemplo de Payload:
Endpoint: PUT /pagamentos/{id}

json
Copiar código
{
  "codigoDebito": 123456,
  "identificadorPagador": "123.456.789-00",
  "metodoPagamento": "BOLETO",
  "valor": 250.50,
  "status": "PROCESSADO_FALHA"
}
Resposta esperada:

Código HTTP: 400 Bad Request ou 409 Conflict (dependendo da implementação).
Mensagem: "Pagamentos processados com sucesso não podem ser alterados."
3. Quando o pagamento está PROCESSADO_FALHA
Cenário esperado:
Pode ser alterado apenas para PENDENTE_PROCESSAMENTO.
Exemplo de Payload:
Endpoint: PUT /pagamentos/{id}

json
Copiar código
{
  "codigoDebito": 123456,
  "identificadorPagador": "123.456.789-00",
  "metodoPagamento": "BOLETO",
  "valor": 250.50,
  "status": "PENDENTE_PROCESSAMENTO"
}
Resposta esperada:

Código HTTP: 200 OK
O status deve ser atualizado para PENDENTE_PROCESSAMENTO.
Como validar os testes?
Inicialize o pagamento com o status PENDENTE_PROCESSAMENTO.

Crie um novo pagamento com o status inicial PENDENTE_PROCESSAMENTO.
Altere para PROCESSADO_SUCESSO ou PROCESSADO_FALHA e valide.

Altere o status para os casos inválidos e valide se a API retorna erro.

Faça alterações permitidas para PROCESSADO_FALHA e PENDENTE_PROCESSAMENTO.

exemplo para alterar 
