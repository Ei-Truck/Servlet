# Servlet Ei Truck

Este módulo contém os **Servlets responsáveis pela camada de controle do sistema Ei Truck**, que fazem a **ponte entre o front-end (JSP/HTML)** e a **camada de persistência (DAOs e banco de dados PostgreSQL)**.  
Os Servlets gerenciam requisições HTTP, validam dados, e encaminham as respostas adequadas para as views da aplicação.

---

## Estrutura Geral do Projeto

O projeto está organizado segundo o padrão **MVC (Model–View–Controller)**:

| Camada     | Localização | Responsabilidade                                                                         |
|------------|--------------|------------------------------------------------------------------------------------------|
| **Model**  | `model/` | Entidades Java que representam tabelas do banco (Administrador, Analista, Unidade, etc). |
| **DAO**    | `dao/` | Manipulação de dados (CRUD e consultas específicas).                                     |
| **Servlet** | `servlet/` | Controladores responsáveis por processar requisições e respostas.                        |
| **View**   | `webapp/` (arquivos `.jsp` e `.html`) | Interface apresentada ao usuário.                                                        |

---

## Principais Servlets

| Servlet | Descrição                                                                            | Principais Ações |
|----------|--------------------------------------------------------------------------------------|------------------|
| **AdministradorServlet** | Controla operações de cadastro, login e gerenciamento de administradores.            | `doPost`, `doGet`, criação, edição e exclusão. |
| **AnalistaServlet** | Gerencia os dados e autenticação dos analistas vinculados às unidades.               | Cadastro, atualização e login de analistas. |
| **EnderecoServlet** | Gerencia o cadastro e listagem de endereços, que são relacionados a unidades.        | Inserção e consulta de endereços. |
| **UnidadeServlet** | Controla o cadastro e listagem de unidades, relacionando-as a endereços e segmentos. | Inserção e consulta de unidades. |
| **SegmentoServlet** | Mantém os segmentos de atuação de cada unidade.                                      | Criação e atualização de segmentos. |
| **TipoOcorrenciaServlet** | Administra os tipos de ocorrência e suas gravidades.                                 | Cadastro e atualização. |

---

## Integração com o Banco de Dados

Os Servlets se comunicam com o **banco PostgreSQL**.

As principais tabelas associadas incluem:

| Tabela            | Entidade Java    | Relação                         |
|-------------------|------------------|---------------------------------|
| `administrador`   | `Administrador`  | Login e controle de acesso.     |
| `analista`        | `Analista`       | Vínculo com unidade e segmento. |
| `endereco`        | `Endereco`       | Vínculo com unidade.            |
| `unidade`         | `Unidade`        | Conecta segmento e endereço.    |
| `segmento`        | `Segmento`       | Classificação das unidades.     |
| `tipo_ocorrencia` | `TipoOcorrencia` | Catálogo de tipos e gravidades. |

---

## Fluxo de Requisição (MVC)

1. O usuário envia uma requisição HTTP através de um formulário ou link (`.jsp`).
2. O **Servlet** correspondente recebe a requisição (`doGet` ou `doPost`).
3. O Servlet interage com as classes **DAO** para acessar o banco.
4. A resposta é encaminhada para a **View**, exibindo mensagens ou resultados.

---

## Como Executar

### Configurar o ambiente
- Instale o **Apache Tomcat** (versão 10+)
- Configure no IntelliJ IDEA ou Eclipse EE
- Certifique-se de ter o PostgreSQL em execução e o banco do `dbEiTruck` configurado

### Implantar o projeto
- Execute com o Tomcat no modo **Run/Debug**
- Acesse no navegador

---

## Dependências Principais

- **Jakarta Servlet API 5.0+**
- **PostgreSQL Driver (42.7+)**
- **Apache Tomcat**
- **Maven** (para build e dependências)
- **.env** (para conectar a um BD)
- **Java 17+**

---

## Estrutura de Pastas

