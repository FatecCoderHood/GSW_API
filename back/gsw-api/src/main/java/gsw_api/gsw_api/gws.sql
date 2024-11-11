CREATE DATABASE gwsapi;
USE gwsapi;

CREATE TABLE tb_portal_noticia(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100),
    url VARCHAR(255),
    categoria VARCHAR(45),
    parametrizacao TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE tb_noticia(
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255),
    conteudo TEXT,
    dta_publicacao DATETIME,
    autor VARCHAR(100),
    tags JSON,
    id_portal_noticia BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_portal_noticia) REFERENCES tb_portal_noticia(id)
);

CREATE TABLE tb_tag(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL UNIQUE,
    descricao VARCHAR(255),
    ativa BOOLEAN NOT NULL,
    data_criacao DATE,
    PRIMARY KEY (id)
);

CREATE TABLE tb_noticia_tag (
    noticia_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (noticia_id, tag_id),
    FOREIGN KEY (noticia_id) REFERENCES tb_noticia(id),
    FOREIGN KEY (tag_id) REFERENCES tb_tag(id)
);

CREATE TABLE tb_api (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    chave_acesso TEXT NOT NULL,
    url VARCHAR(2048),
    nome VARCHAR(100) NOT NULL,
    payload VARCHAR(255)
);

ALTER TABLE tb_noticia
    ADD COLUMN api_id BIGINT;

ALTER TABLE tb_noticia
    ADD CONSTRAINT fk_noticia_api
    FOREIGN KEY (api_id) REFERENCES tb_api(id) ON DELETE CASCADE;

INSERT INTO tb_api (nome, payload, chave_acesso, url)
VALUES ('Nome da API', 'Conteúdo do payload', 'sua-chave-de-acesso', 'https://suaapi.com');

INSERT INTO tb_portal_noticia (nome, url, parametrizacao) VALUES
('UOL', 'https://www.uol.com.br/', NULL),
('Yahoo', 'https://news.yahoo.com/', NULL),
('INFOMONEY', 'https://www.infomoney.com.br/', NULL),
('G1', 'https://g1.globo.com/', NULL),
('Roca News', 'https://www.roca.com/about-roca/news', NULL),
('The New York Times', 'https://www.nytimes.com/international/', NULL),
('Central Grama', 'https://centraldagrama.com/gramas', NULL),
('Terra', 'https://www.terra.com.br/', NULL),
('CNN', 'https://www.cnnbrasil.com.br/', NULL),
('Notícias Agrícolas', 'https://www.noticiasagricolas.com.br/', NULL),
('BBC', 'https://www.bbc.com/', NULL);

UPDATE tb_portal_noticia SET parametrizacao = 
'{"URL": "h1.text-3xl.md:text-5xl.font-bold.tracking-tighter.text-wl-neutral-950 a","titulo": "h1.text-3xl.md:text-5xl.font-bold.tracking-tighter.text-wl-neutral-950","conteudo": "div.text-lg.md:text-xl.font-medium.tracking-tight.text-wl-neutral-600","data": "time[datetime]"}'
WHERE id = 6;

INSERT INTO tb_portal_noticia (nome, url, parametrizacao)
VALUES ('PODER 360', 'https://www.poder360.com.br/', '{"URL": "div.sectionGrid__grid__columnTwo article a","titulo": "h1.title","conteudo": "p.bullet","autor": "a.solar-author-name","data": "div.solar-date time"}');

SET SQL_SAFE_UPDATES = 0;

UPDATE tb_portal_noticia 
SET nome = 'BBCBRASIL', 
    url = 'https://www.bbc.com/', 
    parametrizacao = '{"URL": "div._evt h2 a","titulo": "h1.content-head__title","conteudo": "p.content-text__container","autor": "p.content-publication-data__from","data": "p.content-publication-data__updated time"}'
WHERE id = 5;

INSERT INTO tb_portal_noticia (nome, url, parametrizacao)
VALUES ('CNN Brasil', 'https://www.cnnbrasil.com.br/', '{"URL": "h1.single-header__title","titulo": "h1.single-header__title","conteudo": "p.single-header__excerpt","autor": "p.author__name a","data": "time.single-header__time"}');

SET SQL_SAFE_UPDATES = 1;

UPDATE tb_portal_noticia SET parametrizacao = 
'{"URL": "div.sectionGrid__grid__columnTwo article a","titulo": "h1.title","conteudo": "p.bullet","autor": "a.solar-author-name","data": "div.solar-date time"}'
WHERE id = 1;

INSERT INTO tb_noticia (titulo, conteudo, dta_publicacao, autor) VALUES
('Mandioca é um alimento versátil', 'A mandioca, também conhecida como aipim, é muito utilizada na culinária brasileira.', '2024-11-01', 'Autor A'),
('Receitas com aipim', 'Aprenda a fazer deliciosas receitas com aipim.', '2024-10-30', 'Autor B'),
('Benefícios da mandioca', 'A mandioca é rica em carboidratos e traz muitos benefícios para a saúde.', '2024-10-29', 'Autor C');

ALTER TABLE tb_portal_noticia
ADD COLUMN periodicidade VARCHAR(255) NULL;

SELECT * FROM tb_portal_noticia;