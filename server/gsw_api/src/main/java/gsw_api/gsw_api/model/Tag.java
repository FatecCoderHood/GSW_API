package gsw_api.gsw_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "ativa", nullable = false)
    private Boolean ativa;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @ManyToMany(mappedBy = "tags")
    @JsonManagedReference
    private Set<Noticia> noticias = new HashSet<>();

    public Tag() {
        // Construtor padrão
    }

    public Tag(String nome, String descricao, Boolean ativa, LocalDate dataCriacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativa = ativa;
        this.dataCriacao = dataCriacao;
    }
}
