# Common Transação Lib

## Introdução

A `common-transacao-lib` é uma biblioteca Java desenvolvida com Spring Boot, projetada para fornecer um conjunto de funcionalidades comuns para transações financeiras. Ela encapsula o modelo de domínio principal, a lógica de negócios e as exceções relacionadas a transações, permitindo que seja facilmente integrada em outros microserviços do ecossistema financeiro.

## Arquitetura de Tecnologias

A biblioteca é construída sobre as seguintes tecnologias:

- **Java 17**: Versão da linguagem Java utilizada.
- **Spring Boot 3.2.5**: Framework principal para o desenvolvimento da aplicação.
- **Spring Data JPA**: Para persistência de dados e interação com o banco de dados.
- **Lombok**: Para reduzir código boilerplate, como getters, setters e construtores.
- **Jakarta Validation**: Para validação de dados nas entidades.

## Arquitetura do Projeto

A `common-transacao-lib` segue uma arquitetura em camadas para garantir a separação de responsabilidades e a manutenibilidade do código.

- **`domain`**: Esta camada contém o núcleo da lógica de negócios da biblioteca.
    - **`entity`**: As classes que representam as entidades do domínio, como `Transacao`. Elas são mapeadas para as tabelas do banco de dados e contêm os dados de negócio.
    - **`model`**: Classes e enums que servem como objetos de valor ou modelos de suporte, como `TipoTransacao` e `StatusTransacao`.
    - **`exception`**: Exceções customizadas (`BusinessException`, `ResourceNotFoundException`, etc.) que representam erros específicos do domínio.

A estrutura de pacotes é organizada da seguinte forma:

```
br.com.ntt.common.transacao
└── domain
    ├── entity
    │   └── Transacao.java
    ├── exception
    │   ├── AccessDeniedException.java
    │   ├── BusinessException.java
    │   └── ResourceNotFoundException.java
    └── model
        ├── StatusTransacao.java
        └── TipoTransacao.java
```

## Modelo de Domínio

O coração da biblioteca é o seu modelo de domínio, que representa as entidades e os conceitos de negócio.

### Entidades Principais

- **`Transacao`**: A entidade central que representa uma transação financeira. Seus principais atributos são:
    - `id`: Identificador único da transação (UUID).
    - `usuarioId`: Identificador do usuário que realizou a transação.
    - `valor`: O montante da transação.
    - `tipo`: O tipo da transação (veja `TipoTransacao`).
    - `status`: O estado atual da transação (veja `StatusTransacao`).
    - `dataHoraSolicitacao`: Data e hora em que a transação foi solicitada.
    - `dataHoraFinalizacao`: Data e hora em que a transação foi concluída.
    - `moeda`: A moeda da transação (ex: "BRL", "USD").
    - `taxaCambio`: A taxa de câmbio aplicada, se houver.
    - `descricao`: Uma descrição textual da transação.
    - `conta`: O número da conta envolvida.
    - `valorAtualizado`: O valor final após a aplicação de taxas ou câmbio.

### Enums

- **`TipoTransacao`**: Enum que define os tipos de transações possíveis:
    - `DEPOSITO`
    - `SAQUE`
    - `COMPRA`
    - `TRANSFERENCIA`
    - `SAIDA_EM_DINHEIRO`

- **`StatusTransacao`**: Enum que representa o ciclo de vida de uma transação:
    - `PENDENTE`
    - `AUTORIZADO`
    - `REJEITADO`
    - `ERRO_PROCESSAMENTO`

## Exceções

A biblioteca define um conjunto de exceções customizadas para lidar com erros de negócio de forma padronizada:

- **`BusinessException`**: Exceção genérica para erros de regra de negócio.
- **`AccessDeniedException`**: Lançada quando uma operação é negada por falta de permissão.
- **`ResourceNotFoundException`**: Lançada quando um recurso esperado não é encontrado.

## Configuração e Uso

Para utilizar a `common-transacao-lib` em outro projeto Maven, adicione-a como uma dependência no seu `pom.xml`:

```xml
<dependency>
    <groupId>br.com.ntt.common.transacao</groupId>
    <artifactId>common-transacao-lib</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Como a biblioteca usa Spring Data JPA, o projeto que a consumir precisará fornecer a configuração de `DataSource` para o banco de dados.

Para garantir que os microsserviços `transacao-producer` e `transacao-consumer` utilizem a versão mais recente da biblioteca, é crucial instalá-la no seu repositório Maven local antes de iniciar os serviços. Execute o seguinte comando na raiz do projeto `common-transacao-lib`:

```bash
mvn clean install
```

## Funcionalidades

- **Modelo de Dados de Transação**: Oferece um modelo de domínio robusto e bem definido para representar transações financeiras.
- **Gerenciamento de Estado**: Controla o ciclo de vida das transações através de status bem definidos.
- **Tratamento de Erros Padronizado**: Fornece exceções específicas para os erros mais comuns em aplicações financeiras.
- **Reutilização de Código**: Centraliza a lógica de negócio comum, evitando duplicação de código entre diferentes serviços.
