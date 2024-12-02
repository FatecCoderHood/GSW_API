# <p align="center"> Manual de Instalação do GSW

## Requisitos Mínimos

- Java 11 ou mais recente
- Git
- Visual Studio Code

### Sistemas Operacionais Suportados

**Windows**
- Windows 10 ou mais recente

**Linux**
- Ubuntu/Debian 18.04 ou mais recente

**Mac**
- MacOS 10.14 ou mais recente

### Espaço Livre no HD ou SSD

- Mínimo de 2,7 GB de espaço livre

## Instalação do Git

### Windows/Linux/Mac

### 1. Acesse o link correspondente ao seu sistema operacional:  

- [Windows](https://git-scm.com/download/win)
- [Linux](https://git-scm.com/download/linux)
- [Mac](https://git-scm.com/download/mac)

### 2. Selecione a versão Standalone Installer, escolhendo entre 64 bits ou 32 bits conforme a arquitetura do seu computador:  

- **Windows:** Abra o prompt de comando e digite `systeminfo`
- **Linux:** Abra o terminal e digite `uname -m`
- **Mac:** Abra o terminal e digite `uname -m`

### 3. Clique em Download e siga as instruções de instalação.  

   
![Instalação do Git](IMAGENS/001.GIT-INSTALL.png)



## Instalação do Visual Studio Code

### 1. Acesse o site do [Visual Studio Code](https://code.visualstudio.com/) e baixe a versão correspondente ao seu sistema operacional.  

![Selecionando SO no VSCode](IMAGENS/001-ESCOLHER-SO-VSCODE.png)

### 2. Siga as instruções de instalação.

## Clonando o Repositório do Projeto GSW_API

### 1. Abra o Visual Studio Code.  

### 2. Clique em "Clone Repository" e cole o link do repositório:  

- https://github.com/FatecCoderHood/GSW_API.git  

![Clonando Repositório](IMAGENS/001.CLONAR-REPOSITORIO-VIA-VSCODE.png)

## Instalação do Node.js

### 1. Baixe e instale o Node.js pelo site: [Node.js Download](https://nodejs.org/pt/download/)  

![Instalação do Node.js](IMAGENS/001.NODE-JS.png)

### 2. Aceite a licença de instalação.  

![Aceitando Licença do Node.js](IMAGENS/02.NODE-JS.png)

## Instalação do Java

### 1. Baixe e instale o Java pelo site: [Java Download](https://www.java.com/pt_BR/download/)  

![Instalação do Java](IMAGENS/001-img-install-java.png)

### 2. Aceite a licença de instalação e clique em "Next".  

![Aceitando Licença do Java](IMAGENS/ACEITAR-LICENCA-JAVA.png)

## Configuração de Variáveis de Ambiente  


### 1. Abra o Menu Iniciar e procure por "variáveis de ambiente".  
![variaveis do ambiente](https://github.com/user-attachments/assets/52857ddf-1b36-4ebe-824a-c986f3c03a22)  

### 2. Clique em "Editar as variáveis de ambiente do sistema".  

![variaveis do ambiente 3](https://github.com/user-attachments/assets/d1a95594-ad8a-41b3-9e82-d45e7f7bf499)  

### 3. Em “Variáveis do sistema”, selecione Path e adicione o caminho para a pasta `bin` dentro da instalação do Java 
(exemplo: C:\Program Files\Java\jdk-xx.x.x_xx\bin).  

![variaveis do ambiente 4](https://github.com/user-attachments/assets/3a941798-d52f-419a-a278-97983ba5a0b0)  

### 4. Clique em ok e aplique a alteração.  

## Instalação do MySQL

### 1. Baixe e instale o MySQL pelo site: [MySQL Download](https://dev.mysql.com/downloads/installer/)  

![Instalação do MySQL](IMAGENS/001.INSTALL-MYSQL.png)

### 2. Escolha a versão correta para o seu sistema operacional e siga as instruções de instalação.  

![Instalação do MySQL](IMAGENS/imagem_msql_02.webp)

### 3. Crie um usuário e senha para o MySQL:  

- **Usuário:** root
- **Senha:** root
  
    ## Alterando a Senha no Projeto
   
Se você já possui um usuário e uma senha personalizados configurados no seu banco de dados, será necessário atualizar a senha diretamente no projeto. Siga os passos abaixo:  
- Navegue até o arquivo de configuração, localizado no seguinte caminho:
  C:\Users\aline\Documents\FATEC-3-BD\GSW_API\back\gsw-api\src\main\resources\application-dev.properties
- Localize a linha que contém as informações do banco de dados, semelhante à imagem abaixo:
  

![trocar senha do Mysql no projeto](https://github.com/user-attachments/assets/c13ee9b9-d9fc-4fae-ab60-f794e1083559)  

- Atualize o valor da senha (e o usuário, caso necessário), respeitando o formato e a estrutura existente no arquivo.
- Salve o arquivo e feche o editor.


### 4. Clique em "Next" e finalize a instalação.  

![Finalizando Instalação do MySQL](IMAGENS/imagem_msql_09.png)

## Próxima Etapa

Instalação finalizada.  
**Próxima etapa:** [Clique aqui para ir à próxima etapa](https://github.com/FatecCoderHood/GSW_API/blob/manual-criacao/documentacao/Manual/manual-rodar-programa.md)

