# Desafio de Padrões de Projeto com Spring Boot - Agendamento de Consultas

## Santander 2025 - Back-End com Java | DIO

Este projeto foi desenvolvido como parte do desafio de código "Design Patterns com Java: Dos Clássicos (GoF) ao Spring Framework" do bootcamp **Santander 2025 - Back-End com Java**, oferecido pela [Digital Innovation One (DIO)](https://web.dio.me/track/santander-2025-java-back-end).

Neste projeto desenvolvi uma API REST para um sistema de agendamento de consultas, aplicando de forma prática diversos Padrões de Projeto (Design Patterns) para construir um software robusto, desacoplado e de fácil manutenção.

-----

## 📖 Descrição do Projeto

A API simula um sistema onde é possível cadastrar médicos e pacientes, e então agendar consultas que relacionam ambos. O foco não está na complexidade das regras de negócio, mas sim na arquitetura do software e na aplicação estratégica dos padrões de projeto.

-----

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3.5.5**
* **Spring Web**
* **Spring Data JPA**
* **Spring AOP**
* **Maven**
* **H2 Database** (Banco de dados em memória)
* **Lombok**
* **Springdoc OpenAPI (Swagger)** para documentação da API

-----

## ✨ Padrões de Projeto Aplicados

Este projeto foi uma oportunidade para explorar e implementar os seguintes padrões de projeto:

### Padrões Fundamentais (GoF e de Arquitetura)

* #### Singleton

  **Objetivo:** Garantir que uma classe tenha apenas uma única instância e fornecer um ponto de acesso global a ela.
  **Implementação:** Este padrão é aplicado implicitamente pelo Spring Framework. Todos os nossos componentes (`@Service`, `@Repository`, `@Controller`, `@Component`, etc.) são, por padrão, gerenciados como Beans Singleton no contêiner do Spring.

* #### Dependency Injection (Injeção de Dependência)

  **Objetivo:** Inverter o controle sobre a criação e gerenciamento de dependências, promovendo o baixo acoplamento.
  **Implementação:** Utilizado em toda a aplicação através da **Injeção por Construtor**, a prática recomendada pelo Spring. Com o auxílio do Lombok (`@RequiredArgsConstructor`), as dependências são declaradas como `final`, garantindo imutabilidade.

* #### Facade

  **Objetivo:** Fornecer uma interface simplificada para um subsistema complexo.
  **Implementação:** A classe `AppointmentService` atua como uma fachada. Ela esconde a complexidade de orquestrar múltiplos repositórios (`DoctorRepository`, `PatientRepository`), mappers e eventos para realizar a operação de agendar uma consulta através de um único método (`schedule`).

* #### Repository

  **Objetivo:** Abstrair a camada de acesso a dados, desacoplando a lógica de negócio da persistência.
  **Implementação:** Utilizado através das interfaces do Spring Data JPA (`AppointmentRepository`, `DoctorRepository`, `PatientRepository`), que abstraem completamente as operações de CRUD.

* #### Builder

  **Objetivo:** Facilitar a construção de objetos complexos de forma legível e passo a passo.
  **Implementação:** Utilizado nas entidades (`Doctor`, `Patient`, `Appointment`) através da anotação `@Builder` do Lombok, permitindo a criação de instâncias de forma fluida.

* #### Mapper (ou Converter)

  **Objetivo:** Desacoplar objetos de diferentes camadas (ex: DTOs e Entidades), centralizando a lógica de conversão.
  **Implementação:** A classe `AppointmentMapper` é responsável por converter `AppointmentRequestDTO` em uma entidade `Appointment` e vice-versa, mantendo o `AppointmentService` focado apenas na lógica de negócio.

### Padrões Comportamentais

* #### Observer

  **Objetivo:** Permitir que um objeto (subject) notifique uma lista de objetos dependentes (observers) sobre qualquer mudança de estado, de forma desacoplada.
  **Implementação:** Utilizando o sistema de eventos do Spring.

    1.  **Evento:** A classe `AppointmentScheduledEvent` é criada.
    2.  **Publicador:** O `AppointmentService` injeta `ApplicationEventPublisher` e publica o evento após um agendamento ser salvo com sucesso.
    3.  **Ouvinte (Listener):** A classe `NotificationListener` usa a anotação `@EventListener` para "ouvir" o evento e executar uma ação secundária (como simular o envio de um e-mail), sem que o serviço principal saiba de sua existência.

* #### Proxy

  **Objetivo:** Fornecer um substituto ou um "representante" para outro objeto para controlar o acesso a ele, adicionando funcionalidades antes ou depois da execução do método original.
  **Implementação:** Utilizando Spring AOP (Programação Orientada a Aspectos).

    1.  **Anotação:** `@LogExecutionTime` foi criada para marcar métodos que devem ser monitorados.
    2.  **Aspecto:** A classe `LoggingAspect` intercepta a execução de qualquer método anotado com `@LogExecutionTime`.
    3.  **Funcionalidade:** O aspecto mede o tempo de execução do método e registra em log, uma funcionalidade transversal adicionada de forma limpa e sem alterar o código de negócio no `AppointmentService`.

-----

## 🗺️ Diagrama de Classes (UML)

```mermaid
classDiagram
    direction BT

    class AgendamentoController {
        <<Controller>>
        +agendar(AgendamentoRequestDTO): ResponseEntity~AgendamentoResponseDTO~
    }

    class AgendamentoService {
        <<Service>>
        -ConsultaRepository consultaRepository
        -PacienteRepository pacienteRepository
        -MedicoRepository medicoRepository
        -ApplicationEventPublisher eventPublisher
        +agendarConsulta(AgendamentoRequestDTO): Consulta
    }
    note for AgendamentoService "Padrão Facade: Simplifica a complexidade de agendar, orquestrando repositórios e eventos."

    class Consulta {
        <<Entity>>
        -Long id
        -LocalDateTime dataHora
        -Paciente paciente
        -Medico medico
        -StatusConsulta status
    }

    class Paciente {
        <<Entity>>
        -Long id
        -String nome
        -String email
    }

    class Medico {
        <<Entity>>
        -Long id
        -String nome
        -String especialidade
        -String email
    }

    class ConsultaRepository {
        <<Repository>>
        +findByMedicoAndDataHora(Medico, LocalDateTime): Optional~Consulta~
    }

    class AgendamentoRequestDTO {
        <<DTO>>
        -Long pacienteId
        -Long medicoId
        -LocalDateTime dataHora
    }

    class AgendamentoResponseDTO {
        <<DTO>>
        -Long consultaId
        -String nomePaciente
        -String nomeMedico
        -LocalDateTime dataHora
    }

    class ConsultaAgendadaEvent {
        <<Event>>
        -Consulta consulta
    }
    note for ConsultaAgendadaEvent "Padrão Observer: O evento que será publicado quando uma consulta for agendada."

    class EmailNotificationListener {
        <<Listener>>
        +onConsultaAgendada(ConsultaAgendadaEvent)
    }
    note for EmailNotificationListener "Padrão Observer: 'Ouve' o evento e reage enviando um e-mail. Totalmente desacoplado do serviço de agendamento."

    class AgendaMedicoListener {
        <<Listener>>
        +onConsultaAgendada(ConsultaAgendadaEvent)
    }

    class LoggingAspect {
        <<Aspect>>
        +logExecutionTime(ProceedingJoinPoint)
    }
    note for LoggingAspect "Padrão Proxy: Intercepta chamadas de método (ex: no AgendamentoService) para adicionar funcionalidades como logging, sem alterar o código original."


    AgendamentoController o-- AgendamentoRequestDTO : usa
    AgendamentoController o-- AgendamentoResponseDTO : retorna
    AgendamentoController --|> AgendamentoService : depende de

    AgendamentoService --|> ConsultaRepository
    AgendamentoService --|> PacienteRepository
    AgendamentoService --|> MedicoRepository
    AgendamentoService ..> ConsultaAgendadaEvent : publica

    Consulta "1" -- "1" Paciente
    Consulta "1" -- "1" Medico

    ConsultaAgendadaEvent <.. EmailNotificationListener : escuta
    ConsultaAgendadaEvent <.. AgendaMedicoListener : escuta

    LoggingAspect ..> AgendamentoService : intercepta
```

-----

## 🚀 Como Executar o Projeto

1.  **Pré-requisitos:**

    * Java 17 ou superior
    * Maven 3.8 ou superior

2.  **Clonando o repositório:**

    ```bash
    git clone git@github.com:JuanBailke/Scheduling-System.git
    cd scheduling
    ```

3.  **Executando a aplicação:**
    Você pode executar a aplicação através da sua IDE (IntelliJ, Eclipse, VSCode) encontrando a classe principal `SchedulingApplication.java` e rodando-a.
    Ou, através da linha de comando com o Maven:

    ```bash
    mvn spring-boot:run
    ```

-----

## 📡 Como Usar a API

A aplicação estará disponível em `http://localhost:8080`.

### Documentação Interativa (Swagger UI)

A forma mais fácil de interagir com a API é através da interface do Swagger, que é gerada automaticamente.

* **URL:** `http://localhost:8080/swagger-ui.html`

### Banco de Dados em Memória (H2 Console)

Você pode visualizar o estado do banco de dados em memória através do console do H2.

* **URL:** `http://localhost:8080/h2-console`
* **JDBC URL:** `jdbc:h2:mem:schedulingdb`
* **User Name:** `sa`
* **Password:** (deixe em branco)

-----

## 👤 Autor

**Juan Bailke**

* [LinkedIn](https://www.linkedin.com/in/juan-felipe-cavalari-bailke)
* [GitHub](https://github.com/JuanBailke)