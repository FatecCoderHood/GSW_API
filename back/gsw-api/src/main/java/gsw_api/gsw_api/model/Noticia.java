package gsw_api.gsw_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@Entity
@Table(name = "tb_noticia")
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "conteudo", nullable = false, columnDefinition="LONGTEXT")
    private String conteudo;

    @Column(name = "dta_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "autor")
    private String autor;

    @ManyToOne
    @JoinColumn(name = "api_id", nullable = true)
    @JsonBackReference
    private Api api;

    @ManyToMany
    @JoinTable(
            name = "tb_noticia_tag",
            joinColumns = @JoinColumn(name = "noticia_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    
    @JsonManagedReference
    private Set<Tag> tags = new HashSet<>();


    public Noticia() {

    }

}
