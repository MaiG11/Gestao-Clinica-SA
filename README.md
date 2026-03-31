# Sistema de Gestão Clínica

---

## 1.Descrição do Sistema

### 🔹Contexto do Problema

A clínica utilizava anteriormente um sistema manual baseado em papel, no qual consultas eram registradas em agendas físicas.

Esse modelo apresentava diversos problemas:

* Dificuldade na organização dos horários
* Risco de perda de informações
* Falta de controle sobre consultas realizadas
* Possibilidade de conflitos de agendamento
* Baixa eficiência no atendimento

Com o aumento da demanda de pacientes, tornou-se necessária a informatização do processo.

---

### 🔹Solução Desenvolvida

Foi desenvolvido um sistema de gestão clínica utilizando API REST, com o objetivo de:

* Cadastrar pacientes e médicos
* Realizar agendamento de consultas
* Controlar datas e horários
* Evitar conflitos de agendamento
* Garantir integridade e organização dos dados

O sistema centraliza todas as informações e automatiza processos que antes eram manuais.
---

### 🔹Tecnologias Utilizadas

* **Java 17** 
Utilizado como linguagem principal por ser robusta e amplamente utilizada no mercado.
* **Spring Boot**
Framework utilizado para facilitar a criação da API REST, reduzindo configurações e aumentando produtividade.
* **Spring Data JPA**
Responsável pela comunicação com o banco de dados, simplificando operações de persistência.
* **MySQL**
Banco de dados relacional utilizado para armazenamento permanente dos dados.
* **H2 Database**
Banco em memória utilizado para testes e desenvolvimento.
* **Maven**
Gerenciador de dependências utilizado para organizar o projeto.
* **Swagger**
Utilizado para documentação e testes das rotas da API.

---

## 2.Diagrama ER

![Diagrama ER](images/diagrama-er.png)

### 🔹Descrição

O sistema é composto por três entidades principais:

* Paciente
* Médico
* Consulta

A entidade **Consulta** atua como relacionamento entre Paciente e Médico.

### 🔹Relacionamneto
    • Um paciente pode ter várias consultas
    • Um médico pode atender várias consultas
    • Cada consulta pertence a um único paciente e um único médico

Representação:
Paciente 1 → N Consulta N ← 1 Médico

### 🔹Justificativa
Essa modelagem foi adotada para permitir flexibilidade no agendamento e manter a integridade dos dados.

---

## 3.Estrutura de Pacotes

![Estrutura de Pacotes](images/estrutura-pacotes.png)

### 🔹Organização

* **Controller**
Responsável por receber requisições HTTP e retornar respostas.
* **Service**
Contém as regras de negócio do sistema.
* **Repository**
Responsável pelo acesso ao banco de dados.
* **Model**
Representa as entidades do sistema.
* **DTO**
Responsável pela transferência de dados e validações.


### 🔹Justificativa

Essa separação melhora:
    • Organização do código
    • Facilidade de manutenção
    • Reutilização de componentes
    • Clareza na responsabilidade de cada parte
A arquitetura segue o padrão de camadas (Layered Architecture), promovendo baixo acoplamento e alta coesão.

---

## 4.Endpoints da API

![Swagger Endpoints](images/swagger-endpoints.png)

### 🔹Paciente

| Método | Rota            | Descrição          |
| ------ | --------------- | ------------------ |
| GET    | /pacientes      | Listar pacientes   |
| POST   | /pacientes      | Cadastrar paciente |
| GET    | /pacientes/{id} | Listar paciente id |
| DELETE | /pacientes/{id} | Remover paciente   |

---

### 🔹Médico

| Método | Rota          | Descrição        |
| ------ | ------------- | ---------------- |
| GET    | /medicos      | Listar médicos   |
| POST   | /medicos      | Cadastrar médico |
| GET    | /medicos/{id} | Listar médico |
| DELETE | /medicos/{id} | Remover médico   |
| GET    | /medicos/especialidade{especialidade} | Lista por especialidade|

---

### 🔹Consulta

