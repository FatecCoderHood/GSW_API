package gsw_api.gsw_api.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativa")
    private Boolean ativa;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao = LocalDate.now();

    @Column(name = "cor")
    private String cor;

    // Novos campos para sinônimos
    @Column(name = "sinonimo1", length = 255)
    private String sinonimo1;

    @Column(name = "sinonimo2", length = 255)
    private String sinonimo2;

    public Tag() {

    }

    public Tag(String nome) {
        this.nome = nome;
        this.descricao = "";
        this.ativa = true;
        this.dataCriacao = LocalDate.now();
    }

    public Tag(String nome, String descricao, Boolean ativa, LocalDate dataCriacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativa = (ativa != null) ? ativa : true;
        this.dataCriacao = (dataCriacao != null) ? dataCriacao : LocalDate.now();
    }

    public Tag(String nome, String descricao, Boolean ativa, LocalDate dataCriacao, String sinonimo1, String sinonimo2) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativa = (ativa != null) ? ativa : true;
        this.dataCriacao = (dataCriacao != null) ? dataCriacao : LocalDate.now();
        this.sinonimo1 = sinonimo1;
        this.sinonimo2 = sinonimo2;
    }
}
