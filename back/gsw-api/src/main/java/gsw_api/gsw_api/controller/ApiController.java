package gsw_api.gsw_api.controller;

import gsw_api.gsw_api.dao.ApiRepository;
import gsw_api.gsw_api.dto.DadosApi;
import gsw_api.gsw_api.model.Api;
import gsw_api.gsw_api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    @Autowired
    private ApiRepository apiRepository;
    @Autowired
    private ApiService apiService;


    @PostMapping
    public ResponseEntity<Api> createApi(@RequestBody DadosApi dados) {
        Api api = apiService.create(dados);
        return ResponseEntity.ok(api);
    }


    @GetMapping
    public List<Api> getAllApis() {
        return apiRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Api> getApiById(@PathVariable Long id) {
        Optional<Api> api = apiRepository.findById(id);
        return api.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Api> updateApi(@PathVariable Long id, @RequestBody Api apiDetails) {
        Optional<Api> optionalApi = apiRepository.findById(id);

        if (!optionalApi.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Api api = optionalApi.get();
        api.setNome(apiDetails.getNome());
        api.setPayload(apiDetails.getPayload());
        api.setChaveAcesso(apiDetails.getChaveAcesso());
        api.setUrl(apiDetails.getUrl());

        Api updatedApi = apiRepository.save(api);
        return ResponseEntity.ok(updatedApi);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApi(@PathVariable Long id) {
        if (!apiRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        apiRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Api>> getAllApis(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) String chaveAcesso,
            @RequestParam(required = false) String payload,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("nome"));
        Page<Api> result = apiService.filterApis(nome, url, chaveAcesso, payload, pageable);

        return ResponseEntity.ok(result);
    }
}
