package gsw_api.gsw_api.dao;

import gsw_api.gsw_api.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {
    boolean existsByNome(String nome);
    Optional<Tag> findByNome(String nome);
    
    List<Tag> findByNomeIn(List<String> nomes);
}
