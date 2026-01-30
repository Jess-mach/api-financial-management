# Documentação Técnica da API de Gestão de Usuários

## Arquitetura

A API de Gestão de Usuários segue uma arquitetura limpa e orientada a casos de uso, promovendo uma clara separação de responsabilidades e alta coesão. As principais camadas são:

- **Domínio (`domain`):** Contém as entidades de negócio (`Usuario`, `DadosToken`), enums (`PerfilUsuario`) e exceções de domínio. Esta camada é o coração da aplicação e não depende de nenhuma outra.

- **Aplicação (`application`):** Orquestra o fluxo de dados e executa a lógica de negócio através dos casos de uso (`usecase`). Define as interfaces dos gateways (`gateways`) que são implementadas pela camada de infraestrutura.

- **Infraestrutura (`infra`):** Implementa os detalhes técnicos, como a exposição da API (controllers), a persistência de dados (JPA, repositórios) e a implementação dos gateways de aplicação.
    - **`controller`:** Expondo os endpoints da API REST.
    - **`persistence`:** Gerenciando a interação com o banco de dados.
    - **`gateways`:** Implementações concretas das interfaces definidas na camada de aplicação.

- **Configuração (`config`):** Responsável pela configuração do Spring, como segurança (Spring Security) e a documentação da API (Springdoc).

## Funcionalidades

A API oferece as seguintes funcionalidades:

- **Autenticação:**
  - `POST /login`: Autentica um usuário e retorna um token JWT.

- **Gerenciamento de Usuários:**
  - `POST /usuarios`: Cria um novo usuário.
  - `GET /usuarios`: Retorna uma lista de todos os usuários.
  - `GET /usuarios/{id}`: Retorna os detalhes de um usuário específico.
  - `PUT /usuarios/{id}`: Atualiza os dados de um usuário.
  - `DELETE /usuarios/{id}`: Exclui um usuário.

- **Cadastro em Massa:**
  - `POST /usuarios/cadastromassa`: Permite o cadastro de múltiplos usuários a partir de um arquivo CSV ou Excel.

## Tecnologias Utilizadas

- **Java 17:** Linguagem de programação principal.
- **Spring Boot 3.5.9:** Framework para o desenvolvimento de aplicações Java.
- **Spring Security:** Para a implementação da autenticação e autorização.
- **Spring Data JPA:** Para a persistência de dados.
- **PostgreSQL:** Banco de dados relacional.
- **Flyway:** Para o versionamento do banco de dados.
- **Maven:** Para o gerenciamento de dependências e do build da aplicação.
- **Lombok:** Para a redução de código boilerplate.
- **JWT (Java JWT):** Para a geração e validação de tokens de autenticação.
- **Springdoc OpenAPI:** Para a geração da documentação da API.
- **Apache POI e Commons CSV:** Para o processamento de arquivos no cadastro em massa.

## Configuração

A aplicação utiliza o `application.properties` para as configurações principais e perfis do Spring para configurações específicas de ambiente (ex: `application-prod.properties`).

### Variáveis de Ambiente

- `DATASOURCE_URL`: URL de conexão com o banco de dados PostgreSQL.
- `DATASOURCE_USERNAME`: Nome de usuário do banco de dados.
- `DATASOURCE_PASSWORD`: Senha do banco de dados.
- `JWT_SECRET`: Chave secreta para a assinatura dos tokens JWT.

### Perfis

- **default:** Perfil padrão, utilizado para desenvolvimento.
- **prod:** Perfil de produção. Desabilita a interface do Swagger.
- **docker:** Perfil utilizado no ambiente Docker.

## Build e Execução

### Pré-requisitos

- Java 17
- Maven

### Build

Para construir a aplicação, execute o seguinte comando na raiz do projeto:

```bash
mvn clean install
```

### Execução

Para executar a aplicação, utilize o seguinte comando:

```bash
java -jar target/usuario-api-0.0.1-SNAPSHOT.jar
```

Também é possível executar a aplicação utilizando o Docker:

```bash
docker-compose up -d
```


DATASOURCE_PASSWORD=password_usr DATASOURCE_URL=jdbc:postgresql://localhost:5432/usuarios_db DATASOURCE_USERNAME=user_usr mvn spring-boot:run



DATASOURCE_PASSWORD=password_usr;DATASOURCE_URL=jdbc:postgresql://localhost:5432/usuarios_db;DATASOURCE_USERNAME=user_usr


http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs.yaml