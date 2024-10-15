package gsw_api.gsw_api.model;

import com.google.gson.Gson;
import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.model.PortalNoticia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

public class Parametrizacao {
    @Autowired
    private String URL;
    private String titulo;
    private String conteudo;
    private String autor;
    private String data;

    // String json = "{"
    //         + "'URL': 'Computing and Information systems',"
    //         + "'titulo' : 'idkT',"
    //         + "'conteudo' : 'idkC',"
    //         + "'autor' : 'idkA',"
    //         + "'data' : 'idkD'"
    //         + "}";

    public Parametrizacao(String JSON) {
        Parametrizacao p = new Gson().fromJson(JSON, Parametrizacao.class);
        this.URL = p.URL;
        this.autor = p.autor;
        this.conteudo = p.conteudo;
        this.data = p.data;
        this.titulo = p.titulo;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // public List<Noticia> ParametrizacaoToNoticia(PortalNoticia portal) throws IOException
    // {
    //     List<Noticia> noticias = new ArrayList<>();

    //     Document doc = Jsoup.connect(portal.getUrl()).get();
    //     Elements newsHeadlines = doc.select(this.URL);
    //     for(Element headline : newsHeadlines)
    //     {
    //         Noticia noticia = new Noticia();
    //         noticia.setTitulo(headline.text());
    //         noticias.add(noticia);
    //     }

    //     return noticias;
    // }

    
}
