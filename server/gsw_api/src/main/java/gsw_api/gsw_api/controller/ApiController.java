package gsw_api.gsw_api.controller;

import gsw_api.gsw_api.dao.ApiRepository;
import gsw_api.gsw_api.model.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiRepository apiRepository;


    @PostMapping
    public Api createApi(@RequestBody Api api) {
        return apiRepository.save(api);
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

}
