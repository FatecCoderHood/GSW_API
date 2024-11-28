package gsw_api.gsw_api.dao;

import gsw_api.gsw_api.model.Api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends JpaRepository<Api, Long>, JpaSpecificationExecutor<Api> {
    // Método para buscar APIs pela periodicidade
    List<Api> findByPeriodicidade(String periodicidade);
}