package gsw_api.gsw_api.model;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

public class Parametrizacao {
    @Autowired
    private String URL;
    private String titulo;
    private String conteudo;
    private String autor;
    private String data;

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
}
