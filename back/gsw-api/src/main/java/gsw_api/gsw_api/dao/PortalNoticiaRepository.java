package gsw_api.gsw_api.dao;

import gsw_api.gsw_api.model.PortalNoticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PortalNoticiaRepository extends JpaRepository<PortalNoticia, Long> {

    // Alterado para buscar por nome, url e periodicidade de uma vez
    Optional<PortalNoticia> findByNomeAndUrlAndPeriodicidade(String nome, String url, String periodicidade);
}
