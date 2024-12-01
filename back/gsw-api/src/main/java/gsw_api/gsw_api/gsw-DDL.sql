-- Criação do Banco de Dados
CREATE DATABASE gswapi;
USE gswapi;

-- Drop all tables to guarantee a correct creation
DROP TABLE IF EXISTS tb_noticia_tag;
DROP TABLE IF EXISTS tb_tag;
DROP TABLE IF EXISTS tb_noticia;
DROP TABLE IF EXISTS tb_api;
DROP TABLE IF EXISTS tb_portal_noticia;

-- Criação da tabela tb_portal_noticia
CREATE TABLE tb_portal_noticia (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100),
    url VARCHAR(255),
    parametrizacao VARCHAR(255),
    periodicidade VARCHAR(255) NULL,
    tipo VARCHAR(50),
    PRIMARY KEY (id)
);

-- Criação da tabela tb_tag
CREATE TABLE tb_tag (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL UNIQUE,
    descricao VARCHAR(255),
    ativa BOOLEAN NOT NULL,
    cor VARCHAR(255),
    data_criacao DATE,
    sinonimo1 VARCHAR(100),  
    sinonimo2 VARCHAR(100),  
    PRIMARY KEY (id)
);

-- Criação da tabela tb_api
CREATE TABLE tb_api (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    payload VARCHAR(255),
    chave_acesso TEXT NOT NULL,
    url VARCHAR(2048),
    periodicidade VARCHAR(255),
    tipo VARCHAR(50),
    PRIMARY KEY (id)
);

-- Criação da tabela tb_noticia
CREATE TABLE tb_noticia (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255),
    conteudo TEXT,
    dta_publicacao DATETIME,
    autor VARCHAR(512),
    tags JSON,
    fonte VARCHAR(100),
    id_portal_noticia BIGINT,
    api_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (id_portal_noticia) REFERENCES tb_portal_noticia(id),
    FOREIGN KEY (api_id) REFERENCES tb_api(id) ON DELETE CASCADE
);

-- Tabela de relacionamento entre notícias e tags
CREATE TABLE tb_noticia_tag (
    noticia_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (noticia_id, tag_id),
    FOREIGN KEY (noticia_id) REFERENCES tb_noticia(id),
    FOREIGN KEY (tag_id) REFERENCES tb_tag(id)
);
