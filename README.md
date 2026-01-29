![Java](https://img.shields.io/badge/Java-17+-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-DB-blue)

# 🛒 Spring E-commerce API

API REST de um sistema de e-commerce desenvolvida com **Spring Boot**, contemplando fluxo completo de autenticação, carrinho de compras, pedidos e pagamento **simulado**, com controle de status e segurança via **JWT**.

Projeto com foco em **boas práticas de backend**, arquitetura em camadas e validações de negócio.

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Bean Validation
- JWT (JSON Web Token)
- PostgreSQL
- Hibernate
- Lombok
- Maven
- Postman (para testes da API)

---

## 📌 Funcionalidades

- Cadastro e autenticação de usuários
- Autorização via JWT
- Carrinho de compras:
  - Adicionar itens
  - Remover itens
  - Visualizar carrinho
- Criação de pedidos a partir do carrinho
- Validação de pedido antes do pagamento
- Pagamento **simulado (fake payment)**  
  _(Não realiza integração com gateways reais. Uso exclusivamente educacional.)_
- Atualização de status do pedido
- Listagem de pedidos do usuário autenticado

---

## 🔄 Fluxo do Pedido

```text
Usuário autenticado
      ↓
Carrinho de compras
      ↓
Criar pedido
      ↓
Validação do pedido
      ↓
Pagamento (simulado)
      ↓
Status atualizado para PAID
```

---

## 📦 Status do Pedido

Os pedidos seguem o seguinte fluxo de status:

- **CREATED** → Pedido criado  
- **PAID** → Pedido pago com sucesso  

---

## 🔒 Segurança

- Autenticação stateless com JWT
- Proteção de rotas com Spring Security
- Validação de ownership dos recursos por usuário autenticado

---

## 🔐 Autenticação

A autenticação é feita utilizando **JWT (JSON Web Token)**:

1. Usuário realiza login  
2. Recebe um token JWT  
3. O token deve ser enviado no header das requisições protegidas:

```http
Authorization: Bearer <token>
```

---

## 📡 Principais Endpoints

### 🔑 Autenticação
- **POST** `/auth/login`
- **POST** `/auth/register`

### 🛒 Carrinho
- **POST** `/cart/items`
- **GET** `/cart`
- **DELETE** `/cart/items/{id}`

### 📦 Pedido
- **POST** `/orders`
- **GET** `/orders`
- **POST** `/orders/{id}/pay`

---

## ⚙️ Como Executar o Projeto

### ✅ Pré-requisitos
- Java 17+
- Maven
- PostgreSQL

### 🚀 Passos para execução
```bash
git clone https://github.com/Gabriel-Kobayashi/spring-ecommerce.git
cd spring-ecommerce
mvn spring-boot:run
```
A aplicação será iniciada em:  
👉 http://localhost:8080

---

## 🗄️ Banco de Dados

O projeto utiliza **PostgreSQL** como banco de dados.

As credenciais de acesso devem ser configuradas no arquivo:

- `application.properties` **ou**
- `application.yml`

---

## 🧪 Testes

Os endpoints foram testados utilizando o **Postman**, validando:

- Autenticação
- Fluxo completo de **carrinho → pedido → pagamento**
- Regras de autorização

---

## 📌 Objetivo do Projeto

Este projeto tem como objetivo demonstrar conhecimentos em:

- Desenvolvimento de APIs REST com **Spring Boot**
- Segurança com **Spring Security e JWT**
- Modelagem de domínio
- Implementação de regras de negócio
- Organização e boas práticas em projetos backend

---

## 📄 Licença

Projeto de uso **educacional** e destinado a fins de **portfólio**.




