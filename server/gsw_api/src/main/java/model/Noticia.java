package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
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

}
