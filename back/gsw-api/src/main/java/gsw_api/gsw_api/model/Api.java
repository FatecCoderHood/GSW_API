package gsw_api.gsw_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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




    @OneToMany(mappedBy = "api", cascade = CascadeType.ALL)
    private List<Noticia> noticias;


    public Api() {}

    public Api(String nome, String url, String chaveAcesso, String payload) {
		this.nome = nome;
		this.url = url;
        this.chaveAcesso = chaveAcesso;
        this.payload = payload;
	}

    @Override
    public String toString() {
        return "Api{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", payload='" + payload + '\'' +
                ", chaveAcesso='" + chaveAcesso + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}