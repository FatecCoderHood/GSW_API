package gsw_api.gsw_api.service;

import gsw_api.gsw_api.dao.ApiRepository;
import gsw_api.gsw_api.dao.NoticiaRepository; // Importe o repositório de Noticia
import gsw_api.gsw_api.model.Api;
import gsw_api.gsw_api.model.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApiScrapingService {

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private NoticiaRepository noticiaRepository; 

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Noticia> scrapeFromApi(Api api) {
        List<Noticia> noticias = new ArrayList<>();
    
        // Fazendo a requisição à API externa
        List<Map<String, Object>> response = restTemplate.getForObject(api.getUrl(), List.class);
    
        if (response != null) {
            for (Map<String, Object> item : response) {
                Noticia noticia = new Noticia();
    
                // Verifica se os campos necessários estão presentes
                if (item.containsKey("setup") && item.containsKey("punchline")) {
                    noticia.setTitulo((String) item.get("setup"));
                    noticia.setConteudo((String) item.get("punchline"));
                    noticia.setAutor("Da API: " + api.getNome());
    
                    // Associar a API à notícia
                    noticia.setApi(api);
                    
                    // Preencher a coluna fonte com "API"
                    noticia.setFonte("API");  // Agora a fonte será preenchida corretamente
    
                    // Salvar a notícia
                    noticiaRepository.save(noticia);
                } else {
                    System.err.println("Campo esperado não encontrado no item: " + item);
                }
    
                noticias.add(noticia);
            }
        }
    
        return noticias;
    }
}
