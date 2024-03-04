# Desafio Back-end

Temos 2 tipos de usuários, os comuns e lojistas, ambos têm carteira com dinheiro e realizam transferências entre eles. Vamos nos atentar **somente** ao fluxo de transferência entre dois usuários.

Requisitos:

-   Para ambos tipos de usuário, temos o Nome, CPF, e-mail, Tipo, Saldo e Senha. CPF e e-mails devem ser únicos no sistema. Sendo assim, seu sistema deve permitir apenas um cadastro com o mesmo CPF ou endereço de e-mail.

-   Usuários podem enviar dinheiro (efetuar transferência) para lojistas e entre usuários.

-   Lojistas **só recebem** transferências, não enviam dinheiro para ninguém.

-   Validar se o usuário tem saldo antes da transferência.

-   Antes de finalizar a transferência, deve-se consultar um serviço autorizador externo.

-   A operação de transferência deve ser uma transação (ou seja, revertida em qualquer caso de inconsistência) e o dinheiro deve voltar para a carteira do usuário que envia.

-   No recebimento de pagamento, o usuário ou lojista precisa receber notificação (envio de email, sms) enviada por um serviço de terceiro e eventualmente este serviço pode estar indisponível/instável.

-   Este serviço deve ser RESTFul.

### Payload

POST /users

```json
{
	"name": "Joao",
	"cpf": "80060060070",
	"email": "joao@exemplo.com",
	"password": "senha",
	"userType": "COMMON",
	"amount": 10
}
```

POST /transactions

```json
{
    "payer_id": 4,
    "payee_id": 7,
    "amount": 10
}
```
