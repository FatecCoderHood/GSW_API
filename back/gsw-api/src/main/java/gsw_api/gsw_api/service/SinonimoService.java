package gsw_api.gsw_api.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SinonimoService {

    private final RestTemplate restTemplate;

    @Autowired
    public SinonimoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> buscarSinonimos(String termo) {
        String url = "https://api.datamuse.com/synonyms?word=" + termo;
        String[] sinonimos = restTemplate.getForObject(url, String[].class);
        return sinonimos != null ? Arrays.asList(sinonimos) : Collections.emptyList();
    }
}
