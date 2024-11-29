-- Inserção de dados iniciais na tabela tb_portal_noticia
INSERT INTO tb_portal_noticia (nome, url, parametrizacao, periodicidade, tipo) VALUES
('UOL', 'https://www.uol.com.br/', "{'URL': 'div._evt h2 a','titulo' : 'h1.content-head__title','conteudo' : 'p.content-text__container','autor' : 'p.content-publication-data__from','data' : 'p.content-publication-data__updated time'}", NULL, 'Portal'),
('Veja', 'https://veja.abril.com.br', "{'URL': 'div.col-s-12 a','titulo' : 'h1.title','conteudo' : 'section.content p span','autor' : 'span.author strong span','data' : 'span.date-post'}", NULL, 'Portal'),
('Estadão', 'https://www.estadao.com.br', "{'URL': 'div.noticia-content-block a','titulo' : 'div.container-news-informs h1','conteudo' : 'p.styles__ParagraphStyled-sc-6adecn-0','autor' : 'div.names a','data' : 'div.principal-dates span time'}", NULL, 'Portal'),
('INFOMONEY', 'https://www.infomoney.com.br/', "{'URL': 'h2.font-im-sans a','titulo' : 'h1.text-3xl','conteudo' : 'article.im-article p','autor' : 'a.text-base','data' : 'p.im-mob-core-description time'}", NULL, 'Portal'),
('Gazeta', 'https://www.gazetadopovo.com.br', "{'URL': 'a.link-hover-tertiary-gray','titulo' : 'h1.post-title','conteudo' : 'div.wrapper p','autor' : 'a.gtm-link-autor','data' : 'ul.wgt-date li'}", NULL, 'Portal'),
('Terra', 'https://www.terra.com.br/', NULL, NULL, 'Portal'),
('BrasildeFato', 'https://www.brasildefato.com.br', NULL, NULL, 'Portal'),
('G1', 'https://g1.globo.com/', "{'URL': 'div.sectionGrid__grid__columnTwo article a','titulo' : 'h1.title','conteudo' : 'p.bullet','autor' : 'a.solar-author-name','data' : 'div.solar-date time'}", NULL, 'Portal');

-- Inserção de dados iniciais na tabela tb_api
INSERT INTO tb_api (`chave_acesso`, `url`, `nome`, `payload`, `periodicidade`, `tipo`) VALUES 
('', 'https://official-joke-api.appspot.com/jokes/ten', 'Piada', '{"titulo": "setup", "conteudo": "punchline"}', 'Semanal', 'API'),
('', 'https://jsonplaceholder.typicode.com/posts?userId=1', 'jsonplaceholder', '{"titulo": "title", "conteudo": "body"}', 'Semanal', 'API'),
('', 'https://potterapi-fedeperin.vercel.app/pt/spells?max=10', 'Feitiços', '{"titulo": "spell", "conteudo": "use"}', 'Semanal', 'API');