
cd ~/workspace/financial.management

clear

docker compose up -d --build

Todos os logs:

docker compose logs -f transacao-consumer-api

docker compose logs -f usuarios-api

docker compose logs -f transacao-producer-api

Apenas novos logs:

clear && docker compose logs -f --tail 0 transacao-consumer-api

clear && docker compose logs -f --tail 0 usuarios-api

clear && docker compose logs -f --tail 0 transacao-producer-api


http://localhost:8080/swagger-ui/index.html#/autenticacao-controller/efetuarLogin

{
    "login": "jessica.costa",
    "senha": "Gerente@Senha2026"
}


Novo Usuario/ Atualização: 
{
    "nome": "testandodenovo",
    "email": "usuario_001@email.com",
    "login": "usuario_001",
    "senha": "Teste@12345",
    "perfilUsuario": "ADMINISTRADOR"
}

{
	"nome": "Ricardo Almeida",
	"email": "ricardo.gerente@empresa.com",
	"login": "ralmeida_mgr",
	"senha": "Gerente@Senha2026",
	"perfilUsuario": "GERENTE"
 }
  {
    "nome": "Ana Carolina Souza",
    "email": "ana.admin@empresa.com",
    "login": "asouza_admin",
    "senha": "Admin#Complexa987",
    "perfilUsuario": "ADMINISTRADOR"
  }
  {
    "nome": "Lucas Oliveira",
    "email": "lucas.user@empresa.com",
    "login": "loliveira_std",
    "senha": "User!Padrao456",
    "perfilUsuario": "USUARIO"
  }
  {
    "nome": "Beatriz Santos",
    "email": "beatriz.gerente@empresa.com",
    "login": "bsantos_ger",
    "senha": "Secur@Gerencia12",
    "perfilUsuario": "GERENTE"
  }

http://localhost:8081/swagger-ui/index.html

Nova Transacao

{
    "valor": "323.00",
    "tipo": "DEPOSITO",
    "moeda": "EUA",
    "descricao": "Depósito em conta corrente", 
    "conta": 5
}

{
    "usuarioId": "bd3eccad-76c7-4aa3-bb92-673a686e962f",
    "valor": "9999323.00",
    "tipo": "SAQUE",
    "moeda": "BRL",
    "descricao": "Depósito em conta corrente", 
    "conta": 5
}

{
    "usuarioId": "bd3eccad-76c7-4aa3-bb92-673a686e962f",
    "valor": "323.00",
    "tipo": "DEPOSITO",
    "moeda": "BRL",
    "descricao": "Depósito em conta corrente",
    "conta": 5
  },
  {
    "usuarioId": "a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d",
    "valor": "150.50",
    "tipo": "SAQUE",
    "moeda": "BRL",
    "descricao": "Saque via Terminal Autoatendimento",
    "conta": 12
  },
  {
    "usuarioId": "f9e8d7c6-b5a4-4321-9876-543210fedcba",
    "valor": "1200.00",
    "tipo": "TRANSFERENCIA",
    "moeda": "BRL",
    "descricao": "Transferência PIX enviada",
    "conta": 7
  },
  {
    "usuarioId": "bd3eccad-76c7-4aa3-bb92-673a686e962f",
    "valor": "50.00",
    "tipo": "DEPOSITO",
    "moeda": "BRL",
    "descricao": "Depósito via boleto",
    "conta": 5
  }

