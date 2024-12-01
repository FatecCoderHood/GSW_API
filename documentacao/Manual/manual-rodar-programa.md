
# Pré-requisitos

Certifique-se de ter concluído todas as etapas descritas no manual de instalação antes de prosseguir.

## Instruções

Se você já completou as etapas do manual de instalação, siga os passos abaixo:

1. Abra o projeto GSW_API no Visual Studio Code.

2. Abra o terminal do Visual Studio Code. Existem três opções para abrir o terminal:
    - Atalho: `Ctrl + ``
    - Menu: Selecione **Ver** > **Terminal** (em inglês: **View** > **Terminal**)
    - Menu: Selecione **Terminal** > **Novo Terminal** (em inglês: **Terminal** > **New Terminal**)

3. Após abrir o terminal, digite o seguinte comando:

## Instruções de Execução

### Database

Execute o script abaixo para criação das tabelas no seu DBMS ou SGBD em português:
```sh
./back/gsw-ap/src/main/java/gsw_api/gsw_api/gws.sql
```

### Back-end

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

## Funcionamento da Aplicação

Para entender mais sobre o funcionamento do programa GSW, [clique aqui](manual-rodar-programa).

