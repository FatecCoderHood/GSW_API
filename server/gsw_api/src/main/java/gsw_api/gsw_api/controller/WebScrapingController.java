package gsw_api.gsw_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.model.PortalNoticia;
import gsw_api.gsw_api.service.PortalNoticiaService;
import gsw_api.gsw_api.service.WebScrapingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/web_scrap")
@CrossOrigin(origins = "http://localhost:3000")
public class WebScrapingController {

    @Autowired
    private PortalNoticiaService portalNoticiaService;
    private WebScrapingService webScrapingService;

    @Operation(summary = "Get a list of all Noticias")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of Noticias retrieved", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class)))
    })
    @GetMapping
    public ResponseEntity<List<Noticia>> findAll() throws IOException {
        Optional<PortalNoticia> optionalPortalNoticia = portalNoticiaService.findById(Long.valueOf(3));
        List<Noticia> noticias = new ArrayList<>();

        if (optionalPortalNoticia.isPresent()) {
            PortalNoticia portalNoticia = optionalPortalNoticia.get();
            webScrapingService = new WebScrapingService();
            noticias = webScrapingService.ParametrizacaoToNoticia(portalNoticia);
        }
        return ResponseEntity.ok(noticias);
    }

}
