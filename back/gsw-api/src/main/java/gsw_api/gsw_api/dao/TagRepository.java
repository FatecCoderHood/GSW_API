package gsw_api.gsw_api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gsw_api.gsw_api.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>
{
    Optional<Tag> findByNome(String nome);

    boolean existsByNome(String nome);
}