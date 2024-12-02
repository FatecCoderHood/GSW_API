package gsw_api.gsw_api.dao;

import gsw_api.gsw_api.model.Tag;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {
    
    boolean existsByNome(String nome);
    Optional<Tag> findByNome(String nome);
    List<Tag> findByNomeIn(List<String> nomes);

    List<Tag> findBySinonimo1(String sinonimo1);
    List<Tag> findBySinonimo2(String sinonimo2);
    List<Tag> findByNomeOrSinonimo1OrSinonimo2(String nome, String sinonimo1, String sinonimo2);
}
