package gsw_api.gsw_api.controller;

import gsw_api.gsw_api.dto.DadosNoticia;
import gsw_api.gsw_api.dto.DadosTag;
import gsw_api.gsw_api.dto.FiltroNoticia;
import gsw_api.gsw_api.model.Noticia;
import gsw_api.gsw_api.service.NoticiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @Operation(summary = "Create a new Noticia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Noticia created successfully", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Noticia> create(@RequestBody DadosNoticia dadosNoticia) {
        Noticia noticia = noticiaService.create(dadosNoticia);
        return ResponseEntity.ok(noticia);
    }

    @Operation(summary = "Find Noticia by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Noticia found", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class))),
        @ApiResponse(responseCode = "404", description = "Noticia not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Noticia> findById(@PathVariable Long id) {
        return noticiaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get a list of all Noticias")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of Noticias retrieved", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class)))
    })
    @GetMapping
    public ResponseEntity<List<Noticia>> findAll() {
        List<Noticia> noticias = noticiaService.findAll();
        return ResponseEntity.ok(noticias);
    }

    @Operation(summary = "Update an existing Noticia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Noticia updated successfully", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Noticia.class))),
        @ApiResponse(responseCode = "404", description = "Noticia not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Noticia> update(@PathVariable Long id, @RequestBody DadosNoticia dadosNoticia) {
        Noticia noticia = noticiaService.update(id, dadosNoticia);
        return noticia != null ? ResponseEntity.ok(noticia) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a Noticia by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Noticia deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Noticia not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticiaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filter Noticias based on criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Filtered Noticias retrieved", 
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
        return ResponseEntity.ok(/*noticiaService.unassociateTags(noticiaId, tagId)*/).build();
    }
}
