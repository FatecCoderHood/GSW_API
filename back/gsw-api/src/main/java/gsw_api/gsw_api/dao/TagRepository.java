package gsw_api.gsw_api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gsw_api.gsw_api.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByNome(String nome);

    boolean existsByNome(String nome);
}