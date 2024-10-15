package gsw_api.gsw_api.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.model.Parametrizacao;
import gsw_api.gsw_api.model.PortalNoticia;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WebScrapingService {

    private Parametrizacao parametrizacao;

    public Parametrizacao getParametrizacaoByJSON(String JSON)
    {
        parametrizacao = new Parametrizacao(JSON);
        return parametrizacao;
    }

    public List<Noticia> ParametrizacaoToNoticia(PortalNoticia portal) throws IOException
    {
        List<Noticia> noticias = new ArrayList<>();
        getParametrizacaoByJSON(portal.getParametrizacao());

        Document doc = Jsoup.connect(portal.getUrl()).get();
        Elements newsHeadlines = doc.select(parametrizacao.getURL());
        for(Element headline : newsHeadlines)
        {
            Noticia noticia = new Noticia();
            Document noticiaHTML = Jsoup.connect(headline.absUrl("href")).get();
            noticia.setAutor(noticiaHTML.select(parametrizacao.getAutor()).text());
            noticia.setTitulo(noticiaHTML.select(parametrizacao.getTitulo()).text());
            noticia.setConteudo(noticiaHTML.select(parametrizacao.getConteudo()).text());
            String dataStr = noticiaHTML.select(parametrizacao.getData()).text();
            System.out.println(dataStr);
            if (dataStr.length() >= 10)
            {
                dataStr = dataStr.substring(0, 10).replace('/', '-');
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate localDate = LocalDate.parse(dataStr, formatter);
                noticia.setDataPublicacao(localDate);
            }
            
            noticias.add(noticia);
        }

        return noticias;
    }

}
