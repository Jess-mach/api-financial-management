# transacao-producer-api

API para produção e gerenciamento de transações financeiras.

## 📜 Descrição

Esta aplicação é responsável por receber, processar, persistir e publicar transações financeiras. Ela é construída com uma arquitetura em camadas para garantir a separação de responsabilidades e a manutenibilidade do código.

## ✨ Funcionalidades

*   **Criação de Transações**: Endpoint para criar novas transações financeiras.
*   **Listagem e Busca**: Consulta de transações por usuário e por ID.
*   **Exportação de Dados**: Geração de relatórios de transações em formato Excel.
*   **Análise de Despesas**: Funcionalidade para analisar as despesas de um usuário.

## 🛠️ Arquitetura e Tecnologias

O projeto segue uma arquitetura em camadas (Application, Domain, Infra, Config) e utiliza as seguintes tecnologias:

*   **Java 17** e **Spring Boot 3.2.5**
*   **Spring Data JPA** com **PostgreSQL** para persistência de dados.
*   **Flyway** para versionamento do banco de dados.
*   **Spring Kafka** para mensageria assíncrona.
*   **Spring Security** com **JWT** para autenticação e autorização.
*   **Docker** e **Docker Compose** para containerização e orquestração do ambiente.
*   **Springdoc (Swagger)** para documentação da API.
*   **Apache POI** para geração de arquivos Excel.

## 🚀 Como Executar

### Pré-requisitos

*   [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
*   [Maven 3.8+](https://maven.apache.org/download.cgi)
*   [Docker](https://www.docker.com/get-started/) e [Docker Compose](https://docs.docker.com/compose/install/)

### 1. Executando com Docker (Recomendado)

O `docker-compose.yml` orquestra todo o ambiente necessário: banco de dados PostgreSQL, Zookeeper, Kafka e a própria API.

**a. Crie a rede externa:**
O docker-compose está configurado para usar uma rede externa chamada `rede-sistema`. Crie-a com o seguinte comando:
```bash
docker network create rede-sistema
```

**b. Inicie os containers:**
No diretório raiz do projeto, execute o comando:
```bash
docker-compose up -d --build
```
Este comando irá construir a imagem da API e iniciar todos os serviços em background. O serviço `kafka-setup` criará automaticamente o tópico `TRANSACAO-TOPIC` no Kafka.

### 2. Executando Localmente (Alternativo)

Se preferir não usar Docker, você pode executar a aplicação localmente. Para isso, você precisará de uma instância do PostgreSQL e do Kafka em execução.

**a. Configure as variáveis de ambiente:**
Exporte as seguintes variáveis de ambiente no seu terminal:
```bash
export DATASOURCE_URL=jdbc:postgresql://localhost:5433/transacoes_db
export DATASOURCE_USERNAME=db_user
export DATASOURCE_PASSWORD=db_password
export JWT_SECRET=seu-segredo-jwt
export SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

**b. Crie o tópico no Kafka:**
Se o tópico `TRANSACAO-TOPIC` não existir, crie-o com o comando:
```bash
docker exec -it kafka kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TRANSACAO-TOPIC
```
*(Este comando assume que você tem um container Kafka chamado `kafka` em execução)*

**c. Inicie a aplicação:**
Execute o seguinte comando Maven:
```bash
mvn spring-boot:run
```

## 📚 Documentação da API

Com a aplicação em execução (via Docker ou localmente), a documentação da API, gerada com o Springdoc, estará disponível no seu navegador através do seguinte link:

[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)


DATASOURCE_PASSWORD=db_password DATASOURCE_URL=jdbc:postgresql://localhost:5433/transacoes_db DATASOURCE_USERNAME=db_user JWT_SECRET=12345678 mvn spring-boot:run

docker exec -it kafka kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TRANSACAO-TOPIC


http://localhost:8081/swagger-ui/index.html