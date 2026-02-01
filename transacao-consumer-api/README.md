# Documentação Técnica: `transacao-consumer-api`

Este documento fornece uma visão detalhada da arquitetura, funcionalidades e configuração do microsserviço `transacao-consumer-api`.

## 1. Arquitetura

A `transacao-consumer-api` é projetada com base nos princípios da **Arquitetura Limpa (Clean Architecture)**, garantindo que o sistema seja desacoplado, testável e fácil de manter. O fluxo de dados e a interação entre as camadas são ilustrados abaixo.

### Diagrama de Arquitetura

```
+-----------------------+      +-------------------------+
|  Produtor de Eventos  |----->|      Apache Kafka       |
| (Sistema Externo)     |      |  (Tópico: TRANSACAO-TOPIC) |
+-----------------------+      +-------------------------+
                                        |
                                        v (Consome Mensagem)
+--------------------------------------------------------------------------+
|                          transacao-consumer-api                          |
|                                                                          |
|   +------------------------------------------------------------------+   |
|   | CAMADA DE INFRAESTRUTURA                                         |   |
|   |  - Kafka Consumer (`@KafkaListener`)                             |   |
|   |  - Repositório JPA -> [Banco de Dados (PostgreSQL)]              |   |
|   |  - Cliente HTTP ----> [API Externa de Câmbio]                    |   |
|   +------------------------------------------------------------------+   |
|                              ^          | (Injeção de Dependência)       |
|  (Implementa Interfaces)     |          v                              |
|   +------------------------------------------------------------------+   |
|   | CAMADA DE APLICAÇÃO (Use Cases & Gateways)                       |   |
|   |  - `ProcessarTransacao` (Orquestrador)                           |   |
|   |  - `ValidadorDeTransacao` (Regras de Negócio)                    |   |
|   |  - Interfaces: `RepositorioDeDespesa`, `RepositorioConversao`    |   |
|   +------------------------------------------------------------------+   |
|                              ^                                         |
|                              | (Depende de)                            |
|   +------------------------------------------------------------------+   |
|   | CAMADA DE DOMÍNIO (Fornecida pela `common-transacao-lib`)          |   |
|   |  - Entidades: `RegistroDespesa`, `TransacaoDto`                  |   |
|   +------------------------------------------------------------------+   |
|                                                                          |
+--------------------------------------------------------------------------+
```

## 2. Funcionalidades

*   **Consumo de Mensagens:** Escuta o tópico `TRANSACAO-TOPIC` no Kafka para receber novas transações em tempo real.
*   **Processamento de Transações:** Orquestra a validação e persistência de cada transação recebida.
*   **Validação de Negócio:** Aplica um conjunto de regras para garantir a integridade e validade de cada transação.
*   **Conversão de Moeda:** Interage com uma API externa para obter cotações e realizar conversões de moeda quando necessário.
*   **Persistência de Dados:** Salva as transações processadas em um banco de dados PostgreSQL.
*   **Documentação de API:** Expõe uma interface Swagger UI para visualização e teste dos endpoints disponíveis.

## 3. Biblioteca Compartilhada (`common-transacao-lib`)

Para evitar a duplicação de código e manter um modelo de dados consistente entre diferentes microsserviços, foi criada a biblioteca `common-transacao-lib`.

*   **Propósito:** Centralizar as entidades de domínio (`TransacaoDto`, `RegistroDespesa`, etc.) que são compartilhadas entre o produtor de transações e este consumidor.
*   **Vantagens:**
    *   **Fonte Única de Verdade:** Garante que todos os serviços utilizem a mesma estrutura de dados.
    *   **Reutilização de Código:** Evita a reescrita das mesmas classes de modelo em múltiplos projetos.
    *   **Manutenibilidade:** Facilita a atualização do modelo de domínio, pois a mudança precisa ser feita em um único local.

A `transacao-consumer-api` inclui esta biblioteca como uma dependência Maven, utilizando suas classes na camada de domínio.

## 4. Como Executar o Projeto

### Via IDE (IntelliJ, VSCode, etc.)

1.  **Pré-requisitos:**
    *   Java 17
    *   Maven
    *   Docker (para rodar Kafka e PostgreSQL)

2.  **Inicie a Infraestrutura (Kafka e Postgres):**
    Use o `docker-compose.yml` para iniciar os serviços necessários.

    ```bash
    docker-compose up -d kafka postgres
    ```

3.  **Crie o Tópico no Kafka:**
    Execute o comando abaixo para criar o tópico que a aplicação irá consumir.

    ```bash
    docker exec -it kafka kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TRANSACAO-TOPIC
    ```

4.  **Configure as Variáveis de Ambiente na sua IDE:**
    Crie uma configuração de execução (Run Configuration) para a aplicação Spring Boot e defina as seguintes variáveis de ambiente:

    ```
    DB_HOST=localhost
    DB_PORT=5433
    DB_NAME=transacoes_db
    DATASOURCE_USERNAME=postgres
    DATASOURCE_PASSWORD=postgres
    JWT_SECRET=sua-chave-secreta-aqui
    ```

5.  **Execute a Aplicação:**
    Inicie a aplicação através da sua IDE (geralmente clicando no botão "Run" na classe principal `TransacaoConsumerApiApplication`).

### Via Docker

1.  **Pré-requisitos:**
    *   Docker e Docker Compose

2.  **Inicie todos os serviços:**
    O `docker-compose.yml` está configurado para orquestrar a aplicação e suas dependências. A partir da raiz do projeto, execute:

    ```bash
    docker-compose up --build
    ```
    *   O comando `--build` força a reconstrução da imagem da aplicação, garantindo que as últimas alterações no código sejam incluídas.
    *   Para rodar em segundo plano, adicione o parâmetro `-d`.

## 5. Acesso à Documentação da API (Swagger)

Após iniciar a aplicação (seja via IDE ou Docker), a documentação da API estará disponível e pronta para testes no seguinte endereço:

[http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)
