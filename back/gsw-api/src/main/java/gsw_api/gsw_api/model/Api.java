package gsw_api.gsw_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@Entity
@Table(name = "tb_api")
public class Api {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "payload", nullable = false)
    private String payload;

    @Column(name = "chave_acesso", columnDefinition = "TEXT", nullable = false)
    private String chaveAcesso;

    @Column(name = "url", length = 2048, nullable = false)
    private String url;

    @Column(name = "tipo", nullable = false)
    private String tipo;  // Nova coluna 'tipo'

    @Column(name = "periodicidade", nullable = false)
    private String periodicidade;  // Nova coluna 'periodicidade'

    @OneToMany(mappedBy = "api", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Noticia> noticias = new ArrayList<>();

    public Api() {}

    public Api(String nome, String url, String chaveAcesso, String payload, String tipo, String periodicidade) {
        this.nome = nome;
        this.url = url;
        this.chaveAcesso = chaveAcesso;
        this.payload = payload;
        this.tipo = tipo;
        this.periodicidade = periodicidade;
    }

    @Override
    public String toString() {
        return "Api{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", payload='" + payload + '\'' +
                ", chaveAcesso='" + chaveAcesso + '\'' +
                ", url='" + url + '\'' +
                ", tipo='" + tipo + '\'' +
                ", periodicidade='" + periodicidade + '\'' +
                '}';
    }
}
