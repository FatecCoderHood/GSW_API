package gsw_api.gsw_api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gsw_api.gsw_api.model.Tag;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag>
{
    boolean existsByNome(String nome);
    Optional<Tag> findByNome(String nome);
}
