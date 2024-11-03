package gsw_api.gsw_api.dao;

import gsw_api.gsw_api.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long>, JpaSpecificationExecutor<Noticia> {
    List<Noticia> findByTituloContaining(String titulo);
    List<Noticia> findByDataPublicacaoBetween(LocalDate startDate, LocalDate endDate);
    List<Noticia> findByTags_NomeIn(List<String> nomesTags);
}

