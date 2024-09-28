
create database gwsapi;
use gwsapi;
 
CREATE TABLE tb_portal_noticia(
id BIGINT NOT NULL auto_increment,
nome varchar(100),
url varchar(255),
categoria varchar(45),
parametrizacao varchar(255),
primary key (id)
);
 
CREATE TABLE tb_noticia(
id BIGINT NOT NULL auto_increment,
titulo varchar (255),
conteudo text,
dta_publicacao datetime,
autor varchar(100),
tags JSON,
id_portal_noticia bigint,
primary key (id),
FOREIGN KEY (id_portal_noticia) REFERENCES tb_portal_noticia(id)
);
 
CREATE TABLE tb_tag(
id BIGINT NOT NULL auto_increment,
nome VARCHAR(255) NOT NULL UNIQUE,
descricao VARCHAR(255),
ativa BOOLEAN NOT NULL,
data_criacao DATE,
primary key (id)
);
 
CREATE TABLE tb_noticia_tag (
    noticia_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (noticia_id, tag_id),
    FOREIGN KEY (noticia_id) REFERENCES tb_noticia(id),
    FOREIGN KEY (tag_id) REFERENCES tb_tag(id)
);
 
 
alter table tb_portal_noticia drop column categoria;