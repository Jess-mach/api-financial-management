
# Documentação Técnica - API de Gestão de Usuários

## 1. Arquitetura

A API de Gestão de Usuários é construída utilizando uma arquitetura em camadas, promovendo a separação de responsabilidades e a manutenibilidade do código.

- **Camada de Aplicação (`application`):** Contém a lógica de negócio da aplicação, orquestrando as operações entre as outras camadas. Inclui os `usecases` que definem as funcionalidades principais.
- **Camada de Domínio (`domain`):** Representa o coração do negócio, com as entidades, modelos e exceções específicas do domínio de usuários.
- **Camada de Infraestrutura (`infra`):** Responsável pela comunicação com o mundo exterior, como o banco de dados, e a exposição da API REST. Inclui `controllers`, `repositories` e configurações de `security`.
- **Camada de Configuração (`config`):** Centraliza as configurações da aplicação, como a configuração de segurança e a documentação da API (OpenAPI/Swagger).

### Tecnologias Utilizadas:

- **Java 17:** Linguagem de programação principal.
- **Spring Boot 3:** Framework para criação de aplicações web.
- **Spring Data JPA:** Para persistência de dados em banco relacional.
- **Spring Security:** Para autenticação e autorização.
- **PostgreSQL:** Banco de dados relacional.
- **Flyway:** Para versionamento e migração de banco de dados.
- **Maven:** Gerenciador de dependências e build.
- **Docker:** Para containerização da aplicação e do banco de dados.
- **JWT (Java Web Token):** Para geração e validação de tokens de autenticação.
- **SpringDoc (OpenAPI):** Para documentação da API.
- **Apache POI & Commons CSV:** Para manipulação de arquivos Excel e CSV.

## 2. Funcionalidades

A API oferece as seguintes funcionalidades:

- **CRUD de Usuários:**
    - Cadastro de novos usuários.
    - Listagem de todos os usuários.
    - Busca de usuário por ID.
    - Atualização de dados de um usuário.
    - Exclusão de um usuário.
- **Autenticação:**
    - Endpoint de login que retorna um token JWT em caso de sucesso.
- **Cadastro em Massa:**
    - Funcionalidade para cadastrar múltiplos usuários a partir de um arquivo CSV.
- **Documentação da API:**
    - A API é autodocumentada utilizando OpenAPI (Swagger), acessível em `/swagger-ui.html`.

## 3. Configuração e Execução

### 3.1. Execução Local (via IDE)

1.  **Pré-requisitos:**
    - JDK 17 ou superior.
    - Maven.
    - Uma instância do PostgreSQL em execução.

2.  **Configuração do Banco de Dados:**
    - Crie um banco de dados no PostgreSQL (ex: `usuarios_db`).
    - Atualize o arquivo `src/main/resources/application.properties` com as credenciais do seu banco:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/usuarios_db
      spring.datasource.username=<seu_usuario>
      spring.datasource.password=<sua_senha>
      ```

3.  **Execução:**
    - Execute a classe `br.com.ntt.usuario.Application` a partir da sua IDE.
    - A API estará disponível em `http://localhost:8080`.

### 3.2. Execução com Docker

1.  **Pré-requisitos:**
    - Docker.
    - Docker Compose.

2.  **Execução:**
    - No diretório raiz do projeto, execute o comando:
      ```bash
      docker-compose up --build
      ```
    - O comando irá:
        - Criar uma imagem Docker para a API.
        - Subir um container para o banco de dados PostgreSQL (`usuarios-db`).
        - Subir um container para a aplicação (`usuarios-api`).
    - A API estará disponível em `http://localhost:8080`.
    - O banco de dados estará acessível na porta `5432`.

### 3.3. Endpoints da API

- **Autenticação:** `POST /login`
- **Usuários:**
    - `GET /usuarios`
    - `GET /usuarios/{id}`
    - `POST /usuarios`
    - `PUT /usuarios/{id}`
    - `DELETE /usuarios/{id}`
- **Cadastro em Massa:** `POST /usuarios/upload`

Para mais detalhes sobre os endpoints e os dados esperados, acesse a documentação do Swagger em http://localhost:8080/swagger-ui.html após iniciar a aplicação.
