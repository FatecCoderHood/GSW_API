CREATE DATABASE gwsapi;
USE gwsapi;

CREATE TABLE tb_portal_noticia(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100),
    url VARCHAR(255),
    parametrizacao TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE tb_api (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    chave_acesso TEXT NOT NULL,
    url VARCHAR(2048),
    nome VARCHAR(100) NOT NULL,
    payload VARCHAR(255)
);

CREATE TABLE tb_noticia(
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255),
    conteudo TEXT,
    dta_publicacao DATETIME,
    autor VARCHAR(100),
    tags JSON,
    id_portal_noticia BIGINT,
    api_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_portal_noticia) REFERENCES tb_portal_noticia(id),
    FOREIGN KEY (api_id) REFERENCES tb_api(id) ON DELETE CASCADE
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

INSERT INTO tb_noticia (titulo, conteudo, dta_publicacao, autor) VALUES 
('Desafios do agronegócio brasileiro', 'Os principais desafios enfrentados pelo setor atualmente...', CURRENT_DATE, 'Ricardo Almeida'),
('Sustentabilidade no campo', 'Práticas sustentáveis estão se tornando essenciais para o futuro...', CURRENT_DATE, 'Fernanda Costa'),
('O papel da tecnologia no agronegócio', 'Como a tecnologia está transformando o setor agrícola...', CURRENT_DATE, 'Lucas Mendes'),
('Culturas de inverno em ascensão', 'As culturas de inverno estão se mostrando cada vez mais rentáveis...', CURRENT_DATE, 'Tatiane Lima'),
('O futuro da agricultura digital', 'Tendências e inovações na agricultura digital...', CURRENT_DATE, 'Felipe Rocha'),
('Mercado de orgânicos cresce no Brasil', 'O consumo de produtos orgânicos está aumentando no país...', CURRENT_DATE, 'Gabriela Martins'),
('Desenvolvimento rural sustentável', 'Importância do desenvolvimento rural sustentável para o Brasil...', CURRENT_DATE, 'Eduardo Silva'),
('O impacto das políticas agrícolas', 'Como as políticas públicas afetam o agronegócio...', CURRENT_DATE, 'Patrícia Ferreira'),
('Avanços na biotecnologia agrícola', 'Novas técnicas de biotecnologia estão mudando o setor...', CURRENT_DATE, 'André Santos'),
('Como o clima afeta a produção agrícola', 'Estudo sobre os efeitos das mudanças climáticas na agricultura...', CURRENT_DATE, 'Juliana Almeida'),
('Tendências em agroindústria', 'As novas tendências que estão moldando a agroindústria...', CURRENT_DATE, 'Bruno Gomes'),
('Mercado de café em alta', 'O mercado de café está se expandindo em nível global...', CURRENT_DATE, 'Clara Pires');

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

INSERT INTO tb_api (nome, payload, chave_acesso, url)
VALUES ('Nome da API', 'Conteúdo do payload', 'sua-chave-de-acesso', 'https://suaapi.com');

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