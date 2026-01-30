
cd ~/workspace/financial.management

clear

docker compose up -d --build

Todos os logs:

docker compose logs -f transacao-consumer-api

docker compose logs -f usuarios-api

docker compose logs -f transacao-producer-api

Apenas novos logs:

docker compose logs -f --tail 0 transacao-consumer-api

docker compose logs -f --tail 0 usuarios-api

docker compose logs -f --tail 0 transacao-producer-api


http://localhost:8080/swagger-ui/index.html#/autenticacao-controller/efetuarLogin

{
    "login": "Irving_Robel",
    "senha": "Teste@12345"
}


Novo Usuario: 
{
    "nome": "testandodenovo",
    "email": "usuario_001@email.com",
    "login": "usuario_001",
    "senha": "Teste@12345",
    "perfilUsuario": "ADMINISTRADOR"
}

http://localhost:8081/swagger-ui/index.html

Nova Transacao

{
    "usuarioId": "bd3eccad-76c7-4aa3-bb92-673a686e962f",
    "valor": "323.00",
    "tipo": "DEPOSITO",
    "moeda": "BRL",
    "descricao": "Depósito em conta corrente", 
    "conta": 5
}

usuarioId: 3f97e5fc-b446-40c7-9d75-ed649f4a8ac0




eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJBUEkgRmluYW5jaWFsLm1hbmFnZW1lbnQiLCJzdWIiOiJJcnZpbmdfUm9iZWwiLCJleHAiOjE3Njk3NDk0Njd9.GZBdGNTC96N8eQuwrOY8hkeX9T466euDYtrqiEczXZ0