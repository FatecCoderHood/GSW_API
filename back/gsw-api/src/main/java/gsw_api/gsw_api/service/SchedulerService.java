package gsw_api.gsw_api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import gsw_api.gsw_api.service.WebScrapingService;
import gsw_api.gsw_api.dao.NoticiaRepository;
import gsw_api.gsw_api.dao.PortalNoticiaRepository;
import gsw_api.gsw_api.dao.TagRepository;
import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.model.PortalNoticia;
import gsw_api.gsw_api.service.NoticiaService;

@Component
public class SchedulerService {

    private static final String expressaoDiaria =  "0 0 0 * * *";
    private static final String expressaoSemanal =  "0 0 0 * * 0";
    private static final String expressaoQuinzenal =  "0 0 0 1,15 * ?";
    private static final String expressaoMensal =  "0 0 0 1 * *";

    @Autowired
    private NoticiaService noticiaService;
    @Autowired
    private PortalNoticiaService portalNoticiaService;
    @Autowired
    private NoticiaRepository noticiaRepository;
    private WebScrapingService webScrapingService;
    @Autowired
    private TagRepository tagRepository;

    // @Scheduled(fixedRate = 5000) //Executa a cada 5 segundos
    @Scheduled(cron = expressaoDiaria)
    public void SchDiario() throws IOException {

        webScrapingGenerico("Diário");
    }

    // @Scheduled(fixedRate = 10000) //Executa a cada 10 segundos
    @Scheduled(cron = expressaoSemanal)
    public void SchSemanal() throws IOException {

        webScrapingGenerico("Semanal");
    }

    // @Scheduled(fixedRate = 20000)
    @Scheduled(cron = expressaoQuinzenal)
    public void SchQuinzenal() throws IOException {

        webScrapingGenerico("Quinzenal");
    }

    // @Scheduled(fixedRate = 30000)
    @Scheduled(cron = expressaoMensal)
    public void SchMensal() throws IOException {

        webScrapingGenerico("Mensal");
    }

    private ResponseEntity<List<Noticia>> webScrapingGenerico(String periodicidade) throws IOException {

        webScrapingService = new WebScrapingService();
        noticiaService = new NoticiaService();

        //Vetor com todos os portais do banco
        List<PortalNoticia> portais = portalNoticiaService.findAllByPeriodicidade(periodicidade);
        //Vetor das noticias a serem inseridas no banco
        List<Noticia> noticias = new ArrayList<>();

        webScrapingService.tagList = tagRepository.findAll();

        //Rotina que retorna o vetor de noticias gerados via webscraping
        noticias = webScrapingService.gerarNoticiasByPortais(portais);

        return ResponseEntity.ok(noticiaRepository.saveAll(noticias)) ;
    }
}
