# <p align="center"> Manual de Configuração da aplicação  

## Pré-requisitos

Certifique-se de ter concluído todas as etapas descritas no manual de instalação antes de prosseguir.

## Instruções

Se você já completou as etapas do manual de instalação, siga os passos abaixo:  

# Configuração do banco de dados no MySQL  

### 1. Abrir o MySQL e inicializar o banco local  

![SQL 1](https://github.com/user-attachments/assets/3eca5f65-1e7d-4b6f-9a44-db79544c47f4)  

### 2. Copiar o código SQL na aplicação  
- Copiar o código que está no GSW_API > back\gsw-api > src > main > java\gsw_api\gsw_api > gsw-DDL.sql
  

![SQL2](https://github.com/user-attachments/assets/13fd1e7b-981c-4acb-b2b6-25456cffabfc)  

### 3. Criação do banco de dados da aplicação  
- Segue imagem com os passos necessários para a criação do banco de dados da aplicação
  
![SQL 3](https://github.com/user-attachments/assets/025d9a42-a883-44e2-9433-48fb8d3f27f3)  

## Sugestão de API's e portais
- Na pasta DML (GSW_API > back\gsw-api > src > main > java\gsw_api\gsw_api > gsw-DDL.sql) tem sugestões de inserts a serem realizados no banco
    
![SQL 4](https://github.com/user-attachments/assets/4a9d1e71-71ea-49f8-8642-5dfb3144447e)  

# Instruções de como inicializar a aplicação no VSCode  

### 1. Abra o projeto GSW_API no Visual Studio Code.

### 2. Abra o terminal do Visual Studio Code. Existem três opções para abrir o terminal:
- Atalho: `Ctrl + ``
- Menu: Selecione **Ver** > **Terminal** (em inglês: **View** > **Terminal**)
      
![execução1](https://github.com/user-attachments/assets/fbda0543-37cd-449b-9a6d-675a731686af)  

### 3. Após abrir o terminal, digite os seguintes comandos:  

## Back-end

```sh
cd back/gsw-api
mvn clean install
mvn spring-boot:run
```

### Front-end

```sh
cd front/gsw-api
npm install
npm run dev
```

Clique na porta 3000 para acessar a aplicação.  

![execução2](https://github.com/user-attachments/assets/b6b80482-997c-43eb-91aa-54a7af6a41f1)  

## Funcionamento da Aplicação

Para entender mais sobre o funcionamento do programa GSW, [Clique aqui para ir à próxima etapa](https://github.com/FatecCoderHood/GSW_API/blob/manual-criacao/documentacao/Manual/APLICACAO-RODANDO.md)


