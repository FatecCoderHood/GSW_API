package gsw_api.gsw_api.dao;

import gsw_api.gsw_api.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long>, JpaSpecificationExecutor<Noticia> {
    List<Noticia> findByTituloContaining(String titulo);
    List<Noticia> findByDataPublicacaoBetween(LocalDate startDate, LocalDate endDate);
    List<Noticia> findByTags_NomeIn(List<String> nomesTags);

    // Método para buscar por título e conteúdo
    Optional<Noticia> findByTituloAndConteudo(String titulo, String conteudo);

    @Modifying
    @Query(value = "select * from tb_noticia tb order by id desc limit 100", nativeQuery = true)
    List<Noticia> findAllLimit100();
}

