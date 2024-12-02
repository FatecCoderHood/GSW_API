# <p align="center"> Manual de busca dos parâmtros para scraping  

## Como Extrair Parâmetros dos Portais Desejados

### Exemplo: G1 (https://g1.globo.com/)

- Será necessário extrair os seguintes parâmetros: título, subtítulo, autor, data e corpo da notícia.

### Passos:

## 1. Título  

### 1.1. Pressione F12 no teclado.  

![scraping1](https://github.com/user-attachments/assets/2595da24-43e2-4d14-b918-0e6fbb7d7687)  


### 1.2. Na página de inspeção de elementos do navegador, clique no ícone de seleção por tag.  

![scraping2](https://github.com/user-attachments/assets/412d047e-d290-43fd-9db9-9abb9a1690e3)  

### 1.3. Clique no texto do título desejado.  

### 1.4. Clique com o botão direito do mouse e selecione Copy > Copy OuterHtml.  

![scraping3](https://github.com/user-attachments/assets/e3c544c9-8e0a-4042-bca9-18b2d9ed49fe)  

## 2. Autor  

### 2.1. Pressione F12 no teclado.  

![scraping1](https://github.com/user-attachments/assets/2595da24-43e2-4d14-b918-0e6fbb7d7687)  

### 2.2. Na página de inspeção de elementos do navegador, clique no ícone de seleção por tag.  

### 2.3. Clique no texto do autor desejado.  

### 2.4. Clique com o botão direito do mouse e selecione Copy > Copy OuterHtml.  

![scraping4](https://github.com/user-attachments/assets/d30b7c28-5a7e-41d7-8463-705b8b40f0ad)  

## 3. Data  

### 3.1. Pressione F12 no teclado.  

![scraping1](https://github.com/user-attachments/assets/2595da24-43e2-4d14-b918-0e6fbb7d7687)  

### 3.2. Na página de inspeção de elementos do navegador, clique no ícone de seleção por tag.  

### 3.3. Clique no texto do autor desejado.  

### 3.4. Clique com o botão direito do mouse e selecione Copy > Copy OuterHtml.  

![scraping5](https://github.com/user-attachments/assets/d6824255-1555-48b9-bca3-ffccb7d93c96)  

## Como Extrair Parâmetros das API's Desejadas

## Passo 1: Entendendo os Dados Retornados pela API  

Acesse o endpoint que você deseja consumir. No nosso caso, usamos:
https://official-joke-api.appspot.com/jokes/ten
Este endpoint retorna um array de objetos no seguinte formato:  


![webapi1](https://github.com/user-attachments/assets/6e8eb30e-0ed7-4ff1-b5ca-1443e54a9ba7)

- Cada objeto contém:

setup: Uma introdução ou pergunta, geralmente usada como título.
punchline: A resposta ou desfecho, usada como conteúdo.
type e id: Informações adicionais que podem ou não ser relevantes para sua aplicação.  


## Passo 2: Identificando os Campos Importantes
Título: Normalmente, o primeiro campo textual de interesse. Neste caso, o campo setup.
Conteúdo: Geralmente o campo que complementa o título e tem maior quantidade de texto ou significado direto. Aqui, o campo punchline.  


## Passo 3: Definindo o Padrão de Payload
O objetivo é transformar os dados retornados em um padrão que sua aplicação entenda. Vamos usar o seguinte formato para nossa aplicação:  


'{"titulo": "setup", "conteudo": "punchline"}'  

![apiscraping2](https://github.com/user-attachments/assets/6cfd428d-8e00-4662-8779-383f866e01d6)  


No exemplo acima:  


setup será mapeado para titulo.
punchline será mapeado para conteudo.  

## Passo 4: Transformando os Dados  

Agora, ajustamos os dados retornados para o formato desejado:  



![api2](https://github.com/user-attachments/assets/dd194790-f0a0-475e-bdf4-cd2343cf6e11)  


## Considerações Finais  


Sempre analise a estrutura de dados retornada pela API antes de definir seu padrão.  


Agora você está pronto para consumir qualquer API, identificar os campos relevantes e transformá-los para o padrão que sua aplicação necessita!  



