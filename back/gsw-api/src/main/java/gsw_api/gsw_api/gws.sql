
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

CREATE TABLE tb_api (
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  documentacao VARCHAR(255) NOT NULL,
  chave_acesso TEXT NOT NULL,
  active BOOLEAN NOT NULL,
  url VARCHAR(2048)
);

alter table tb_api add column nome varchar(100) not null;

ALTER TABLE tb_api
DROP COLUMN documentacao;

ALTER TABLE tb_api
ADD COLUMN payload VARCHAR(255);

ALTER TABLE tb_noticia
    ADD COLUMN api_id BIGINT;

ALTER TABLE tb_noticia
    ADD CONSTRAINT fk_noticia_api
        FOREIGN KEY (api_id) REFERENCES tb_api(id) ON DELETE CASCADE;

alter table tb_api
    drop column active;


INSERT INTO tb_api (nome, payload, chave_acesso, url)
VALUES ('Nome da API', 'Conteúdo do payload', 'sua-chave-de-acesso', 'https://suaapi.com');

SELECT * FROM tb_api;

update tb_portal_noticia set parametrizacao = 
        "{'URL': 'div._evt h2 a','titulo' : 'h1.content-headtitle','conteudo' : 'p.content-textcontainer','autor' : 'p.content-publication-datafrom','data' : 'p.content-publication-dataupdated time'}"
where id = 3;

update tb_portal_noticia set parametrizacao = 
        "{'URL': 'div.sectionGridgridcolumnTwo article a','titulo' : 'h1.title','conteudo' : 'p.bullet','autor' : 'a.solar-author-name','data' : 'div.solar-date time'}"
 where id = 1;

 


