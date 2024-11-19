package gsw_api.gsw_api.service;

import gsw_api.gsw_api.dao.ApiRepository;
import gsw_api.gsw_api.dto.DadosApi;
import gsw_api.gsw_api.model.Api;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApiService {

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    public List<Api> getAllApis() {
        return apiRepository.findAll();
    }

    public Optional<Api> getApiById(Long id) {
        return apiRepository.findById(id);
    }

    private boolean APIsDuplicados(String nome, String url) {
        return false;
    }

    @Transactional
    public Api create(DadosApi dadosApi) {
        if (APIsDuplicados(dadosApi.nome(), dadosApi.url())) {
            throw new IllegalArgumentException("API já cadastrado com este nome ou URL.");
        }

        Api api = new Api(dadosApi.nome(), dadosApi.url(), dadosApi.chaveAcesso(), dadosApi.payload(), dadosApi.tipo(), dadosApi.periodicidade());
        return apiRepository.save(api);
    }

    public void deleteApi(Long id) {
        Api api = apiRepository.findById(id).orElseThrow(() -> new RuntimeException("API not found"));
        apiRepository.delete(api);
    }

    public Api updateApi(Long id, Api apiDetails) {
        Api api = apiRepository.findById(id).orElseThrow(() -> new RuntimeException("API not found"));
        api.setNome(apiDetails.getNome());
        api.setPayload(apiDetails.getPayload());
        api.setChaveAcesso(apiDetails.getChaveAcesso());
        api.setUrl(apiDetails.getUrl());
        api.setTipo(apiDetails.getTipo());
        api.setPeriodicidade(apiDetails.getPeriodicidade());
        return apiRepository.save(api);
    }

    public Page<Api> filterApis(String nome, String url, String chaveAcesso, String payload, String tipo, String periodicidade, Pageable pageable) {
        return apiRepository.findAll(createSpecification(nome, url, chaveAcesso, payload, tipo, periodicidade), pageable);
    }

    private Specification<Api> createSpecification(String nome, String url, String chaveAcesso, String payload, String tipo, String periodicidade) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (nome != null && !nome.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
            }

            if (url != null && !url.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("url"), url));
            }

            if (chaveAcesso != null && !chaveAcesso.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("chaveAcesso"), chaveAcesso));
            }

            if (payload != null && !payload.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("payload"), payload));
            }

            if (tipo != null && !tipo.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("tipo"), tipo));
            }

            if (periodicidade != null && !periodicidade.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("periodicidade"), periodicidade));
            }

            return predicate;
        };
    }
}
