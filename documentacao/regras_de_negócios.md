# Business rules for gsw project.

## Descrição

Este projeto tem como objetivo criar um sistema de gerenciamento de portais de notícias, permitindo a captura, organização e consulta de notícias de diversas fontes.

## Backlog

### Sprint 1

- **Como** um administrador, **eu quero** cadastrar portais de notícias, **para que** eu possa gerenciar a origem das notícias que serão consultadas e capturadas via scraping.
  
  Critérios de aceitação:
  
  - O sistema deve permitir o registro de nome e URL do portal.
  - Eu devo poder visualizar, editar e excluir portais cadastrados.

- **Como** um usuário final, **eu quero** acessar uma tela de consulta de notícias com filtros de pesquisa, **para que** eu possa encontrar notícias relevantes de forma rápida e fácil.
  
  Critérios de aceitação:
  
  - A tela deve permitir filtrar as notícias por título, data de publicação, autor e tags.
  - As notícias devem ser exibidas em formato de lista, com paginação.
  - Eu devo poder visualizar o conteúdo completo da notícia selecionada.

- **Como** um administrador, **eu quero** visualizar jornalistas responsáveis pelas publicações, **para que** eu possa associar as notícias a seus respectivos autores e manter um registro de jornalistas.

  Critérios de aceitação:
  
  - O sistema deve registrar o nome dos jornalistas automaticamente.
  - Eu devo poder visualizar os jornalistas cadastrados.

### Sprint 2

- **Como** um sistema automatizado, **eu quero** capturar os dados de notícias de portais e APIs cadastradas, **para que** as informações sejam armazenadas no banco de dados para posterior consulta e análise.
  
  Critérios de aceitação:
  
  - O sistema deve realizar web scraping periodicamente e de forma automatizada.
  - O sistema deve capturar título, conteúdo, data de publicação e autor das notícias.
  - Os dados capturados devem ser armazenados de forma estruturada no banco de dados.

- **Como** um administrador, **eu quero** cadastrar tags no sistema, **para que** eu possa organizar as notícias e APIs de acordo com tópicos ou categorias específicas.
  
  Critérios de aceitação:
  
  - O sistema deve permitir o cadastro de tags com nome.
  - Eu devo poder associar tags às notícias e APIs para melhorar a pesquisa.

### Sprint 3

- **Como** um administrador, **eu quero** cadastrar APIs externas no sistema, **para que** eu possa integrar dados externos no processo de captura de notícias e informações.
  
  Critérios de aceitação:
  
  - O sistema deve permitir o registro da URL da API e chave de acesso.
  - Eu devo poder visualizar, editar e excluir APIs cadastradas.

- **Como** um usuário, **eu quero** que o sistema sugira tags relacionadas com base no conteúdo das notícias, **para que** eu possa associar automaticamente as notícias às tags apropriadas e facilitar a pesquisa.
  
  Critérios de aceitação:
  
  - O sistema deve sugerir tags automaticamente com base no conteúdo da notícia capturada.
  - Eu devo poder visualizar e editar as tags sugeridas antes de confirmá-las.

### Sprint 4

- **Como** um administrador, **eu quero** acessar uma tela de consulta de APIs com filtros de pesquisa, **para que** eu possa gerenciar e encontrar facilmente as APIs cadastradas no sistema.
  
  Critérios de aceitação:
  
  - A tela deve permitir filtrar as APIs por nome ou data de cadastro.
  - As APIs devem ser exibidas em formato de lista, com paginação.
  - Eu devo poder visualizar os detalhes completos da API selecionada.