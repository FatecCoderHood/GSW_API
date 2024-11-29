package gsw_api.gsw_api.controller;

import gsw_api.gsw_api.dto.DadosNoticia;
import gsw_api.gsw_api.dto.DadosTag;
import gsw_api.gsw_api.dto.FiltroNoticia;
import gsw_api.gsw_api.model.Api;
import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.dao.ApiRepository;
import gsw_api.gsw_api.service.NoticiaService;
import gsw_api.gsw_api.service.ApiScrapingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @Autowired
    private ApiScrapingService apiScrapingService;

    @Autowired
    private ApiRepository apiRepository;

    @Operation(summary = "Criar uma nova Notícia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícia criada com sucesso", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class))),
        @ApiResponse(responseCode = "400", description = "Entrada inválida", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Noticia> create(@RequestBody DadosNoticia dadosNoticia) {
        Noticia noticia = noticiaService.create(dadosNoticia);
        return ResponseEntity.ok(noticia);
    }

    @Operation(summary = "Encontrar Notícia por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícia encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class))),
        @ApiResponse(responseCode = "404", description = "Notícia não encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Noticia> findById(@PathVariable Long id) {
        return noticiaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obter uma lista de todas as Notícias")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de Notícias recuperada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class)))
    })
    @GetMapping
    public ResponseEntity<List<Noticia>> findAll() {
        List<Noticia> noticias = noticiaService.findAll();
        return ResponseEntity.ok(noticias);
    }

    @Operation(summary = "Atualizar uma Notícia existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícia atualizada com sucesso", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class))),
        @ApiResponse(responseCode = "404", description = "Notícia não encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Noticia> update(@PathVariable Long id, @RequestBody DadosNoticia dadosNoticia) {
        Noticia noticia = noticiaService.update(id, dadosNoticia);
        return noticia != null ? ResponseEntity.ok(noticia) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Deletar uma Notícia por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notícia deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notícia não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticiaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar Notícias com base em critérios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícias filtradas recuperadas", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class)))
    })
    @PostMapping("/filtrar")
    public ResponseEntity<List<Noticia>> filtrarNoticias(@RequestBody FiltroNoticia filtro) {
        List<Noticia> noticiasFiltradas = noticiaService.filtrarNoticias(filtro);
        return ResponseEntity.ok(noticiasFiltradas);
    }

    @Operation(summary = "Vincular Tags à Notícia")
    @PostMapping("/vincularTags")
    public ResponseEntity<List<DadosTag>> vincularTags(@RequestParam Long noticiaId, @RequestBody List<String> tags)
    {
        //TODO: Melhorar erros nesta rota
        return ResponseEntity.ok(noticiaService.associateTags(noticiaId, tags));
    }

    @Operation(summary = "Desvincular Tag da Notícia")
    @DeleteMapping("/{noticiaId}/{tagId}")
    public ResponseEntity<Boolean> desvincularTag(@PathVariable Long noticiaId, @PathVariable Long tagId)
    {
        //TODO: Melhorar erros nesta rota
        return ResponseEntity.ok(noticiaService.unassociateTags(noticiaId, tagId));
    }

    @Operation(summary = "Obter todas as Notícias, incluindo aquelas de APIs externas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todas as Notícias recuperadas", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class)))
    })
    @GetMapping("/todas")
    public ResponseEntity<List<Noticia>> getTodasAsNoticias() {
        return ResponseEntity.ok(new ArrayList<>(noticiaService.findAll()));
    }
}
