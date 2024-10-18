package gsw_api.gsw_api.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.model.Parametrizacao;
import gsw_api.gsw_api.model.PortalNoticia;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScrapingService {

    private Parametrizacao parametrizacao;

    public Parametrizacao getParametrizacaoByJSON(String JSON) {
        parametrizacao = new Parametrizacao(JSON);
        return parametrizacao;
    }

    //Rotina que itera pelos portais do banco e gerando o vetor de noticias portal por portal
    public List<Noticia> gerarNoticiasByPortais(List<PortalNoticia> portais) throws IOException {
        //Lista de TODAS noticias que serão inseridas no banco independente do portal
        List<Noticia> noticiasGerais = new ArrayList<>();

        for (PortalNoticia p : portais) {

            boolean isVazio = p.getParametrizacao() == null || p.getParametrizacao().isEmpty();
            if (!isVazio) {
                //Lista de todas noticias do portal que esta sendo iterado atualmente
                List<Noticia> noticiasPortal = new ArrayList<>();

                noticiasPortal = parametrizacaoToNoticia(p);
                noticiasGerais.addAll(noticiasPortal);
            }
        }
        return noticiasGerais;
    }

    //Rotina para retornar todas as noticias de um portal via webscraping
    public List<Noticia> parametrizacaoToNoticia(PortalNoticia portal) throws IOException {

        List<Noticia> noticias = new ArrayList<>();
        getParametrizacaoByJSON(portal.getParametrizacao());

        Document doc = Jsoup.connect(portal.getUrl()).get();
        Elements noticiasHTML = doc.select(parametrizacao.getURL());

        for (Element headline : noticiasHTML) {
            boolean isVazio = headline.absUrl("href") == null || headline.absUrl("href").isEmpty();
            if (!isVazio) {

                Noticia noticia = new Noticia();
                Document noticiaHTML = Jsoup.connect(headline.absUrl("href")).get();
                noticia.setAutor(noticiaHTML.select(parametrizacao.getAutor()).text());
                noticia.setTitulo(noticiaHTML.select(parametrizacao.getTitulo()).text());
                noticia.setConteudo(noticiaHTML.select(parametrizacao.getConteudo()).text());
                String dataStr = noticiaHTML.select(parametrizacao.getData()).text();
               
                if (dataStr.length() >= 10) {
                    dataStr = dataStr.substring(0, 10).replace('/', '-');
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate localDate = LocalDate.parse(dataStr, formatter);
                    noticia.setDataPublicacao(localDate);
                }

                noticias.add(noticia);
            }
        }
        return noticias;
    }

}
