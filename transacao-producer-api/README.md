# Documentação Técnica: `transacao-producer-api`

Este documento fornece uma visão detalhada da arquitetura, funcionalidades e configuração do microsserviço `transacao-producer-api`.

## 1. Arquitetura

A `transacao-producer-api` é projetada com base nos princípios da **Arquitetura Limpa (Clean Architecture)**, também conhecida como Arquitetura Hexagonal. Essa abordagem visa isolar a lógica de negócio central de detalhes de infraestrutura, resultando em um sistema desacoplado, testável e de fácil manutenção.

### Diagrama de Arquitetura

```
+-----------------------+      +----------------------------------+
|      Cliente API      |----->|     transacao-producer-api       |
| (Ex: Frontend, Postman)|      |      (Endpoints REST)            |
+-----------------------+      +----------------------------------+
                                        |
                                        v (Recebe Requisição HTTP)
+--------------------------------------------------------------------------+
|                          transacao-producer-api                          |
|                                                                          |
|   +------------------------------------------------------------------+   |
|   | CAMADA DE INFRAESTRUTURA (Adaptadores de Entrada)                |   |
|   |  - `@RestController` (Recebe DTOs)                               |   |
|   |  - `SecurityFilter` (Valida JWT)                                 |   |
|   +------------------------------------------------------------------+   |
|                              | (Chama Casos de Uso)                    |
|                              v                                         |
|   +------------------------------------------------------------------+   |
|   | CAMADA DE APLICAÇÃO (Casos de Uso & Portas)                      |   |
|   |  - `CriarTransacao`, `ListarTransacao`, `ExportarTransacao`      |   |
|   |  - Interfaces (Portas): `RepositorioDeTransacao`, `ProdutorDeEventos` |   |
|   +------------------------------------------------------------------+   |
|                              ^          |                              |
|  (Depende de)                |          v (Usa Portas para o exterior) |
|   +--------------------------+---------------------------------------+   |
|   | CAMADA DE DOMÍNIO (Entidades de Negócio)                         |   |
|   |  - `Transacao`, `Usuario`                                        |   |
|   |  - (Classes fornecidas pela `common-transacao-lib`)              |   |
|   +------------------------------------------------------------------+   |
|                              ^                                         |
|                              | (Implementa Interfaces/Portas)          |
|   +------------------------------------------------------------------+   |
|   | CAMADA DE INFRAESTRUTURA (Adaptadores de Saída)                  |   |
|   |  - Repositório JPA -> [Banco de Dados (PostgreSQL)]              |   |
|   |  - Kafka Producer --> [Apache Kafka (Tópico: TRANSACAO-TOPIC)]   |   |
|   |  - Gerador de Relatório (PDF/XLSX)                               |   |
|   +------------------------------------------------------------------+   |
|                                                                          |
+--------------------------------------------------------------------------+
```

## 2. Funcionalidades

*   **Criação de Transações:** Expõe um endpoint REST para receber, validar e registrar novas transações financeiras.
*   **Autenticação e Autorização:** Protege os endpoints utilizando um filtro de segurança baseado em JSON Web Tokens (JWT).
*   **Publicação de Eventos:** Após validar e persistir uma transação, publica uma mensagem no tópico `TRANSACAO-TOPIC` do Kafka para que outros serviços (como o `transacao-consumer-api`) possam processá-la.
*   **Persistência de Dados:** Salva as transações em um banco de dados PostgreSQL, utilizando Flyway para controle de versionamento do schema.
*   **Consulta e Exportação:** Permite a consulta de transações e a exportação dos dados em formatos de arquivo como PDF e XLSX.
*   **Documentação de API:** Expõe uma interface Swagger UI para visualização e teste dos endpoints disponíveis.

## 3. Biblioteca Compartilhada (`common-transacao-lib`)

Para evitar a duplicação de código e manter um modelo de dados consistente entre os microsserviços, foi criada a biblioteca `common-transacao-lib`.

*   **Propósito:** Centralizar as entidades de domínio e DTOs (Data Transfer Objects) que são compartilhados entre o produtor e o consumidor de transações.
*   **Vantagens:**
    *   **Fonte Única de Verdade:** Garante que todos os serviços utilizem a mesma estrutura de dados para representar uma transação.
    *   **Reutilização de Código:** Evita a reescrita das mesmas classes de modelo em múltiplos projetos.
    *   **Manutenibilidade:** Facilita a atualização do modelo de domínio, pois a mudança precisa ser feita em um único local.

A `transacao-producer-api` inclui esta biblioteca como uma dependência Maven, utilizando suas classes nas camadas de domínio e infraestrutura (DTOs).

## 4. Como Executar o Projeto

### Via IDE (IntelliJ, VSCode, etc.)

1.  **Pré-requisitos:**
    *   Java 17
    *   Maven
    *   Docker (para rodar Kafka e PostgreSQL)

2.  **Crie a rede Docker:**
    Este projeto utiliza uma rede compartilhada para a comunicação entre os contêineres.
    ```bash
    docker network create rede-sistema
    ```

3.  **Inicie a Infraestrutura (Kafka e Postgres):**
    Use o `docker-compose.yml` para iniciar os serviços de dependência.
    ```bash
    docker-compose up -d transacoes-db zookeeper kafka kafka-setup
    ```
    *O serviço `kafka-setup` criará automaticamente o tópico `TRANSACAO-TOPIC`.*

4.  **Configure as Variáveis de Ambiente na sua IDE:**
    Crie uma configuração de execução (Run Configuration) para a aplicação Spring Boot e defina as seguintes variáveis de ambiente (ou altere o `application.properties`):

    ```properties
    # Configuração do Banco de Dados
    spring.datasource.url=jdbc:postgresql://localhost:5433/transacoes_db
    spring.datasource.username=db_user
    spring.datasource.password=db_password

    # Configuração do Kafka
    spring.kafka.bootstrap-servers=localhost:9092

    # Segredo para assinatura do JWT (exemplo)
    api.security.token.secret=4a6b8a3e6a2d9f1c8e7d6c5b4a3f2e1d0c9b8a7f6e5d4c3b2a1f0e9d8c7b6a5
    ```

5.  **Execute a Aplicação:**
    Inicie a aplicação através da sua IDE (geralmente clicando no botão "Run" na classe principal `TransacaoProducerApiApplication`). A API estará disponível em `http://localhost:8080`.

### Via Docker

1.  **Pré-requisitos:**
    *   Docker e Docker Compose
    

2.  **Inicie todos os serviços:**
    O `docker-compose.yml` está configurado para orquestrar a aplicação e todas as suas dependências. A partir da raiz do projeto, execute:

    ```bash
    docker-compose up --build
    ```
    *   O comando `--build` força a reconstrução da imagem da aplicação, garantindo que as últimas alterações no código sejam incluídas.
    *   Para rodar em segundo plano, adicione o parâmetro `-d`.

## 5. Acesso à Documentação da API (Swagger)

Após iniciar a aplicação, a documentação da API estará disponível e pronta para testes no seguinte endereço (considerando a execução via `docker-compose`):

[http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
