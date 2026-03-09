# 🚀 Microsserviços com Docker + Kubernetes

> Sistema distribuído de alta escalabilidade em Java 25 LTS, orquestrado com Docker e Kubernetes, com observabilidade completa via Prometheus + Grafana.

[![Java](https://img.shields.io/badge/Java-25_LTS-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-green?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue?style=flat-square&logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker)](https://www.docker.com/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-K8s-326CE5?style=flat-square&logo=kubernetes)](https://kubernetes.io/)
[![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub_Actions-2088FF?style=flat-square&logo=githubactions)](https://github.com/features/actions)

---

## 📌 1. Problema de Negócio

Aplicações monolíticas crescem até um ponto em que **uma única falha derruba todo o sistema**, o deploy de uma pequena mudança exige parar o serviço inteiro e a escala horizontal se torna inviável ou cara demais.

Este projeto resolve esse problema construindo uma arquitetura distribuída real, onde cada domínio de negócio (usuários, produtos, pagamentos) é um serviço independente, pode ser escalado individualmente e falha de forma isolada — sem comprometer os demais.

---

## 🎯 2. Objetivo do Projeto

Demonstrar, na prática, como construir e operar uma arquitetura de microsserviços no padrão exigido por empresas de alto nível técnico, cobrindo:

- **Separação de responsabilidades** entre domínios de negócio via microsserviços independentes
- **Orquestração de containers** com Docker Compose (local) e Kubernetes (produção)
- **Observabilidade completa**: coleta de métricas, dashboards e alertas em tempo real
- **Pipeline de CI/CD** automatizado do commit ao deploy em cluster K8s

---

## 🏗️ 3. Arquitetura da Solução

```
Cliente
   │
   ▼
┌─────────────────────────────────────────────────┐
│            API Gateway — NGINX (:4500)           │
└──────────┬──────────────┬───────────────┬───────┘
           │              │               │
           ▼              ▼               ▼
  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
  │ Users Service│ │Prod. Service │ │Pay.  Service │
  │   (:8081)    │ │   (:8082)    │ │   (:8083)    │
  └──────┬───────┘ └──────┬───────┘ └──────┬───────┘
         └────────────────┼────────────────┘
                          ▼
                ┌──────────────────┐
                │  PostgreSQL 18   │
                │     (:5432)      │
                └──────────────────┘

Observabilidade:
  Prometheus (:9090) ──► Grafana (:3000)
```

**Endpoints expostos pelo Gateway:**

| Rota | Serviço de Destino | Porta Interna |
|---|---|---|
| `/users` | users-service | 8081 |
| `/products` | products-service | 8082 |
| `/payments` | payments-service | 8083 |

---

## ⚙️ 4. Decisões Técnicas

**Por que Java 25 LTS + Spring Boot?**
Java 25 LTS oferece suporte a longo prazo, performance aprimorada com Virtual Threads (Project Loom) e é o padrão de mercado para sistemas corporativos de alta disponibilidade. Spring Boot acelera o desenvolvimento sem abrir mão da robustez.

**Por que PostgreSQL 18?**
Banco relacional com maturidade comprovada, suporte a transações ACID, alto desempenho em leituras e integridade referencial — essencial para o domínio de pagamentos. Avaliei MongoDB, mas a natureza relacional dos dados (user → payment → product) favorece um banco relacional.

**Por que Docker + Kubernetes?**
Docker garante paridade entre ambientes (dev/prod). Kubernetes adiciona auto-healing, rolling deployments e escala horizontal declarativa — os deployments de `users-service`, `products-service` e `payments-service` já rodam com `replicas: 2` prontos para escalar.

**Por que NGINX como API Gateway?**
Solução leve, com proxy reverso nativo e alta performance. Avaliado Kong, mas para o escopo atual o NGINX cobre roteamento, load balancing e centralização de entrada sem overhead adicional.

**Por que Prometheus + Grafana?**
Stack padrão de mercado para observabilidade. Prometheus faz scraping das métricas expostas pelo Spring Actuator a cada 15 segundos; Grafana exibe latência, taxa de erros e throughput por serviço em tempo real.

---

## 🛠️ 5. Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 25 LTS | Linguagem principal dos microsserviços |
| Spring Boot | 3.2.0 | Framework web + JPA + testes |
| PostgreSQL | 18 | Banco de dados relacional |
| Docker / Docker Compose | latest | Containerização e orquestração local |
| Kubernetes | latest | Orquestração em produção |
| NGINX | latest | API Gateway e proxy reverso |
| Prometheus | latest | Coleta de métricas |
| Grafana | latest | Visualização e dashboards |
| GitHub Actions | — | Pipeline CI/CD |
| Maven | 3.x | Build e gerenciamento de dependências |
| Lombok | — | Redução de boilerplate Java |

---

## 💻 6. Requisitos de Hardware e Software

### Hardware Mínimo (Local / Dev)

| Recurso | Mínimo | Recomendado |
|---|---|---|
| CPU | 2 núcleos | 4 núcleos |
| RAM | 8 GB | 16 GB |
| Disco | 10 GB livres | 20 GB livres |

> ⚠️ O stack completo (5 serviços + banco + Prometheus + Grafana) consome aproximadamente **5–6 GB de RAM** em execução.

### Software Necessário

| Ferramenta | Versão | Como instalar |
|---|---|---|
| **Docker Desktop** | 4.x ou superior | [docs.docker.com](https://docs.docker.com/get-docker/) |
| **Docker Compose** | v2 (embutido no Docker Desktop) | Incluído no Docker Desktop |
| **JDK** | 25 LTS | [adoptium.net](https://adoptium.net/) |
| **Maven** | 3.9+ | [maven.apache.org](https://maven.apache.org/download.cgi) |
| **kubectl** | latest | [kubernetes.io](https://kubernetes.io/docs/tasks/tools/) |
| **Git** | 2.x+ | [git-scm.com](https://git-scm.com/) |

---

## ▶️ 7. Como Executar

### Opção A — Docker Compose (Desenvolvimento Local)

```bash
# 1. Clone o repositório
git clone https://github.com/Santosdevbjj/docker-e-microsservicos
cd docker-e-microsservicos

# 2. Suba todos os serviços
docker-compose up --build

# 3. Aguarde todos os containers ficarem saudáveis (≈ 60 segundos)
# Acompanhe os logs em tempo real com:
docker-compose logs -f
```

**Serviços disponíveis após o start:**

| Serviço | URL |
|---|---|
| API Gateway | http://localhost:4500 |
| Users Service | http://localhost:8081/users |
| Products Service | http://localhost:8082/products |
| Payments Service | http://localhost:8083/payments |
| Grafana | http://localhost:3000 |
| Prometheus | http://localhost:9090 |

**Parar o ambiente:**
```bash
docker-compose down
```

**Remover volumes (limpar banco):**
```bash
docker-compose down -v
```

---

### Opção B — Kubernetes (Produção)

```bash
# 1. Certifique-se que kubectl está configurado e apontando para seu cluster
kubectl cluster-info

# 2. Aplique os manifests na ordem correta
kubectl apply -f k8s/postgres-deployment.yaml
kubectl apply -f k8s/users-deployment.yaml
kubectl apply -f k8s/products-deployment.yaml
kubectl apply -f k8s/payments-deployment.yaml
kubectl apply -f k8s/gateway-deployment.yaml

# 3. Suba a stack de observabilidade
kubectl apply -f k8s/prometheus-configmap.yaml
kubectl apply -f k8s/prometheus-deployment.yaml
kubectl apply -f k8s/grafana-configmap.yaml
kubectl apply -f k8s/grafana-deployment.yaml

# 4. Verifique o status dos pods
kubectl get pods -w

# 5. Obtenha o IP externo do gateway
kubectl get svc gateway
```

---

### Testando os Endpoints

```bash
# Criar um usuário
curl -X POST http://localhost:4500/users \
  -H "Content-Type: application/json" \
  -d '{"name": "João Silva", "email": "joao@email.com"}'

# Listar usuários
curl http://localhost:4500/users

# Criar um produto
curl -X POST http://localhost:4500/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Produto A", "price": 99.90}'

# Registrar um pagamento
curl -X POST http://localhost:4500/payments \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "productId": 1, "amount": 99.90}'
```

---

## 🔄 8. CI/CD Pipeline

O pipeline do GitHub Actions é ativado em **push** ou **pull request** na branch `main` e executa 3 estágios automaticamente:

```
Push → main
    │
    ▼
┌─────────────────────────────┐
│  Stage 1: build-test        │
│  mvn clean verify           │
│  (users / products /        │
│   payments services)        │
└──────────┬──────────────────┘
           │ sucesso
           ▼
┌─────────────────────────────┐
│  Stage 2: docker-build-push │
│  Build + push para DockerHub│
└──────────┬──────────────────┘
           │ sucesso
           ▼
┌─────────────────────────────┐
│  Stage 3: deploy-k8s        │
│  kubectl apply nos          │
│  manifests do cluster       │
└─────────────────────────────┘
```

**Secrets necessários no repositório GitHub:**

| Secret | Descrição |
|---|---|
| `DOCKER_USERNAME` | Usuário do DockerHub |
| `DOCKER_PASSWORD` | Senha / Access Token do DockerHub |
| `KUBECONFIG` | Conteúdo do kubeconfig do cluster K8s |

---

## 📊 9. Observabilidade — Dashboard Grafana

O arquivo `faang-dashboard.json` contém um dashboard pré-configurado com 6 painéis:

| Painel | Métrica Monitorada |
|---|---|
| Users Service — Requests Latency | Latência média de requisições (s) |
| Products Service — Error Rate | Taxa de erros HTTP por minuto |
| Payments Service — Throughput | Requisições por segundo |
| PostgreSQL Connections | Conexões ativas no banco |
| CPU Usage per Pod | Uso de CPU por pod no K8s |
| Memory Usage per Pod | Uso de memória por pod no K8s |

**Como importar o dashboard:**
1. Acesse Grafana em `http://localhost:3000` (usuário: `admin` / senha: `admin`)
2. Vá em **Dashboards → Import**
3. Faça upload do arquivo `faang-dashboard.json`

---

## 🗂️ 10. Estrutura do Projeto

```
docker-e-microsservicos/
├── ci-cd/
│   └── github-actions.yml          # Pipeline CI/CD
├── database/
│   └── init.sql                    # Schema inicial do PostgreSQL
├── gateway/
│   ├── Dockerfile                  # Build do NGINX Gateway
│   └── nginx.conf                  # Configuração de rotas e proxy
├── k8s/
│   ├── users-deployment.yaml
│   ├── products-deployment.yaml
│   ├── payments-deployment.yaml
│   ├── gateway-deployment.yaml
│   ├── postgres-deployment.yaml
│   ├── prometheus-configmap.yaml
│   ├── prometheus-deployment.yaml
│   ├── grafana-configmap.yaml
│   └── grafana-deployment.yaml
├── src/main/java/com/faang/
│   ├── users/                      # Microsserviço de Usuários
│   ├── products/                   # Microsserviço de Produtos
│   └── payments/                   # Microsserviço de Pagamentos
├── docker-compose.yml
├── Dockerfile
├── faang-dashboard.json            # Dashboard Grafana pré-configurado
└── pom.xml
```

---

## 📚 11. Aprendizados

O maior desafio técnico foi garantir a **ordem de inicialização dos containers**: o Spring Boot tentava conectar ao PostgreSQL antes do banco estar pronto, gerando falhas silenciosas. A solução foi configurar o `depends_on` no Docker Compose e adicionar retry de conexão no nível da aplicação.

Outro ponto de aprendizado foi a **configuração do Prometheus para scraping via K8s**: serviços com `ClusterIP` não são acessíveis externamente, exigindo que o Prometheus rode dentro do mesmo cluster e use os nomes de serviço DNS internos.

Por fim, entender a **diferença entre `ClusterIP` e `LoadBalancer`** no K8s foi fundamental para expor apenas o Gateway externamente, mantendo os microsserviços isolados na rede interna do cluster — uma decisão de segurança deliberada.

---

## 🔮 12. Próximos Passos

- [ ] Implementar **autenticação JWT** no API Gateway
- [ ] Adicionar **circuit breaker** com Resilience4j para tolerância a falhas
- [ ] Configurar **Horizontal Pod Autoscaler (HPA)** baseado em métricas do Prometheus
- [ ] Implementar **distributed tracing** com OpenTelemetry + Jaeger
- [ ] Aumentar cobertura de **testes unitários e de integração** (meta: 80%)
- [ ] Adicionar **cache** com Redis para os endpoints de leitura de produtos
- [ ] Configurar **PersistentVolumeClaim** no K8s para persistência do PostgreSQL

---

## 👨‍💻 Autor

**Sergio Santos**
🔗 

---


