# <p align = "center"> Coderhood - API 3º Semestre - BD 2024

<p align="center">
  <img src="https://github.com/user-attachments/assets/31805bfb-677f-4777-9824-651643cc0aac" width="400" height="400" />
</p>




<p align="center">
  <a href ="#mortar_board-integrantes"> Integrantes </a>  •
  <a href ="#dart-objetivo"> Objetivo </a>  •
  <a href="#page_facing_up-requisitos-funcionais"> Requisitos Funcionais </a> •
  <a href="#page_with_curl-requisitos-não-funcionais"> Requisitos Não Funcionais </a>
</p>
<p align="center">
  <a href ="#calendar-cronograma"> Cronograma </a>  •
  <a href="#date-product-backlog"> Product Backlog </a> •
  <a href="#bookmark-tecnologias-utilizadas"> Tecnologias Utilizadas </a>
</p>
<p align="center">
  <a href="#instruções-de-execução"> Instruções de Execução </a>
</p>


## :mortar_board: Integrantes:

| **Nome**                   | **Função**            | **LinkedIn**                                                  |
|:----------------------:|:-----------------:|:----------------------------------------------------------:|
| Aline Ramos            | Product Owner     | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat-square&logo=linkedin&labelColor=blue)](https://www.linkedin.com/in/aline-ramos-3186b130)|
| Juan Cursino           | Scrum Master      | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat-square&logo=linkedin&labelColor=blue)](http://www.linkedin.com/in/juan-cursino)|
| Alexandre Jonas        | Desenvolvedor     | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat-square&logo=linkedin&labelColor=blue)](https://www.linkedin.com/in/alexandre-jonas-de-souza-fonseca-989920181)|
| Caique Almeida         | Desenvolvedor     | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat-square&logo=linkedin&labelColor=blue)](https://www.linkedin.com/in/caique-almeida-privado)|
| Cristiane Alvares      | Desenvolvedor     | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat-square&logo=linkedin&labelColor=blue)](https://www.linkedin.com/in/cristiane-alvares)|
| Jean César             | Desenvolvedor     | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat-square&logo=linkedin&labelColor=blue)](https://www.linkedin.com/in/jean-rodrigues-0569a0251)|
| Michel Momose          | Desenvolvedor     | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat-square&logo=linkedin&labelColor=blue)](https://www.linkedin.com/in/michel-momosemichel-momose-b78a04203)|
| Rafael Trevizoli       | Desenvolvedor     | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat-square&logo=linkedin&labelColor=blue)](https://www.linkedin.com/in/rafael-trevizoli)|
| Rennerson Vasconcelos  | Desenvolvedor     | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat-square&logo=linkedin&labelColor=blue)](https://www.linkedin.com/in/rennerson-vasconcelos-afonso-136107169/)|


## :dart: Objetivo

Criar mecanismo para mapeamento de portais de notícias estratégicas, mecanismo de captura 
rotineira para geração de histórico, e num momento futuro aplicação de análises baseadas em IA 
e/ou machine learning visando cruzamento de dados para identificação de ações estratégicas para 
o negócio.
Essa estrutura e conceito deve ser aplicada para APIs de fornecimento de dados estratégicos, como 
por exemplo, previsão do tempo.
## :page_facing_up: Requisitos Funcionais
| **Descrição** | **Requisito** |
|-------- | :--------: |
| Cadastro de Portais de Notícias | 1 |
| Cadastro de APIs | 2 |
| Cadastro de Tags | 3 |
| Cadastro de Jornalistas | 4 |
| Processo de web scraping (capturar os dados de notícias e APIs e armazenar em banco de dados) | 5 |
| Indicação de tags que estão relacionadas | 6 |
| Tela de consulta de notícias, com filtros de pesquisa | 7 |
| Tela de APIs, com filtros de pesquisa | 8 |


## :page_with_curl: Requisitos Não Funcionais

| **Descrição** | **Requisito** |
|-------- | :--------: |
| Prever um grande volume de notícias armazenadas | 9 |
| Utilizar softwares livres | 10 |
| Java (linguagem de programação, frameworks e APIs) | 11 |
| Deverá ser uma aplicação web | 12 |
| O front-end deve ser desenvolvido de forma minimalista | 13 |
| Documentação: manual de usuário, diagrama entidade-relacionamento e instruções de instalação | 14 |

## :calendar: Cronograma

| Sprint  | Nome | Data inicio  | Data Fim | Status |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| --  | KickOff   | 26/08   | 30/08 | Ok |
|  1  | Sprint 1   | 09/09   | 29/09 | Ok |
|  2  | Sprint 2   | 30/09   | 20/10 |    |
|  3  | Sprint 3   | 21/10   | 10/11 |    |
|  4  | Sprint 4   | 11/11   | 01/12 |    |
|  5  | Feira de Soluções  | 12/12 |    |


## :date: Product BackLog
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>User Stories</th>
        <th>Épico</th>
        <th>Sprint</th>
        <th>Prioridade</th>
        <th>Requisito do Parceiro</th>
    </tr>
    </thead>
    <tbody>
    <tr>
    <td>1</td>
    <td>Eu como usuário administrador, eu quero cadastrar portais de notícias no sistema, para que eu possa definir as fontes das quais o sistema irá capturar informações.</td>
    <td>Cadastro de Portais de Notícias</td>
    <td>1</td>
    <td>Alta</td>
    <td>1,4,13</td>
</tr>
<tr>
    <td>2</td>
    <td>Eu como um usuário, eu quero consultar as notícias armazenadas no sistema com a possibilidade de aplicar filtros, para encontrar informações específicas rapidamente.</td>
    <td>Tela de Consulta de Notícias com Filtros</td>
    <td>1</td>
    <td>Média</td>
    <td>7,13</td>
</tr>
<tr>
    <td>3</td>
    <td>Eu, como usuário do sistema, quero coletar informações de sites de notícias, armazená-las em um banco de dados e depois poder consultá-las sempre que precisar.</td>
    <td>Processo de Web Scraping</td>
    <td>2</td>
    <td>Alta</td>
    <td>4,5,13</td>
</tr>
<tr>
    <td>4</td>
    <td>Eu como usuário administrador, eu quero cadastrar APIs externas no sistema, para que o sistema possa consumir dados estratégicos dessas fontes.</td>
    <td>Cadastro de APIs</td>
    <td>2</td>
    <td>Média</td>
    <td>2,13</td>
</tr>
<tr>
    <td>5</td>
    <td>Eu como um usuário, eu quero consultar as APIs cadastradas no sistema com a possibilidade de aplicar filtros, para encontrar APIs específicas rapidamente.</td>
    <td>Tela de Consulta de APIs com Filtros</td>
    <td>3</td>
    <td>Alta</td>
    <td>8,13</td>
</tr>
<tr>
    <td>6</td>
    <td>Eu como usuário administrador, eu quero cadastrar tags no sistema, para categorizar as notícias e facilitar a busca e análise posterior.</td>
    <td>Cadastro de Tags</td>
    <td>3</td>
    <td>Média</td>
    <td>3,13</td>
</tr>
<tr>
    <td>7</td>
    <td>Eu como um usuário, eu quero que o sistema me sugira tags relacionadas a uma notícia, para facilitar a categorização e análise das informações.</td>
    <td>Indicação de Tags Relacionadas</td>
    <td>4</td>
    <td>Alta</td>
    <td>6,13</td>
</tr>
    </tbody>
</table>

## :chart_with_upwards_trend: Gráfico Burndown
<div align="center">
    <img src="documentacao\Burndown\sprint2\burndown_sprint2_2024-10-20(01).jpg" alt="Gráfico Burndown" alt="Gráfico Burndown" width="75%">
</div>

## :bookmark: Tecnologias Utilizadas
<h4 align="center">

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
![Stack Overflow](https://img.shields.io/badge/-Stackoverflow-FE7A16?style=for-the-badge&logo=stack-overflow&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)

</h4>

## Instruções de Execução
### Database
Execute o script abaixo para criação das tabelas no seu DBMS ou SGBD em português.
> ./back/gsw-ap/src/main/java/gsw_api/gsw_api/gws.sql

### Back-end
``` Bash
cd back/gsw-api
mvn clean install
mvn spring-boot:run
```

### Front-end
``` Bash
cd front/gsw-api
npm i
npm run dev
```
