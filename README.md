# 🚀 Projeto: Microsserviços com Docker + Kubernetes

## 📌 Visão Geral
Sistema distribuído em **Java 25 LTS** com **PostgreSQL 18**, rodando em **Docker/Kubernetes**, dividido em microserviços (usuários, produtos, pagamentos) e exposto via **API Gateway (NGINX)**.

## 🎯 Objetivo
Demonstrar arquitetura escalável e observável em padrão **FAANG/Big Tech**, com CI/CD, testes automatizados e documentação clara.

## ⚙️ Decisões Técnicas
- **Java 25 LTS + Spring Boot** para robustez e maturidade.
- **PostgreSQL 18** como banco relacional.
- **Docker + Kubernetes** para orquestração e escalabilidade.
- **Prometheus + Grafana** para observabilidade.
- **NGINX** como API Gateway.

## 🛠 Tecnologias Utilizadas
- Java 25 LTS
- Spring Boot
- PostgreSQL 18
- Docker / Kubernetes
- Prometheus / Grafana
- GitHub Actions (CI/CD)

## ▶️ Como Executar
```bash
git clone https://github.com/Santosdevbjj/docker-e-microsservicos
cd docker-e-microsservicos
docker-compose up --build
