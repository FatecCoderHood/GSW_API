package gsw_api.gsw_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gsw_api.gsw_api.dao.NoticiaRepository;
import gsw_api.gsw_api.dao.PortalNoticiaRepository;
import gsw_api.gsw_api.dao.TagRepository;
import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.model.PortalNoticia;
import gsw_api.gsw_api.service.PortalNoticiaService;
import gsw_api.gsw_api.service.WebScrapingService;
import gsw_api.gsw_api.service.NoticiaService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/web_scrap")
@CrossOrigin(origins = "http://localhost:3000")
public class WebScrapingController {

    @Autowired
    private NoticiaService noticiaService;
    @Autowired
    private PortalNoticiaService portalNoticiaService;
    @Autowired
    private NoticiaRepository noticiaRepository;
    private WebScrapingService webScrapingService;
    @Autowired
    private TagRepository tagRepository;
    

    @Operation(summary = "Webscraping de notícias de todos os postais")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of Noticias retrieved", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class)))
    })
    @GetMapping
    public ResponseEntity<List<Noticia>> webScraping() throws IOException {

        webScrapingService = new WebScrapingService();
        noticiaService = new NoticiaService();

        //Vetor com todos os portais do banco
        List<PortalNoticia> portais = portalNoticiaService.findAll();
        //Vetor das noticias a serem inseridas no banco
        List<Noticia> noticias = new ArrayList<>();

        webScrapingService.tagList = tagRepository.findAll();

        //Rotina que retorna o vetor de noticias gerados via webscraping
        noticias = webScrapingService.gerarNoticiasByPortais(portais);

        return ResponseEntity.ok(noticiaRepository.saveAll(noticias));
    }

}
