package gsw_api.gsw_api.service;

import gsw_api.gsw_api.dao.ApiRepository;
import gsw_api.gsw_api.dao.NoticiaRepository;
import gsw_api.gsw_api.model.Api;
import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.model.Parametrizacao;
import gsw_api.gsw_api.model.Tag;

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
    private Parametrizacao parametrizacao;
    public List<Tag> tagList = new ArrayList<>();

    public Parametrizacao getParametrizacaoByJSON(String JSON) {
        parametrizacao = new Parametrizacao(JSON);
        return parametrizacao;
    }

    public List<Noticia> scrapeFromApi(Api api) {
        List<Noticia> noticias = new ArrayList<>();
    
        // Fazendo a requisição à API externa
        List<Map<String, Object>> response = restTemplate.getForObject(api.getUrl(), List.class);

        Parametrizacao parametrizacao = getParametrizacaoByJSON(api.getPayload());
    
        if (response != null) {
            for (Map<String, Object> item : response) {
                Noticia noticia = new Noticia();
    
                // Verifica se os campos necessários estão presentes
                if (item.containsKey(parametrizacao.getTitulo()) && item.containsKey(parametrizacao.getConteudo())) {
                    noticia.setTitulo((String) item.get(parametrizacao.getTitulo()));
                    noticia.setConteudo((String) item.get(parametrizacao.getConteudo()));
                    noticia.setAutor("Da API: " + api.getNome());
    
                    // Associar a API à notícia
                    noticia.setApi(api);
                    
                    // Preencher a coluna fonte com "API"
                    noticia.setFonte("API");  // Agora a fonte será preenchida corretamente
    
                    noticia.getTags().addAll(AssociarTagsScraping(noticia.getConteudo()));
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

    private List<Tag> AssociarTagsScraping(String conteudo){     
        List<Tag> newTags = new ArrayList<>();

        for (Tag tag : tagList)
        {
            if (conteudo.contains(tag.getNome())) {
                newTags.add(tag);
            }
        }

        return newTags;
    }
}
