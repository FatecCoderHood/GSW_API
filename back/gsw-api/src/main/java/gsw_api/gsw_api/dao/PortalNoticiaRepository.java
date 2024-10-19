package gsw_api.gsw_api.dao;

import gsw_api.gsw_api.model.PortalNoticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PortalNoticiaRepository extends JpaRepository<PortalNoticia, Long> {
    Optional<PortalNoticia> findByNomeOrUrl(String nome, String url);
}