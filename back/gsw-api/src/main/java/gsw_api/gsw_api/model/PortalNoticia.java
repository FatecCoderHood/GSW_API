package gsw_api.gsw_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_portal_noticia")
public class PortalNoticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "parametrizacao")
    private String parametrizacao;

    @Column(name = "periodicidade")
    private String periodicidade;

    // Construtores
    public PortalNoticia() {}

    public PortalNoticia(String nome, String url, String parametrizacao, String periodicidade) {
        this.nome = nome;
        this.url = url;
        this.parametrizacao = parametrizacao;
        this.periodicidade = periodicidade;
    }
}
