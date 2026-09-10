# Budgeting API — Gestão Financeira Inteligente com Spring AI

Projeto desenvolvido como Desafio de Projeto do módulo de **Spring AI** no bootcamp/trilha de Spring Boot da **Digital Innovation One (DIO)**.

A aplicação é uma API REST de gestão financeira pessoal que utiliza capacidades de **Inteligência Artificial Generativa** (Spring AI + OpenAI) para extrair, categorizar e registrar transações financeiras a partir de descrições em texto e arquivos de áudio.

---

## O que o Projeto Faz

* **Registro de Transações via Texto:** Processa entradas de texto sobre gastos/ganhos e categoriza automaticamente.
* **Transcrição e Processamento de Áudio (Speech-to-Text):** Processa arquivos de áudio enviando para o modelo de IA, transcrevendo a fala e registrando a transação orçamentária.
* **Function Calling (Tools):** Permite que o LLM execute funções reais da aplicação para persistir dados no repositório financeiro.
* **Tratamento de Erros e Resiliência (Melhoria do Desafio):** Captura falhas de validação nos dados de entrada e erros de comunicação com a API de IA sem expor exceções brutas ao cliente.

---

## Melhoria Implementada

Neste desafio de projeto, foi implementado um **Mecanismo Global de Tratamento de Exceções e Validação de Dados**:

1. **Validação de Parâmetros de Entrada (`spring-boot-starter-validation`):**
   * Adicionadas anotações de validação (`@Valid`, `@NotBlank`, `@NotNull`) nos DTOs de requisição (`TransactionRequest`).
2. **Manipulador Global de Exceções (`GlobalExceptionHandler`):**
   * `@RestControllerAdvice` configurado para capturar erros de requisições malformadas (HTTP 400 Bad Request) com mensagens descritivas dos campos inválidos.
   * Tratamento direcionado para erros de integração com a API da OpenAI (HTTP 401 Unauthorized para chaves inválidas e HTTP 429 para limite de cota/rate-limit excedido).
   * Padronização das respostas de erro no DTO `ErrorResponseDTO`.

---

## Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.x**
* **Spring AI** (OpenAI Chat, Speech e Transcription Models)
* **Gradle**
* **Jakarta Bean Validation**

---

## Como Executar a Aplicação

### 1. Pré-requisitos
* Java 21 instalado
* Uma chave de API da OpenAI (`OPENAI_API_KEY`)

### 2. Configurar a Chave de API
Defina a variável de ambiente no seu terminal:

**Windows (PowerShell):**
```powershell
$env:OPENAI_API_KEY="sua_chave_aqui"