| Método | Rota            | Descrição          |
| ------ | --------------- | ------------------ |
| GET    | /consultas      | Listar consultas   |
| POST   | /consultas      | Agendar consulta   |
| GET    | /consultas/{id} | Listar consultas|
| DELETE    | /consultas/{id} | Cancelar consulta |
| GET | /consultas/paciente{id} | Lista todas consultas do paciente por id  |

##  🔹Justificativa dos métodos HTTP
    • GET → buscar dados
    • POST → criar registros
    • DELETE → remover registros

---

## 5.DTOs Utilizados

## 🔹Objetivo

Os DTOs foram utilizados para:

* Validação de dados de entrada
* Segurança da aplicação
* Evita expor entidades diretamente

## 🔹Exemplo de validação utilizadas

* `@NotBlank` → campos obrigatórios
* `@NotNull`  → não permite valores nulos
* `@Size` → limita tamanho
* `@Email` → valida formato

## 🔹Justificativa

Alguns campos não são retornados por segurança ou por não serem necessários na resposta.


---

## 6.Regras de Negócio

## 🔹Regras implementadas

* Não permite paciente sem nome
* Não permite CPF duplicado
* Não permite CRM duplicado
* Não permite consulta em data passada
* Não permite conflito de horário
* Campos obrigatórios devem ser preenchidos

## 🔹Explicação

Essas regras garantem:
    • Integridade dos dados
    • Consistência do sistema
    • Evitar erros de agendamento


---

## 7.Desafio Implementado

## 🔹Objetivo do Desafio

O desafio proposto consistia em listar todas as consultas de um paciente específico, incluindo os dados do médico relacionado.

## 🔹Implementação no Sistema

No sistema desenvolvido, essa funcionalidade não foi implementada com SQL direto, pois foi utilizada a tecnologia Spring Data JPA, que realiza automaticamente as associações entre as entidades.

Isso foi possível devido ao mapeamento realizado na entidade Consulta, conforme exemplo:

@ManyToOne
@JoinColumn(name = "id_paciente")
private Paciente paciente;

@ManyToOne
@JoinColumn(name = "id_medico")
private Medico medico;
Esses relacionamentos permitem que o sistema recupere, automaticamente, os dados do paciente e do médico vinculados a cada consulta.

## 🔹Funcionamento

Ao realizar uma busca de consultas (por exemplo, utilizando um método como findAll() no repositório), o JPA é responsável por montar internamente as junções (JOINs) entre as tabelas, retornando os dados já relacionados.

## 🔹Representação em SQL (Conceitual)

Embora não tenha sido implementado diretamente no código, o funcionamento da consulta pode ser representado pela seguinte instrução SQL:

```sql
SELECT c.id_consulta, p.nome, m.nome
FROM consulta c
JOIN paciente p ON c.id_paciente = p.id_paciente
JOIN medico m ON c.id_medico = m.id_medico;
```
## 🔹Explicação Técnica

A consulta acima utiliza a operação JOIN para relacionar as tabelas:
    • consulta
    • paciente
    • medico

Permitindo a recuperação de dados completos em uma única operação.
No sistema desenvolvido, essa lógica é abstraída pelo JPA, o que elimina a necessidade de escrever SQL manual, mantendo o código mais limpo e organizado.

## 🔹Justificativa da Abordagem

A utilização do JPA foi escolhida porque:
    • Reduz a complexidade do código
    • Evita erros em consultas SQL manuais
    • Facilita a manutenção do sistema
    • Permite trabalhar com orientação a objetos
---

## 8.Como Executar o Projeto

1. Criar banco:

```sql
CREATE DATABASE DB_GESTAO_CLINICA;
```

2. Executar o projeto:

| Pré Requisitos: | Passos:            
| ------ | --------------- | 
| Java17    |Clonar o projeto      |   
| Maven     |Configurar o banco de dados     | 
| MSQL      |Executar a aplicação| 


3. Acessar Swagger:
   http://localhost:8080/swagger-ui.html

---

## 9.Considerações Finais

O sistema desenvolvido resolveu o problema inicial da clínica, substituindo um processo manual por uma solução automatizada.

Com isso, foi possível:

* Melhor organização
* Redução de erros
* Maior eficiência
* Controle de dados

A arquitetura adotada permite evolução futura do sistema.
---
