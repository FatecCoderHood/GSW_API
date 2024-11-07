package gsw_api.gsw_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gsw_api.gsw_api.dto.SinonimoResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SinonimoService {

    private final RestTemplate restTemplate;

    @Autowired
    public SinonimoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> buscarSinonimos(String termo) {
        String url = "https://api.datamuse.com/words?rel_syn=" + termo;
        SinonimoResponse[] resposta = restTemplate.getForObject(url, SinonimoResponse[].class);
        
        // Se houver sinônimos, retorna a lista de sinônimos. Se não, retorna o próprio termo ou uma mensagem padrão.
        if (resposta != null && resposta.length > 0) {
            return Arrays.stream(resposta)
                         .map(SinonimoResponse::getWord)
                         .collect(Collectors.toList());
        } else {
            // Caso não haja sinônimos, retornamos o próprio termo ou uma lista vazia
            return Collections.singletonList(termo);  // ou Collections.emptyList();
        }
    }
}
