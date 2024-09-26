package gsw_api.gsw_api.model;

import java.time.LocalDate;
import java.util.UUID;

public class Noticia {

    private String idNoticia;
    private String titulo;
    private String conteudo;
    private LocalDate dataPublicacao;
    private String autor;
    private String portalNoticiaId;
    
    public Noticia (String titulo, String conteudo, LocalDate dataPublicacao, String autor, String portalNoticiaId) {

        this.idNoticia = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.autor = autor;
        this.portalNoticiaId = portalNoticiaId;
    }
    public String getIdNoticia() {
        return idNoticia;
    }
    public void setIdNoticia(String idNoticia) {
        this.idNoticia = idNoticia;
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
    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }
    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getPortalNoticiaId() {
        return portalNoticiaId;
    }
    public void setPortalNoticiaId(String portalNoticiaId) {
        this.portalNoticiaId = portalNoticiaId;
    }
}
