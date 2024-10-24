package gsw_api.gsw_api.service;

import gsw_api.gsw_api.dao.ApiRepository;
import gsw_api.gsw_api.dto.DadosApi;
import gsw_api.gsw_api.dto.DadosPortalNoticia;
import gsw_api.gsw_api.model.Api;
import gsw_api.gsw_api.model.PortalNoticia;

import org.springframework.beans.factory.annotation.Autowired;
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
        // Optional<Api> existingApi = apiRepository. findByNomeOrUrl(nome, url);
        return false; //existingApi.isPresent();
    }

    @Transactional
    public Api create(DadosApi dadosApi) {
        if (APIsDuplicados(dadosApi.nome(), dadosApi.url())) {
            throw new IllegalArgumentException("API já cadastrado com este nome ou URL.");
        }
        
        // String nome, String url, String chaveAcesso, String payload
        Api api = new Api(dadosApi.nome(), dadosApi.url(), dadosApi.chaveAcesso(), dadosApi.payload());
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
        return apiRepository.save(api);
    }
}
