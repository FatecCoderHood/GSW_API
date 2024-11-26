package gsw_api.gsw_api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import gsw_api.gsw_api.dao.NoticiaRepository;
import gsw_api.gsw_api.model.Api;
import gsw_api.gsw_api.model.Noticia;

@Component
public class SchedulerApiService {

    private static final String expressaoDiaria = "0 0 0 * * *"; // Executa diariamente à meia-noite
    private static final String expressaoSemanal = "0 0 0 * * 0"; // Executa semanalmente aos domingos

    @Autowired
    private ApiScrapingService apiScrapingService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private NoticiaRepository noticiaRepository;

    // @Scheduled(fixedRate = 5000) //Executa a cada 5 segundos
    @Scheduled(cron = expressaoDiaria)
    public void executarScrapingDiario() throws IOException {
        realizarScrapingApis("Diário");
    }

    // @Scheduled(fixedRate = 10000) //Executa a cada 10 segundos
    @Scheduled(cron = expressaoSemanal)
    public void executarScrapingSemanal() throws IOException {
        realizarScrapingApis("Semanal");
    }

    private void realizarScrapingApis(String periodicidade) throws IOException {
        // Recuperar as APIs com a periodicidade especificada
        List<Api> apis = apiService.findAllByPeriodicidade(periodicidade);

        if (apis.isEmpty()) {
            System.out.println("Nenhuma API configurada para a periodicidade: " + periodicidade);
            return;
        }

        // Coletar notícias de cada API
        List<Noticia> todasNoticias = new ArrayList<>();
        for (Api api : apis) {
            todasNoticias.addAll(apiScrapingService.scrapeFromApi(api));
        }

        // Salvar as notícias no banco de dados
        noticiaRepository.saveAll(todasNoticias);

        System.out.println("Scraping concluído para periodicidade: " + periodicidade);
    }
}