```
Servlet/
├── eitruck/
│   ├── src/
│   │   ├── main/
│   │       ├── java/
│   │       │   ├── org/
│   │       │       ├── example/
│   │       │           ├── eitruck/
│   │       │               ├── conexao/
│   │       │               │   └── Conexao.java
│   │       │               ├── dao/
│   │       │               │   ├── AdministradorDAO.java
│   │       │               │   ├── AnalistaDAO.java
│   │       │               │   ├── EnderecoDAO.java
│   │       │               │   ├── SegmentoDAO.java
│   │       │               │   ├── TipoOcorrenciaDAO.java
│   │       │               │   └── UnidadeDAO.java
│   │       │               ├── filter/
│   │       │               │   └── FiltroAutenticador.java
│   │       │               ├── model/
│   │       │               │   ├── Administrador.java
│   │       │               │   ├── Analista.java
│   │       │               │   ├── Endereco.java
│   │       │               │   ├── Segmento.java
│   │       │               │   ├── TipoOcorrencia.java
│   │       │               │   └── Unidade.java
│   │       │               ├── servlet/
│   │       │               │   ├── AdministradorServlet.java
│   │       │               │   ├── AnalistaServlet.java
│   │       │               │   ├── DashboardServlet.java
│   │       │               │   ├── EnderecoServlet.java
│   │       │               │   ├── LoginServlet.java
│   │       │               │   ├── LogoutServlet.java
│   │       │               │   ├── SegmentoServlet.java
│   │       │               │   ├── TipoOcorrenciaServlet.java
│   │       │               │   └── UnidadeServlet.java
│   │       │               ├── util/
│   │       │                   ├── Hash.java
│   │       │                   └── Regex.java
│   │       ├── resources/
│   │       │   └── .env
│   │       ├── webapp/
│   │           ├── WEB-INF/
│   │           │   └── web.xml
│   │           ├── html/
│   │           │   ├── area-restrita/
│   │           │   │   ├── paginas/
│   │           │   │   │   ├── administrador/
│   │           │   │   │   │   ├── administrador.jsp
│   │           │   │   │   │   ├── editar_administrador.jsp
│   │           │   │   │   │   └── processar_administrador.jsp
│   │           │   │   │   ├── analista/
│   │           │   │   │   │   ├── analista.jsp
│   │           │   │   │   │   ├── editar_analista.jsp
│   │           │   │   │   │   └── processar_analista.jsp
│   │           │   │   │   ├── dashboard/
│   │           │   │   │   │   └── dashboard.jsp
│   │           │   │   │   ├── endereco/
│   │           │   │   │   │   ├── editar_endereco.jsp
│   │           │   │   │   │   ├── endereco.jsp
│   │           │   │   │   │   └── processar_endereco.jsp
│   │           │   │   │   ├── ocorrencia/
│   │           │   │   │   │   ├── editar_ocorrencia.jsp
│   │           │   │   │   │   ├── ocorrencia.jsp
│   │           │   │   │   │   └── processar_ocorrencia.jsp
│   │           │   │   │   ├── segmento/
│   │           │   │   │   │   ├── editar_segmento.jsp
│   │           │   │   │   │   ├── processar_segmento.jsp
│   │           │   │   │   │   └── segmento.jsp
│   │           │   │   │   ├── unidade/
│   │           │   │   │       ├── editar_unidade.jsp
│   │           │   │   │       ├── processar_unidade.jsp
│   │           │   │   │       └── unidade.jsp
│   │           │   │   └── tela_carregamento.jsp
│   │           │   ├── landing-page/
│   │           │       └── politica_privacidade.html
│   │           ├── imagens/
│   │           ├── js/
│   │           │   ├── landing-page/
│   │           │       └── script.js
│   │           ├── style/
│   │           │   ├── area-restrita/
│   │           │   │   ├── paginas/
│   │           │   │   │   ├── administrador.css
│   │           │   │   │   ├── analista.css
│   │           │   │   │   ├── dashboard.css
│   │           │   │   │   ├── endereco.css
│   │           │   │   │   ├── ocorrencia.css
│   │           │   │   │   ├── segmento.css
│   │           │   │   │   └── unidade.css
│   │           │   │   ├── login.css
│   │           │   │   └── tela_carregamento.css
│   │           │   ├── landing-page/
│   │           │       ├── politica_privacidade.css
│   │           │       └── style.css
│   │           ├── erro.jsp
│   │           ├── index.html
│   │           └── login.jsp
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── .gitignore
└── README.md
```

---

## Autores

Equipe **Ei Truck**  
Desenvolvido como parte do **projeto interdisciplinar de análise e automação de telemetria de transportes**.
