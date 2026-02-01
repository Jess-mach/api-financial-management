# Financial Management

## Visão Geral do Projeto

Este projeto consiste em um sistema de gerenciamento financeiro baseado em uma arquitetura de microserviços. Ele é composto por três serviços principais: `usuario-api` para gerenciamento de usuários, `transacao-producer-api` para registrar transações e `transacao-consumer-api` para processar essas transações de forma assíncrona. A comunicação entre os serviços de transação é realizada através do Apache Kafka, garantindo desacoplamento e escalabilidade.

## Arquitetura

O sistema é composto pelos seguintes componentes:

- **usuario-api**: Um serviço Spring Boot responsável pelo cadastro, autenticação e gerenciamento de usuários. Ele utiliza um banco de dados PostgreSQL para persistir os dados dos usuários. A autenticação é baseada em JWT (JSON Web Tokens), garantindo que a comunicação entre os serviços seja segura.
- **transacao-producer-api**: Serviço Spring Boot que expõe endpoints para o recebimento de transações. Ao receber uma nova transação, ele a publica em um tópico no Kafka.
- **transacao-consumer-api**: Serviço Spring Boot que consome as transações do tópico do Kafka, processa e as salva em seu próprio banco de dados PostgreSQL.
- **common-transacao-lib**: Uma biblioteca Java compartilhada entre os projetos de transação, contendo modelos de dados e funcionalidades comuns.
- **PostgreSQL**: Dois bancos de dados PostgreSQL são utilizados, um para o serviço de usuários e outro para os serviços de transação.
- **Kafka**: Utilizado como sistema de mensageria para a comunicação assíncrona entre o produtor e o consumidor de transações.
- **Zookeeper**: Necessário para o gerenciamento do cluster Kafka.

## Funcionalidades

### usuario-api
- Cadastro de novos usuários.
- Autenticação de usuários: O login de um usuário gera um token JWT que deve ser utilizado no header de autorização das requisições subsequentes para os serviços protegidos.
- Consulta de dados de usuários.

### transacao-producer-api
- Recebimento de novas transações financeiras.
- Validação dos dados da transação.
- Publicação das transações em um tópico do Kafka.

### transacao-consumer-api
- Consumo de transações do tópico do Kafka.
- Processamento e persistência das transações.

### common-transacao-lib
- Contém as entidades JPA e DTOs (Data Transfer Objects) compartilhados pelos serviços de transação.

## Configuração para Ambiente Local

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior
- Docker e Docker Compose

### Passos para Configuração

1. **Navegue até o diretório do projeto:**
   ```bash
   cd caminho/para/o/seu/projeto
   ```

2. **Limpe o terminal (opcional):**
   ```bash
   clear
   ```

3. **Execute os serviços com Docker Compose:**
   ```bash
   docker compose up -d --build
   ```
   Este comando irá construir as imagens Docker para cada serviço e iniciar todos os contêineres em background.

### Visualizando os Logs

Para acompanhar os logs de cada serviço, utilize os seguintes comandos:

**Todos os logs desde a criação do contêiner:**
```bash
docker compose logs -f transacao-consumer-api
docker compose logs -f usuarios-api
docker compose logs -f transacao-producer-api
```

**Apenas os novos logs:**
```bash
clear && docker compose logs -f --tail 0 transacao-consumer-api
clear && docker compose logs -f --tail 0 usuarios-api
clear && docker compose logs -f --tail 0 transacao-producer-api
```

## Documentação da API (Swagger)

Cada serviço da API possui sua própria documentação Swagger, que pode ser acessada através dos seguintes URLs após a inicialização dos serviços:

- **usuario-api**:
  - URL: `http://localhost:8080/swagger-ui.html`
- **transacao-producer-api**:
  - URL: `http://localhost:8081/swagger-ui.html`
- **transacao-consumer-api**:
  - URL: `http://localhost:8082/swagger-ui.html`

Através dessas interfaces, é possível visualizar todos os endpoints disponíveis, seus parâmetros e respostas, além de interagir com a API diretamente. Para os endpoints que requerem autenticação, é necessário primeiro obter um token JWT através do endpoint de login e depois informá-lo no campo de autorização do Swagger (geralmente no formato `Bearer <seu-token>`).
