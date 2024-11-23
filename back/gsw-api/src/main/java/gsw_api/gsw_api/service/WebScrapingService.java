package gsw_api.gsw_api.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.model.Parametrizacao;
import gsw_api.gsw_api.model.PortalNoticia;
import gsw_api.gsw_api.model.Tag;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScrapingService {

    private static final Logger logger = LoggerFactory.getLogger(WebScrapingService.class);
    private Parametrizacao parametrizacao;
    public List<Tag> tagList = new ArrayList<>();

    public Parametrizacao getParametrizacaoByJSON(String JSON) {
        parametrizacao = new Parametrizacao(JSON);
        return parametrizacao;
    }

    public List<Noticia> gerarNoticiasByPortais(List<PortalNoticia> portais) throws IOException {
        List<Noticia> noticiasGerais = new ArrayList<>();

        for (PortalNoticia p : portais) {
            boolean isVazio = p.getParametrizacao() == null || p.getParametrizacao().isEmpty();
            if (!isVazio) {
                List<Noticia> noticiasPortal = parametrizacaoToNoticia(p);
                noticiasGerais.addAll(noticiasPortal);
            }
        }
        return noticiasGerais;
    }

    public List<Noticia> parametrizacaoToNoticia(PortalNoticia portal) throws IOException {

        List<Noticia> noticias = new ArrayList<>();
        Parametrizacao parametrizacao = getParametrizacaoByJSON(portal.getParametrizacao());

        Document doc = Jsoup.connect(portal.getUrl()).get();
        Elements noticiasHTML = doc.select(parametrizacao.getURL());

        for (Element headline : noticiasHTML) {
            boolean isVazio = headline.absUrl("href") == null || headline.absUrl("href").isEmpty();
            if (!isVazio) {
                try {
                    Noticia noticia = new Noticia();
                    Document noticiaHTML = Jsoup.connect(headline.absUrl("href")).get();

                    String titulo = noticiaHTML.select(parametrizacao.getTitulo()).text();
                    String conteudo = noticiaHTML.select(parametrizacao.getConteudo()).text();
                    String autor = noticiaHTML.select(parametrizacao.getAutor()).text();
                    String dataStr = noticiaHTML.select(parametrizacao.getData()).text();

                    boolean isDuplicada = noticias.stream()
                        .anyMatch(n -> n.getTitulo().equals(titulo) && n.getConteudo().equals(conteudo));

                    if (!titulo.isEmpty() && !conteudo.isEmpty() && !autor.isEmpty() && !isDuplicada) {
                        noticia.setTitulo(titulo);
                        noticia.setConteudo(conteudo);
                        noticia.setAutor(autor);

                        noticia.setFonte("Portal");  

                        noticia.setPortalNoticia(portal);

                        if (dataStr.length() >= 10) {

                            try {
                                dataStr = dataStr.substring(0, 10).replace('/', '-');
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                LocalDate localDate = LocalDate.parse(dataStr, formatter);
                                noticia.setDataPublicacao(localDate);
                            } catch (Exception e) {
                                // TODO: handle exception
                            }

                        }
                        noticia.getTags().addAll(AssociarTagsScraping(conteudo));
                        noticias.add(noticia);
                    } else if (isDuplicada) {
                        logger.warn("Notícia duplicada ignorada: " + titulo + " - " + autor);
                    } else {
                        logger.warn("Notícia ignorada por falta de informações obrigatórias: Título, Autor ou Conteúdo.");
                    }
                } catch (IOException e) {
                    logger.error("Erro ao acessar a notícia: " + headline.absUrl("href"), e);
                }
            }
        }
        return noticias;
    }

    private List<Tag> AssociarTagsScraping(String conteudo){     
        List<Tag> newTags = new ArrayList<>();

        for (Tag tag : tagList)
        {
            if (conteudo.contains(tag.getNome())) {
                newTags.add(tag);
            }
        }

        return newTags;
    }
}
